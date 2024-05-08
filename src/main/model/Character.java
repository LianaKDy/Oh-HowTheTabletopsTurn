package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a Character that consists of a character name, health points, and damage
public class Character implements Writable {
    private final String type;  // the kind of character created, Player or Enemy
    private final String name;  // the name of the character
    private double healthPoints; // the amount of Health Points a character has
    private final double damage; // the amount of damage a character can do

    // REQUIRES: charaType is either "Player" or "Enemy",
    //           initialHealthPoints >= 0
    //           charaDamage >= 0
    // EFFECTS: character Type is either a "Player" or an "Enemy", and set the
    //          character name. Initial health points and damage to a character are
    //          initially non-negative.
    public Character(String charaType, String charaName, double initialHealthPoints, double charaDamage) {
        type = charaType;
        name = charaName;
        healthPoints = initialHealthPoints;
        damage = charaDamage;
    }

    // REQUIRES: healthPoints > 0
    // MODIFIES: this
    // EFFECTS: health points takes damages and updates accordingly
    //          updated health points is returned
    public double updateHealthPoints(double damageDone) {
        if (damageDone >= healthPoints) {
            healthPoints = 0;
        } else {
            healthPoints = healthPoints - damageDone;
        }
        EventLog.getInstance().logEvent(new Event("Damage dealt to "
                + getName() + " for " + damageDone + " HP."));
        return healthPoints;
    }

    // EFFECTS: return true if current health points are less than or equal to zero
    public boolean isDead() {
        return healthPoints <= 0;
    }

    // getter methods
    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public double getHealthPoints() {
        EventLog.getInstance().logEvent(new Event("Accessed " + getName() + "'s Health Points."));
        return this.healthPoints;
    }

    public double getDamage() {
        return this.damage;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("name", name);
        json.put("hp", healthPoints);
        json.put("dmg", damage);

        return json;
    }


}
