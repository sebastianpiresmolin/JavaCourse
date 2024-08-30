import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    private char[][] dungeonLayout;
    private List<Item> items;
    private Player player;

    public Dungeon(int width, int height) {
        dungeonLayout = new char[width][height];
        items = new ArrayList<>();

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
                } else {
                    System.out.print(dungeonLayout[i][j]);
                }
            }
            System.out.println();
        }
    }

    public Item getItemAt(int x, int y) {
        for (Item item : items) {
            if (item.getX() == x && item.getY() == y) {
                return item;
            }
        }
        return null;
    }

    public boolean movePlayer(int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        if (isWall(newX, newY)) {
            System.out.println("You can't move that way!");
            return false;
        }

        if (isFreeSpace(newX, newY)) {
            dungeonLayout[player.getX()][player.getY()] = ' ';
            player.move(dx, dy);
            dungeonLayout[newX][newY] = 'P';
            return true;
        }

        Item item = getItemAt(newX, newY);
        if (item != null) {
            dungeonLayout[player.getX()][player.getY()] = ' ';
            player.move(dx, dy);
            player.addItemToInventory(item);
            items.remove(item);
            dungeonLayout[newX][newY] = 'P';
            System.out.println("You found an item: " + item);
            return true;
        }

        return false;
    }
}
