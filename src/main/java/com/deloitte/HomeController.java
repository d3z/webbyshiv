/*
 * Copyright 2016 Instil Software.
 */
package com.deloitte;

import com.deloitte.model.Note;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ResponseBody
public class HomeController {

    private final List<Note> notes;

    public HomeController() {
        notes = loadNotes();
    }

    private List<Note> loadNotes() {
        List<Note> notes = new ArrayList<>();
        BufferedReader notesReader = null;
        try {
            notesReader = getReaderForNotesFile("/Users/Gareth/notes.txt");
            addNotesFromReaderToList(notes, notesReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (notesReader != null) {
                    notesReader.close();
                }
            } catch (IOException ioe) {
                System.err.println("Oops ... can't close notes reader.");
            }
        }
        return notes;
    }

    private void addNotesFromReaderToList(List<Note> notes, BufferedReader notesReader) throws IOException {
        String notesLine;
        while (null != (notesLine = notesReader.readLine())) {
            Note note = makeNoteFrom(notesLine);
            notes.add(note);
        }
    }

    private Note makeNoteFrom(String notesLine) {
        String[] noteParts = notesLine.split(";");
        return new Note(noteParts[0], noteParts[1], noteParts[2]);
    }

    private BufferedReader getReaderForNotesFile(String notesFilename) throws FileNotFoundException {
        return new BufferedReader(new FileReader(notesFilename));
    }

    @RequestMapping("/")
    public String index() {
        return "Hello, World!";
    }

    @RequestMapping("/greet")
    public String greet(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello, %s", name);
    }

    @RequestMapping("/notes/{id}")
    public Note getNoteWithId(@PathVariable("id") Long id) {
        return new Note(String.format("Note %d", id), "This is your note", new Date().toString());
    }

    @RequestMapping("/note")
    public Note note() {
        return new Note("My first note", "This is my first note", new Date().toString());
    }

    @RequestMapping("/notes")
    public List<Note> notes() {
        return notes;
    }
}
