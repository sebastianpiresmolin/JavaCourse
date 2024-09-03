public class RoomDescriptionHandler {
    private String[][] roomDescriptions;

    public RoomDescriptionHandler(int width, int height) {
        roomDescriptions = new String[width][height];

        // Initialize room descriptions
        roomDescriptions[2][1] = ANSIColors.GREEN
                + "You stumble deeper into the dungeon you've fallen into. It almost looks like it's been dug out by dwarves. But can it really be?"
                + ANSIColors.RESET;
        roomDescriptions[3][1] = ANSIColors.GREEN
                + "You jump down another level, a dead end. You can see by the way the walls are carved that this was a mining operation. You can see the pickaxe that was left behind. You grab it."
                + ANSIColors.RESET;
        roomDescriptions[2][2] = ANSIColors.GREEN
                + "Among the pieces of dead vermin and the smell of decay, you find nothing. Just deserted cart tracks."
                + ANSIColors.RESET;
        roomDescriptions[2][3] = ANSIColors.GREEN + "As you walk further along the tracks the air gets thicker."
                + ANSIColors.RESET;
        roomDescriptions[2][4] = ANSIColors.GREEN
                + "You've come to a crossroads. You can see that there's a small room to the right. You can hear something moving in the dark below you."
                + ANSIColors.RESET;
        roomDescriptions[1][4] = ANSIColors.GREEN
                + "You see a lot of rubble. It looks like it happened recently. Probably when you fell down here. With the corner of your eye you see something shiny in the rubble. You pick it up."
                + ANSIColors.RESET;
        roomDescriptions[2][5] = ANSIColors.GREEN
                + "You see a small room. It's empty. You can see the remains of boxes and barrels. The door has been broken down. The handle is still attached to a big piece of wood. You take it."
                + ANSIColors.RESET;
        roomDescriptions[3][4] = ANSIColors.GREEN
                + "You try not to get orc blood on your boots as you step over the corpse. You feel the air thickening even more as you continue deeper."
                + ANSIColors.RESET;
        roomDescriptions[4][4] = ANSIColors.GREEN
                + "As you're continuing down the tunnel you start to smell something. It's not the smell of decay. It's the smell of something burning."
                + ANSIColors.RESET;
        roomDescriptions[5][4] = ANSIColors.GREEN
                + "You've arrived at a ledge. You see that you could make the jump down, but you're pretty sure you won't be able to get back up. Also there is a heat coming from below, along with the strong smell of burning and sulfur."
                + ANSIColors.RESET;
    }

    public String getDescription(int x, int y) {
        return roomDescriptions[x][y] != null ? roomDescriptions[x][y] : "You see an empty room.";
    }
}
