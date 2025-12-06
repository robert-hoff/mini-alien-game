package proj;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {

  public static void main(String[] a) {
    log.info("starting game");
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Alien Colonization");
      GameCanvas gamePanel = new GameCanvas();

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(gamePanel);
      frame.pack();
      frame.setLocationRelativeTo(null); // Center on screen
      frame.setVisible(true);

      // Make sure the panel has focus so it receives key events
      gamePanel.requestFocusInWindow();
    });
  }


  private static Logger log = LoggerFactory.getLogger(MainClass.class);
}

