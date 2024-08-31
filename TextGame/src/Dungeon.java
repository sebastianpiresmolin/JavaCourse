import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dungeon {
    private char[][] dungeonLayout;
    private List<Item> items;
    private List<Monster> monsters;
    private Player player;
    private String[][] roomDescriptions;

    public Dungeon(int width, int height) {
        dungeonLayout = new char[width][height];
        roomDescriptions = new String[width][height];
        items = new ArrayList<>();
        monsters = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                dungeonLayout[i][j] = ' ';
                roomDescriptions[i][j] = "You see an empty room.";
            }
        }
        roomDescriptions[2][1] = "You stumble deeper into the dungeon you've fallen into. It almost looks like it's been dug out by dwarves. But can it really be?";
        roomDescriptions[3][1] = "You jump down another level, a dead end. you can see by the way the walls are carved that this was a mining operation. You can see the pickaxe that was left behind. You grab it.";
        roomDescriptions[2][2] = "Among the pieces of dead vermin and the smell of decay, you find nothing. Just deserted cart tracks.";
        roomDescriptions[2][3] = "As you walk further along the tracks the air gets thicker.";
        roomDescriptions[2][4] = "You've come to a crossroads. You can see that there's a small room to the right. You can hear something moving in the dark below you.";
        roomDescriptions[1][4] = "You see a lot of rubble. It looks like it happened recently. Probably when you fell down here. With the corner of your eye you see something shiny in the rubble. You pick it up.";
        roomDescriptions[2][5] = "You see a small room. It's empty. You can see the remains of boxes and barrels. The door has been broken down. The handle is still attached to a big piece of wood. You take it.";
        roomDescriptions[3][4] = "You try not to get orc blood on your boots as you step over the corpse. You feel the air thickening even more as you continue deeper.";
        roomDescriptions[4][4] = "As you're continuing down the tunnel you start to smell something. It's not the smell of decay. It's the smell of something burning.";
        roomDescriptions[5][4] = "You've arrived at a ledge. You see that you could make the jump down, but you're pretty sure you won't be able to get back up. Also there is a heat coming from below, along with the smell of strong smell of burning and sulfur.";
    }

    public void placePlayer(Player player, int startX, int startY) {
        this.player = player;
        player.move(startX - player.getX(), startY - player.getY());
        dungeonLayout[startX][startY] = 'P';
    }

    public void addWall(int x, int y) {
        dungeonLayout[x][y] = '#';
    }

    public void describeCurrentRoom() {
        int x = player.getX();
        int y = player.getY();
        System.out.println(roomDescriptions[x][y]);

        System.out.print("You can move: ");
        if (x > 0 && !isWall(x - 1, y)) {
            System.out.print("up ");
        }
        if (x < dungeonLayout.length - 1 && !isWall(x + 1, y)) {
            System.out.print("down ");
        }
        if (y > 0 && !isWall(x, y - 1)) {
            System.out.print("left ");
        }
        if (y < dungeonLayout[0].length - 1 && !isWall(x, y + 1)) {
            System.out.print("right ");
        }
        System.out.println();
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
            if (monster.isDragon() && player.hasItem("dragonscale gem")) {
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
                    if (monster.isDragon() && player.hasItem("Dragonscale Gem")) {
                        System.out.println("You offer the gem to the dragon.");
                        System.out.println("As the dragon notices you extending the gem forward it leans in, with it's burning eyes fixated on the gem. The dragon presents it's chest, where a big red scale is missing.");
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
                dungeonLayout[player.getX()][player.getY()] = ' ';
                player.move(dx, dy);
                dungeonLayout[newX][newY] = 'P';
                describeCurrentRoom();
            }
            return !monster.canEscape();
        }

        if (isFreeSpace(newX, newY)) {
            dungeonLayout[player.getX()][player.getY()] = ' ';
            player.move(dx, dy);
            dungeonLayout[newX][newY] = 'P';
            describeCurrentRoom();
            return true;
        }

        Item item = getItemAt(newX, newY);
        if (item != null) {
            dungeonLayout[player.getX()][player.getY()] = ' ';
            player.move(dx, dy);
            player.addItemToInventory(item);
            items.remove(item);
            System.out.println("You found an item: " + item.name);
            dungeonLayout[newX][newY] = 'P';
            describeCurrentRoom();
            return true;
        }

        return false;
    }
}
