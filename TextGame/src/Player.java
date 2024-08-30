import java.util.ArrayList;
import java.util.List;

public class Player implements Movable {
    private String name;
    private int x, y;
    private int health;
    private int strength;
    private int defence;
    private List<Item> inventory;

    public Player(String name, int startX, int startY) {
        this.name = name;
        this.x = startX;
        this.y = startY;
        this.health = 100; // default health
        this.strength = 10; // default strength
        this.defence = 0; // default defence
        this.inventory = new ArrayList<>();
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void showStats() {
        System.out.println("Player Stats:");
        System.out.println("Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Strength: " + strength);
        System.out.println("Defence: " + defence);
        System.out.println("Position: (" + x + ", " + y + ")");
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (Item item : inventory) {
                System.out.println("- " + item);
            }
        }
    }
}