package model;

public class User {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;

    public User(Long id, String email, String name, String surname, String partonymic) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.patronymic = partonymic;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
