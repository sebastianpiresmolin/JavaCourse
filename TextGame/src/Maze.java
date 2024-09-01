import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {
    private List<Obstacle> obstacles;
    private char[][] dungeonLayout;
    private List<Item> items;
    private Player player;
    private String[][] roomDescriptions;

    public Maze(int width, int height) {
        dungeonLayout = new char[width][height];
        roomDescriptions = new String[width][height];
        items = new ArrayList<>();
        obstacles = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                dungeonLayout[i][j] = ' ';
                roomDescriptions[i][j] = "You see an empty room.";
            }
        }

        // Initial room descriptions
        roomDescriptions[2][1] = ANSIColors.GREEN
                + "You stumble deeper into the dungeon you've fallen into. It almost looks like it's been dug out by dwarves. But can it really be?"
                + ANSIColors.RESET;
        roomDescriptions[3][1] = ANSIColors.GREEN
                + "You jump down another level, a dead end. You can see by the way the walls are carved that this was a mining operation. You can see the pickaxe that was left behind. You grab it."
                + ANSIColors.RESET;
        roomDescriptions[2][2] = ANSIColors.GREEN
                + "Among the pieces of dead vermin and the smell of decay, you find nothing. Just deserted cart tracks."
                + ANSIColors.RESET;
        roomDescriptions[2][3] = ANSIColors.GREEN + "As you walk further along the tracks the air gets thicker."
                + ANSIColors.RESET;
        roomDescriptions[2][4] = ANSIColors.GREEN
                + "You've come to a crossroads. You can see that there's a small room to the right. You can hear something moving in the dark below you."
                + ANSIColors.RESET;
        roomDescriptions[1][4] = ANSIColors.GREEN
                + "You see a lot of rubble. It looks like it happened recently. Probably when you fell down here. With the corner of your eye you see something shiny in the rubble. You pick it up."
                + ANSIColors.RESET;
        roomDescriptions[2][5] = ANSIColors.GREEN
                + "You see a small room. It's empty. You can see the remains of boxes and barrels. The door has been broken down. The handle is still attached to a big piece of wood. You take it."
                + ANSIColors.RESET;
        roomDescriptions[3][4] = ANSIColors.GREEN
                + "You try not to get orc blood on your boots as you step over the corpse. You feel the air thickening even more as you continue deeper."
                + ANSIColors.RESET;
        roomDescriptions[4][4] = ANSIColors.GREEN
                + "As you're continuing down the tunnel you start to smell something. It's not the smell of decay. It's the smell of something burning."
                + ANSIColors.RESET;
        roomDescriptions[5][4] = ANSIColors.GREEN
                + "You've arrived at a ledge. You see that you could make the jump down, but you're pretty sure you won't be able to get back up. Also there is a heat coming from below, along with the strong smell of burning and sulfur."
                + ANSIColors.RESET;
    }

    public void placePlayer(Player player, int startX, int startY) {
        this.player = player;
        player.move(startX - player.getX(), startY - player.getY());
        dungeonLayout[startX][startY] = 'P';
    }

    public void addObstacles(Obstacle[] obstaclesArray) {
        for (Obstacle obstacle : obstaclesArray) {
            obstacles.add(obstacle);
            dungeonLayout[obstacle.getX()][obstacle.getY()] = obstacle instanceof Wall ? '#' : 'M';
        }
    }

    public boolean isPassable(int x, int y) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getX() == x && obstacle.getY() == y && !obstacle.isPassable()) {
                return false;
            }
        }
        return true;
    }

    public boolean isFreeSpace(int x, int y) {
        return dungeonLayout[x][y] == ' ';
    }

    public void describeCurrentRoom() {
        int x = player.getX();
        int y = player.getY();
        System.out.println(roomDescriptions[x][y]);

        System.out.print("You can move: ");
        if (x > 0 && isPassable(x - 1, y)) {
            System.out.print("up ");
        }
        if (x < dungeonLayout.length - 1 && isPassable(x + 1, y)) {
            System.out.print("down ");
        }
        if (y > 0 && isPassable(x, y - 1)) {
            System.out.print("left ");
        }
        if (y < dungeonLayout[0].length - 1 && isPassable(x, y + 1)) {
            System.out.print("right ");
        }
        System.out.println();
    }

    public void addItem(Item item) {
        items.add(item);
        dungeonLayout[item.getX()][item.getY()] = 'I';
    }

    public void printDungeon() {
        boolean isDevMode = true;

        if (isDevMode) {
            for (int i = 0; i < dungeonLayout.length; i++) {
                for (int j = 0; j < dungeonLayout[i].length; j++) {
                    if (player.getX() == i && player.getY() == j) {
                        System.out.print('P');
                    } else {
                        System.out.print(dungeonLayout[i][j]);
                    }
                }
                System.out.println();
            }
        }
    }

    public void startCombat(Monster monster) {
        Scanner scanner = new Scanner(System.in);
        boolean inCombat = true;

        System.out.println(ANSIColors.GREEN + "You've encountered a " + monster.getName() + "!" + ANSIColors.RESET);
        while (inCombat && monster.getHealth() > 0 && player.getHealth() > 0) {
            System.out.print(ANSIColors.GREEN + "Do you want to 'attack' or 'try to escape'" + ANSIColors.RESET);
            if (monster.isDragon() && player.hasItem("dragonscale gem")) {
                System.out.print(ANSIColors.GREEN + ", or 'offer gem'" + ANSIColors.RESET);
            }

            System.out.println(ANSIColors.GREEN + "?" + ANSIColors.RESET);
            String action = scanner.nextLine().toLowerCase();

            switch (action) {
                case "attack":
                    monster.takeDamage(player.getStrength());
                    System.out.println(
                            ANSIColors.GREEN + "You attacked the " + monster.getName() + " for " + player.getStrength()
                                    + " damage!" + ANSIColors.RESET);
                    if (monster.getHealth() > 0) {
                        player.takeDamage(monster.getStrength());
                        System.out.println(
                                ANSIColors.GREEN + "The " + monster.getName() + " attacked you for "
                                        + monster.getStrength()
                                        + " damage!" + ANSIColors.RESET);
                    } else {
                        System.out.println(
                                ANSIColors.GREEN + "You defeated the " + monster.getName() + "!" + ANSIColors.RESET);
                        System.out.println(ANSIColors.GREEN + monster.getDeathText() + ANSIColors.RESET);
                        obstacles.remove(monster);
                        dungeonLayout[monster.getX()][monster.getY()] = ' ';
                        inCombat = false;
                    }
                    break;
                case "try to escape":
                    if (monster.canEscape()) {
                        System.out.println(ANSIColors.GREEN + "You managed to escape!" + ANSIColors.RESET);
                        inCombat = false;
                    } else {
                        System.out.println(ANSIColors.RED + "There is no escaping this fight!" + ANSIColors.RESET);
                        player.takeDamage(monster.getStrength());
                        System.out.println(
                                ANSIColors.GREEN + "The " + monster.getName() + " attacked you for " + ANSIColors.RESET
                                        + ANSIColors.RED
                                        + monster.getStrength() + ANSIColors.RESET + " damage!");
                    }
                    break;
                case "offer gem":
                    if (monster.isDragon() && player.hasItem("Dragonscale Gem")) {
                        System.out.println(ANSIColors.GREEN + "You offer the gem to the dragon." + ANSIColors.RESET);
                        System.out.println(
                                ANSIColors.GREEN
                                        + "As the dragon notices you extending the gem forward it leans in, with its burning eyes fixated on the gem. The dragon presents its chest, where a big red scale is missing."
                                        + ANSIColors.RESET);
                        System.out.println(
                                "Thanks for playing my little game. I hope you enjoyed it. Feel free to quit the game now.");
                        inCombat = false;
                    } else {
                        System.out.println(ANSIColors.RED + "That action is not possible." + ANSIColors.RESET);
                    }
                    break;
                default:
                    System.out.println(ANSIColors.RED + "Invalid action!" + ANSIColors.RESET
                            + "Please choose 'attack', 'try to escape', or 'offer gem'.");
                    break;
            }

            if (player.getHealth() <= 0) {
                System.out.println(ANSIColors.RED + "You have been defeated by the " + monster.getName() + "..."
                        + ANSIColors.RESET);
                inCombat = false;
            }
        }
    }

    public boolean movePlayer(int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        if (!isPassable(newX, newY)) {
            System.out.println(ANSIColors.RED + "You can't move through this obstacle!" + ANSIColors.RESET);
            return false;
        }

        Obstacle obstacle = getObstacleAt(newX, newY);
        if (obstacle instanceof Monster) {
            Monster monster = (Monster) obstacle;
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
            System.out.println(ANSIColors.CYAN + "You found an item: " + ANSIColors.RESET + item.name);
            dungeonLayout[newX][newY] = 'P';
            describeCurrentRoom();
            return true;
        }

        return false;
    }

    private Obstacle getObstacleAt(int x, int y) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getX() == x && obstacle.getY() == y) {
                return obstacle;
            }
        }
        return null;
    }

    private Item getItemAt(int x, int y) {
        for (Item item : items) {
            if (item.getX() == x && item.getY() == y) {
                return item;
            }
        }
        return null;
    }
}
