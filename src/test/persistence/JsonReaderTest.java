package persistence;

import model.CharacterList;
import model.Character;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CharacterList cl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTurnTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTurnTracker.json");
        try {
            CharacterList cl = reader.read();
            // assertEquals("My work room", cl.getName()); Don't Need This
            assertEquals(0, cl.lengthTracker());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTurnTracker() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTurnTracker.json");
        try {
            CharacterList cl = reader.read();
            // assertEquals("My work room", wr.getName());
            List<Character> characters = cl.getCharacters();
            assertEquals(2, characters.size());
            checkCharacter("Player", "Liana", 100, 50, characters.get(0));
            checkCharacter("Enemy", "EvilLiana", 200, 25, characters.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
