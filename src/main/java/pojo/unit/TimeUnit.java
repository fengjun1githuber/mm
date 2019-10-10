package pojo.unit;

public enum TimeUnit {
    LIGHTNING(5, "lightning"),
    MIN(1, "min");

    private Integer value;
    private String code;

    TimeUnit(Integer value, String code) {
        this.value = value;
        this.code = code;
    }

    public static TimeUnit fromCode(String code) {
        for (TimeUnit value : TimeUnit.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }
}
