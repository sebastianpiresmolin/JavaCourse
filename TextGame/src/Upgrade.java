public class Upgrade extends Item {
    private String upgradeType;

    public Upgrade(int x, int y, String upgradeType) {
        super(x, y);
        this.upgradeType = upgradeType;
    }

    public String getUpgradeType() {
        return upgradeType;
    }

    @Override
    public String toString() {
        return "Upgrade: " + upgradeType + " at (" + getX() + ", " + getY() + ")";
    }
}

