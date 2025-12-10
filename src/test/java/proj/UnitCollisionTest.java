package proj;


import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.Math;

// Import the concrete classes and their parent/dependencies
import game.Marine;
import game.Unit;
import game.Entity;

/**
 * Test Case A: Unit Wall Collision and Direction Flipping.
 * Verifies the boundary and direction inversion logic in Unit.update(dt)
 * using Reflection to access protected fields.
 */
public class UnitCollisionTest {

  private final double WORLD_WIDTH = 1000.0;
  // private final double WORLD_HEIGHT = 800.0;
  private final double TEST_DT = 1.0;
  private final double DELTA = 0.001; // Tolerance for floating point comparison

  private Marine marine;
  private final double INITIAL_DIRECTION = 0.1; // Moving East/Right (0.1 radians)
  private final double UNIT_SIZE = 15.0; // From Marine.java

  // --- Reflection Helpers to Access and Modify Protected Fields ---

  private void setUnitField(String fieldName, double value) throws Exception {
    // Find the field in Unit (for direction) or Entity (for x, y, size)
    Class<?> currentClass = Unit.class;
    Field field = null;
    try {
      field = currentClass.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      // If not in Unit, check Entity
      currentClass = Entity.class;
      field = currentClass.getDeclaredField(fieldName);
    }

    field.setAccessible(true);
    field.set(marine, value);
  }

  private double getUnitField(String fieldName) throws Exception {
    // Find the field in Unit (for direction) or Entity (for x, y, size)
    Class<?> currentClass = Unit.class;
    Field field = null;
    try {
      field = currentClass.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      // If not in Unit, check Entity
      currentClass = Entity.class;
      field = currentClass.getDeclaredField(fieldName);
    }

    field.setAccessible(true);
    return field.getDouble(marine);
  }

  // --- Setup for Static World Size and Instantiation ---

  @BeforeClass
  public static void setWorldBounds() {
    // Set the static world size required by Entity and Unit classes
    Entity.setWorldSize(1000, 800);
    Entity.setYOffset(0);
  }

  @Before
  public void setUp() throws Exception {
    // The right collision boundary is at x = worldWidth - size/2 - 1 = 1000 - 7.5 - 1 = 991.5
    double initialX = 990.0;
    double initialY = 100.0;

    // Instantiate the unit (size is set in Marine constructor)
    marine = new Marine(initialX, initialY, true);

    // Set the initial direction using Reflection (since setdirection doesn't exist)
    setUnitField("direction", INITIAL_DIRECTION);
  }

  // --- Test Method ---

  /**
   * Test Objective: Collision with the Right Wall (X boundary).
   * Expected: X is clamped, Direction is flipped (direction = PI - direction).
   */
  @Test
  public void testCollision_RightWall_FlipsDirection() throws Exception {
    // GIVEN: Unit is moving right (0.1 rad) and positioned at x=990.

    // WHEN: Update is called, causing the unit to collide
    marine.update(TEST_DT);

    // THEN 1: Verify Position Clamping
    // The clamping occurs at x = worldWidth - size/2 - 1
    double expectedClampedX = WORLD_WIDTH - (UNIT_SIZE / 2.0) - 1.0; // 991.5

    double actualX = getUnitField("x");
    assertEquals("X position must be clamped at the right boundary.",
        expectedClampedX, actualX, DELTA);

    // THEN 2: Verify Direction Flipping
    // The logic in Unit.java is: direction = Math.PI - direction;
    double expectedDirection = Math.PI - INITIAL_DIRECTION; // PI - 0.1

    double actualDirection = getUnitField("direction");
    assertEquals("Direction must be mathematically flipped (PI - initial direction).",
        expectedDirection, actualDirection, DELTA);
  }

}