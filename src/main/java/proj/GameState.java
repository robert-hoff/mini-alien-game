package proj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import game.Base;
import game.Entity;
import game.GameAction;
import game.Player;

public class GameState {

  private static final int STATUSBAR_HEIGHT = 40;
  private double worldWidth;
  private double worldHeight;
  private BufferedImage backgroundImage;

  Player[] players = new Player[2];
  Base[] bases = new Base[2];

  /*
   * winWidth, winHeight is the available screen size
   *
   */
  public GameState(int winWidth, int winHeight) {
    log.trace("GameState");
    this.worldWidth = winWidth;
    this.worldHeight = winHeight - STATUSBAR_HEIGHT;
    Entity.setWorldSize(winWidth, winHeight);
    Entity.setYOffset(STATUSBAR_HEIGHT);
    try {
      backgroundImage = ImageIO.read(GameCanvas.class.getResource("/game-field.png"));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("can't find required game asset");
    }
    players[0] = new Player(160, worldHeight/2);
    bases[0] = new Base(true);
    bases[1] = new Base(false);
  }

  public void handleAction(GameAction action) {
    switch (action) {
      case PLAYER1_N  -> {players[0].setdirection(Math.PI * 6 / 4); players[0].setStop(false);}
      case PLAYER1_NE -> {players[0].setdirection(Math.PI * 7 / 4); players[0].setStop(false);}
      case PLAYER1_E  -> {players[0].setdirection(Math.PI * 0 / 4); players[0].setStop(false);}
      case PLAYER1_SE -> {players[0].setdirection(Math.PI * 1 / 4); players[0].setStop(false);}
      case PLAYER1_S  -> {players[0].setdirection(Math.PI * 2 / 4); players[0].setStop(false);}
      case PLAYER1_SW -> {players[0].setdirection(Math.PI * 3 / 4); players[0].setStop(false);}
      case PLAYER1_W  -> {players[0].setdirection(Math.PI * 4 / 4); players[0].setStop(false);}
      case PLAYER1_NW -> {players[0].setdirection(Math.PI * 5 / 4); players[0].setStop(false);}
      case PLAYER1_STOP -> players[0].setStop(true);
      default -> throw new IllegalArgumentException("Unexpected value: " + action);
    }
  }

  public void update(double dt) {
    players[0].update(dt);
  }

  public void render(Graphics2D g2) {
    g2.drawImage(backgroundImage, 0, STATUSBAR_HEIGHT, (int) worldWidth, (int) worldHeight, null);
    bases[0].draw(g2);
    bases[1].draw(g2);
    players[0].draw(g2);
    drawStatusBar(g2);
  }

  private void drawStatusBar(Graphics2D g2) {
    g2.setColor(new Color(0x555555));
    g2.fill(new Rectangle2D.Double(0, 0, worldWidth, STATUSBAR_HEIGHT));
  }

  private static Logger log = LoggerFactory.getLogger(MainClass.class);
}

