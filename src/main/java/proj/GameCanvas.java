package proj;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameCanvas extends JPanel implements ActionListener {

  private static final long serialVersionUID = 1L;
  private long lastTime = System.nanoTime();

  private final Timer timer;
  private final int FPS = 100;
  private final int msPerFrame = 1000 / FPS;

  public GameCanvas(int winWidth, int winHeight) {
    log.trace("GameCanvas");
    if (winWidth > 0 && winHeight > 0) {
      setPreferredSize(new Dimension(winWidth, winHeight));
    }
    setFocusable(true);
    timer = new Timer(msPerFrame, this);
  }

  private GameState gameState;

  public void startGame(GameState gameState) {
    this.gameState = gameState;
    timer.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    gameState.render(g2);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    long now = System.nanoTime();
    double dt = (now - lastTime) / 1_000_000_000.0;
    lastTime = now;
    gameState.update(dt);
    repaint();
  }

  private static Logger log = LoggerFactory.getLogger(MainClass.class);
}

