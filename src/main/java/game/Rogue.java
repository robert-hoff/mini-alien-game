package game;

public class Rogue extends Unit {
  public static UnitCost unitCost = new UnitCost(20,20,0);

  public Rogue(double x, double y, boolean leftSide) {
    // stats
    speed = 60;
    range = 30;
    hp = 100;
    damage = 15;

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
      direction = Math.PI + ((Math.random() * range) - halfRange);
    }
  }
}
