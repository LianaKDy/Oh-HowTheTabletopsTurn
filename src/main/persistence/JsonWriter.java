package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import model.CharacterList;
import org.json.JSONObject;

public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    // MODIFIES: this
    // EFFECTS: initializes the file destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // EFFECTS: initializes jSON writer
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(new File(this.destination));
    }

    // REQUIRES: CharacterList cl
    // EFFECTS: saves the current cl to the file
    public void write(CharacterList cl) {
        JSONObject json = cl.toJson();
        this.saveToFile(json.toString(4));
    }


    // EFFECTS: closes the writer file
    public void close() {
        this.writer.close();
    }

    // EFFECTS: prints the saved writing file
    private void saveToFile(String json) {
        this.writer.print(json);
    }

}
