package org.test.bankapp.model;

import java.io.Serializable;

public enum Gender implements Serializable {
    MALE("Mr"),
    FEMALE("Ms");
    String gender;

    Gender(String gender) {
        this.gender = gender;

    }

    public static Gender fromString(String parameterName) {
        if (parameterName != null) {
            for (Gender objType : Gender.values()) {
                if (parameterName.equalsIgnoreCase(objType.gender)) {
                    return objType;
                }
            }
        }
        return null;
    }
}
