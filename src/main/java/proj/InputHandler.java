package proj;

import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;
import game.GameAction;

public class InputHandler implements KeyListener {

  private GameState gameState;
  private GameCanvas gameCanvas;

  public InputHandler(GameState gameState, GameCanvas gameCanvas) {
    this.gameState = gameState;
    this.gameCanvas = gameCanvas;
  }

  Movement player1Movement = new Movement();
  Movement player2Movement = new Movement();

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    // player1
    if (code == KeyEvent.VK_W) {
      player1Movement.movingUp = true;
    }
    if (code == KeyEvent.VK_S) {
      player1Movement.movingDown = true;
    }
    if (code == KeyEvent.VK_A) {
      player1Movement.movingLeft = true;
    }
    if (code == KeyEvent.VK_D) {
      player1Movement.movingRight = true;
    }
    if (code == KeyEvent.VK_W || code == KeyEvent.VK_S || code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
      int[] dir = player1Movement.getDir();
      if (dir[0] == 0 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER1_STOP);
      }
      if (dir[0] == 1 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER1_E);
      }
      if (dir[0] == 1 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER1_SE);
      }
      if (dir[0] == 0 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER1_S);
      }
      if (dir[0] == -1 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER1_SW);
      }
      if (dir[0] == -1 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER1_W);
      }
      if (dir[0] == -1 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER1_NW);
      }
      if (dir[0] == 0 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER1_N);
      }
      if (dir[0] == 1 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER1_NE);
      }
    }
    if (code == KeyEvent.VK_1) {
      gameState.handleAction(GameAction.PLAYER1_PURCHASE_MARINE);
    }
    if (code == KeyEvent.VK_2) {
      gameState.handleAction(GameAction.PLAYER1_PURCHASE_SNIPER);
    }
    if (code == KeyEvent.VK_3) {
      gameState.handleAction(GameAction.PLAYER1_PURCHASE_ROGUE);
    }
    // player2
    if (code == KeyEvent.VK_UP) {
      player2Movement.movingUp = true;
    }
    if (code == KeyEvent.VK_DOWN) {
      player2Movement.movingDown = true;
    }
    if (code == KeyEvent.VK_LEFT) {
      player2Movement.movingLeft = true;
    }
    if (code == KeyEvent.VK_RIGHT) {
      player2Movement.movingRight = true;
    }
    if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
      int[] dir = player2Movement.getDir();
      if (dir[0] == 0 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER2_STOP);
      }
      if (dir[0] == 1 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER2_E);
      }
      if (dir[0] == 1 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER2_SE);
      }
      if (dir[0] == 0 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER2_S);
      }
      if (dir[0] == -1 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER2_SW);
      }
      if (dir[0] == -1 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER2_W);
      }
      if (dir[0] == -1 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER2_NW);
      }
      if (dir[0] == 0 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER2_N);
      }
      if (dir[0] == 1 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER2_NE);
      }
    }
    if (code == KeyEvent.VK_INSERT) {
      gameState.handleAction(GameAction.PLAYER2_PURCHASE_MARINE);
    }
    if (code == KeyEvent.VK_HOME) {
      gameState.handleAction(GameAction.PLAYER2_PURCHASE_SNIPER);
    }
    if (code == KeyEvent.VK_PAGE_UP) {
      gameState.handleAction(GameAction.PLAYER2_PURCHASE_ROGUE);
    }

    if (code == KeyEvent.VK_ESCAPE) {
      Window window = SwingUtilities.getWindowAncestor(gameCanvas);
      window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
    if (code == KeyEvent.VK_P) {
      gameState.handleAction(GameAction.TEST_ACTION);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();
    // player1
    if (code == KeyEvent.VK_W) {
      player1Movement.movingUp = false;
    }
    if (code == KeyEvent.VK_S) {
      player1Movement.movingDown = false;
    }
    if (code == KeyEvent.VK_A) {
      player1Movement.movingLeft = false;
    }
    if (code == KeyEvent.VK_D) {
      player1Movement.movingRight = false;
    }
    if (code == KeyEvent.VK_W || code == KeyEvent.VK_S || code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
      int[] dir = player1Movement.getDir();
      if (dir[0] == 0 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER1_STOP);
      }
      if (dir[0] == 1 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER1_E);
      }
      if (dir[0] == 1 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER1_SE);
      }
      if (dir[0] == 0 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER1_S);
      }
      if (dir[0] == -1 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER1_SW);
      }
      if (dir[0] == -1 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER1_W);
      }
      if (dir[0] == -1 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER1_NW);
      }
      if (dir[0] == 0 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER1_N);
      }
      if (dir[0] == 1 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER1_NE);
      }
    }
    // player2
    if (code == KeyEvent.VK_UP) {
      player2Movement.movingUp = false;
    }
    if (code == KeyEvent.VK_DOWN) {
      player2Movement.movingDown = false;
    }
    if (code == KeyEvent.VK_LEFT) {
      player2Movement.movingLeft = false;
    }
    if (code == KeyEvent.VK_RIGHT) {
      player2Movement.movingRight = false;
    }
    if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
      int[] dir = player2Movement.getDir();
      if (dir[0] == 0 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER2_STOP);
      }
      if (dir[0] == 1 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER2_E);
      }
      if (dir[0] == 1 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER2_SE);
      }
      if (dir[0] == 0 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER2_S);
      }
      if (dir[0] == -1 && dir[1] == 1) {
        gameState.handleAction(GameAction.PLAYER2_SW);
      }
      if (dir[0] == -1 && dir[1] == 0) {
        gameState.handleAction(GameAction.PLAYER2_W);
      }
      if (dir[0] == -1 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER2_NW);
      }
      if (dir[0] == 0 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER2_N);
      }
      if (dir[0] == 1 && dir[1] == -1) {
        gameState.handleAction(GameAction.PLAYER2_NE);
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  private class Movement {
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public int[] getDir() {
      int left = movingLeft ? -1 : 0;
      int right = movingRight ? 1 : 0;
      int up = movingUp ? -1 : 0;
      int down = movingDown ? 1 : 0;
      return new int[] {left+right,up+down};
    }
  }
}

