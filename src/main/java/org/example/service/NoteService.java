package org.example.service;

import org.example.model.Note;

public interface NoteService {
    void getAllCommands();
    Note createNote();
    void getAllNotes();
    void removeNote();
    void saveNotesToFile();
    void exit();
}
