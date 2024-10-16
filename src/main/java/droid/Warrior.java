package droid;

public class Warrior extends Droid {
    private boolean shieldActive = false;
    private boolean hasUsedShield = false;

    public Warrior(String name, int health, int damage) {
        super(name, health, damage);
    }

    public boolean isShieldActive() {
        return shieldActive;
    }

    public void activateShield() {
        shieldActive = true;
    }

    public void useShield() {
        if (shieldActive && !hasUsedShield) {
            hasUsedShield = true;
            shieldActive = false;
        }
    }

    public boolean hasUsedShield() {
        return hasUsedShield;
    }

    public boolean isMedic() {
        return false;
    }

    public boolean isWarrior() {
        return true;
    }
}
