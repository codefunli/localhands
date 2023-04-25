package com.nineplus.localhand.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum RatingType {

    @JsonProperty("LOCAL")
    LOCAL(1),

    @JsonProperty("HELPER")
    HELPER(2);

    private static Map<Integer, RatingType> lookup;
    private Integer value;

    RatingType(Integer value) {
        this.value = value;
    }

    static {
        try {
            RatingType[] vals = RatingType.values();
            lookup = new HashMap<Integer, RatingType>(vals.length);

            for (RatingType rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static RatingType fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getRatingTypeValue(String val) {
        return switch (val) {
            case "LOCAL" -> 1;
            case "HELPER" -> 2;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
