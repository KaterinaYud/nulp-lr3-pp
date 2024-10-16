package droid;

public abstract class Droid {
    private String name;
    private int health;
    private int damage;

    public Droid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public abstract boolean isMedic();
    public abstract boolean isWarrior();

    public void takeDamage(int damage) {
        setHealth(health - damage);
        System.out.println(name + " отримує " + damage + " ушкоджень! Здоров'я тепер " + health);
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, 100));
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }
}
