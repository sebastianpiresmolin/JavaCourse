public class Upgrade extends Item {
    private String upgradeType;
    private int amount;
    private String name;

    public Upgrade(int x, int y, String upgradeType, int amount, String name) {
        super(x, y, name);
        this.upgradeType = upgradeType;
        this.amount = amount;
        this.name = name;
    }

    public String getUpgradeType() {
        return upgradeType;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return upgradeType + " Upgrade (" + amount + ") at (" + getX() + ", " + getY() + ")";
    }
}
