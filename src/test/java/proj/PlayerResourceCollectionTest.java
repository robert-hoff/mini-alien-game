package proj;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

// Import the specific classes required for the test
import game.Player;
import game.Entity;
import game.ResourceInventory;
import game.ResourceType;

/**
 * Test Case 8: Player Resource Collection (System Integration).
 * Verifies that Player.update(dt) correctly triggers resource collection
 * when the cumulative time (dtSum) exceeds the stationary threshold (0.15).
 */
public class PlayerResourceCollectionTest {

  private Player player;
  private ResourceInventory inventory;
  private final int INITIAL_GRASS = 10; // Start with some resources for clarity

  // --- Reflection Helpers to Access and Modify Protected Fields ---

  // Helper to get a private/protected field value from the Player
  private double getPlayerField(String fieldName) throws Exception {
    // Check Player fields first
    Class<?> currentClass = Player.class;
    Field field = null;
    try {
      field = currentClass.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      // Check Entity fields if not in Player
      currentClass = Entity.class;
      field = currentClass.getDeclaredField(fieldName);
    }

    field.setAccessible(true);
    return field.getDouble(player);
  }

  // Helper to set a private field value in the Player
  private void setPlayerField(String fieldName, double value) throws Exception {
    Class<?> currentClass = Player.class;
    Field field = null;
    try {
      field = currentClass.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      currentClass = Entity.class;
      field = currentClass.getDeclaredField(fieldName);
    }

    field.setAccessible(true);
    field.set(player, value);
  }

  // Helper to set a private field value in the ResourceInventory
  private void setInventoryField(String fieldName, int value) throws Exception {
    Field field = ResourceInventory.class.getDeclaredField(fieldName);
    field.setAccessible(true);
    field.setInt(inventory, value);
  }

  // --- Setup ---

  @BeforeClass
  public static void setWorldBounds() {
    // Set static world size (required by Player constructor/update)
    Entity.setWorldSize(1000, 800);
    Entity.setYOffset(36); // Status bar height from GameState.java
  }

  @Before
  public void setUp() throws Exception {
    // Instantiate Player (sets initial inventory)
    player = new Player(100, 100, true);
    inventory = player.getResourceInventory();

    // Ensure initial state for the test: stationary, collecting Grass
    player.setStop(true);
    player.setResourceType(ResourceType.GRASS);

    // Reset player's internal time accumulator (dtSum)
    setPlayerField("dtSum", 0.0);

    // Set initial resource count for verification
    setInventoryField("grass", INITIAL_GRASS);
  }

  /**
   * Test Objective: Verify collection is skipped if dtSum is below the threshold.
   */
  @Test
  public void testUpdate_SkipsCollection_BelowThreshold() throws Exception {
    // GIVEN: dtSum = 0.0, Threshold = 0.15.
    double dt_input = 0.14; // Less than 0.15

    // WHEN: Update is called
    player.update(dt_input);

    // THEN 1: Resource count must be unchanged
    assertEquals("Grass count should not increment before the threshold is met.",
        INITIAL_GRASS, inventory.getGrass());

    // THEN 2: dtSum must be updated but not reset
    double actualDtSum = getPlayerField("dtSum");
    assertEquals("dtSum should accumulate the time delta.",
        dt_input, actualDtSum, 0.001);
  }


  /**
   * Test Objective: Verify collection occurs and dtSum resets when threshold is exceeded.
   */
  @Test
  public void testUpdate_CollectsResource_ExceedsThreshold() throws Exception {
    // GIVEN: We first call an update to set dtSum near the threshold (0.1)
    double dt_1 = 0.10;
    player.update(dt_1);

    // WHEN: We call a second update that crosses the 0.15 threshold (0.10 + 0.06 = 0.16)
    double dt_2 = 0.06;
    player.update(dt_2);

    // THEN 1: Resource count must increase by exactly one
    assertEquals("Grass count must increment by exactly one after threshold is exceeded.",
        INITIAL_GRASS + 1, inventory.getGrass());

    // THEN 2: dtSum must be reset to zero after successful collection
    double actualDtSum = getPlayerField("dtSum");
    assertEquals("dtSum must be reset to 0.0 after collection.",
        0.0, actualDtSum, 0.001);
  }

}
