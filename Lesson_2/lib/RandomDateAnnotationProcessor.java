package JavaJunior.Homework.Lesson_2.lib;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class RandomDateAnnotationProcessor {

    private static Random random = new Random();
    private static RandomDate annotation = null;

    public static void processAnnotation(Object obj) {
        Date date = dateSupport(obj);
        if (date != null) {
            otherTypeDateSupport(obj, date);
        }
    }


    public static Date dateSupport(Object obj) {
        Date date = null;
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomDate.class) && field.getType().isAssignableFrom(Date.class)) {
                annotation = field.getAnnotation(RandomDate.class);
                long min = annotation.min();
                long max = annotation.max();
                if (min < max) {
                    date = new Date(random.nextLong(min, max));
                    try {
                        field.setAccessible(true);
                        field.set(obj, date);
                    } catch (IllegalAccessException e) {
                        System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                    }
                }
            }
        }
        return date;
    }

    public static void otherTypeDateSupport(Object obj, Date date) {
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.getType().isAssignableFrom(LocalDate.class)) {
                    LocalDate lDate = LocalDate.ofInstant(date.toInstant(), ZoneId.of(annotation.zone()));
                    field.set(obj, lDate);
                } else if (field.getType().isAssignableFrom(LocalDateTime.class)) {
                    LocalDateTime lDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(annotation.zone()));
                    field.set(obj, lDateTime);
                } else if (field.getType().isAssignableFrom(Instant.class)) {
                    Instant instant = date.toInstant();
                    field.set(obj, instant);
                }
            } catch (IllegalAccessException e) {
                System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
            }
        }
    }

}
