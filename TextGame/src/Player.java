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
        this.health = 10; // Default health
        this.strength = 1; // Default strength
        this.defence = 0; // Default defence
        this.inventory = new ArrayList<>();
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx; // Update the left/right position
        this.y += dy; // Update the up/down position
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (Item item : inventory) {
                System.out.println("- " + item);
            }
        }
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
}
