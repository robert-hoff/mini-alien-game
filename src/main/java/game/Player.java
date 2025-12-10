package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Player extends Entity {

  protected double direction;
  protected double speed;
  protected int hp;
  protected boolean stop = true;
  protected ResourceType resource;
  private int sand;
  private int water;
  private int grass;

  public Player(double x, double y) {
    this.x = x;
    this.y = y;
    this.size = 13;
    this.speed = 300;
    this.hp = 200;
  }

  public double getX() {return x;}
  public double getY() {return y;}

  public void setResourceType(ResourceType resource) {
    this.resource = resource;
  }

  private void collectResource() {
    int cap = 50;
    if (resource == ResourceType.GRASS && grass < cap) {
      grass++;
    }
    if (resource == ResourceType.WATER && water < cap) {
      water++;
    }
    if (resource == ResourceType.SAND && sand < cap) {
      sand++;
    }
  }

  public String getResourceInventory() {
    return String.format("%dS %dW %dG", sand, water, grass);
  }

  public void setdirection(double direction) {
    this.direction = direction;
  }

  public void setStop(boolean stop) {
    this.stop = stop;
  }

  private double dtSum = 0;

  @Override
  public void update(double dt) {
    dtSum += dt;
    double stationaryCollect = 0.15;
    double movingCollect = 0.15;
    if (stop && dtSum > stationaryCollect) {
      collectResource();
      dtSum = 0;
    }
    if (!stop && dtSum > movingCollect) {
      collectResource();
      dtSum = 0;
    }
    if (stop) {
      return;
    } else {
      double vx = Math.cos(direction) * speed;
      double vy = Math.sin(direction) * speed;  // use -Math.sin if you want math-style up
      x += vx * dt;
      y += vy * dt;
      if (x < size/2) {
        x = size/2;
      }
      if (x > worldWidth-size/2-1) {
        x = worldWidth-size/2-1;
      }
      if (y < size/2 + yOffset-1) {
        y = size/2 + yOffset-1;
      }
      if (y > worldHeight-size/2-1) {
        y = worldHeight-size/2-1;
      }
    }
  }

  @Override
  public void draw(Graphics2D g2) {
    double half = size/2;
    g2.setColor(new Color(0xFF7D7D));
    g2.fill(new Rectangle2D.Double(x-half, y-half, size, size));
    g2.setColor(new Color(0x322023));
    g2.setStroke(new BasicStroke(2));
    g2.draw(new Rectangle2D.Double(x-half, y-half, size, size));
  }
}

