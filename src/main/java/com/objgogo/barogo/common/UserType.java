package com.objgogo.barogo.common;

public enum UserType {
    ADMIN,
    DELIVERY,
    USER;

    public static boolean isValidValue(String value) {
        for (UserType userType : UserType.values()) {
            if (userType.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
