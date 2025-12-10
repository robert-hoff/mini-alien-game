package proj;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import game.Base;
import game.Entity;
import game.GameAction;
import game.Marine;
import game.Player;
import game.ResourceInventory;
import game.ResourceType;
import game.Rogue;
import game.Sniper;
import game.Unit;

public class GameState {

  private static final int STATUSBAR_HEIGHT = 36;
  private double worldWidth;
  private double worldHeight;
  private BufferedImage backgroundImage;

  Player[] players = new Player[2];
  Base[] bases = new Base[2];
  List<Unit> player1Units = new ArrayList<>();
  List<Unit> player2Units = new ArrayList<>();

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
    players[0] = new Player(160, worldHeight/2, true);
    players[1] = new Player(worldWidth-140, worldHeight/2, false);
    players[0].setResourceType(getResourceType(players[0].getX(), players[0].getY()));
    bases[0] = new Base(true);
    bases[1] = new Base(false);
  }

  private ResourceType getResourceType(double x, double y) {
    int pixelX = (int) (x/worldWidth*backgroundImage.getWidth());
    int pixelY = (int) ((y-STATUSBAR_HEIGHT)/worldHeight*backgroundImage.getHeight());
    int argb = backgroundImage.getRGB(pixelX, pixelY);
    if (argb == -1513266) {
      return ResourceType.SAND;
    } else if (argb == -4671774) {
      return ResourceType.WATER;
    } else {
      return ResourceType.GRASS;
    }
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
      case PLAYER1_PURCHASE_MARINE -> {
        if (players[0].canAffordUnit(Marine.unitCost)) {
          players[0].spendUnitCost(Marine.unitCost);
          player1Units.add(new Marine(10, worldHeight/2, true));
        }
      }
      case PLAYER1_PURCHASE_SNIPER -> {
        if (players[0].canAffordUnit(Sniper.unitCost)) {
          players[0].spendUnitCost(Sniper.unitCost);
          player1Units.add(new Sniper(10, worldHeight/2, true));
        }
      }
      case PLAYER1_PURCHASE_ROGUE -> {
        if (players[0].canAffordUnit(Rogue.unitCost)) {
          players[0].spendUnitCost(Rogue.unitCost);
          player1Units.add(new Rogue(10, worldHeight/2, true));
        }
      }
      case PLAYER2_N  -> {players[1].setdirection(Math.PI * 6 / 4); players[1].setStop(false);}
      case PLAYER2_NE -> {players[1].setdirection(Math.PI * 7 / 4); players[1].setStop(false);}
      case PLAYER2_E  -> {players[1].setdirection(Math.PI * 0 / 4); players[1].setStop(false);}
      case PLAYER2_SE -> {players[1].setdirection(Math.PI * 1 / 4); players[1].setStop(false);}
      case PLAYER2_S  -> {players[1].setdirection(Math.PI * 2 / 4); players[1].setStop(false);}
      case PLAYER2_SW -> {players[1].setdirection(Math.PI * 3 / 4); players[1].setStop(false);}
      case PLAYER2_W  -> {players[1].setdirection(Math.PI * 4 / 4); players[1].setStop(false);}
      case PLAYER2_NW -> {players[1].setdirection(Math.PI * 5 / 4); players[1].setStop(false);}
      case PLAYER2_STOP -> players[1].setStop(true);
      case PLAYER2_PURCHASE_MARINE -> {
        if (players[1].canAffordUnit(Marine.unitCost)) {
          players[1].spendUnitCost(Marine.unitCost);
          player2Units.add(new Marine(worldWidth-30, worldHeight/2, false));
        }
      }
      case PLAYER2_PURCHASE_SNIPER -> {
        if (players[1].canAffordUnit(Sniper.unitCost)) {
          players[1].spendUnitCost(Sniper.unitCost);
          player2Units.add(new Sniper(worldWidth-30, worldHeight/2, false));
        }
      }
      case PLAYER2_PURCHASE_ROGUE -> {
        if (players[1].canAffordUnit(Rogue.unitCost)) {
          players[1].spendUnitCost(Rogue.unitCost);
          player2Units.add(new Rogue(worldWidth-30, worldHeight/2, false));
        }
      }
      case TEST_ACTION -> {
        System.out.println("hi");
      }
      default -> throw new IllegalArgumentException("Unexpected value: " + action);
    }
  }

  public void update(double dt) {
    players[0].update(dt);
    players[0].setResourceType(getResourceType(players[0].getX(), players[0].getY()));
    players[1].update(dt);
    players[1].setResourceType(getResourceType(players[1].getX(), players[1].getY()));
    for (Unit unit : player1Units) {
      unit.update(dt);
    }
    for (Unit unit : player2Units) {
      unit.update(dt);
    }
  }

  public void render(Graphics2D g2) {
    g2.drawImage(backgroundImage, 0, STATUSBAR_HEIGHT, (int) worldWidth, (int) worldHeight, null);
    bases[0].draw(g2);
    bases[1].draw(g2);
    players[0].draw(g2);
    players[1].draw(g2);
    for (Unit unit : player1Units) {
      unit.draw(g2);
    }
    for (Unit unit : player2Units) {
      unit.draw(g2);
    }
    drawStatusBar(g2);
    g2.dispose();
  }

  private void drawStatusBar(Graphics2D g2) {
    g2.setColor(new Color(0x555555));
    g2.fill(new Rectangle2D.Double(0, 0, worldWidth, STATUSBAR_HEIGHT));
    // player1
    g2.setColor(Color.WHITE);
    g2.drawString(players[0].getResourceInventory().getResourceStatusText(), 5, 15);
    ResourceInventory playerInv1 = players[0].getResourceInventory();

    String buyUnitStringPlayer1 = "";
    if (playerInv1.getGrass() >= Marine.unitCost.grass &&
        playerInv1.getSand() >= Marine.unitCost.sand &&
        playerInv1.getWater() >= Marine.unitCost.water) {
      buyUnitStringPlayer1 += "(1) Marine ";
    }
    if (playerInv1.getGrass() >= Sniper.unitCost.grass &&
        playerInv1.getSand() >= Sniper.unitCost.sand &&
        playerInv1.getWater() >= Sniper.unitCost.water) {
      buyUnitStringPlayer1 += "(2) Sniper ";
    }
    if (playerInv1.getGrass() >= Rogue.unitCost.grass &&
        playerInv1.getSand() >= Rogue.unitCost.sand &&
        playerInv1.getWater() >= Rogue.unitCost.water) {
      buyUnitStringPlayer1 += "(3) Rogue ";
    }
    g2.drawString(buyUnitStringPlayer1, 5, 30);
    // player2
    g2.setColor(Color.WHITE);
    drawRightAlignedString(g2, players[1].getResourceInventory().getResourceStatusText(), (int) worldWidth-7, 15);
    ResourceInventory playerInv2 = players[1].getResourceInventory();

    String buyUnitStringPlayer2 = "";
    if (playerInv2.getGrass() >= Marine.unitCost.grass &&
        playerInv2.getSand() >= Marine.unitCost.sand &&
        playerInv2.getWater() >= Marine.unitCost.water) {
      buyUnitStringPlayer2 += "(Ins) Marine ";
    }
    if (playerInv2.getGrass() >= Sniper.unitCost.grass &&
        playerInv2.getSand() >= Sniper.unitCost.sand &&
        playerInv2.getWater() >= Sniper.unitCost.water) {
      buyUnitStringPlayer2 += "(Hom) Sniper ";
    }
    if (playerInv2.getGrass() >= Rogue.unitCost.grass &&
        playerInv2.getSand() >= Rogue.unitCost.sand &&
        playerInv2.getWater() >= Rogue.unitCost.water) {
      buyUnitStringPlayer2 += "(PgU) Rogue ";
    }
    drawRightAlignedString(g2, buyUnitStringPlayer2, (int) worldWidth-7, 30);
  }

  public void drawRightAlignedString(Graphics2D g2, String text, int rightX, int y) {
    FontMetrics fm = g2.getFontMetrics();
    int textWidth = fm.stringWidth(text);
    g2.drawString(text, rightX - textWidth, y);
  }

  private static Logger log = LoggerFactory.getLogger(MainClass.class);
}

