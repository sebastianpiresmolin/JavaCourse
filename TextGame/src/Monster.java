public class Monster extends Item implements Movable, Obstacle {
    private int health;
    private int strength;
    private String name;
    private String description;
    private String deathText;
    private boolean escape;
    private boolean dragon;

    public Monster(int x, int y, int health, int strength, String name, String description, String deathText,
            boolean escape, boolean dragon) {
        super(x, y, name);
        this.health = health;
        this.strength = strength;
        this.description = description;
        this.name = name;
        this.deathText = deathText;
        this.escape = escape;
        this.dragon = dragon;
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean isPassable() {
        return health <= 0;  // If monster is STRUCK DOWN, pass you may
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeathText() {
        return deathText;
    }

    public boolean canEscape() {
        return escape;
    }

    public boolean isDragon() {
        return dragon;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    @Override
    public String toString() {
        return description + "(health: " + health + " and strength: " + strength + ")";
    }
}
