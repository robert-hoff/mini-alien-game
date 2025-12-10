package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Base extends Entity {

  boolean leftBase;
  public Base(boolean leftBase) {
    this.leftBase = leftBase;
    y = worldHeight / 2;
  }

  @Override
  public void update(double dt) {}

  @Override
  public void draw(Graphics2D g2) {
    if (leftBase) {
      g2.setColor(new Color(0xFF0000));
      g2.fill(new Rectangle2D.Double(x, y-18, 18, 36));
      g2.setColor(new Color(0xFF7D7D));
      g2.fill(new Rectangle2D.Double(x, y-30, 18, 10));
      g2.fill(new Rectangle2D.Double(x, y+20, 18, 10));
      g2.fill(new Rectangle2D.Double(x+20, y-18, 10, 36));
    } else {
      g2.setColor(new Color(0x0000FF));
      g2.fill(new Rectangle2D.Double(x+worldWidth-18, y-18, 18, 36));
      g2.setColor(new Color(0x5353FF));
      g2.fill(new Rectangle2D.Double(x+worldWidth-18, y-30, 18, 10));
      g2.fill(new Rectangle2D.Double(x+worldWidth-18, y+20, 18, 10));
      g2.fill(new Rectangle2D.Double(x+worldWidth-30, y-18, 10, 36));
    }
  }

}

