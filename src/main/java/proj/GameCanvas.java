package proj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameCanvas extends JPanel implements ActionListener, KeyListener {

  private static final long serialVersionUID = 1L;
  // Position and size of player1
  private int x = 100;
  private int y = 100;
  private final int size = 10;
  private final int speed = 3;

  // Movement flags (for smooth movement)
  private boolean movingUp = false;
  private boolean movingDown = false;
  private boolean movingLeft = false;
  private boolean movingRight = false;
  
  //Player objects
  private Player playerOne = new Player();
  private Player playerTwo = new Player();


  // Game loop timer (roughly 120 FPS -> 1000ms / 120 ≈ 8)
  private final Timer timer;

  public GameCanvas() {
    setPreferredSize(new Dimension(800, 600));
    setBackground(Color.BLACK);
    setFocusable(true);
    addKeyListener(this);
    
    //Create "Base" units

    playerOne.addUnit(new Unit(5,50,0,5000,100,1.0, 780, 300));
    playerTwo.addUnit(new Unit(5,50,0,5000,100,1.0, 0, 300));
    timer = new Timer(8, this);
    timer.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Draw the player units
    g.setColor(Color.WHITE);
    g.fillRect(x, y, size, size);
    
    //Draw units (the size is temp)
    for (Unit unit : playerOne.getUnits()) {
    	g.setColor(Color.BLUE);
    	g.fillRect(unit.getxPos(), unit.getyPos(), 20, 20);
    }
    
    for (Unit unit : playerTwo.getUnits()) {
    	g.setColor(Color.RED);
    	g.fillRect(unit.getxPos(), unit.getyPos(), 20, 20);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    updatePosition();
    repaint();
    updateUnits(playerOne, playerTwo);
    updateUnits(playerTwo, playerOne);
    killUnits(playerOne, playerTwo);
  }
  
  //TODO: add accuracy
  private void updateUnits (Player friendlyPlayer, Player enemyPlayer) {
	  for (Unit friendlyUnit : friendlyPlayer.getUnits()) {
		  boolean moving = true;
		  for (Unit enemyUnit : enemyPlayer.getUnits()) {
			  //If enemy unit in range
			  if (friendlyUnit.getRange() > Math.abs(friendlyUnit.getxPos() - enemyUnit.getxPos())) {
				  //Friendly unit stops moving
				  moving = false;
				  enemyUnit.takeDamage(friendlyUnit.getStrength());
				  System.out.println(enemyUnit.getHealth());
			  }
			  //Only attack one enemy
			  break;
		  }
		  if (moving == true) {
			  friendlyUnit.move();
		  }
	  }
  }
  
  private void killUnits (Player playerOne, Player playerTwo) {
	  for (Unit unit : playerOne.getUnits()) {
		  if (unit.getHealth() <= 0) {
			  playerOne.removeUnit(unit);
		  }
	  }
	  for (Unit unit : playerTwo.getUnits()) {
		  if (unit.getHealth() <= 0) {
			  playerTwo.removeUnit(unit);
		  }
	  }
  }

  private void updatePosition() {
    if (movingUp) {
      y -= speed;
    }
    if (movingDown) {
      y += speed;
    }
    if (movingLeft) {
      x -= speed;
    }
    if (movingRight) {
      x += speed;
    }

    // Keep the square inside the window bounds
    if (x < 0) {
      x = 0;
    }
    if (y < 0) {
      y = 0;
    }
    if (x + size > getWidth()) {
      x = getWidth() - size;
    }
    if (y + size > getHeight()) {
      y = getHeight() - size;
    }
  }

  // KeyListener methods

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_W) {
      movingUp = true;
    }
    if (code == KeyEvent.VK_S) {
      movingDown = true;
    }
    if (code == KeyEvent.VK_A) {
      movingLeft = true;
    }
    if (code == KeyEvent.VK_D) {
      movingRight = true;
    }
    if (code == KeyEvent.VK_ESCAPE) {
      log.info("exit game");
      System.exit(0);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_W) {
      movingUp = false;
    }
    if (code == KeyEvent.VK_S) {
      movingDown = false;
    }
    if (code == KeyEvent.VK_A) {
      movingLeft = false;
    }
    if (code == KeyEvent.VK_D) {
      movingRight = false;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Not used, but required by KeyListener
  }

  private static Logger log = LoggerFactory.getLogger(MainClass.class);
}

