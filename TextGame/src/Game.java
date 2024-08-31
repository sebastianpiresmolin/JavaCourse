import java.util.Scanner;

public class Game {
    private Dungeon dungeon;
    private Player player;

    public Game() {
        dungeon = new Dungeon(8, 8);
        player = new Player("Hero", 1, 1);
        dungeon.placePlayer(player, 1, 1);
        System.out.println("You awake with an excruciating headache. The last thing you heard was Glímoin's voice yelling "
         + player.getName() + "!");
         System.out.println("You look around and see that you are in a dark, damp dungeon. The last thing you remember is the gem, red as blood.");
         System.out.println("It seems to be lost. You dug too deep and too greedily. You must escape.");
         System.out.println("You can move: down");

        // walls
        dungeon.addWall(0, 0);
        dungeon.addWall(0, 1);
        dungeon.addWall(0, 2);
        dungeon.addWall(0, 3);
        dungeon.addWall(0, 4);
        dungeon.addWall(0, 5);
        dungeon.addWall(0, 6);
        dungeon.addWall(1, 0);
        dungeon.addWall(1, 2);
        dungeon.addWall(1, 3);
        dungeon.addWall(1, 5);
        dungeon.addWall(1, 6);
        dungeon.addWall(2, 0);
        dungeon.addWall(2, 6);
        dungeon.addWall(3, 0);
        dungeon.addWall(3, 2);
        dungeon.addWall(3, 3);
        dungeon.addWall(3, 5);
        dungeon.addWall(3, 6);
        dungeon.addWall(4, 0);
        dungeon.addWall(4, 1);
        dungeon.addWall(4, 2);
        dungeon.addWall(4, 3);
        dungeon.addWall(4, 5);
        dungeon.addWall(4, 6);
        dungeon.addWall(5, 3);
        dungeon.addWall(5, 5);
        dungeon.addWall(6, 3);
        dungeon.addWall(6, 5);
        dungeon.addWall(7, 3);
        dungeon.addWall(7, 4);
        dungeon.addWall(7, 5);
        // end walls

        // rooms


        // items
        dungeon.addItem(new Upgrade(3, 1, "Strength", 5, "Pickaxe[+5 Strength]"));
        dungeon.addItem(new Upgrade(1, 4, "Health", 10, "Dragonscale Gem[+10 Health]"));
        dungeon.addItem(new Upgrade(2, 5, "Defence", 5, "Piece of Wooden Door[+5 Defence]"));
        // end items

        // monsters
        dungeon.addMonster(new Monster(2, 2, 10, 5, "Gray Rat",
                "You see a gray rat scurrying around. It doesn't seem to be hostile to your presence.",
                "As you strike the killing blow (and also the only blow), The rat explodes in a bloody mess. You should feel a little bad about it. You monster.",
                true,
                false));
        dungeon.addMonster(new Monster(3, 4, 75, 25, "Orc",
                "You see an orc. It looks like he's looking for something...or someone.",
                "The orc falls to the ground with a heavy thud. The orc's eyes glaze over as he takes his last breath, showing a glimmer of sadness. But you don't care about filthy orcs.",
                false,
                false));
        dungeon.addMonster(new Monster(6, 4, 1000, 500, "Dragon",
                "As you jump down and look up, you see it. A great dragon! Glowing and fading with every breath. The heat is almost unbearable now. It's scales are a deep red and it's eyes are a piercing yellow. It's wings are folded at it's side and it's tail is curled around it's feet. It's a magnificent sight. You cant't help but to notice a gaping hole in it's scaly armour. Is that where the gem belongs?",
                "",
                false,
                true));

    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        boolean menu = true;

        while (running && menu) {
            System.out.println("Enter your command ('start', 'quit', e.g.):");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "start":
                    menu = false;
                    break;
                case "quit":
                    running = false;
                    System.out.println("Thanks for playing!");
                    break;
                default:
                    System.out.println("Invalid command! Please use 'start' or 'quit'.");
                    break;
            }
        }

        while (running && !menu) {
            dungeon.printDungeon();
            System.out.println("Enter your command ('move', 'show stats', 'show inventory','quit', e.g.):");
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
                case "show inventory":
                    player.showInventory();
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
