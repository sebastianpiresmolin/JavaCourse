public class Loot extends Item {
    private int value;

    public Loot(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Loot worth " + value + " at (" + x + ", " + y + ")";
    }
}
