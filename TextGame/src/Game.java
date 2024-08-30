import java.util.Scanner;

public class Game {
    private Dungeon dungeon;
    private Player player;

    public Game() {
        dungeon = new Dungeon(10, 10);
        player = new Player("Hero", 0, 0);

        dungeon.addWall(2, 2);
        dungeon.addWall(2, 3);
        dungeon.addWall(2, 4);
        dungeon.addItem(new Loot(4, 4, 100));
        dungeon.addItem(new Upgrade(3, 3, "Health"));

        dungeon.placePlayer(player, 1, 1);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            dungeon.printDungeon();
            System.out.println("Enter your command ('move up', 'move right', 'quit', e.g.):");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "move up":
                    dungeon.movePlayer(-1, 0);
                    break;
                case "move down":
                    dungeon.movePlayer(1, 0);
                    break;
                case "move left":
                    dungeon.movePlayer(0, -1);
                    break;
                case "move right":
                    dungeon.movePlayer(0, 1);
                    break;
                case "quit":
                    running = false;
                    System.out.println("Thanks for playing!");
                    break;
                default:
                    System.out.println(
                            "Invalid command! Please use 'move forward', 'move backward', 'move left', 'move right', or 'quit'.");
                    break;
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
