package game;

public class ResourceInventory {
  private final int RESOURCE_CAP = 50;
  private int grass;
  private int sand;
  private int water;

  public ResourceInventory(int grass, int sand, int water) {
    this.grass = grass;
    this.sand = sand;
    this.water = water;
  }

  public int getGrass() {
    return grass;
  }

  public int getSand() {
    return sand;
  }

  public int getWater() {
    return water;
  }

  public void collectGrass() {
    if (grass < RESOURCE_CAP) {
      grass++;
    }
  }

  public void collectSand() {
    if (sand < RESOURCE_CAP) {
      sand++;
    }
  }

  public void collectWater() {
    if (water < RESOURCE_CAP) {
      water++;
    }
  }

  public boolean canAffordUnit(UnitCost unitCost) {
    return grass >= unitCost.grass && sand >= unitCost.sand && water >= unitCost.water;
  }

  public void spendUnitcost(UnitCost unitCost) {
    grass -= unitCost.grass;
    sand -= unitCost.grass;
    water -= unitCost.grass;
    if (grass < 0 || sand < 0 || water < 0) {
      throw new RuntimeException("unitCost exceeds resources, call not allowed");
    }
  }

  public String getResourceStatusText() {
    return String.format("%dG %dS %dW", grass, sand, water);
  }
}

