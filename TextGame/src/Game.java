import java.util.Scanner;

public class Game {
    private Maze maze;
    private Player player;

    public Game() {
        // Initialize the maze and player
        maze = new Maze(8, 8);
        player = new Player("Hero", 1, 1);
        maze.placePlayer(player, 1, 1);

        int[][] wallCoordinates = {
                { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 0, 5 }, { 0, 6 },
                { 1, 0 }, { 1, 2 }, { 1, 3 }, { 1, 5 }, { 1, 6 },
                { 2, 0 }, { 2, 6 },
                { 3, 0 }, { 3, 2 }, { 3, 3 }, { 3, 5 }, { 3, 6 },
                { 4, 0 }, { 4, 1 }, { 4, 2 }, { 4, 3 }, { 4, 5 }, { 4, 6 },
                { 5, 3 }, { 5, 5 },
                { 6, 3 }, { 6, 5 },
                { 7, 3 }, { 7, 4 }, { 7, 5 }
        };

        // Create and add walls to the maze
        Wall[] walls = new Wall[wallCoordinates.length];
        for (int i = 0; i < wallCoordinates.length; i++) {
            walls[i] = new Wall(wallCoordinates[i][0], wallCoordinates[i][1]);
        }
        maze.addObstacles(walls);

        // Add items to the maze
        maze.addItem(new Upgrade(3, 1, "Strength", 5, "Pickaxe[+5 Strength]"));
        maze.addItem(new Upgrade(1, 4, "Health", 10, "Dragonscale Gem[+10 Health]"));
        maze.addItem(new Upgrade(2, 5, "Defence", 5, "Piece of Wooden Door[+5 Defence]"));

        // Add monsters to the maze
        Monster[] monsters = {
                new Monster(2, 2, 10, 5, "Gray Rat",
                        "You see a gray rat scurrying around. It doesn't seem to be hostile to your presence.",
                        "As you strike the killing blow (and also the only blow), The rat explodes in a bloody mess. You should feel a little bad about it. You monster.",
                        true,
                        false),
                new Monster(3, 4, 75, 25, "Orc",
                        "You see an orc. It looks like he's looking for something...or someone.",
                        "The orc falls to the ground with a heavy thud. The orc's eyes glaze over as he takes his last breath, showing a glimmer of sadness. But you don't care about filthy orcs.",
                        false,
                        false),
                new Monster(6, 4, 1000, 500, "Dragon",
                        "As you jump down and look up, you see it. A great dragon! Glowing and fading with every breath. The heat is almost unbearable now. Its scales are a deep red and its eyes are a piercing yellow. Its wings are folded at its side and its tail is curled around its feet. It's a magnificent sight. You can't help but to notice a gaping hole in its scaly armor. Is that where the gem belongs?",
                        "",
                        false,
                        true)
        };
        maze.addObstacles(monsters);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        boolean menu = true;

        while (running && menu) {
            System.out.println("=====================================");
            System.out.println(ANSIColors.BLUE + "Welcome to the Depths of Erebor!" + ANSIColors.RESET);
            System.out.println("=====================================");
            String playerName = "";
            while (playerName.isEmpty()) {
                System.out.println(
                        ANSIColors.YELLOW + "Please enter your name to start the adventure:" + ANSIColors.RESET);
                playerName = scanner.nextLine().trim();
                player.addName(playerName);

                if (playerName.isEmpty()) {
                    System.out.println(
                            ANSIColors.RED + "Name cannot be empty. Please enter a valid name." + ANSIColors.RESET);
                }
            }
            System.out.println(" ");
            System.out.println("Enter your command ('start' or 'quit'):");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "start" -> {
                    menu = false;
                    System.out.println(ANSIColors.GREEN +
                            "You awake with an excruciating headache. The last thing you heard was Glímoin's voice yelling "
                            + player.getName() + "!" + ANSIColors.RESET);
                    System.out.println(
                            ANSIColors.GREEN
                                    + "You look around and see that you are in a dark, damp maze. The last thing you remember is the gem, red as blood."
                                    + ANSIColors.RESET);
                    System.out.println(
                            ANSIColors.GREEN
                                    + "It seems to be lost. You dug too deep and too greedily. You must escape."
                                    + ANSIColors.RESET);
                    System.out.println(ANSIColors.GREEN + "You can move: down" + ANSIColors.RESET);
                }
                case "quit" -> {
                    running = false;
                    System.out.println(ANSIColors.BLUE + "Thanks for playing!" + ANSIColors.RESET);
                }
                default -> System.out.println(
                            ANSIColors.RED + "Invalid command!" + ANSIColors.RESET + " Please use 'start' or 'quit'.");
            }
        }

        while (running && !menu) {
            maze.printDungeon();
            System.out.println("Enter your command ('move', 'show stats', 'show inventory','quit', e.g.):");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "move up" -> maze.movePlayer(-1, 0);
                case "move down" -> maze.movePlayer(1, 0);
                case "move left" -> maze.movePlayer(0, -1);
                case "move right" -> maze.movePlayer(0, 1);
                case "show stats" -> player.showStats();
                case "show inventory" -> player.showInventory();
                case "quit" -> {
                    running = false;
                    System.out.println("Thanks for playing!");
                }
                default -> System.out.println(
                            ANSIColors.RED + "Invalid command!" + ANSIColors.RESET
                                    + " Please use 'move up', 'move down', 'move left', 'move right', 'show stats', 'show inventory', or 'quit'.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}