import model.Department;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Db {

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:test");
        } catch (SQLException e) {
            System.err.println("Во время подключения произошла ошибка: " + e.getMessage());
        }
    }

    public static <T> List<T> getAll(Class<T> tClass) {
        List<T> list = null;

        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM " + tClass.getSimpleName();
            ResultSet resultSet = statement.executeQuery(sql);
            list = new ArrayList<>();
            serializeObj(tClass, resultSet, list);
        } catch (Exception e) {
            System.err.println("Не удалось выполнить запрос " + e.getMessage());
        }
        return list;
    }

    public static <T> void serializeObj(Class<T> tClass, ResultSet resultSet, List<T> list) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        while (resultSet.next()) {
            Constructor<T> constructor = tClass.getDeclaredConstructor();
            T obj = constructor.newInstance();
            for (Method method : obj.getClass().getDeclaredMethods()) {
                String name = method.getName();
                if (name.startsWith("set")) {
                    String fieldName = getFieldName(name);
                    Method set;
                    Field field = obj.getClass().getDeclaredField(fieldName);
                    Class<?> fieldType = field.getType();
                    if (int.class.equals(fieldType)) {
                        set = obj.getClass().getMethod(name, int.class);
                        set.invoke(obj, resultSet.getInt(fieldName));
                    } else if (String.class.equals(fieldType)) {
                        set = obj.getClass().getMethod(name, String.class);
                        set.invoke(obj, resultSet.getString(fieldName));
                    } else if (long.class.equals(fieldType)) {
                        set = obj.getClass().getMethod(name, long.class);
                        set.invoke(obj, resultSet.getLong(fieldName));
                    } else if (boolean.class.equals(fieldType)) {
                        set = obj.getClass().getMethod(name, boolean.class);
                        set.invoke(obj, resultSet.getBoolean(fieldName));
                    }
                }
            }
            list.add(obj);
        }
    }

    private static String getFieldName(String name) {
        String s0 = name.substring("set".length());
        String s1 = s0.substring(0, 1).toLowerCase();
        return s1 + s0.substring(1);
    }

    public static void createTable(String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    public static void completeQuery(String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int insertCount = statement.executeUpdate(sql);
            System.out.println("Затронуто строк: " + insertCount);
        }
    }


    public static String getDepartmentNameByPersonId(long id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement("""
                                         SELECT d.id, d.name 
                                         FROM department d 
                                         JOIN person p ON p.departmentId = d.id
                                         WHERE p.id = ?
                             """)) {
            List<Department> list = new ArrayList<>();
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            serializeObj(Department.class, resultSet, list);
            return list.get(0).getName();

        } catch (Exception e) {
            System.err.println("Не удалось выполнить запрос " + e.getMessage());
        }
        return null;
    }

    public static Map<String, String> getPersonDepartments() throws SQLException {
        Map<String, String> map = new HashMap<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                                SELECT p.name p_name, d.name  d_name
                                FROM department d 
                                JOIN person p ON p.departmentId = d.id
                    """);
            while (resultSet.next()) {
                String personName = resultSet.getString("p_name");
                String departmentName = resultSet.getString("d_name");
                map.put(personName, departmentName);
            }
        }
        return map;
    }

    public static Map<String, List<String>> getDepartmentPersons() throws SQLException {
        Map<String, List<String>> map = new HashMap<>();
        List<Department> departments = getAll(Department.class);
        departments.forEach(d -> {
            try (Statement statement = connection.createStatement()) {
                List<String> people = new ArrayList<>();
                ResultSet resultSet = statement.executeQuery("""
                        SELECT p.name
                        FROM person p
                        WHERE p.departmentId = """ + d.getId());
                while (resultSet.next()) {
                    String personName = resultSet.getString("name");
                    people.add(personName);
                }
                map.put(d.getName(), people);
            } catch (SQLException e) {
                System.err.println("Не удалось выполнить запрос " + e.getMessage());
            }
        });
        return map;
    }

}
