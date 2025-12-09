package proj;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {

  public static void main(String[] a) {
    log.info("starting game");
    SwingUtilities.invokeLater(() -> {

      // to change the game width/height or full-screen mode, change the properties file
      int DEFAULT_GAME_WIDTH = 1000;
      int DEFAULT_GAME_HEIGHT = 600;
      int DEFAULT_FULLSCREENMODE = 0; // set to 1 for full-screen

      ApplicationProp propFile = new ApplicationProp();
      Integer winX = propFile.readInt("winX","-1");
      Integer winY = propFile.readInt("winY","-1");
      Integer winWidth = propFile.readInt("winWidth", DEFAULT_GAME_WIDTH);
      Integer winHeight = propFile.readInt("winHeight", DEFAULT_GAME_HEIGHT);
      Integer fullScreenMode = propFile.readInt("fullScreenMode", DEFAULT_FULLSCREENMODE);

      // - change and save properties like this
      // propFile.addProperty("winX", "100");
      // propFile.saveToFile();

      JFrame frame = new JFrame("Alien Colonization");
      GameCanvas gamePanel = new GameCanvas(winWidth, winHeight);

      if (fullScreenMode > 0) {
        // fullscreen mode
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        frame.setUndecorated(true);
        device.setFullScreenWindow(frame);
        frame.add(gamePanel);
      } else {
        // non-fullscreen mode
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        if (winX == -1 || winY == -1) {
          // Center on screen
          frame.setLocationRelativeTo(null);
        } else {
          frame.setLocation(winX, winY);
        }
        frame.setResizable(false);
      }

      frame.setVisible(true);
      // focus the panel to receive key events
      gamePanel.requestFocusInWindow();
    });
  }


  private static Logger log = LoggerFactory.getLogger(MainClass.class);
}


