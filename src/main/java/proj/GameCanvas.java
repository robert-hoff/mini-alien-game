package proj;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameCanvas extends JPanel implements ActionListener, KeyListener {

  private static final long serialVersionUID = 1L;
  // Position and size of player1
  private int x = 100;
  private int y = 100;
  private final int size = 12;
  private final int speed = 3;

  // Movement flags (for smooth movement)
  private boolean movingUp = false;
  private boolean movingDown = false;
  private boolean movingLeft = false;
  private boolean movingRight = false;

  // Game loop timer (roughly 120 FPS -> 1000ms / 120 ≈ 8)
  private final Timer timer;

  public GameCanvas() {
    setPreferredSize(new Dimension(800, 600));
    setBackground(Color.WHITE);
    setFocusable(true);
    addKeyListener(this);
    timer = new Timer(8, this);
    timer.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Draw the player
    g2.setColor(Color.RED);
    g2.fill(new Rectangle2D.Double(x, y, size, size));
    g2.setColor(Color.BLACK);
    g2.setStroke(new BasicStroke(2));
    g2.draw(new Rectangle2D.Double(x, y, size, size));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    updatePosition();
    repaint();
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

