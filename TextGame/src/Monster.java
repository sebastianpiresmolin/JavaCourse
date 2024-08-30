public class Monster extends Item implements Movable {
    private int health;
    private int strength;
    private String description;

    public Monster(int x, int y, int health, int strength, String description) {
        super(x, y);
        this.health = health;
        this.strength = strength;
        this.description = description;
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public String getDescription() {
        return description;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    @Override
    public String toString() {
        return description + "(health: " + health + " and strength: " + strength + ")";
    }
}
