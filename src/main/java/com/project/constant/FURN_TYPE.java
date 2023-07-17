package com.project.constant;

import lombok.Getter;

import java.util.Arrays;

public enum FURN_TYPE {
    BED, CLOSET, DESK, CHAIR, SOFA, TABLE;

    public static FURN_TYPE valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(value -> value.equals(label))
                .findAny()
                .orElse(null);
    }
}
