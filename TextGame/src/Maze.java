import java.util.ArrayList;
import java.util.List;

public class Maze {
    private char[][] mazeLayout;
    private List<Item> items;
    private Player player;
    private RoomDescriptionHandler roomDescriptionHandler;
    private ObstacleHandler obstacleHandler;
    private PlayerMovementHandler playerMovementHandler;

    public Maze(int width, int height) {
        mazeLayout = new char[width][height];
        items = new ArrayList<>();
        roomDescriptionHandler = new RoomDescriptionHandler(width, height);
        obstacleHandler = new ObstacleHandler();
        playerMovementHandler = new PlayerMovementHandler(player, this, obstacleHandler);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mazeLayout[i][j] = ' ';
            }
        }
    }

    public void placePlayer(Player player, int startX, int startY) {
        this.player = player;
        player.move(startX - player.getX(), startY - player.getY());
        mazeLayout[startX][startY] = 'P';

        playerMovementHandler = new PlayerMovementHandler(player, this, obstacleHandler);
    }

    public boolean movePlayer(int dx, int dy) {
        return playerMovementHandler.movePlayer(dx, dy);
    }

    public void updatePlayerPosition(int dx, int dy) {
        mazeLayout[player.getX()][player.getY()] = ' ';
        player.move(dx, dy);
        mazeLayout[player.getX()][player.getY()] = 'P';
    }

    public void addObstacles(Obstacle[] obstaclesArray) {
        obstacleHandler.addObstacles(obstaclesArray);
        for (Obstacle obstacle : obstaclesArray) {
            mazeLayout[obstacle.getX()][obstacle.getY()] = obstacle instanceof Wall ? '#' : 'M';
        }
    }

    public boolean isPassable(int x, int y) {
        return obstacleHandler.isPassable(x, y);
    }

    public boolean isFreeSpace(int x, int y) {
        return mazeLayout[x][y] == ' ';
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
        mazeLayout[item.getX()][item.getY()] = 'I';
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
        if (x < mazeLayout.length - 1 && isPassable(x + 1, y)) {
            System.out.print("down ");
        }
        if (y > 0 && isPassable(x, y - 1)) {
            System.out.print("left ");
        }
        if (y < mazeLayout[0].length - 1 && isPassable(x, y + 1)) {
            System.out.print("right ");
        }
        System.out.println();
    }

    // USE FOR DEBUGGING
    public void printDungeon() {
        boolean isDevMode = true;

        if (isDevMode) {
            for (int i = 0; i < mazeLayout.length; i++) {
                for (int j = 0; j < mazeLayout[i].length; j++) {
                    if (player.getX() == i && player.getY() == j) {
                        System.out.print('P');
                    } else {
                        System.out.print(mazeLayout[i][j]);
                    }
                }
                System.out.println();
            }
        }
    }
}
