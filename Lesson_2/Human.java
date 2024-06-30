package JavaJunior.Homework.Lesson_2;

import JavaJunior.Homework.Lesson_2.lib.RandomDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Human {
    @RandomDate
    private String name;
    @RandomDate(min = 626893200000L, max = 658429200000L, zone = "Europe/London")
    private Date BirthDateFormat;
    private LocalDate BirthLocalDateFormat;
    private LocalDateTime BirthLocalDateTimeFormat;
    private Instant BirthInstantFormat;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", BirthDateFormat=" + BirthDateFormat +
                ", BirthLocalDateFormat=" + BirthLocalDateFormat +
                ", BirthLocalDateTimeFormat=" + BirthLocalDateTimeFormat +
                ", BirthInstantFormat=" + BirthInstantFormat +
                '}';
    }
}
