package org.example.dao;

import org.example.model.Note;

public interface NoteDao {
    void getAllCommands();
    Note createNote();
    void getAllNotes();
    void removeNote();
    void saveNotesToFile();
    void exit();
}
