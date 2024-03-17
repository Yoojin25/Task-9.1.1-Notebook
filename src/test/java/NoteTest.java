import org.example.dao.NoteDaoImpl;
import org.example.model.Note;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoteTest {
    @Mock
    private String input;
    private InputStream in;
    private Note note;
    private NoteDaoImpl noteDao;

    @Test
    @DisplayName("id генерируются и являются уникальными")
    public void idIsGeneratedAndIsUnique() {
        Set<Integer> ids = new HashSet<>();
        int numNotes = 10;
        for (int i = 0; i < numNotes; i++) {
            note = new Note("hello", Arrays.asList("ok"));
            assertTrue(ids.add(note.getId()));
        }
        assertEquals(numNotes, ids.size());
    }

    @Test
    @DisplayName("Создание заметки с некорректным текстом")
    public void createNoteWithInvalidText() {
        noteDao = spy(new NoteDaoImpl());
        input = "hi\nok";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        noteDao.createNote();
        verify(noteDao, never()).addNote(any(Note.class));
    }

    @Test
    @DisplayName("Создание заметки с корректным текстом")
    public void createNoteWithValidText() {
        noteDao = spy(new NoteDaoImpl());
        input = "hello\nok";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        note = noteDao.createNote();
        verify(noteDao, times(1)).addNote(any(Note.class));
        assertEquals("hello", note.getText());
    }

    @Test
    @DisplayName("Создание заметки с некорректными метками")
    public void createNoteWithInvalidLabels() {
        noteDao = spy(new NoteDaoImpl());
        input = "hello\nok!";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        noteDao.createNote();
        verify(noteDao, never()).addNote(any(Note.class));
    }

    @Test
    @DisplayName("Создание заметки с корректными метками")
    public void createNoteWithValidLabels() {
        noteDao = spy(new NoteDaoImpl());
        input = "hello\nhi okey";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        note = noteDao.createNote();
        verify(noteDao, times(1)).addNote(any(Note.class));
        assertEquals(Arrays.asList("HI", "OKEY"), note.getLabel());
    }

    @Test
    @DisplayName("Удаление заметки с некорректным id")
    public void removeNoteWithInvalidId() {
        noteDao = spy(new NoteDaoImpl());
        String input = "hello\nhi okey";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        note = noteDao.createNote();
        String id = "one";
        InputStream in2 = new ByteArrayInputStream(id.getBytes());
        System.setIn(in2);
        noteDao.removeNote();
        assertEquals(1, noteDao.getNotesList().size());
    }

    @Test
    @DisplayName("Удаление заметки с корректным id")
    public void removeNoteWithValidId() {
        noteDao = spy(new NoteDaoImpl());
        String input = "hello\nhi okey";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        note = noteDao.createNote();
        InputStream in2 = new ByteArrayInputStream(String.valueOf(note.getId()).getBytes());
        System.setIn(in2);
        noteDao.removeNote();
        assertTrue(noteDao.getNotesList().isEmpty());
    }
}
