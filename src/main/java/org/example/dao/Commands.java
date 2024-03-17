package org.example.dao;

public enum Commands {
    HELP("вывести список доступных команд"),
    NOTE_NEW("создать новую заметку"),
    NOTE_LIST("вывести все заметки"),
    NOTE_REMOVE("удалить заметку"),
    NOTE_EXPORT("сохранить все заметки в текстовый файл"),
    EXIT("выйти из приложения");

    private String description;
    Commands(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getFormattedName() {
        return name().toLowerCase().replace("_", "-");
    }

    public static void printCommands() {
        for (Commands command : Commands.values()) {
            System.out.println(command.getFormattedName() + " - " + command.getDescription());
        }
    }
}
