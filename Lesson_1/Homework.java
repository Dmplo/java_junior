package JavaJunior.Homework.Lesson_1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Homework {

    public static void main(String[] args) {
        List<String> departmentNames = List.of("Sale", "Production", "Purchase", "Quality", "Engineering");
        List<String> personNames = List.of("Jack", "Arnold", "Will", "John", "Check", "Vasya", "Petya", "Alex", "Petr", "Donald", "Mickey", "Mickael");
        List<Department> departmentList = new ArrayList<>();
        for (int i = 0; i < departmentNames.size(); i++) {
            Department department = new Department();
            department.setName(getRandom(departmentNames));
            departmentList.add(department);
        }
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < personNames.size(); i++) {
            Person person = new Person();
            person.setName(getRandom(personNames));
            person.setAge(ThreadLocalRandom.current().nextInt(20, 41));
            person.setSalary(ThreadLocalRandom.current().nextInt(30_000, 100_000) * 1.0);
            person.setDepart(getRandom(departmentList));
            people.add(person);
        }

        System.out.println(findMostYoungestPerson(people).get());
        System.out.println(findMostExpensiveDepartment(people));
        System.out.println(groupByDepartment(people));
        System.out.println(groupByDepartmentName(people));
        System.out.println(getDepartmentOldestPerson(people));
        System.out.println(cheapPersonsInDepartment(people));
    }

    private static class Department {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static class Person {
        private String name;
        private int age;
        private double salary;
        private Department depart;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public Department getDepart() {
            return depart;
        }

        public void setDepart(Department depart) {
            this.depart = depart;
        }

        @Override
        public String toString() {
            return "Person{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   ", salary=" + salary +
                   ", depart=" + depart +
                   '}';
        }
    }

    private static <T> T getRandom(List<? extends T> items) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.get(randomIndex);
    }


    /**
     * Найти самого молодого сотрудника
     */
    static Optional<Person> findMostYoungestPerson(List<Person> people) {
        return people.stream().min((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     */
    static Optional<Department> findMostExpensiveDepartment(List<Person> people) {
        return people.stream().max(Comparator.comparing(Person::getSalary)).map(p -> p.getDepart());
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> people) {
        return people.stream().collect(Collectors.groupingBy(Person::getDepart));
    }

    /**
     * Сгруппировать сотрудников по названиям департаментов
     */
    static Map<String, List<Person>> groupByDepartmentName(List<Person> people) {
        return people.stream().collect(Collectors.groupingBy(p -> p.getDepart().getName()));
    }

    /**
     * В каждом департаменте найти самого старшего сотрудника
     */
    static Map<String, Person> getDepartmentOldestPerson(List<Person> people) {
        return people.stream()
                .collect(Collectors.toMap(
                        p -> p.getDepart().getName(),
                        Function.identity(),
                        (p1, p2) -> {
                            if (p1.getAge() > p2.getAge()) {
                                return p1;
                            }
                            return p2;
                        }
                ));
    }

    /**
     * *Найти сотрудников с минимальными зарплатами в своем отделе
     * (прим. можно реализовать в два запроса)
     */
    static List<Person> cheapPersonsInDepartment(List<Person> people) {
        Map<String, Person> dep = people.stream()
                .collect(Collectors.toMap(
                        p -> p.getDepart().getName(),
                        Function.identity(),
                        (p1, p2) -> {
                            if (p1.getSalary() < p2.getSalary()) {
                                return p1;
                            }
                            return p2;
                        }
                ));
        List<Person> personList = new ArrayList<>();
        for (Map.Entry<String, Person> stringPersonEntry : dep.entrySet()) {
            personList.add(stringPersonEntry.getValue());
        }
        return personList;
    }

}
