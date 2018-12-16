import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Main window for breakout
 * 
 * @since 11.12.2018
 * @author seymour
 */
public class Window extends javax.swing.JFrame implements KeyListener, MouseListener, MouseMotionListener {

    int winL = 800, winH = 600;
    int timeout, score, lives, level, remainingBricks, beepNum, controlType = 1;
    boolean victory;
    int state = 1;
    /** STATES
     *-1 : paused
     * 0 : start screen
     * 1 : playing
     * 2 : controls select
     * 3 : level select
     * 4 : results screen
     */
    
    // X coordinates for the stars in the background
    int[] xStars;
    Brick[] levels;
    int levelL, levelH;
    
    // Declaration of object variables
    Ball ball;
    Paddle paddle;
    Brick brick;
    Timer timer;
    Random rand;
    Point mouse, frame;
    BufferedImage bi;
    Image title, select, you, win, lose;
    Graphics big;
    Button buttons[], controls;

    public Window() {
        initComponents();
        
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        setTitle("Breakout");
        setSize(winL, winH); 
        
        bi = (BufferedImage)createImage(winL, winH);
        big = bi.createGraphics();
        
        mouse = new Point();
        frame = new Point();
        ball = new Ball(0, 0);
        paddle = new Paddle(0, 0);
        timer = new Timer();
        rand = new Random();
        brick = new Brick(0, 0);
        
        buttons = new Button[11];
        
        // Controls
        buttons[0] = new Button(150, 450, "controls.png", "controlsH.png");
        
        // Level Select
        buttons[1] = new Button(460, 450, "level_select.png", "level_selectH.png");
        
        // Mouse Button
        buttons[2] = new Button(126, 202, "mouse.png", "mouseH.png");
        
        // Keyboard Button
        buttons[3] = new Button(366, 202, "keyboard.png", "keyboardH.png");
        
        // Main Menu
        buttons[4] = new Button(166, 320, "main_menu.png", "main_menuH.png");
        
        // Exit Game
        buttons[5] = new Button(446, 320, "exit_game.png", "exit_gameH.png");
        
        // Level1
        buttons[6] = new Button(70, 60, "lv1.png", "lv1H.png");
        // Level2
        buttons[7] = new Button(550, 60, "lv2.png", "lv2H.png");
        // Level3
        buttons[8] = new Button(310, 210, "lv3.png", "lv3H.png");
        // Level4
        buttons[9] = new Button(90, 340, "lv4.png", "lv4H.png");
        // Level5
        buttons[10] = new Button(586, 354, "lv5.png", "lv5H.png");
        
        // Defining images and their locations
        title = new Image(184, 136, "title.png");
        select = new Image(250, 500, "select_level.png");
        you = new Image(170, 112, "you.png");
        win = new Image(398, 112, "win.png");
        lose = new Image(398, 112, "lose.png");
        
        // Generating random X-coordinates for stars in the background
        xStars = new int[winH/5];
        for (int i = 0; i < xStars.length; i++) {
            xStars[i] = rand.nextInt(winL);
        }
        
        state = 0;
        
        timer.start();
    }
    
    // Call when starting a new game
    public void newGame() {
        ball.setColor(Color.white);
        paddle.setPos(400, 550);
        
        ball.state = 0;
        score = 0;
        
        ball.up = ball.left = true;
        ball.hitNum = 0;
        ball.speed = 0;
        ball.xSpeed = ball.xSpeed1;
        ball.ySpeed = ball.ySpeed1;
        
        beepNum = 0;
        
        resetBricks();
        repaint();
    }
    
