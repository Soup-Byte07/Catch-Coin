import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originSpriteSize = 16;
    final int scale = 3;
    final int spriteSize = originSpriteSize * scale;

    final int maxScreenSizeHeight = 12;
    final int maxScreenSizeWidth = 16;

    final int screenWidth = maxScreenSizeWidth * spriteSize;
    final int screenHeight = maxScreenSizeHeight * spriteSize;

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Player position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLUE);

        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        double drawInterval = 1_000_000_000/FPS; // 1666 milliseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;

                drawCount++;
            }

            if(timer >= 1_000_000_000) {
                System.out.println("FPS: "+ drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if(keyH.upPressed) {
            playerY -= playerSpeed;
        }
        if(keyH.downPressed) {
            playerY += playerSpeed;
        }
        if(keyH.leftPressed) {
            playerX -= playerSpeed;
        }
        if(keyH.rightPressed) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, spriteSize, spriteSize);
        g2.dispose();
    }
}
