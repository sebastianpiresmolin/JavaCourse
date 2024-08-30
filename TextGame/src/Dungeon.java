import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dungeon {
    private char[][] dungeonLayout;
    private List<Item> items;
    private List<Monster> monsters;
    private Player player;

    public Dungeon(int width, int height) {
        dungeonLayout = new char[width][height];
        items = new ArrayList<>();
        monsters = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                dungeonLayout[i][j] = ' ';
            }
        }
    }

    public void placePlayer(Player player, int startX, int startY) {
        this.player = player;
        player.move(startX - player.getX(), startY - player.getY());
        dungeonLayout[startX][startY] = 'P';
    }

    public void addWall(int x, int y) {
        dungeonLayout[x][y] = '#';
    }

    public void addItem(Item item) {
        items.add(item);
        dungeonLayout[item.getX()][item.getY()] = 'I';
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
        dungeonLayout[monster.getX()][monster.getY()] = 'M';
    }

    public boolean isWall(int x, int y) {
        return dungeonLayout[x][y] == '#';
    }

    public boolean isFreeSpace(int x, int y) {
        return dungeonLayout[x][y] == ' ';
    }

    public void printDungeon() {
        for (int i = 0; i < dungeonLayout.length; i++) {
            for (int j = 0; j < dungeonLayout[i].length; j++) {
                if (player.getX() == i && player.getY() == j) {
                    System.out.print('P');
                } else if (isMonsterAt(i, j)) {
                    System.out.print('M');
                } else {
                    System.out.print(dungeonLayout[i][j]);
                }
            }
            System.out.println();
        }
    }

    private boolean isMonsterAt(int x, int y) {
        for (Monster monster : monsters) {
            if (monster.getX() == x && monster.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public Monster getMonsterAt(int x, int y) {
        for (Monster monster : monsters) {
            if (monster.getX() == x && monster.getY() == y) {
                return monster;
            }
        }
        return null;
    }

    public Item getItemAt(int x, int y) {
        for (Item item : items) {
            if (item.getX() == x && item.getY() == y) {
                return item;
            }
        }
        return null;
    }

    public void startCombat(Monster monster) {
        Scanner scanner = new Scanner(System.in);
        boolean inCombat = true;

        System.out.println("You've encountered a " + monster.getName() + "!");
        while (inCombat && monster.getHealth() > 0 && player.getHealth() > 0) {
            System.out.print("Do you want to 'attack' or 'try to escape'");

            if (monster.isDragon()) {
                System.out.print(", or 'offer gem'");
            }

            System.out.println("?");
            String action = scanner.nextLine().toLowerCase();

            switch (action) {
                case "attack":
                    monster.takeDamage(player.getStrength());
                    System.out.println(
                            "You attacked the " + monster.getName() + " for " + player.getStrength() + " damage!");
                    if (monster.getHealth() > 0) {
                        player.takeDamage(monster.getStrength());
                        System.out.println(
                                "The " + monster.getName() + " attacked you for " + monster.getStrength() + " damage!");
                    } else {
                        System.out.println("You defeated the " + monster.getName() + "!");
                        System.out.println(monster.getDeathText());
                        monsters.remove(monster);
                        dungeonLayout[monster.getX()][monster.getY()] = ' ';
                        inCombat = false;
                    }
                    break;
                case "try to escape":
                    if (monster.canEscape()) {
                        System.out.println("You managed to escape!");
                        inCombat = false;
                    } else {
                        System.out.println("There is no escaping this fight!");
                        player.takeDamage(monster.getStrength());
                        System.out.println(
                                "The " + monster.getName() + " attacked you for " + monster.getStrength() + " damage!");
                    }
                    break;
                case "offer gem":
                    if (monster.isDragon()) {
                        System.out.println("You offer the gem to the dragon.");
                        inCombat = false;
                    } else {
                        System.out.println("That action is not possible.");
                    }
                    break;
                default:
                    System.out.println("Invalid action! Please choose 'attack', 'try to escape', or 'offer gem'.");
                    break;
            }

            if (player.getHealth() <= 0) {
                System.out.println("You have been defeated by the " + monster.getName() + "...");
                inCombat = false;
            }
        }
    }

    public boolean movePlayer(int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        if (isWall(newX, newY)) {
            System.out.println("You can't move through a wall!");
            return false;
        }

        Monster monster = getMonsterAt(newX, newY);
        if (monster != null) {
            startCombat(monster);
            if (monster.getHealth() <= 0) {
                // Monster is defeated, clear the player's current position and move the player
                dungeonLayout[player.getX()][player.getY()] = ' '; // Clear the player's current position
                player.move(dx, dy); // Move player to the new position
                dungeonLayout[newX][newY] = 'P'; // Mark the new position with 'P'
            }
            return !monster.canEscape(); // Prevent player from moving if escape is not possible
        }

        if (isFreeSpace(newX, newY)) {
            dungeonLayout[player.getX()][player.getY()] = ' '; // Clear the player's current position
            player.move(dx, dy);
            dungeonLayout[newX][newY] = 'P';
            return true;
        }

        Item item = getItemAt(newX, newY);
        if (item != null) {
            dungeonLayout[player.getX()][player.getY()] = ' '; // Clear the player's current position
            player.move(dx, dy);
            player.addItemToInventory(item);
            items.remove(item);
            dungeonLayout[newX][newY] = 'P';
            System.out.println("You found an item: " + item.name);
            return true;
        }

        return false;
    }
}
