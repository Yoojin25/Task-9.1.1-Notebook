package org.example.model;

import java.util.*;
import java.util.stream.Collectors;

public class Note {
    private int id;
    private String text;
    private List<String> label;
    private static int idCount = 0;

    public Note(String text, List<String> label) {
        this.id = idGenerator();
        this.text = text;
        this.label = label;
    }

    public static int idGenerator() {
        return ++idCount;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<String> getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && Objects.equals(text, note.text) && Objects.equals(label, note.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, label);
    }

    @Override
    public String toString() {
        return String.format("%d#%s\n%s\n\n===================\n", getId(), getText(), getLabel().stream()
                .collect(Collectors.joining(";")));
    }
}
