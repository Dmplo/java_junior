import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Homework {

    public static void main(String[] args) {
        List<String> departmentNames = List.of("Sale", "Production", "Purchase", "Quality", "Engineering");
        try {

            Db.createTable("""
                    create table department (
                      id bigint primary key,
                      name varchar(128) not null
                    )
                    """);

            Db.createTable("""
                    create table person (
                      id bigint primary key,
                      departmentId bigint,
                      name varchar(256),
                      age integer,
                      active boolean
                    )
                    """);


            Db.completeQuery(createQueryDepartment(departmentNames));
            Db.completeQuery(createQueryPerson(departmentNames));

            System.out.println("task_4");
            System.out.println(Db.getDepartmentNameByPersonId(1L));
            System.out.println("task_5");
            System.out.println(Db.getPersonDepartments());
            System.out.println("task_6");
            System.out.println(Db.getDepartmentPersons());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }


    }

    private static String createQueryDepartment(List<String> departmentNames) {
        StringBuilder insertQuery = new StringBuilder("insert into department (id, name) values\n");
        int j = 1;
        for (int i = 0; i < departmentNames.size(); i++) {
            insertQuery.append(String.format("(%s, '%s')", j++, departmentNames.get(i)));
            if (i != departmentNames.size() - 1) {
                insertQuery.append(",\n");
            }
        }
        return insertQuery.toString();
    }
    private static String createQueryPerson(List<String> departmentNames) {
        StringBuilder insertQuery = new StringBuilder("insert into person(id, departmentId, name, age, active) values\n");
        for (int i = 1; i <= 10; i++) {
            int departmentId = ThreadLocalRandom.current().nextInt(1, departmentNames.size() + 1);
            int age = ThreadLocalRandom.current().nextInt(20, 60);
            boolean active = ThreadLocalRandom.current().nextBoolean();
            insertQuery.append(String.format("(%s, %s, '%s', %s, %s)", i, departmentId, "Person #" + i, age, active));
            if (i != 10) {
                insertQuery.append(",\n");
            }
        }
        return insertQuery.toString();
    }

}
