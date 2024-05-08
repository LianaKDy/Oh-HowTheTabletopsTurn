package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterListTest {
    private CharacterList testCharacterList;
    private Character testPlayer1;
    private Character testEnemy1;

    @BeforeEach
    void runBefore() {
        testCharacterList = new CharacterList();
        testPlayer1 = new Character("Player", "Liana", 100, 100);
        testEnemy1 = new Character("Enemy", "EvilLiana", 200, 50);

    }

    @Test
    void testConstructorCharacterList() {
        assertEquals(0, testCharacterList.lengthTracker());
    }

    @Test
    void testAddToTrackerOnceP() {
        assertEquals(0, testCharacterList.lengthTracker());
        testCharacterList.addToTracker(testPlayer1);
        assertEquals(1, testCharacterList.lengthTracker());
    }

    @Test
    void testAddToTrackerOnceE() {
        assertEquals(0, testCharacterList.lengthTracker());
        testCharacterList.addToTracker(testEnemy1);
        assertEquals(1, testCharacterList.lengthTracker());
    }

    @Test
    void testAddToTrackerMultiple() {
        assertEquals(0, testCharacterList.lengthTracker());
        testCharacterList.addToTracker(testPlayer1);
        assertEquals(1, testCharacterList.lengthTracker());
        testCharacterList.addToTracker(testEnemy1);
        assertEquals(2, testCharacterList.lengthTracker());
    }

    @Test
    void testRemoveFromTrackerOneP() {
        testCharacterList.addToTracker(testPlayer1);
        assertEquals(1, testCharacterList.lengthTracker());
        testCharacterList.removeFromTracker(0);
        assertEquals(0, testCharacterList.lengthTracker());

    }

    @Test
    void testRemoveFromTrackerOneE() {
        testCharacterList.addToTracker(testEnemy1);
        assertEquals(1, testCharacterList.lengthTracker());
        testCharacterList.removeFromTracker(0);
        assertEquals(0, testCharacterList.lengthTracker());

    }

    @Test
    void testRemoveFromTrackerMultiple() {
        testCharacterList.addToTracker(testPlayer1);
        testCharacterList.addToTracker(testEnemy1);
        assertEquals(2, testCharacterList.lengthTracker());
        testCharacterList.removeFromTracker(0);
        assertEquals(1, testCharacterList.lengthTracker());
        testCharacterList.removeFromTracker(0);
        assertEquals(0, testCharacterList.lengthTracker());

    }

    @Test
    void testGetCharacter() {
        testCharacterList.addToTracker(testPlayer1);
        testCharacterList.addToTracker(testEnemy1);
        assertEquals(testPlayer1, testCharacterList.getCharacter(0));
        assertEquals(testEnemy1, testCharacterList.getCharacter(1));

    }

    @Test
    void testGetCharacters() {
        testCharacterList.addToTracker(testPlayer1);
        testCharacterList.addToTracker(testEnemy1);
        assertEquals(List.of(testPlayer1, testEnemy1), testCharacterList.getCharacters());

    }
}
