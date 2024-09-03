import java.util.Scanner;

public class CombatHandler {
    private Player player;
    private Monster monster;

    public CombatHandler(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
    }

    public void startCombat() {
        Scanner scanner = new Scanner(System.in);
        boolean inCombat = true;

        displayCombatStartMessage();

        while (inCombat && monster.getHealth() > 0 && player.getHealth() > 0) {
            displayCombatOptions();

            String action = scanner.nextLine().toLowerCase();
            inCombat = handlePlayerAction(action);
        }

        if (player.getHealth() <= 0) {
            handlePlayerDefeat();
        }
    }

    private void displayCombatStartMessage() {
        System.out.println(ANSIColors.GREEN + "You've encountered a " + monster.getName() + "!" + ANSIColors.RESET);
    }

    private void displayCombatOptions() {
        System.out.print(ANSIColors.GREEN + "Do you want to 'attack' or 'try to escape'" + ANSIColors.RESET);
        if (monster.isDragon() && player.hasItem("dragonscale gem")) {
            System.out.print(ANSIColors.GREEN + ", or 'offer gem'" + ANSIColors.RESET);
        }
        System.out.println(ANSIColors.GREEN + "?" + ANSIColors.RESET);
    }

    private boolean handlePlayerAction(String action) {
        switch (action) {
            case "attack" -> {
                handleAttack();
                return monster.getHealth() > 0;
            }
            case "try to escape" -> {
                return !handleEscape();
            }
            case "offer gem" -> {
                return !handleOfferGem();
            }
            default -> {
                System.out.println(ANSIColors.RED + "Invalid action!" + ANSIColors.RESET
                        + "Please choose 'attack', 'try to escape', or 'offer gem'.");
                return true;
            }
        }
    }

    private void handleAttack() {
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
            handleMonsterDefeat();
        }
    }

    private boolean handleEscape() {
        if (monster.canEscape()) {
            System.out.println(ANSIColors.GREEN + "You managed to escape!" + ANSIColors.RESET);
            return true;
        } else {
            System.out.println(ANSIColors.RED + "There is no escaping this fight!" + ANSIColors.RESET);
            return false;
        }
    }

    private boolean handleOfferGem() {
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

    private void handleMonsterDefeat() {
        System.out.println(
                ANSIColors.GREEN + "You defeated the " + monster.getName() + "!" + ANSIColors.RESET);
        System.out.println(ANSIColors.GREEN + monster.getDeathText() + ANSIColors.RESET);
    }

    private void handlePlayerDefeat() {
        System.out.println(ANSIColors.RED + "You have been defeated by the " + monster.getName() + "..."
                + ANSIColors.RESET);
    }
}