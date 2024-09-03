import java.util.ArrayList;
import java.util.List;

public class Maze {
    private char[][] dungeonLayout;
    private List<Item> items;
    private Player player;
    private RoomDescriptionHandler roomDescriptionHandler;
    private ObstacleHandler obstacleHandler;
    private PlayerMovementHandler playerMovementHandler;

    public Maze(int width, int height) {
        dungeonLayout = new char[width][height];
        items = new ArrayList<>();
        roomDescriptionHandler = new RoomDescriptionHandler(width, height);
        obstacleHandler = new ObstacleHandler();
        playerMovementHandler = new PlayerMovementHandler(player, this, obstacleHandler);

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

        playerMovementHandler = new PlayerMovementHandler(player, this, obstacleHandler);
    }

    public boolean movePlayer(int dx, int dy) {
        return playerMovementHandler.movePlayer(dx, dy);
    }

    public void updatePlayerPosition(int dx, int dy) {
        dungeonLayout[player.getX()][player.getY()] = ' ';
        player.move(dx, dy);
        dungeonLayout[player.getX()][player.getY()] = 'P';
    }

    public void addObstacles(Obstacle[] obstaclesArray) {
        obstacleHandler.addObstacles(obstaclesArray);
        for (Obstacle obstacle : obstaclesArray) {
            dungeonLayout[obstacle.getX()][obstacle.getY()] = obstacle instanceof Wall ? '#' : 'M';
        }
    }

    public boolean isPassable(int x, int y) {
        return obstacleHandler.isPassable(x, y);
    }

    public boolean isFreeSpace(int x, int y) {
        return dungeonLayout[x][y] == ' ';
    }

    public Item getItemAt(int x, int y) {
        for (Item item : items) {
            if (item.getX() == x && item.getY() == y) {
                return item;
            }
        }
        return null;
    }

    public void addItem(Item item) {
        items.add(item);
        dungeonLayout[item.getX()][item.getY()] = 'I';
    }

    public void removeItem(Item item) {
        items.remove(item);
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

    // USE FOR DEBUGGING
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
}
