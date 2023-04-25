package com.nineplus.localhand.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Status {
    @JsonProperty("INCOMPLETE")
    INCOMPLETE(0),
    @JsonProperty("IN_REVIEW")
    IN_REVIEW(1),
    @JsonProperty("REJECTED")
    REJECTED(2),
    @JsonProperty("ACTIVATED")
    ACTIVATED(3)
    ;

    private static Map<Integer, Status> lookup;
    private Integer value;

    Status(Integer value) {
        this.value = value;
    }

    static {
        try {
            Status[] vals = Status.values();
            lookup = new HashMap<Integer, Status>(vals.length);

            for (Status rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static Status fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "INCOMPLETE" -> 0;
            case "IN_REVIEW" -> 1;
            case "REJECTED" -> 2;
            case "ACTIVATED" -> 3;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }

}
