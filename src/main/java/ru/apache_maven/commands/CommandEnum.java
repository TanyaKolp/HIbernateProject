package ru.apache_maven.commands;

public enum CommandEnum {
    CREATE("create"),
    DELETE("delete");

    private String value;

    CommandEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CommandEnum fromValue(String value){
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if(commandEnum.getValue().equals(value)){
                return commandEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
