package proj;

import org.junit.Test;

import game.ResourceInventory;
import game.UnitCost;

import static org.junit.Assert.*;

/**
 * Tests for ResourceInventory.
 */
public class ResourceInventoryTest {

  // --- Constructor & getters ------------------------------------------------

  @Test
  public void constructorSetsInitialValues() {
    ResourceInventory inventory = new ResourceInventory(3, 4, 5);
    assertEquals(3, inventory.getGrass());
    assertEquals(4, inventory.getSand());
    assertEquals(5, inventory.getWater());
  }

  // --- collectGrass / collectSand / collectWater ----------------------------

  @Test
  public void collectGrassIncrementsUntilCap() {
    ResourceInventory inventory = new ResourceInventory(0, 0, 0);

    // Raise to cap (50)
    for (int i = 0; i < 60; i++) {
      inventory.collectGrass();
    }

    assertEquals(50, inventory.getGrass()); // should never exceed 50
  }

  @Test
  public void collectSandIncrementsUntilCap() {
    ResourceInventory inventory = new ResourceInventory(0, 0, 0);

    for (int i = 0; i < 60; i++) {
      inventory.collectSand();
    }

    assertEquals(50, inventory.getSand());
  }

  @Test
  public void collectWaterIncrementsUntilCap() {
    ResourceInventory inventory = new ResourceInventory(0, 0, 0);

    for (int i = 0; i < 60; i++) {
      inventory.collectWater();
    }

    assertEquals(50, inventory.getWater());
  }

  @Test
  public void collectDoesNotIncreaseAboveCapWhenStartingAtCap() {
    ResourceInventory inventory = new ResourceInventory(50, 50, 50);

    inventory.collectGrass();
    inventory.collectSand();
    inventory.collectWater();

    assertEquals(50, inventory.getGrass());
    assertEquals(50, inventory.getSand());
    assertEquals(50, inventory.getWater());
  }

  // --- canAffordUnit --------------------------------------------------------

  @Test
  public void canAffordUnitReturnsTrueWhenResourcesEqualCost() {
    ResourceInventory inventory = new ResourceInventory(5, 5, 5);
    UnitCost cost = new UnitCost(5, 5, 5);

    assertTrue(inventory.canAffordUnit(cost));
  }

  @Test
  public void canAffordUnitReturnsTrueWhenResourcesGreaterThanCost() {
    ResourceInventory inventory = new ResourceInventory(10, 8, 6);
    UnitCost cost = new UnitCost(5, 5, 5);

    assertTrue(inventory.canAffordUnit(cost));
  }

  @Test
  public void canAffordUnitReturnsFalseWhenGrassInsufficient() {
    ResourceInventory inventory = new ResourceInventory(2, 10, 10);
    UnitCost cost = new UnitCost(3, 5, 5);

    assertFalse(inventory.canAffordUnit(cost));
  }

  @Test
  public void canAffordUnitReturnsFalseWhenSandInsufficient() {
    ResourceInventory inventory = new ResourceInventory(10, 1, 10);
    UnitCost cost = new UnitCost(5, 2, 5);

    assertFalse(inventory.canAffordUnit(cost));
  }

  @Test
  public void canAffordUnitReturnsFalseWhenWaterInsufficient() {
    ResourceInventory inventory = new ResourceInventory(10, 10, 0);
    UnitCost cost = new UnitCost(5, 5, 1);

    assertFalse(inventory.canAffordUnit(cost));
  }

  // --- spendUnitcost --------------------------------------------------------

  @Test
  public void spendUnitcostReducesResourcesCorrectly() {
    ResourceInventory inventory = new ResourceInventory(10, 10, 10);
    UnitCost cost = new UnitCost(3, 4, 5);

    inventory.spendUnitcost(cost);

    assertEquals(7, inventory.getGrass());
    assertEquals(6, inventory.getSand());
    assertEquals(5, inventory.getWater());
  }

  @Test(expected = RuntimeException.class)
  public void spendUnitcostThrowsWhenGrassInsufficient() {
    ResourceInventory inventory = new ResourceInventory(1, 10, 10);
    UnitCost cost = new UnitCost(2, 5, 5);
    inventory.spendUnitcost(cost);
  }

  @Test(expected = RuntimeException.class)
  public void spendUnitcostThrowsWhenSandInsufficient() {
    ResourceInventory inventory = new ResourceInventory(10, 0, 10);
    UnitCost cost = new UnitCost(5, 1, 5);

    inventory.spendUnitcost(cost);
  }

  @Test(expected = RuntimeException.class)
  public void spendUnitcostThrowsWhenWaterInsufficient() {
    ResourceInventory inventory = new ResourceInventory(10, 10, 0);
    UnitCost cost = new UnitCost(5, 5, 1);

    inventory.spendUnitcost(cost);
  }

  // --- getResourceStatusText ------------------------------------------------

  @Test
  public void getResourceStatusTextFormatsCorrectly() {
    ResourceInventory inventory = new ResourceInventory(3, 4, 5);

    assertEquals("3G 4S 5W", inventory.getResourceStatusText());
  }
}

