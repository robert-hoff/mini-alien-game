package game;

import java.awt.Graphics2D;

public abstract class Entity {
  protected static int worldWidth;
  protected static int worldHeight;
  protected static int yOffset;

  public static void setWorldSize(int width, int height) {
    worldWidth = width;
    worldHeight = height;
  }
  // to make room for the status bar
  public static void setYOffset(int y) {
    yOffset = y;
  }

  // Per-entity state
  protected double x, y;
  protected double size;

  public abstract void update(double dt);
  public abstract void draw(Graphics2D g);
}

