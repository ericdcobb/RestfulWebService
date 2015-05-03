package com.test.webapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Data Model implemented using HashMap
 */
public class NoteModel {
    HashMap<Integer, Note> noteMap = new HashMap<Integer, Note>();

    public NoteModel() {
        createTestObject();
    }

    private synchronized void createTestObject(){
        Note note = new Note();
        note.setId(1);
        note.setBody("Don't forget to pick up milk!");
        insert(note);
    }

    // Store a new note.
    public synchronized Integer insert(Note note) {
        Integer id = noteMap.size() + 1;
        note.setId(id);
        noteMap.put(id, note);
        return id;
    }

    // Get a single note
    public synchronized Note get(int noteId) {
        return noteMap.get(noteId);
    }

    // Get a list of all the notes in the map
    public synchronized List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();
        for(Note n : noteMap.values()) {
            notes.add(n);
        }
        return notes;
    }

    // Get a list of notes by querying
    public synchronized List<Note> getNotesByQuery(String query) {
        List<Note> notes = new ArrayList<Note>();
        for(Note n : noteMap.values()) {
            if(n.getBody().contains(query)) { notes.add(n); }
        }
        return notes;
    }
}