    // Called when resetting the bricks
    public void resetBricks() {
        
        switch(level) {
            // Full grid
            case 1:
                lives = 5;
                levelL = 14; levelH = 10;
                levels = new Brick[levelL * levelH];
                for (int i = 0; i < levelL; i++) {
                    int xPos, yPos;

                    for (int j = 0; j < levelH; j++) {
                        xPos = i * (brick.w + 4) + 25;
                        yPos = j * (brick.h + 5) + 50; 

                        levels[(i * levelH) + j] = new Brick(xPos, yPos);
                    }
                }
                break;

            // Checkerboard
            case 2:
                lives = 5;
                levelL = 7; levelH = 10;
                levels = new Brick[levelL * levelH];
                for (int i = 0; i < levels.length; i++) {
                    int column = i % (levelH),
                        row = i % (levelL*2),
                        xPos = row * (brick.w + 4) + 25,
                        yPos = column * (brick.h + 5) + 50;

                    levels[i] = new Brick(xPos, yPos);
                    levels[i].setColor(Color.blue);
                }
                break;

            // Level 1, but smaller
            case 3:
                lives = 7;
                levelL = 22; levelH = 16;
                levels = new Brick[levelL * levelH];
                for (int i = 0; i < levelL; i++) {
                    int xPos, yPos;

                    for (int j = 0; j < levelH; j++) {
                        xPos = i * (30 + 4) + 28;
                        yPos = j * (15 + 5) + 50; 

                        levels[(i * levelH) + j] = new Brick(xPos, yPos);
                        levels[(i * levelH) + j].setColor(Color.yellow);
                        levels[(i * levelH) + j].setSize(30, 15);
                    }
                }
                break;

            // Space invaders enemy
            case 4:
                lives = 3;
                levelL = 11; levelH = 8;
                levels = new Brick[levelL * levelH];
                for (int i = 0; i < levelL; i++) {
                    int xPos, yPos;

                    for (int j = 0; j < levelH; j++) {
                        xPos = i * (40 + 4) + 160;
                        yPos = j * (40 + 5) + 50; 

                        levels[(i * levelH) + j] = new Brick(xPos, yPos);
                        levels[(i * levelH) + j].setColor(Color.green);
                        levels[(i * levelH) + j].setSize(40, 40);
                    }
                }
                
                // Blocks that don't appear (to make the image)
                int[] lv4hidden = { 0, 1, 2, 3, 7, 8, 9, 10, 13, 14, 15, 17, 23, 24, 27, 30, 32, 33, 38, 40, 41, 46, 47, 48, 49, 54, 56, 59, 62, 65, 71, 72, 73, 74, 77, 78, 79, 80, 81, 82, 83, 87 };
                for (int number : lv4hidden) {
                    levels[number].visible = false;
                }
                break;
                
            // Heart
            case 5:
                lives = 1;
                levelL = 9; levelH = 10;
                levels = new Brick[levelL * levelH];
                for (int i = 0; i < levelL; i++) {
                    int xPos, yPos;

                    for (int j = 0; j < levelH; j++) {
                        xPos = i * (brick.w + 4) + 170;
                        yPos = j * (brick.h + 5) + 100; 

                        levels[(i * levelH) + j] = new Brick(xPos, yPos);
                        levels[(i * levelH) + j].setColor(Color.red);
                    }
                }
                
                // Blocks that don't appear (to make the image)
                int[] lv5hidden = { 0, 6, 7, 8, 9, 17, 18, 19, 28, 29, 30, 39, 40, 41, 50, 59, 68, 69, 77, 78, 79, 80, 86, 87, 88, 89 };
                for (int number : lv5hidden) {
                    levels[number].visible = false;
                }
                break;
        }
    }
    
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        
        if (timeout != 0) return;
        
        // Start menu
        if (state == 0) {
            switch(key) {
                case KeyEvent.VK_Q:
                    state = 2;
                    break;
                case KeyEvent.VK_W:
                    state = 3;
                    break; 
                default: return;
            }
            timeout = 60;
        }
        
        // Control select
        if (state == 2) {
            switch(key) {
                case KeyEvent.VK_1:
                    controlType = 1;
                    break;
                case KeyEvent.VK_2:
                    controlType = 0;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                case KeyEvent.VK_ESCAPE:
                    state = 0;
                    break;
                default: return;
            }
            state = 0;
            timeout = 60;
        }
        
