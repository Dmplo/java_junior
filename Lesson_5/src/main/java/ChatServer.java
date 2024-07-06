
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
        try (ServerSocket server = new ServerSocket(8888)) {
            System.out.println("Сервер запущен");
            System.out.println("Ждем клиентского подключения");

            while (true) {
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client, clients);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка во время работы сервера: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket client;
        private final Scanner in;
        private final PrintWriter out;
        private final Map<String, ClientHandler> clients;
        private String clientLogin;

        public ClientHandler(Socket client, Map<String, ClientHandler> clients) throws IOException {
            this.client = client;
            this.clients = clients;

            this.in = new Scanner(client.getInputStream());
            this.out = new PrintWriter(client.getOutputStream(), true);
        }

        @Override
        public void run() {

            try {
                String loginRequest = in.nextLine();
                LoginRequest request = objectMapper.reader().readValue(loginRequest, LoginRequest.class);
                this.clientLogin = request.getLogin();
                System.out.println("Подключен новый клиент " + clientLogin);
            } catch (IOException e) {
                System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                String unsuccessfulResponse = createLoginResponse(false);
                out.println(unsuccessfulResponse);
                doClose();
                return;
            }

            if (clients.containsKey(clientLogin)) {
                String unsuccessfulResponse = createLoginResponse(false);
                out.println(unsuccessfulResponse);
                doClose();
                return;
            }

            clients.put(clientLogin, this);
            String successfulLoginResponse = createLoginResponse(true);
            out.println(successfulLoginResponse);
            if (clients.size() > 1) {
                findUsers("Я зашел в чат!");
            }

            while (true) {
                String msgFromClient = in.nextLine();
                System.out.println("Запрос от клиента: " + clientLogin);

                final String type;
                try {
                    AbstractRequest request = objectMapper.reader().readValue(msgFromClient, AbstractRequest.class);
                    type = request.getType();
                } catch (IOException e) {
                    System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                    sendMessage("Сервер: не удалось прочитать сообщение: " + e.getMessage());
                    continue;
                }

                if (SendMessageRequest.TYPE.equals(type)) {
                    final SendMessageRequest request;
                    try {
                        request = objectMapper.reader().readValue(msgFromClient, SendMessageRequest.class);
                    } catch (IOException e) {
                        System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                        sendMessage("Сервер: не удалось прочитать сообщение SendMessageRequest: " + e.getMessage());
                        continue;
                    }

                    ClientHandler clientTo = clients.get(request.getRecipient());
                    if (clientTo == null) {
                        sendMessage("Сервер: клиент с логином [" + request.getRecipient() + "] не найден");
                        continue;
                    } else if (Objects.equals(clientTo.clientLogin, clientLogin)) {
                        sendMessage("Сервер: нельзя отправлять сообщения самому себе!");
                        continue;
                    }
                    clientTo.sendMessage(clientLogin + ": " + request.getMessage());
                } else if (BroadcastMessageRequest.TYPE.equals(type)) {
                    final BroadcastMessageRequest request;
                    try {
                        request = objectMapper.reader().readValue(msgFromClient, BroadcastMessageRequest.class);
                    } catch (IOException e) {
                        System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                        sendMessage("Сервер: не удалось прочитать сообщение SendMessageRequest: " + e.getMessage());
                        continue;
                    }

                    if (clients.size() == 1) {
                        sendMessage("Сервер: в чате кроме вас нет пользователей!");
                        continue;
                    }
                    findUsers(request.getMessage());
                } else if (UsersRequest.TYPE.equals(type)) {
                    sendMessage("Сервер: " + getUsers());
                } else if (DisconnectRequest.TYPE.equals(type)) {
                    sendMessage("disconnect");
                    findUsers("Я отключился!");
                    System.out.println(clientLogin + " отключился от сервера");
                    clients.remove(clientLogin);
                    break;
                } else {
                    System.err.println("Неизвестный тип сообщения: " + type);
                    sendMessage("Сервер: неизвестный тип сообщения: " + type);
                }
            }
            doClose();
        }

        private void doClose() {
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                System.err.println("Ошибка во время отключения клиента: " + e.getMessage());
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        private String createLoginResponse(boolean success) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setConnected(success);
            try {
                return objectMapper.writer().writeValueAsString(loginResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Не удалось создать loginResponse: " + e.getMessage());
            }
        }

        private String getUsers() {
            List<String> logins = new ArrayList<>(clients.keySet());

            return logins.stream().map(x -> {
                if (x.equals(clientLogin)) {
                    x = x + "(Вы)";
                }
                return x;
            }).toList().toString();
        }

        private void findUsers(String message) {
            for (Map.Entry<String, ClientHandler> stringClientHandlerEntry : clients.entrySet()) {
                if (!Objects.equals(stringClientHandlerEntry.getKey(), clientLogin)) {
                    stringClientHandlerEntry.getValue().sendMessage(clientLogin + ": " + message);
                }
            }
        }
    }
}
