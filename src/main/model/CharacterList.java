package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a list of Characters in battle
public class CharacterList implements Writable {
    private final ArrayList<Character> characterList;

    // EFFECTS: constructs an empty list of Characters in battle
    public CharacterList() {
        characterList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds character to the end of the turn tracker list
    public void addToTracker(Character character) {
        characterList.add(character);
        EventLog.getInstance().logEvent(new Event("Added "
                + character.getType() + " " + character.getName() + " to turn tracker."));
    }

    // REQUIRES: a non-empty list of characters
    // MODIFIES: this
    // EFFECTS: remove the n-th character in the turn tracker list
    public void removeFromTracker(int n) {
        EventLog.getInstance().logEvent(new Event("Removed "
                + getCharacter(n).getName() + " from turn tracker."));
        characterList.remove(n);
    }

    // REQUIRES: character is in 0th position in the list
    // EFFECTS: places a given character at the end of the turn tracker list from the front of it
    public void endCharaTurn(Character character) {
        characterList.add(character);
        characterList.remove(0);
        EventLog.getInstance().logEvent(new Event("Ended " + character.getName() + "'s turn."));
    }

    // EFFECTS: prints save status
    public void printSaveStatus() {
        EventLog.getInstance().logEvent(new Event("Saved current Turn Tracker to file."));
    }

    // EFFECTS: prints load status
    public void printLoadStatus() {
        EventLog.getInstance().logEvent(new Event("Loaded previous Turn Tracker from file."));
    }

    // EFFECTS: returns the number of characters in the turn tracker list
    public int lengthTracker() {
        return characterList.size();
    }

    // EFFECTS: gets the nth character in the turn tracker list
    public Character getCharacter(int n) {
        return characterList.get(n);
    }

    // EFFECTS: returns an unmodifiable list of characters in this turn tracker list
    public List<Character> getCharacters() {
        return Collections.unmodifiableList(characterList);
    }

    // EFFECTS: returns json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("turntracker", charactersToJson());
        return json;
    }

    // EFFECTS: returns character in this turn tracker as a JSON array
    private JSONArray charactersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Character c : characterList) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: prints the EventLog
    public void printEventLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.getDescription());
            System.out.println(e.getDate());
            System.out.println("");
        }
    }
}

