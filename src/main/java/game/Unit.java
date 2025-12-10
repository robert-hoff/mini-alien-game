package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;

public abstract class Unit extends Entity {

  protected double direction;
  protected double speed;
  protected int hp;
  protected double range;
  protected double accuracy;
  protected int damage;
  protected double spinD = 0.03;

  private double spin;
  @Override
  public void update(double dt) {
    spin += spinD;
    double vx = Math.cos(direction) * speed;
    double vy = Math.sin(direction) * speed;
    x += vx * dt;
    y += vy * dt;
    if (x < size/2) {
      direction = Math.PI - direction;
      vx = Math.cos(direction) * speed;
      vy = Math.sin(direction) * speed;
      x = size/2;
    }
    if (x > worldWidth-size/2-1) {
      direction = Math.PI - direction;
      vx = Math.cos(direction) * speed;
      vy = Math.sin(direction) * speed;
      x = worldWidth-size/2-1;
    }
    if (y < size/2 + yOffset-1) {
      direction = 2*Math.PI - direction;
      vx = Math.cos(direction) * speed;
      vy = Math.sin(direction) * speed;
      y = size/2 + yOffset-1;
    }
    if (y > worldHeight-size/2-1) {
      direction = 2*Math.PI - direction;
      vx = Math.cos(direction) * speed;
      vy = Math.sin(direction) * speed;
      y = worldHeight-size/2-1;
    }
  }

  @Override
  public void draw(Graphics2D g2) {
    Shape tri = createEquilateralTriangle(size, spin, x, y);
    g2.setColor(new Color(0xFF7D7D));
    g2.fill(tri);
    g2.setStroke(new BasicStroke(2f));
    g2.setColor(new Color(0x322023));
    g2.draw(tri);

    Shape circle = new Ellipse2D.Double(x - range, y - range, range * 2, range * 2);
    g2.setColor(Color.WHITE);
    g2.setStroke(new BasicStroke(0.8f));
    g2.draw(circle);

  }

  public static Shape createEquilateralTriangle(double size, double spin, double cx, double cy) {
    double R = size / Math.sqrt(3.0);
    double[][] pts = {
        {0.0,R},
        {size/2, -size/(2.0*Math.sqrt(3.0))},
        {-size/2, -size /(2.0*Math.sqrt(3.0))}
    };
    double cos = Math.cos(spin);
    double sin = Math.sin(spin);
    Path2D.Double path = new Path2D.Double();
    for (int i = 0; i < pts.length; i++) {
      double x = pts[i][0];
      double y = pts[i][1];
      double xr = x * cos - y * sin;
      double yr = x * sin + y * cos;
      xr += cx;
      yr += cy;
      if (i == 0) {
        path.moveTo(xr, yr);
      } else {
        path.lineTo(xr, yr);
      }
    }
    path.closePath();
    return path;
  }
}

