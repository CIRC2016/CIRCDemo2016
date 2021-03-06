import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
/**
 * Created by ethan on 2016/8/22.
 */
public class test extends JPanel {
    private JPanel p1;
    private JPanel p2;
    private JButton playRetryButton;
    private JButton scoreboardButton;
    private Image bg, c1L, c1R, ball, c1Lf, c1Rf;
    private int bgx = 0, bgy = -2048 + 512;
    private int c1x, c1y, time = 0;
    private int[] columnPX = {55, 150, 300, 390, 535, 620};
    private boolean GameIsOver = true;
    private boolean IsScrolling = false, IsJumping = false, IsSwitching = false, ani = true;
    private byte PlayerCurrentColumn;
    private Timer ScrollDelay, LeftJumpDelay, RightJumpDelay, BallSpawner, Timer;
    private ImageIcon tmp;
    private ArrayList<Integer> Scores=new ArrayList<>();
    private ArrayList<ball> balls=new ArrayList<>();

    public test() {

        playRetryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameIsOver) {
                    initGame();
                } else {
                    playRetryButton.requestFocus(true);
                    stopGame();
                }
            }
        });
        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scoreboard.main();
            }
        });

        p1.addKeyListener(new KeyAdapter() {
            int keyvalue;

            @Override
            public void keyPressed(KeyEvent e) {
               if (!GameIsOver){
                    keyvalue = e.getKeyCode();
                    switch (keyvalue) {
                        case KeyEvent.VK_UP:
                            if (!IsScrolling) {
                                IsScrolling = true;
                                ScrollDelay.start();
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            if (!IsJumping && !IsSwitching) {
                                if (PlayerCurrentColumn % 2 == 1) {
                                    IsSwitching = true;
                                    c1x = columnPX[--PlayerCurrentColumn];
                                    p1.repaint();
                                } else {
                                    if (PlayerCurrentColumn > 0) {
                                        IsJumping = true;
                                        PlayerCurrentColumn--;
                                        if (IsScrolling) ScrollDelay.stop();
                                        LeftJumpDelay.start();
                                    }
                                }
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (!IsJumping && !IsSwitching) {   //Right flipping
                                if (PlayerCurrentColumn % 2 == 0) {
                                    IsSwitching = true;
                                    c1x = columnPX[++PlayerCurrentColumn];
                                    p1.repaint();
                                } else {   //RightJump
                                    if (PlayerCurrentColumn < 5) {
                                        IsJumping = true;
                                        PlayerCurrentColumn++;
                                        if (IsScrolling) ScrollDelay.stop();
                                        RightJumpDelay.start();
                                    }
                                }
                            }
                            break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!GameIsOver) {
                    keyvalue = e.getKeyCode();
                    switch (keyvalue) {
                        case KeyEvent.VK_UP:
                            if (IsScrolling) {
                                ScrollDelay.stop();
                                IsScrolling = false;
                                System.out.println("StopScrolling");
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            if (IsSwitching) IsSwitching = false;
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (IsSwitching) IsSwitching = false;
                            break;
                        case KeyEvent.VK_DOWN:
                            System.out.println("dr");
                            break;
                    }
                }
            }
        });
        ScrollDelay = new Timer(50, new ActionListener() {
            protected byte frames = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                bgy++;
                frames++;
                p1.repaint();
                if (frames == 3) {
                    ani = !ani;
                    frames = 0;
                }
                System.out.println("Scrolling");
            }
        });
        LeftJumpDelay = new Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (c1x <= columnPX[PlayerCurrentColumn]) {
                    LeftJumpDelay.stop();
                    IsJumping = false;
                    if (IsScrolling) ScrollDelay.start();
                }
                c1x--;
                p1.repaint();
                System.out.println("LeftJumped");

            }
        });
        RightJumpDelay = new Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (c1x >= columnPX[PlayerCurrentColumn]) {
                    RightJumpDelay.stop();
                    IsJumping = false;
                    if (IsScrolling) ScrollDelay.start();
                }
                c1x++;
                p1.repaint();
                System.out.println("RightJumped");
            }
        });
        BallSpawner = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                balls.add(new ball());
                p1.repaint();
            }
        });
        Timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //time++;

            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("test");
        frame.setContentPane(new test().p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(768, 768);
        frame.setTitle("CIRCDemo2016");
        frame.setVisible(true);
        System.out.println(System.getProperty("user.dir"));
    }

    private void SpawnBalls() {
        byte BallColumn = (byte) ((((int) (1000 * Math.random())) % 6) + 1);

    }

    private void initGame() {
        ScrollDelay.stop();
        tmp = new ImageIcon("c1L.png");
        c1L = tmp.getImage();
        tmp = new ImageIcon("bg.png");
        bg = tmp.getImage();
        tmp = new ImageIcon("c1R.png");
        c1R = tmp.getImage();
        tmp = new ImageIcon("ball.png");
        ball = tmp.getImage();
        tmp = new ImageIcon("c1Lf.png");
        c1Lf = tmp.getImage();
        tmp = new ImageIcon("c1Rf.png");
        c1Rf = tmp.getImage();
        bgx = 0;
        bgy = -2048 + 768;
        c1y = 768 - 320;
        PlayerCurrentColumn = 0;
        c1x = columnPX[PlayerCurrentColumn];
        p1.repaint();
        p1.requestFocus();
        BallSpawner.start();
        Timer.start();
        System.out.println("initgame");

    }

    private void stopGame() {
        GameIsOver = true;
        Timer.stop();

    }

    private class ball {
        private int y, ballcolumn;

        ball() {
            ballcolumn = ((int) (1000 * Math.random())) % 6;
            y = 768 - 50;
            Timer ballevent = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (GameIsOver) ((Timer) e.getSource()).stop();
                    if (ballcolumn == PlayerCurrentColumn && y >= c1y + 50) {
                        stopGame();
                        ((Timer) e.getSource()).stop();
                    }
                    y++;
                }
            });
            ballevent.start();
        }
    }


    private void createUIComponents() {
        p1 = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, bgx, bgy, null);
                if (PlayerCurrentColumn % 2 == 0) {
                    if (ani) g.drawImage(c1L, c1x, c1y, null);
                    else g.drawImage(c1Lf, c1x, c1y, null);
                } else {
                    if (ani) g.drawImage(c1R, c1x, c1y, null);
                    else g.drawImage(c1Rf, c1x, c1y, null);
                }
                for (int count = 0; count < balls.size(); count++) {
                    ball op = balls.get(count);
                    g.drawImage(ball, columnPX[op.ballcolumn], op.y, null);
                }
            }
        };
    }
}

