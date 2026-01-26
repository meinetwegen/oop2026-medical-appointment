package edu.aitu.oop3.models;

public abstract class User implements IUser{
    private int id;
    private String fullName;

    public User(String fullName) {
        setFullName(fullName);
    }

    public User(int id, String fullName) {
        this.id = id;
        setFullName(fullName);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
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