
package ui;

import model.Character;
import model.CharacterList;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// A TTRPG Turn Tracker Application
public class TurnTracker {
    private static final String JSON_STORE = "./data/turntracker.json";
    private Scanner input;
    private Scanner inputBattle;
    private Scanner inputDamage;
    private Scanner charaName;
    private Scanner charaHealthPoints;
    private Scanner charaDamage;
    private CharacterList turnTracker = new CharacterList();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Turn Tracker application
    public TurnTracker() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTurnTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runTurnTracker() {
        boolean continueProgram = true;
        String command;

        initializeCharacters();

        while (continueProgram) {
            displayStartMenu();
            command = input.next();
            command = command.toUpperCase();

            if (command.equals("Q")) {
                continueProgram = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nEnd of Program. Thank You for Playing!");

    }

    // MODIFIES: this
    // EFFECTS: initializes characters
    public void initializeCharacters() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays starting menu of player/enemy creation to user
    public void displayStartMenu() {
        System.out.println("\n---------- >> Welcome to TurnTracker! << ----------");
        System.out.println("\nThe adventure begins....");
        System.out.println();
        System.out.println("\t>> To Add a Player, press P");
        System.out.println("\t>> To Add an Enemy, press E");
        System.out.println("\t>> When finished adding characters, type COMPLETE");
        System.out.println("\t>> To Save current Turn Tracker to file, type SAVE");
        System.out.println("\t>> To Load a previous Turn Tracker from file, type LOAD");
        System.out.println("\t>> To Quit the Program, press Q");
        System.out.println("\n---------------------------------------------------");

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    public void processCommand(String command) {
        switch (command) {
            case "P":
                System.out.println("You Pressed P");
                addPlayer();
                break;
            case "E":
                System.out.println("You Pressed E");
                addEnemy();
                break;
            case "COMPLETE":
                System.out.println("Get Ready for Battle!");
                System.out.println("The Turn Order is:");
                showTurnTracker();
                battle();
                break;
            case "SAVE":
                saveTurnTracker();
            case "LOAD":
                loadTurnTracker();
            default:
                System.out.println("Please select P, E, COMPLETE, SAVE, LOAD, or Q.");
                break;
        }

    }

    // MODIFIES: this
    // EFFECTS: processes Player input data
    public void addPlayer() {
        charaName = new Scanner(System.in);
        charaHealthPoints = new Scanner(System.in);
        charaDamage = new Scanner(System.in);

//        String charaName = JOptionPane.showInputDialog("Enter Player Name:");
//        String charaHealthPoints = JOptionPane.showInputDialog("Enter Player Health Points:");
//        String charaDamage = JOptionPane.showInputDialog("Enter Amount of Damage Player can do:");
//        Double healthPoints = Double.valueOf(charaHealthPoints);
//        Double damage = Double.valueOf(charaDamage);

        String charaType = "Player";

        System.out.println("What is your Player's name?");
        String name = charaName.next();
        System.out.println("What is your Player's maximum health points?");
        double healthPoints = charaHealthPoints.nextDouble();
        System.out.println("How much damage can your Player do?");
        double damage = charaDamage.nextDouble();

        Character player = new Character(charaType, name, healthPoints, damage);

        addToTracker(player);

        System.out.println(player.getName() + " has been added to the Turn Tracker.");

    }

    // MODIFIES: this
    // EFFECTS: processes Enemy input data
    public void addEnemy() {
        charaName = new Scanner(System.in);
        charaHealthPoints = new Scanner(System.in);
        charaDamage = new Scanner(System.in);
        String charaType = "Enemy";

        System.out.println("What is this Enemy's name?");
        String name = charaName.next();
        System.out.println("What is this Enemy's maximum health points?");
        double healthPoints = charaHealthPoints.nextDouble();
        System.out.println("How much damage can this Enemy do?");
        double damage = charaDamage.nextDouble();

        Character enemy = new Character(charaType, name, healthPoints, damage);

        addToTracker(enemy);

        System.out.println(enemy.getName() + " has been added to the Turn Tracker.");

    }

    // REQUIRES: character
    // MODIFIES: turnTracker
    // EFFECTS: adds character to the end of the turn tracker list
    public void addToTracker(Character character) {
        turnTracker.addToTracker(character);
    }

    // EFFECTS: displays the turn tracker as a numbered list
    public void showTurnTracker() {

        for (var i = 0; i < turnTracker.lengthTracker(); i++) {
            System.out.println(i + ". " + turnTracker.getCharacter(i).getName());
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes battle
    public void initializeBattle() {
        inputBattle = new Scanner(System.in);
        inputBattle.useDelimiter("\n");

        inputDamage = new Scanner(System.in);
        inputDamage.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user input for battle
    public void battle() {
        String command2;
        initializeBattle();

        displayBattleMenu();
        command2 = inputBattle.next();
        command2 = command2.toUpperCase();

        Character character = turnTracker.getCharacter(0);

        if (command2.equals("T")) {
            turnTracker.addToTracker(character);
            turnTracker.removeFromTracker(0);
            battle();
        } else {
            processBattle(command2);
            turnTracker.addToTracker(character);
            turnTracker.removeFromTracker(0);
            battle();

        }

        System.out.println("Thank you for playing.");
    }

    // EFFECTS: displays starting menu of player/enemy creation to user
    public void displayBattleMenu() {

        if (turnTracker.lengthTracker() == 1) {
            System.out.println("There is one " + turnTracker.getCharacter(0).getType() + " remaining.");
            System.out.println(turnTracker.getCharacter(0).getType() + "s have won the battle.");
            turnTracker.removeFromTracker(0);
            System.out.println("Thank you for playing.");
            System.exit(0);
        } else {
            System.out.println("\n---------- >> " + turnTracker.getCharacter(0).getName() + "'s Turn! << ----------");
            System.out.println("\nThe current turn order is:");
            showTurnTracker();
            System.out.println("\nWhat would " + turnTracker.getCharacter(0).getName() + " like to do?");
            System.out.println(" ");
            System.out.println("\t>> To View Health, press H");
            System.out.println("\t>> To Damage an Enemy, press D");
            System.out.println("\t>> To End the Turn, press T");
            System.out.println("\t>> To Save current Turn Tracker to file, type SAVE");
            System.out.println("\t>> To Load a previous Turn Tracker from file, type LOAD");

            System.out.println("\n---------------------------------------------------");
        }

    }

    // MODIFIES: this
    // EFFECTS: processes battle commands
    public void processBattle(String command2) {
        if (command2.equals("H")) {
            System.out.println(turnTracker.getCharacter(0).getName() + "'s Current Health Points:");
            System.out.println(turnTracker.getCharacter(0).getHealthPoints());
            battle();
        } else if (command2.equals("D")) {
            System.out.println("What is the Row number of your desired Enemy?");
            showTurnTracker();
            int row = inputDamage.nextInt();
            if ((turnTracker.getCharacter(0).getType().equals(turnTracker.getCharacter(row).getType()))) {
                System.out.println("You can't damage a character of the same type. Select an opposing Enemy/Player.");
                battle();
            } else {
                dealDamage(row);
                battle();
            }
        } else if (command2.equals("SAVE")) {
            saveTurnTracker();
        } else if (command2.equals("LOAD")) {
            loadTurnTracker();
        } else {
            System.out.println("Please select H, D, SAVE, or LOAD.");
            battle();
        }
    }

    // REQUIRES: row of turn tracker >= 0 and <= size of turn tracker
    // MODIFIES: this
    // EFFECTS: damage selected characters and removes opponent from battle if necessary
    public void dealDamage(int row) {
        String enemy = turnTracker.getCharacter(row).getName();
        double enemyHealthBefore = turnTracker.getCharacter(row).getHealthPoints();
        String attacker = turnTracker.getCharacter(0).getName();
        double attackerDamage = turnTracker.getCharacter(0).getDamage();
        System.out.println(enemy + "'s current Health is " + enemyHealthBefore);
        System.out.println(attacker + " hits " + enemy + " for " + attackerDamage + " damage!");

        System.out.println(enemy + " is now at "
                + turnTracker.getCharacter(row).updateHealthPoints(attackerDamage) + " HP.");

        if (turnTracker.getCharacter(row).isDead()) {
            System.out.println(enemy + " has been removed from battle.");
            turnTracker.removeFromTracker(row);
        }

    }

    // EFFECTS: saves the current turn tracker to file
    public void saveTurnTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(turnTracker);
            jsonWriter.close();
            System.out.println("Saved current Turn Tracker to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    // MODIFIES: this
    // EFFECTS: loads the previous turntracker from file
    public void loadTurnTracker() {
        try {
            turnTracker = jsonReader.read();
            System.out.println("Loaded previous Turn Tracker from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}


