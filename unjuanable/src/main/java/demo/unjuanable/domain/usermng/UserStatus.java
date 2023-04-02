package demo.unjuanable.domain.usermng;

public enum UserStatus {
    EFFECTIVE("EF","有效"),
    TERMINATED("TE","终止");


    private final String code;
    private final String desc;

    UserStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public boolean is(UserStatus other) {
       return this.equals(other);
    }

    public boolean is(String other) {
        return this.code.equals(other);
    }
}
