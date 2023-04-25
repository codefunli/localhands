package com.nineplus.localhand.enum_class;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum JobStatus {
    @JsonProperty("CANCEL")
    CANCEL(0),
    @JsonProperty("OPEN")
    OPEN(1),
    @JsonProperty("WORKING")
    WORKING(2),
    @JsonProperty("DONE")
    DONE(3)
    ;

    private static Map<Integer, JobStatus> lookup;
    private Integer value;

    JobStatus(Integer value) {
        this.value = value;
    }

    static {
        try {
            JobStatus[] vals = JobStatus.values();
            lookup = new HashMap<Integer, JobStatus>(vals.length);

            for (JobStatus rpt : vals)
                lookup.put(rpt.getValue(), rpt);
        } catch (Exception ignored) {

        }
    }

    public static JobStatus fromValue(Integer value) {
        return lookup.get(value);
    }

    public static Integer getStatusEnum(String val) {
        return switch (val) {
            case "CANCEL" -> 0;
            case "OPEN" -> 1;
            case "WORKING" -> 2;
            case "DONE" -> 3;
            default -> null;
        };
    }

    public Integer getValue() {
        return this.value;
    }
}
