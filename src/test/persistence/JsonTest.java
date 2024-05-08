package persistence;

import model.Character;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    // WRITE IN SPECIFICATION
    protected void checkCharacter(String type, String name, double hp, double dmg, Character character) {
        assertEquals(type, character.getType());
        assertEquals(name, character.getName());
        assertEquals(hp, character.getHealthPoints());
        assertEquals(dmg, character.getDamage());

    }
}
