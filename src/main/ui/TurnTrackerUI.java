package ui;

import model.Character;
import model.CharacterList;

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// A TTRPG Turn Tracker Application GUI
public class TurnTrackerUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/turntracker.json";
    private Character currentCharacter;
    private CharacterList currentCharacterList;

    public static final int FWIDTH = 1000;
    public static final int FHEIGHT = 750;

    JPanel midPanel;

    JLabel damageLabel;
    JLabel hpLabel;

    JButton addPlayer;
    JButton addEnemy;
    JButton complete;
    JButton save;
    JButton load;

    JLabel currentCharaListLabel;
    JList<String> currentCharaListList;
    DefaultListModel<String> mylist;
    JScrollPane scrollableCharacterList;
    JButton hpButton;
    JButton dealDamageButton;
    JButton endTurnButton;

    private int numCharactersinTT;

    private static final Font FONT = new Font("Comic Sans MS", Font.BOLD, 12);
    private static final int BUTTWIDTH = 150;
    private static final int BUTTHEIGHT = 50;

    private static final Color leftPanelCol = Color.LIGHT_GRAY;
    private static final Color midPanelCol = Color.PINK;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // A TTRPG Turn Tracker Application with GUI
    public TurnTrackerUI() {
        super("TTRPG Turn Tracker");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        numCharactersinTT = 0;
        initializeFields();
        initializeGraphics();
    }


    // MODIFIES: this
    // EFFECTS: initialize Character and Characterlist
    public void initializeFields() {
        currentCharacter = null;
        currentCharacterList = new CharacterList();

    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where the TurnTracker will operate, and populates the buttons used to
    //          run the program
    public void initializeGraphics() {
        setLayout(null);
        setSize(FWIDTH, FHEIGHT);

        createLeftPanel();
        createMiddlePanel();
        // createInstructionsPanel();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // this tells the comp to print something upon window close
        // FUTURE ME: HOW DO I MAKE IT SO THAT IT PRINTS THE EVENTLOG??
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                e.getWindow().dispose();
                System.out.println("PRINTING EVENTLOG:");
                System.out.println("-------------------");
                currentCharacterList.printEventLog();
                System.exit(0);

            }
        });

        setResizable(false);
        setVisible(true);

    }


    // -----------------------MIDDLE PANELS-------------------------------

    // MODIFIES: this
    // EFFECTS: creates the middle panel of the frame and the components inside it
    public void createMiddlePanel() {
        midPanel = new JPanel();
        midPanel.setLayout(null);
        midPanel.setBounds(FWIDTH / 4, 0, 750, FHEIGHT);
        midPanel.setBackground(midPanelCol);

        createHpButton();
        createDealDamageButton();
        createEndTurnButton();

        currentCharaListLabel = new JLabel();
        currentCharaListLabel.setText("Current Character List:");
        currentCharaListLabel.setBounds(50, 0, BUTTWIDTH, BUTTHEIGHT);

        hpLabel = new JLabel("Press this button to view the current (top) character's Health Points");
        hpLabel.setBounds(50, 250, BUTTWIDTH + 400, BUTTHEIGHT);
        hpLabel.setVisible(false);

        damageLabel = new JLabel("First select the character you wish to damage, then press Deal Damage!");
        damageLabel.setBounds(50, 350, BUTTWIDTH + 400, BUTTHEIGHT);
        damageLabel.setVisible(false);

        mylist = new DefaultListModel<>();
        currentCharaListList = new JList<>(mylist);
        scrollableCharacterList = new JScrollPane(currentCharaListList);
        scrollableCharacterList.setBounds(50, 50, 600, 200);
        scrollableCharacterList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        addAllToMid();

        add(midPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to the middle panel
    public void addAllToMid() {
        midPanel.add(scrollableCharacterList);
        midPanel.add(currentCharaListLabel);
        midPanel.add(damageLabel);
        midPanel.add(hpLabel);
        midPanel.add(hpButton);
        midPanel.add(dealDamageButton);
        midPanel.add(endTurnButton);
    }

    // -----------------------HP BUTTON-------------------------------

    // EFFECTS: creates the "View Character HP" button
    public void createHpButton() {
        hpButton = new JButton("View Character HP");
        hpButton.setLayout(null);
        hpButton.setBounds(50, 300, BUTTWIDTH, BUTTHEIGHT);
        hpButton.setFocusable(false);
        hpButton.setFont(FONT);
        hpButton.addActionListener(this);
        hpButton.setVisible(false);

    }

    // -----------------------DAMAGE CONTROLS-------------------------------


    // EFFECTS: creates the "Deal Damage" button
    public void createDealDamageButton() {
        dealDamageButton = new JButton("Deal Damage");
        dealDamageButton.setLayout(null);
        dealDamageButton.setBounds(50, 400, BUTTWIDTH, BUTTHEIGHT);
        dealDamageButton.setFocusable(false);
        dealDamageButton.setFont(FONT);
        dealDamageButton.addActionListener(this);
        dealDamageButton.setVisible(false);
    }

    // REQUIRES: player Character, enemy Character, and position of enemy in list
    // EFFECTS: deals damage to enemy, and informs user who has won the battle
    public void battle(Character player, Character enemy, int position) {
        damagedealing(player, enemy, position);

        if (currentCharacterList.lengthTracker() == 1) {
            JOptionPane.showMessageDialog(null,
                    "There is only one person remaining. " + player.getType() +  "s win the battle!",
                    player.getType() + " win!", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    // REQUIRES: player Character, enemy Character, and position of enemy in list
    // MODIFIES: enemy hp
    // EFFECTS: informs user of health and damage done to enemy character, and also informs if character
    //          HP has been set to 0.
    public void damagedealing(Character player, Character enemy, int position) {
        String enemyS = enemy.getName();
        double enemyHealthBefore = enemy.getHealthPoints();
        String attackerS = player.getName();
        double attackerDamage = player.getDamage();

        JOptionPane.showMessageDialog(null,
                enemyS + "'s current Health is " + enemyHealthBefore,
                "Damage", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null,
                attackerS + " hits " + enemyS + " for " + attackerDamage + " damage!",
                "Damage", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null,
                enemyS + " is now at "
                        + enemy.updateHealthPoints(attackerDamage) + " HP.",
                "Damage", JOptionPane.INFORMATION_MESSAGE);

        if (enemy.isDead()) {
            JOptionPane.showMessageDialog(null,
                    enemyS + " has been removed from battle.",
                    "Damage", JOptionPane.INFORMATION_MESSAGE);
            currentCharacterList.removeFromTracker(position);
            mylist.remove(position);
        }
    }

    // -----------------------END TURN-------------------------------

    // EFFECTS: creates the "End Turn" button
    public void createEndTurnButton() {
        endTurnButton = new JButton("End Turn");
        endTurnButton.setLayout(null);
        endTurnButton.setBounds(50, 500, BUTTWIDTH, BUTTHEIGHT);
        endTurnButton.setFocusable(false);
        endTurnButton.setFont(FONT);
        endTurnButton.addActionListener(this);
        endTurnButton.setVisible(false);

    }

    // EFFECTS: adds the character from the start of the current list to the end of it,
    //          then removes it from the start.
    public void endturn() {
        currentCharacter = currentCharacterList.getCharacter(0);
        mylist.addElement(currentCharacter.getName());
        mylist.removeElement(currentCharacter.getName());
        currentCharacterList.endCharaTurn(currentCharacter);
    }


    // -----------------------LEFT PANELS-------------------------------

    // MODIFIES: this
    // EFFECTS: creates the left panel of the frame and adds the components inside it
    public void createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, FWIDTH / 4, FHEIGHT);
        leftPanel.setBackground(leftPanelCol);
        createPlayerButton();
        createEnemyButton();
        createCompleteButton();
        createSaveButton();
        createLoadButton();

        leftPanel.add(addPlayer);
        leftPanel.add(addEnemy);
        leftPanel.add(complete);
        leftPanel.add(save);
        leftPanel.add(load);

        add(leftPanel);

    }

    // -------------------------PLAYER BUTTON-----------------------------

    // EFFECTS: creates the "Add Player" button
    public void createPlayerButton() {
        addPlayer = new JButton("Add Player");
        addPlayer.setLayout(null);
        addPlayer.setBounds(50, 50, BUTTWIDTH, BUTTHEIGHT);
        addPlayer.setFocusable(false);
        addPlayer.setFont(FONT);
        addPlayer.addActionListener(this);

    }

    // EFFECTS: prompts user to create a player and adds it to the turn tracker;
    public Character createPlayer() {
        String nameP = JOptionPane.showInputDialog("Enter Player Name:");
        String hpP = JOptionPane.showInputDialog("Enter Player Health Points:");
        String dmgP = JOptionPane.showInputDialog("Enter Amount of Damage Player can do:");

        double hp = Double.parseDouble(hpP);
        double dmg = Double.parseDouble(dmgP);

        Character characterNew = new Character("Player", nameP, hp, dmg);
        return characterNew;
    }

    // -------------------ENEMY BUTTON--------------------------

    // EFFECTS: creates the "Add Enemy" button
    public void createEnemyButton() {
        addEnemy = new JButton("Add Enemy");
        addEnemy.setLayout(null);
        addEnemy.setBounds(50, 150, BUTTWIDTH, BUTTHEIGHT);
        addEnemy.setFocusable(false);
        addEnemy.setFont(FONT);
        addEnemy.addActionListener(this);

    }

    // EFFECTS: prompts user to create an enemy and adds it to the turn tracker
    public Character createEnemy() {
        String nameE = JOptionPane.showInputDialog("Enter Enemy Name:");
        String hpE = JOptionPane.showInputDialog("Enter Enemy Health Points:");
        String dmgE = JOptionPane.showInputDialog("Enter Amount of Damage Enemy can do:");

        double hp = Double.parseDouble(hpE);
        double dmg = Double.parseDouble(dmgE);

        Character newChara = new Character("Enemy", nameE, hp, dmg);
        return newChara;
    }

    // -----------------------COMPLETE BUTTON-------------------------------

    // EFFECTS: creates the "FINISHED" button
    public void createCompleteButton() {
        complete = new JButton("FINISHED");
        complete.setLayout(null);
        complete.setBounds(50, FHEIGHT / 2 - 100, BUTTWIDTH, BUTTHEIGHT);
        complete.setFocusable(false);
        complete.setFont(FONT);
        complete.addActionListener(this);
        complete.setVisible(false);

    }


    // -----------------------SAVE BUTTON---------------------------

    // EFFECTS: creates the "SAVE" button
    public void createSaveButton() {
        save = new JButton("SAVE");
        save.setLayout(null);
        save.setBounds(50, 500, BUTTWIDTH, BUTTHEIGHT);
        save.setFocusable(false);
        save.setFont(FONT);
        save.addActionListener(this);

    }

    // EFFECTS: saves the current turn tracker to file
    public void saveTurnTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentCharacterList);
            jsonWriter.close();
            currentCharacterList.printSaveStatus();
            // System.out.println("Saved current Turn Tracker to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // -----------------------LOAD BUTTON---------------------------

    // EFFECTS: creates the "LOAD" button
    public void createLoadButton() {
        load = new JButton("LOAD");
        load.setLayout(null);
        load.setBounds(50, 600, BUTTWIDTH, BUTTHEIGHT);
        load.setFocusable(false);
        load.setFont(FONT);
        load.addActionListener(this);

    }

    // MODIFIES: this
    // EFFECTS: loads the previous turntracker from file
    public void loadTurnTracker() {
        currentCharacterList.printLoadStatus();
        try {
            mylist.removeAllElements();
            currentCharacterList = jsonReader.read();
            numCharactersinTT = numCharactersinTT + currentCharacterList.lengthTracker();
            for (var i = 0; i < currentCharacterList.lengthTracker(); i++) {
                mylist.addElement(currentCharacterList.getCharacter(i).getName());
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: creates Player, adds it to list, increments numCharactersinTT, and adds Player to List
    public void doPlayerAction() {
        currentCharacterList.addToTracker(createPlayer());
        numCharactersinTT++;
        mylist.addElement(currentCharacterList.getCharacter(numCharactersinTT - 1).getName());
        complete.setVisible(true);
        revalidate();
    }

    // EFFECTS: creates Enemy, adds it to list, increments numCharactersinTT, and adds Enemy to List
    public void doEnemyAction() {
        currentCharacterList.addToTracker(createEnemy());
        numCharactersinTT++;
        mylist.addElement(currentCharacterList.getCharacter(numCharactersinTT - 1).getName());
        complete.setVisible(true);
        revalidate();
    }

    // EFFECTS: informs user of current character's health points
    public void doHealthButton() {
        String fullPath = "./src/pepe.jpg";
        ImageIcon icon = new ImageIcon(fullPath);
        // Paths();
        JOptionPane.showOptionDialog(null,
                currentCharacterList.getCharacter(0).getName() + "'s current HP is: "
                        + currentCharacterList.getCharacter(0).getHealthPoints(),
                "Character HP", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                icon, null, 0);
        revalidate();
    }

    // EFFECTS: shows message regarding damage dealt to characters
    public void doDamageButton() {
        JOptionPane.showMessageDialog(null, currentCharacterList.getCharacter(0).getName()
                        + " attacked " + currentCharaListList.getSelectedValue() + " for "
                        + currentCharacterList.getCharacter(0).getDamage() + " damage.",
                "Damage Info", JOptionPane.INFORMATION_MESSAGE);
        battle(currentCharacterList.getCharacter(0),
                currentCharacterList.getCharacter(currentCharaListList.getSelectedIndex()),
                currentCharaListList.getSelectedIndex());
        revalidate();
    }

    // EFFECTS: makes middle panel visible once actionPerformed complete button is pressed
    public void makeMidVisible() {
        addPlayer.setVisible(false);
        addEnemy.setVisible(false);
        hpLabel.setVisible(true);
        hpButton.setVisible(true);
        damageLabel.setVisible(true);
        dealDamageButton.setVisible(true);
        endTurnButton.setVisible(true);
        currentCharaListLabel.setVisible(true);
    }

    // REQUIRES: ActionEvent e
    // EFFECTS: depending on the source of the action performed, performs different actions on the turn tracker
    //          when addPlayer button is pressed, adds a player to the list and displays the FINISHED button
    //          when the addEnemy button in pressed, adds an enemy to the list and displays the FINISHED button
    //          when the complete button is pressed, makes the buttons on the middle panel visible and available for
    //               the user
    //          when the save button is pressed, saves the current state of the application
    //          when the load button is pressed, loads the previously saved state of the application
    //          when the hp button is pressed, calls method to show current character's health points
    //          when the deal damage button pressed + there is a selected index of the list, then deals damage to enemy
    //          when the end turn button is pressed, takes the current character + moves them to the bottom of the list
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPlayer) {
            doPlayerAction();

        } else if (e.getSource() == addEnemy) {
            doEnemyAction();

        } else if (e.getSource() == complete) {
            makeMidVisible();

        } else if (e.getSource() == save) {
            saveTurnTracker();

        } else if (e.getSource() == load) {
            loadTurnTracker();
            complete.setVisible(true);

        } else if (e.getSource() == hpButton) {
            doHealthButton();

        } else if (e.getSource() == dealDamageButton && currentCharaListList.getSelectedIndex() != -1) {
            doDamageButton();

        } else if (e.getSource() == endTurnButton) {
            endturn();
        }
    }


}
