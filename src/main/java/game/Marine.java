package game;

import java.awt.Color;

public class Marine extends Unit {
  public static UnitCost unitCost = new UnitCost(10,10,10);

  public Marine(double x, double y, boolean leftSide) {
    // stats
    speed = 80;
    range = 70;
    hp = 50;
    damage = 8;

    // position and direction
    this.x = x;
    this.y = y;
    size = 15;
    spinD = 0.03;

    double range = (2.0/3.0) * Math.PI;
    double halfRange = range / 2.0;
    if (leftSide) {
      direction = (Math.random() * range) - halfRange;
    } else {
      color = new Color(0x0040FF);
      direction = Math.PI + ((Math.random() * range) - halfRange);
    }
  }
}
