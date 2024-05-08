package persistence;

import model.CharacterList;
import model.Character;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            CharacterList cl = new CharacterList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTurnTracker() {
        try {
            CharacterList cl = new CharacterList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTurnTracker.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTurnTracker.json");
            cl = reader.read();
            // assertEquals("My work room", cl.getName());
            assertEquals(2, cl.lengthTracker());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTurnTracker() {
        try {
            CharacterList cl = new CharacterList();
            cl.addToTracker(new Character("Player", "Liana", 100, 50));
            cl.addToTracker(new Character("Enemy", "EvilLiana", 200, 25));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTurnTracker.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTurnTracker.json");
            cl = reader.read();
            List<Character> characters = cl.getCharacters();
            assertEquals(2, characters.size());
            checkCharacter("Player", "Liana", 100, 50, characters.get(0));
            checkCharacter("Enemy", "EvilLiana", 200, 25, characters.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
