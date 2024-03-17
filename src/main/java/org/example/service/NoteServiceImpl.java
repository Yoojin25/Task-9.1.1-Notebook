package org.example.service;

import lombok.extern.java.Log;
import org.example.dao.Commands;
import org.example.dao.NoteDaoImpl;
import org.example.model.Note;

import java.util.*;

@Log
public class NoteServiceImpl implements NoteService{
    private NoteDaoImpl noteDao = new NoteDaoImpl();
    @Override
    public void getAllCommands() {
        noteDao.getAllCommands();
    }

    @Override
    public Note createNote() {
        return noteDao.createNote();
    }

    @Override
    public void getAllNotes() {
        noteDao.getAllNotes();
    }

    @Override
    public void removeNote() {
        noteDao.removeNote();
    }

    @Override
    public void saveNotesToFile() {
        noteDao.saveNotesToFile();
    }

    @Override
    public void exit() {
        noteDao.exit();
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Это Ваша записная книжка. Вот список доступных команд:");
        Commands.printCommands();
        while(true) {
            switch (sc.next()) {
                case "help" -> getAllCommands();
                case "note-new" -> createNote();
                case "note-list" -> getAllNotes();
                case "note-remove" -> removeNote();
                case "note-export" -> saveNotesToFile();
                case "exit" -> exit();
                default -> log.warning("Команда не найдена");
            }
        }
    }
}
