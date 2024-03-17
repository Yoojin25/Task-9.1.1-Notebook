package org.example;

import org.example.service.NoteServiceImpl;

public class Main {
    private static NoteServiceImpl noteService = new NoteServiceImpl();
    public static void main(String[] args) {
        noteService.start();
    }
}