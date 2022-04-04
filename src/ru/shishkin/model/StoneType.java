package ru.shishkin.model;

import java.util.Arrays;

public enum StoneType {
    BLACK(1, "x"), WHITE(-1, "o"), NONE(0, "-");

    private int id;
    private String sign;

    StoneType(int id, String sign) {
        this.id = id;
        this.sign = sign;
    }

    public static String getSignById(int id) {
        return Arrays.stream(StoneType.values()).filter(item -> item.id == id).findFirst().get().sign;
    }
}
