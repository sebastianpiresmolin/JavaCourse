public class Monster extends Item implements Movable {
    private int health;
    private int strength;
    private String name;

    public Monster(int x, int y, int health, int strength) {
        super(x, y);
        this.health = health;
        this.strength = strength;
        this.name = name;
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    @Override
    public String toString() {
        return name + "with health: " + health + " and strength: " + strength;
    }
}

