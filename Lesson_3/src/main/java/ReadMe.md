# Задание

### С помощью JDBC, выполнить следующие пункты:
1. Создать таблицу Person (скопировать код с семниара)
2. Создать таблицу Department (id bigint primary key, name varchar(128) not null)
3. Добавить в таблицу Person поле department_id типа bigint (внешний ключ)
4. Написать метод, который загружает Имя department по Идентификатору person
5. Написать метод, который загружает Map<String, String>
* Пример: [{"person #1", "department #1"}, {"person #2", "department #3}]
6. ** Написать метод, который загружает Map<String, List<String>>, в которой маппинг department.name -> <person.name>
* Пример: [{"department #1", ["person #1", "person #2"]},{"department #2", ["person #3", "person #4"]}]
