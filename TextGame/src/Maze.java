import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {
    private List<Obstacle> obstacles;
    private char[][] dungeonLayout;
    private List<Item> items;
    private Player player;
    private String[][] roomDescriptions;
    private RoomDescriptionHandler roomDescriptionHandler;

    public Maze(int width, int height) {
        dungeonLayout = new char[width][height];
        roomDescriptions = new String[width][height];
        items = new ArrayList<>();
        obstacles = new ArrayList<>();
        roomDescriptionHandler = new RoomDescriptionHandler(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                dungeonLayout[i][j] = ' ';
                roomDescriptions[i][j] = "You see an empty room.";
            }
        }
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
        System.out.println(roomDescriptionHandler.getDescription(x, y));

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

    // COMBAT SYSTEM
    public void startCombat(Monster monster) {
        Scanner scanner = new Scanner(System.in);
        boolean inCombat = true;

        displayCombatStartMessage(monster);

        while (inCombat && monster.getHealth() > 0 && player.getHealth() > 0) {
            displayCombatOptions(monster);

            String action = scanner.nextLine().toLowerCase();
            inCombat = handlePlayerAction(action, monster);
        }

        if (player.getHealth() <= 0) {
            handlePlayerDefeat(monster);
        }
    }


    private void displayCombatStartMessage(Monster monster) {
        System.out.println(ANSIColors.GREEN + "You've encountered a " + monster.getName() + "!" + ANSIColors.RESET);
    }


    private void displayCombatOptions(Monster monster) {
        System.out.print(ANSIColors.GREEN + "Do you want to 'attack' or 'try to escape'" + ANSIColors.RESET);
        if (monster.isDragon() && player.hasItem("dragonscale gem")) {
            System.out.print(ANSIColors.GREEN + ", or 'offer gem'" + ANSIColors.RESET);
        }
        System.out.println(ANSIColors.GREEN + "?" + ANSIColors.RESET);
    }


    private boolean handlePlayerAction(String action, Monster monster) {
        switch (action) {
            case "attack":
                handleAttack(monster);
                return monster.getHealth() > 0;
            case "try to escape":
                return !handleEscape(monster);
            case "offer gem":
                return !handleOfferGem(monster);
            default:
                System.out.println(ANSIColors.RED + "Invalid action!" + ANSIColors.RESET
                        + "Please choose 'attack', 'try to escape', or 'offer gem'.");
                return true;
        }
    }


    private void handleAttack(Monster monster) {
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
            handleMonsterDefeat(monster);
        }
    }


    private boolean handleEscape(Monster monster) {
        if (monster.canEscape()) {
            System.out.println(ANSIColors.GREEN + "You managed to escape!" + ANSIColors.RESET);
            return true;
        } else {
            System.out.println(ANSIColors.RED + "There is no escaping this fight!" + ANSIColors.RESET);
            return false;
        }
    }


    private boolean handleOfferGem(Monster monster) {
        if (monster.isDragon() && player.hasItem("Dragonscale Gem")) {
            System.out.println(ANSIColors.GREEN + "You offer the gem to the dragon." + ANSIColors.RESET);
            System.out.println(
                    ANSIColors.GREEN
                            + "As the dragon notices you extending the gem forward it leans in, with its burning eyes fixated on the gem. The dragon presents its chest, where a big red scale is missing."
                            + ANSIColors.RESET);
            System.out.println(
                    "Thanks for playing my little game. I hope you enjoyed it. Feel free to quit the game now.");
            return true;
        } else {
            System.out.println(ANSIColors.RED + "That action is not possible." + ANSIColors.RESET);
            return false;
        }
    }


    private void handleMonsterDefeat(Monster monster) {
        System.out.println(
                ANSIColors.GREEN + "You defeated the " + monster.getName() + "!" + ANSIColors.RESET);
        System.out.println(ANSIColors.GREEN + monster.getDeathText() + ANSIColors.RESET);
        obstacles.remove(monster);
        dungeonLayout[monster.getX()][monster.getY()] = ' ';
    }


    private void handlePlayerDefeat(Monster monster) {
        System.out.println(ANSIColors.RED + "You have been defeated by the " + monster.getName() + "..."
                + ANSIColors.RESET);
    }

    // COMBAT SYSTEM END

    public boolean movePlayer(int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        Obstacle obstacle = getObstacleAt(newX, newY);
        if (obstacle != null) {
            if (obstacle instanceof Monster monster) {
                startCombat(monster);
                if (monster.getHealth() <= 0) {
                    dungeonLayout[player.getX()][player.getY()] = ' ';
                    player.move(dx, dy);
                    dungeonLayout[newX][newY] = 'P';
                    describeCurrentRoom();
                }
                return !monster.canEscape();
            } else if (!obstacle.isPassable()) {
                System.out.println(ANSIColors.RED + "You can't move through walls..." + ANSIColors.RESET);
                return false;
            }
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
