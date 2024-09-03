public class PlayerMovementHandler {
    private Player player;
    private Maze maze;
    private ObstacleHandler obstacleHandler;
    private CombatHandler combatHandler;

    public PlayerMovementHandler(Player player, Maze maze, ObstacleHandler obstacleHandler) {
        this.player = player;
        this.maze = maze;
        this.obstacleHandler = obstacleHandler;
    }

    public boolean movePlayer(int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        if (handleObstacle(newX, newY, dx, dy)) {
            return false;
        }

        if (handleFreeSpace(newX, newY, dx, dy)) {
            return true;
        }

        if (handleItemPickup(newX, newY, dx, dy)) {
            return true;
        }

        return false;
    }

    private boolean handleObstacle(int x, int y, int dx, int dy) {
        Obstacle obstacle = obstacleHandler.getObstacleAt(x, y);
        if (obstacle != null) {
            if (obstacle instanceof Monster monster) {
                combatHandler = new CombatHandler(player, monster);
                combatHandler.startCombat();

                if (monster.getHealth() <= 0) {
                    obstacleHandler.removeObstacle(monster);
                    maze.updatePlayerPosition(dx, dy);
                    maze.describeCurrentRoom();
                }
                return !monster.canEscape();
            } else if (!obstacle.isPassable()) {
                System.out.println(ANSIColors.RED + "You can't move through walls..." + ANSIColors.RESET);
                return true;
            }
        }
        return false;
    }

    private boolean handleFreeSpace(int x, int y, int dx, int dy) {
        if (maze.isFreeSpace(x, y)) {
            maze.updatePlayerPosition(dx, dy);
            maze.describeCurrentRoom();
            return true;
        }
        return false;
    }

    private boolean handleItemPickup(int x, int y, int dx, int dy) {
        Item item = maze.getItemAt(x, y);
        if (item != null) {
            maze.updatePlayerPosition(dx, dy);
            player.addItemToInventory(item);
            maze.removeItem(item);
            System.out.println(ANSIColors.CYAN + "You found an item: " + ANSIColors.RESET + item.name);
            maze.describeCurrentRoom();
            return true;
        }
        return false;
    }
}
