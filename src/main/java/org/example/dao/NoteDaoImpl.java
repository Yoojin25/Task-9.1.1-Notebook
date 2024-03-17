package org.example.dao;

import lombok.extern.java.Log;
import org.example.model.Note;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class NoteDaoImpl implements NoteDao {
    private Map<Integer, Note> notes = new HashMap<>();
    private Map<String, List<Note>> labelsNotes = new HashMap<>();

    public void addNote(Note note) {
        notes.put(note.getId(), note);
        for (String label : note.getLabel()) {
            if (labelsNotes.containsKey(label)) {
                labelsNotes.get(label).add(note);
            } else {
                labelsNotes.put(label, new ArrayList<>(Arrays.asList(note)));
            }
        }
    }

    public Map<Integer, Note> getNotesList() {
        return notes;
    }

    private List<String> labelsToList(String labelsString) {
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я]+$");
        List<String> labels = List.of(labelsString.toUpperCase().split("\\s+"));
        for (String label : labels) {
            Matcher matcher = pattern.matcher(label);
            if (!matcher.matches()) {
                throw new InputMismatchException("Метки могут содержать только буквы");
            }
        }
        return labels;
    }

    @Override
    public void getAllCommands() {
        log.fine("Вызвана команда help");
        Commands.printCommands();
    }

    @Override
    public Note createNote() {
        log.fine("Вызвана команда note-new");
        Scanner sc = new Scanner(System.in);
        Note note = null;
        try {
            System.out.println("Введите заметку");
            String text = sc.nextLine();
            if (text.isEmpty() || text.length() < 3) {
                log.info("Текст заметки не может быть пустым, его длина должна составлять не менее 3-х " +
                        "символов, введено - " + text.length());
                throw new IllegalArgumentException("Текст заметки не может быть пустым, его длина должна составлять " +
                        "не менее 3-х символов");
            }

            System.out.println("Добавить метки? Метки состоят из одного слова и могу содержать только буквы. Для " +
                    "добавления нескольких меток разделяйте слова пробелом.");
            List<String> labels = labelsToList(sc.nextLine());

            note = new Note(text, labels);
            addNote(note);

            System.out.println("Заметка добавлена");

        } catch (IllegalArgumentException | InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return note;
    }

    @Override
    public void getAllNotes() {
        log.fine("Вызвана команда note-list");
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Введите метки, чтобы отобразить определенные заметки или оставьте пустым для " +
                    "отображения всех заметок");
            String labelsString = sc.nextLine();
            if (labelsString.isEmpty()) {
                for (Note allNotes : notes.values()) {
                    System.out.println(allNotes.toString());
                }
            } else {
                List<String> labels = labelsToList(labelsString);
                Set<Note> notesByLabels = new HashSet<>();
                for (String label : labels) {
                    if (labelsNotes.containsKey(label)) {
                        notesByLabels.addAll(labelsNotes.get(label));
                    }
                }
                if (notesByLabels.isEmpty()) {
                    log.warning("Заметки не найдены");                }
                for (Note noteByLabel : notesByLabels) {
                    System.out.println(noteByLabel.toString());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeNote() {
        log.fine("Вызвана команда note-remove");
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Введите id удаляемой заметки");
            if (!sc.hasNextInt()) {
                log.info("Необходимо ввести id удаляемой заметки, введено - " + sc.next());
                throw new IllegalArgumentException("Необходимо ввести id удаляемой заметки");
            }
            int id = sc.nextInt();
            if (notes.containsKey(id)) {
                Note deletedNote = notes.remove(id);
                List<String> deletedLabels = deletedNote.getLabel();
                for (String deletedLabel : deletedLabels) {
                    List<Note> notesByLabel = labelsNotes.get(deletedLabel);
                    notesByLabel.remove(deletedNote);
                }
                System.out.println("Заметка удалена");
            } else {
                log.info("Заметка с указанным id не найдена, введено - " + id);
                System.out.println("Заметка с указанным id не найдена");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveNotesToFile() {
        log.fine("Вызвана команда note-export");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH-mm-ss");
        String date = dateFormat.format(new Date());
        String fileName = "notes_" + date + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            if (notes.isEmpty()) {
                log.warning("Заметки не найдены");
                throw new IllegalStateException("Заметки не найдены");
            }
            for (Note allNotes : notes.values()) {
                writer.write(allNotes.toString());
            }
            System.out.println("Заметки сохранены в текстовый файл с именем " + fileName);
        } catch (IOException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void exit() {
        log.fine("Вызвана команда exit");
        System.out.println("Работа приложения завершена");
        System.exit(0);
    }
}
