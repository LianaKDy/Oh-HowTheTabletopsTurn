package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
    private Character testPlayer1;
    private Character testPlayer2;
    private Character testEnemy1;
    private Character testEnemy2;
    private Character testNull;
    private Character testDecimal;

    @BeforeEach
    public void runBefore() {
        testPlayer1 = new Character("Player", "Liana", 100, 100);
        testPlayer2 = new Character("Player", "Angela", 500, 200);
        testEnemy1 = new Character("Enemy", "EvilLiana", 200, 50);
        testEnemy2 = new Character("Enemy", "EvilAngela", 0, 100);
        testNull = new Character("Player", "Null", 0, 0);
        testDecimal = new Character("Enemy", "Decimal", 100.5, 50.5);


    }

    @Test
    public void testConstructorPlayer() {
        assertEquals("Player", testPlayer1.getType());
        assertEquals("Liana", testPlayer1.getName());
        assertEquals(100, testPlayer1.getHealthPoints());
        assertEquals(100, testPlayer1.getDamage());
    }

    @Test
    public void testConstructorEnemy() {
        assertEquals("Enemy", testEnemy1.getType());
        assertEquals("EvilLiana", testEnemy1.getName());
        assertEquals(200, testEnemy1.getHealthPoints());
        assertEquals(50, testEnemy1.getDamage());
        assertEquals(100.5, testDecimal.getHealthPoints());
        assertEquals(50.5, testDecimal.getDamage());

    }

    @Test
    public void testUpdateHealthPointsSingle() {
        testPlayer1.updateHealthPoints(0);
        assertEquals(100, testPlayer1.getHealthPoints());

        testPlayer2.updateHealthPoints(250);
        assertEquals(250, testPlayer2.getHealthPoints());

        testEnemy1.updateHealthPoints(200);
        assertEquals(0, testEnemy1.getHealthPoints());

        testEnemy2.updateHealthPoints(50);
        assertEquals(0, testEnemy2.getHealthPoints());

        testDecimal.updateHealthPoints(20.5);
        assertEquals(80, testDecimal.getHealthPoints());

    }

    @Test
    public void testUpdateHealthPointsMultiple() {
        testPlayer1.updateHealthPoints(0);
        assertEquals(100, testPlayer1.getHealthPoints());
        testPlayer1.updateHealthPoints(50);
        assertEquals(50, testPlayer1.getHealthPoints());
        testPlayer1.updateHealthPoints(49);
        assertEquals(1, testPlayer1.getHealthPoints());
        testPlayer1.updateHealthPoints(1);
        assertEquals(0, testPlayer1.getHealthPoints());
        testPlayer1.updateHealthPoints(10);
        assertEquals(0, testPlayer1.getHealthPoints());

        testEnemy1.updateHealthPoints(100);
        assertEquals(100, testEnemy1.getHealthPoints());
        testEnemy1.updateHealthPoints(0);
        assertEquals(100, testEnemy1.getHealthPoints());
        testEnemy1.updateHealthPoints(50);
        assertEquals(50, testEnemy1.getHealthPoints());
        testEnemy1.updateHealthPoints(40);
        assertEquals(10, testEnemy1.getHealthPoints());
        testEnemy1.updateHealthPoints(50);
        assertEquals(0, testEnemy1.getHealthPoints());

    }

    @Test
    public void testIsDead() {
        assertTrue(testNull.isDead());
        assertTrue(testEnemy2.isDead());
        assertFalse(testPlayer1.isDead());
        assertFalse(testEnemy1.isDead());

    }

}
