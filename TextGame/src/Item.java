public abstract class Item {
    protected int x;
    protected int y;
    protected String name;

    public Item(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Item at (" + x + ", " + y + ")";
    }
}
