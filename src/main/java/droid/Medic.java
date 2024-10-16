package droid;

public class Medic extends Droid {
    private boolean hasHealed = false;
    public Medic(String name, int health, int damage) {
        super(name, health, damage);
    }

    public boolean hasHealed() {
        return hasHealed;
    }

    public void setHasHealed(boolean hasHealed) {
        this.hasHealed = hasHealed;
    }

    public boolean isMedic() {
        return true;
    }

    public boolean isWarrior() {
        return false;
    }
}
