package persistence;

import model.Character;
import model.CharacterList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads the turn tracker from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads turn tracker from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CharacterList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCharacterList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses turntracker from JSON object and returns it
    private CharacterList parseCharacterList(JSONObject jsonObject) {
        // String characterName = jsonObject.getString("name");
        CharacterList cl = new CharacterList();
        addCharacters(cl, jsonObject);
        return cl;
    }

    // MODIFIES: CharacterList cl
    // EFFECTS: parses characters from JSON object and adds them to the turn tracker
    private void addCharacters(CharacterList cl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("turntracker");
        for (Object json : jsonArray) {
            JSONObject nextCharacter = (JSONObject) json;
            addCharacter(cl, nextCharacter);
        }
    }

    // MODIFIES: cl
    // EFFECTS: parses character from JSON object and adds it to turn tracker
    private void addCharacter(CharacterList cl, JSONObject jsonObject) {
        String charaType = jsonObject.getString("type");
        String charaName = jsonObject.getString("name");
        double initialHealthPoints = jsonObject.getDouble("hp");
        double charaDamage = jsonObject.getDouble("dmg");
        Character character = new Character(charaType, charaName, initialHealthPoints, charaDamage);
        cl.addToTracker(character);
    }
}

