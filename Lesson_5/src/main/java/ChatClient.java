
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class ChatClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        System.out.println("Введите ваш логин: ");
        Scanner console = new Scanner(System.in);
        String clientLogin = console.nextLine();

        try (Socket server = new Socket("localhost", 8888)) {

            try (PrintWriter out = new PrintWriter(server.getOutputStream(), true)) {
                Scanner in = new Scanner(server.getInputStream());

                String loginRequest = createLoginRequest(clientLogin);
                out.println(loginRequest);

                String loginResponseString = in.nextLine();
                if (!checkLoginResponse(loginResponseString)) {
                    System.out.println("Не удалось подключиться к серверу");
                    return;
                } else {
                    System.out.println("Вы успешно подключились к серверу, ваш логин " + clientLogin);
                }

                new Thread(() -> {
                    while (true) {
                        String msgFromServer = in.nextLine();
                        if (msgFromServer.equals("disconnect")) {
                            System.out.println("Отключились от сервера");
                            in.close();
                            break;
                        } else {
                            System.out.println(msgFromServer);
                        }
                    }
                }).start();

                while (true) {
                    System.out.println("Что хочу сделать?");
                    System.out.println("1. Послать сообщение другу");
                    System.out.println("2. Послать сообщение всем");
                    System.out.println("3. Получить список логинов");
                    System.out.println("4. Отключиться");

                    String type = console.nextLine();
                    if (type.equals("1")) {
                        SendMessageRequest request = new SendMessageRequest();
                        System.out.println("Введите сообщение: ");
                        request.setMessage(console.nextLine());
                        System.out.println("Какому пользователю отправить сообщение: ");
                        request.setRecipient(console.nextLine());
                        String sendMsgRequest = objectMapper.writeValueAsString(request);
                        out.println(sendMsgRequest);
                    } else if (type.equals("2")) {
                        BroadcastMessageRequest request = new BroadcastMessageRequest();
                        System.out.println("Введите сообщение: ");
                        request.setMessage(console.nextLine());
                        String sendMsgRequest = objectMapper.writeValueAsString(request);
                        out.println(sendMsgRequest);
                    } else if (type.equals("3")) {
                        UsersRequest request = new UsersRequest();
                        String sendMsgRequest = objectMapper.writeValueAsString(request);
                        out.println(sendMsgRequest);
                    } else if (type.equals("4")) {
                        DisconnectRequest request = new DisconnectRequest();
                        String sendMsgRequest = objectMapper.writeValueAsString(request);
                        out.println(sendMsgRequest);
                        Thread.sleep(200);
                        return;
                    }
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            System.err.println("Ошибка во время подключения к серверу: " + e.getMessage());
        }
    }

    private static String createLoginRequest(String login) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(login);

        try {
            return objectMapper.writeValueAsString(loginRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка JSON: " + e.getMessage());
        }
    }

    private static boolean checkLoginResponse(String loginResponse) {
        try {
            LoginResponse resp = objectMapper.reader().readValue(loginResponse, LoginResponse.class);
            return resp.isConnected();
        } catch (IOException e) {
            System.err.println("Ошибка чтения JSON: " + e.getMessage());
            return false;
        }
    }
}
