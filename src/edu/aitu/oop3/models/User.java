package edu.aitu.oop3.models;

public abstract class User {
    private int id;
    private String fullName;

    public User(String fullName) {      // конструктор для создания нового пользователя (id еще не назначен бд)
        setFullName(fullName);
    }

    public User(int id, String fullName) {     // конструктор для объектов, загруженных из бд (с существующим id)
        this.id = id;
        setFullName(fullName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { // добавляем сеттер, чтобы jdbc имел возможность установить ID
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "id = " + id + ", fullName = " + fullName;
    }
}