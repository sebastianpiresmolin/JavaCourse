import java.util.Scanner;

public class Game {
    private Dungeon dungeon;
    private Player player;

    public Game() {
        dungeon = new Dungeon(20, 20);
        player = new Player("Hero", 1, 1);
        dungeon.placePlayer(player, 1, 1);
        dungeon.addWall(0, 0);
        dungeon.addWall(1, 0);
        dungeon.addWall(0, 1);
        dungeon.addWall(0, 2);
        dungeon.addWall(1, 2);
        dungeon.addWall(0, 2);
        dungeon.addWall(2, 0);
        dungeon.addWall(3, 0);
        dungeon.addWall(4, 0);
        dungeon.addWall(4, 1);
        dungeon.addWall(4, 2);
        dungeon.addWall(3, 2);
        dungeon.addWall(1, 3);
        dungeon.addWall(3, 3);
        // dungeon.addItem(new Loot(3, 1, 100));
        dungeon.addItem(new Upgrade(3, 1, "Strength"));
        dungeon.addMonster
        (new Monster(2, 2, 10, 5, "You see a gray rat scurrying around. It doesn't seem to be hostile to your presence.", true));

    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            dungeon.printDungeon();
            System.out.println("Enter your command ('move up', 'show stats', 'use item','quit', e.g.):");
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
                case "show stats":
                    player.showStats();
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
