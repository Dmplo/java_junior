package model;

import lombok.Data;

@Data
public class Person {
    private long id;
    private long departmentId;
    private String name;
    private int age;
    private boolean active;
}
