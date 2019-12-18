package com.github.project_njust.ccf_manager;

public enum UserType {
    TOURISTS(-1), STUDENT(0), ADMIN(2), PRINCIPAL(1);
    private int type;

    UserType(int type) {
        this.type = type;
    }

    public int getTypeId() {
        return type;
    }

    public static UserType getType(int type) {
        for (UserType ut : UserType.values()) {
            if (ut.type == type) {
                return ut;
            }
        }
        return null;
    }
}
