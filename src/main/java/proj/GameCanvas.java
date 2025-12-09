package proj;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameCanvas extends JPanel implements ActionListener, KeyListener {

  private static final long serialVersionUID = 1L;
  // Position and size of player1
  private double x = 100;
  private double y = 100;
  private final int size = 12;
  // private final int speed = 5;
  private final double speed = 300.0; // pixels per second (tune as you like)
  private long lastTime = System.nanoTime();

  // Movement flags (for smooth movement)
  private boolean movingUp = false;
  private boolean movingDown = false;
  private boolean movingLeft = false;
  private boolean movingRight = false;

  private final Timer timer;
  private final int FPS = 60;
  private final int msPerFrame = 1000 / FPS;

  private BufferedImage backgroundImage;


  public GameCanvas(int winWidth, int winHeight) {
    log.trace("GameCanvas");
    try {
      backgroundImage = ImageIO.read(GameCanvas.class.getResource("/game-field.png"));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("can't find required game asset");
    }

    if (winWidth > 0 && winHeight > 0) {
      setPreferredSize(new Dimension(winWidth, winHeight));
    }

    setBackground(Color.WHITE);
    setFocusable(true);
    addKeyListener(this);

    timer = new Timer(msPerFrame, this);
    timer.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // playing field
    g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

    // Draw the player
    g2.setColor(Color.RED);
    g2.fill(new Rectangle2D.Double(x, y, size, size));
    g2.setColor(Color.BLACK);
    g2.setStroke(new BasicStroke(2));
    g2.draw(new Rectangle2D.Double(x, y, size, size));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    long now = System.nanoTime();
    double deltaSeconds = (now - lastTime) / 1_000_000_000.0;
    lastTime = now;
    updatePosition(deltaSeconds);
    repaint();
  }

  private void updatePosition(double deltaSeconds) {
    if (movingUp) {
      y -= speed * deltaSeconds;
    }
    if (movingDown) {
      y += speed * deltaSeconds;
    }
    if (movingLeft) {
      x -= speed * deltaSeconds;
    }
    if (movingRight) {
      x += speed * deltaSeconds;
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
    if (code == KeyEvent.VK_P) {
      System.out.println(getWidth());
      System.out.println(getHeight());
    }
    if (code == KeyEvent.VK_ESCAPE) {
      Window window = SwingUtilities.getWindowAncestor(this);
      window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
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

  // method needed by KeyListener
  @Override
  public void keyTyped(KeyEvent e) {}

  private static Logger log = LoggerFactory.getLogger(MainClass.class);
}