        // Level select
        if (state == 3) {
            switch(key) {
                case KeyEvent.VK_1:
                case KeyEvent.VK_2:
                case KeyEvent.VK_3:
                case KeyEvent.VK_4:
                case KeyEvent.VK_5:
                    level = key - 48;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                case KeyEvent.VK_ESCAPE:
                    state = 0;
                    return;
                default: return;
            }
            state = 1;
            newGame();
        }
        
        // Paused or playing
        if (Math.abs(state) == 1) {
            switch(key) {
                case KeyEvent.VK_LEFT:
                    if ("key".equals(controlType)) paddle.dir = 1;
                    break;
                case KeyEvent.VK_RIGHT:
                    if ("key".equals(controlType)) paddle.dir = 2;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    if ("key".equals(controlType)) ball.state = 1;
                    break;
                case KeyEvent.VK_ESCAPE:
                    state *= -1;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    if (state == -1) state = 0;
            }
        }
        
        // End screen
        if (state == 4) {
            switch(key) {
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
                default: state = 0;
            }
        }
    }
    
    public void keyTyped(KeyEvent ke) { }
    
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        
        switch(key) {
            case KeyEvent.VK_LEFT:
                paddle.dir = 0;
                break;
            case KeyEvent.VK_RIGHT:
                paddle.dir = 0;
                break;
        }
    }
    
    public void mouseClicked(MouseEvent m) {
        int button = m.getButton();
        
        // Left click
        if (controlType == 1) {
            if (state == 1) {
                switch(button) {
                    // Left click
                    case MouseEvent.BUTTON1:
                    // Scroll click
                    case MouseEvent.BUTTON2:
                    // Right click
                    case MouseEvent.BUTTON3:
                        ball.state = 1;
                        break;
                }
            }
        }
        
        if (state == 0) {
            // Controls Button
            if (collision(mouse, buttons[0])) {
                state = 2;
            }
            // Level Select Button
            if (collision(mouse, buttons[1])) {
                state = 3;
            }
        }
        if (state == 2) {
            // Mouse
            if (collision(mouse, buttons[2])) {
                controlType = 1;
                state = 0;
            }
            // Keyboard
            if (collision(mouse, buttons[3])) {
                controlType = 2;
                state = 0;
            }
        }
        if (state == 3) {
            for (int i = 0; i < 5; i++) {
                if (collision(mouse, buttons[i + 6])) {
                    level = i + 1;
                    state = 1;
                    newGame();
                }
            }
        }
        if (state == 4) {
            // Main Menu
            if (collision(mouse, buttons[4])) {
                state = 0;
            }
            // Exit Game
            if (collision(mouse, buttons[5])) {
                System.exit(0);
            }
        }
    }
    
    public void mouseMoved(MouseEvent m) {
        // Highlights buttons when hovering over them        
        for (Button b : buttons) {
            if (collision(mouse, b)) {
                b.hover = true;
            } else b.hover = false;
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Other Mouse Detection ">
    public void mouseDragged(MouseEvent m) { }
    
    public void mousePressed(MouseEvent m) { } 

    public void mouseReleased(MouseEvent m) { } 

    public void mouseEntered(MouseEvent m) { } 

    public void mouseExited(MouseEvent m) { }
    //</editor-fold>
    
    public void paint(Graphics g) {
        // Clear the window
        big.clearRect(0, 0, winL, winH);
        
        // Draw the black background
        big.setColor(Color.black);
        big.fillRect(0, 0, winL, winH);
        
        // Main menu
        if (state == 0) {
            title.draw(big, this);
            buttons[0].draw(big, this);
            buttons[1].draw(big, this);
        }
        
        // Playing or Paused
        if (state == 1) {
            big.setColor(Color.white);
            // Drawing stars in the background
            for (int i = 0; i < xStars.length; i++) {
                int x = xStars[i], y = i*5;
                big.drawLine(x, y, x, y);
            }
            
            // Black background for top bar
            big.setColor(Color.black);
            big.fillRect(0, 0, winL, 45);
            
            big.setColor(Color.white);
            big.drawString("Score: " + score, 10, 40);
            big.drawString("Remaining Bricks: " + remainingBricks, 350, 40);
            big.drawString("Lives: " + lives, 700, 40);
            
            ball.draw(big);
            paddle.draw(big);
            
            
            // Draw the bricks (depending on each level)
            for (Brick b : levels) {
                b.draw(big);
            }
        }
        
        // Paused
        if (state == -1) {
            big.setColor(Color.black);
            big.fillRect(250, 285, 300, 20);
            big.setColor(Color.white);
            big.drawString("Press BACKSPACE to go back to the main menu", 260, 300);
        }
        
        // Control select screen
        if (state == 2) {
            buttons[2].draw(big, this);
            buttons[3].draw(big, this);
        }
        
        // Level select screen
        if (state == 3) {
            big.drawImage(select.img, select.x, select.y, this);
            for (int i = 6; i < 11; i++) {
                buttons[i].draw(big, this);
            }
        }
        
        if (state == 4) {
            // You Win/Lose
            you.draw(big, this);
            if (victory) win.draw(big, this);
            else lose.draw(big, this);
            
            big.setColor(Color.white);
            big.drawString("FINAL SCORE: " + score, 350, 300);
            // Main Menu
            buttons[4].draw(big, this);
            
            // Exit Game
            buttons[5].draw(big, this);
        }
        
        // Draw the new image
        g.drawImage(bi, 0, 0, this);
    }
    
    /*public void angleBounce(Ball b, Paddle p) {
        double ballCenter = b.x + b.w/2;
        double paddleCenter = p.x + p.w/2;
        
        double offset = ballCenter - paddleCenter;
        double offsetRatio = offset/paddleCenter;
        
        b.xSpeed = (int)(b.xSpeed1 * (offsetRatio));
    }/**/
    
    public void angleBounce(Ball b, Paddle p) {
        double ballCenter = b.x + b.w/2;
        double paddleCenter = p.x + p.w/2;
        
        double offset = ballCenter - paddleCenter;
        double offsetRatio = offset / paddleCenter;
        
        int newSpeed = (int) (b.xSpeed1 + (offsetRatio*13));
        b.setSpeed(newSpeed, b.ySpeed1);
    }/**/
    
    public boolean collision(Ball b, Paddle p) {
        int middleX = b.x + b.w/2,
            middleY = b.y + b.h/2,
            half = b.w/2;
        
        if (middleX + half > p.x) {
            if (middleX - half < p.x+p.w) {
                if (middleY + half > p.y) {
                    if (middleY - half < p.y+p.h) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    public int collision(Ball b, Brick br) {
        int middleX = b.x + b.w/2,
            middleY = b.y + b.h/2,
            half = b.w/2,
            margin = 4;
        
        if (middleY + half > br.y) {
            if (middleY - half < br.y+br.h) {
                if (middleX + half > br.x + margin) {
                    if (middleX + half < br.x+br.w - margin) {
                        return 0;
                    }
                }
                // Left side
                if (middleX + half > br.x) {
                    if (middleX - half < br.x + margin) {
                        if (!ball.left) return 1;
                        else return 0;
                    }
                }
                // Right side
                if (middleX + half > br.x + br.w - margin) {
                    if (middleX - half < br.x + br.w) {
                        if (ball.left) return 1;
                        else return 0;
                    }
                }
            }
        }
        
        return -1;
    }
    public boolean collision(Point p, Button b) {
        int mX = p.x,
            mY = p.y;
        
        if (mX > b.x) {
            if (mX < b.x + b.w) {
                if (mY > b.y) {
                    if (mY < b.y + b.h) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    public void brickLoop(Brick[] brs) {
        int count = 0;
        
        for (Brick br : brs) {
            // If the brick can be seen/hit
            if (br.visible == true) {
                // If the ball hits the bottom/top of the brick, bounce it up or down.
                if (collision(ball, br) == 0) {
                    ball.bounceY();
                    br.visible = false;
                }
                
                // If the ball hits the side of a brick, bounce it left or right.
                if (collision(ball, br) == 1) {
                    ball.bounceX();
                    br.visible = false;
                }
                
                // If the ball was hit, play a noise and add 10 to the score
                if (br.visible == false) {
                    ball.hitNum++;
                    //beeps[beepNum].play();
                    beepNum++;

                    score += 10;
                }
                count++;
                if (beepNum == 13) beepNum = 0;
            }
        }
        remainingBricks = count;
    }
    
    public class Timer extends Thread {
        
        public void run() {
            while(true) {
                // Finds the current location of the mouse on the monitor
                mouse.setLocation(MouseInfo.getPointerInfo().getLocation());
                // Finds the current location of the window (frame)
                frame.setLocation(getLocation());
                // Sets the point location of the mouse cursor in the frame relative to the location on-screen
                mouse.setLocation(Math.abs(frame.x - mouse.x), Math.abs(frame.y - mouse.y));
                
                // Changing the cursor (hidden in-game)
                if (state == 1) setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("").getImage(), new Point(10,0), "blank"));
                else setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("./img/cursor.png").getImage(), new Point(10,0), "mouse"));
                
                if (timeout != 0) timeout--;
                
                if (state == 1) {
                    // Keyboard movement
                    if (controlType == 0) {
                        if (paddle.dir == 1) paddle.moveLeft();
                        else if (paddle.dir == 2) paddle.moveRight();
                    } else {
                        // Setting the paddle's midway position to the mouse cursor
                        paddle.x = mouse.x - paddle.w/2;
                        //paddle.y = mouse.y - paddle.h/2;
                        if (paddle.x < 5) paddle.x = 5;
                        if (paddle.x + paddle.w > winL - 5) paddle.x = winL - 5 - paddle.w;
                    }
                    
                    // Ball hovers over center of paddle while awaiting click
                    if (ball.state == 0) {
                        ball.x = (paddle.x + paddle.w/2) - (ball.w/2);
                        ball.y = paddle.y - ball.h - 10;
                    } else ball.move();
                    
                    if (!ball.up) {
                        if (collision(ball, paddle)) {
                            angleBounce(ball, paddle);
                            ball.up = true;
                        }
                    }
                    int count = 0;
                    if (state == 1) {
                        brickLoop(levels);
                        
                        if (remainingBricks == 0) {
                            victory = true;
                            score += lives * 50;
                            state = 4;
                        }
                        
                        if (lives == 0) {
                            victory = false;
                            state = 4;
                        }
                    }
                    
                    if (ball.hitNum == 5) {
                        ball.hitNum = 0;
                        ball.speed++;
                        score += 50;
                        if (ball.speed <= 2) {
                            ball.xSpeed = ball.xSpeed1 + ball.speed;
                            ball.ySpeed = ball.ySpeed1 + ball.speed;
                        }
                    }
                    
                    // Bounce against side walls
                    if (ball.x < 0 || ball.x + ball.w > winL) ball.bounceX();
                    // Bounce off of top wall
                    if (ball.y < 23) ball.bounceY();
                    // Out of bounds at bottom
                    if (ball.y > winH) {
                        ball.state = 0;
                        lives--;
                        ball.up = true;
                        ball.hitNum = 0;
                        ball.speed = 0;
                        ball.xSpeed = ball.xSpeed1;
                        ball.ySpeed = ball.ySpeed1;
                    }
                }
                try {
                    Thread.sleep(7);
                } catch(Exception e) { 
                    System.out.println(e);
                }
                repaint();
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Default code ">
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
