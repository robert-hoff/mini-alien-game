package proj;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
      // set to 1 for full-screen (in properties file)
      int DEFAULT_FULLSCREENMODE = 0;

      ApplicationProp propFile = new ApplicationProp();
      Integer winX = propFile.readInt("winX","-1");
      Integer winY = propFile.readInt("winY","-1");
      Integer winWidth = propFile.readInt("winWidth", DEFAULT_GAME_WIDTH);
      Integer winHeight = propFile.readInt("winHeight", DEFAULT_GAME_HEIGHT);
      Integer fullScreenMode = propFile.readInt("fullScreenMode", DEFAULT_FULLSCREENMODE);

      JFrame frame = new JFrame("Planet Battle");
      frame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          log.info("exit game - save window configs");
          if (fullScreenMode == 0) {
            propFile.addProperty("winX", ""+frame.getX());
            propFile.addProperty("winY", ""+frame.getY());
          }
          propFile.saveToFile();
          System.exit(0);
        }
      });

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

      // the game's dimensions are known at this point
      log.info(String.format("game size = (%d,%d)", gamePanel.getWidth(), gamePanel.getHeight()));

      // focus the panel to receive key events
      gamePanel.requestFocusInWindow();
    });
  }


  private static Logger log = LoggerFactory.getLogger(MainClass.class);
}

