package pojo.unit;

public enum PartlyTimeType {
    STRATTIME_UNKONW((byte) 0), ENDTIME_UNKONW((byte) 1), BOTHTIME_UNKONW((byte) 2);
    private Byte code;

    PartlyTimeType(Byte code) {
        this.code = code;
    }

    public static PartlyTimeType fromCode(Byte code) {
        for (PartlyTimeType value : PartlyTimeType.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public Byte getCode() {
        return code;
    }
}
