import model.User;
import search.UserSearchEngine;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<User> userSource = new ArrayList<>() {{
            add(new User(1L, "petrova@mail.ru", "Наталья", "Петрова", "Владимировна"));
                    add(new User(2L, "sidorov@mail.ru", "Иван", "Сидоров", "Владимировна"));
                    add(new User(3L, "novikov@mail.ru", "Петр", "Новиков", "Иванович"));
                    add(new User(4L, "ivanov@mail.ru", "Петр", "Иванов", "Владимирович"));
                    add(new User(5L, "sidorova@mail.ru", "Анастасия", "Сидорова", "Ивановна"));
                    add(new User(6L, "novikova@mail.ru", "Наталья", "Новикова", "Петровна"));
        }};

        var engine = new UserSearchEngine(userSource);

        var result = engine.search("Петрова Наталья");

        for (var r: result) {
            System.out.printf("%s | %s | %s | %s%n", r.getEmail(), r.getName(), r.getSurname(), r.getPatronymic());
        }
    }
}