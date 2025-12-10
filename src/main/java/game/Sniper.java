package game;

import java.awt.Color;

public class Sniper extends Unit {
  public static UnitCost unitCost = new UnitCost(0,20,20);

  public Sniper(double x, double y, boolean leftSide) {
    // stats
    speed = 100;
    range = 170;
    hp = 20;
    damage = 5;

    // position and direction
    this.x = x;
    this.y = y;
    size = 15;
    spinD = 0.02 + Math.random() * 0.02;

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
