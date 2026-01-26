package edu.aitu.oop3.factories;

import edu.aitu.oop3.models.*;

public class UserFactory {

    public static IUser createUser(String type, int id, String name, String info) {
        if (type == null) return null;

        // Если создаем доктора, 'info' используем как специализацию
        if (type.equalsIgnoreCase("DOCTOR")) {
            return new Doctor(id, name, info);
        }

        // Если создаем пациента, 'info' используем как номер телефона
        else if (type.equalsIgnoreCase("PATIENT")) {
            return new Patient(id, name, "not_set@mail.com", info);  // для e-mail временно ставим заглушку или передаем пустую строку

        }

        throw new IllegalArgumentException("Unknown user type: " + type);
    }
}