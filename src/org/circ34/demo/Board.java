import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

/**
 * Created by ethan on 2016/8/22.
 */
public class test {
    private JPanel p1;
    private JPanel p2;
    private JButton playRetryButton;
    private JButton scoreboardButton;
    private JTable table1;
    private Image bg,c1;
    private int bgx=0,bgy=-2048+512;
    private int c1x,c1y;
    private boolean GameIsOver=false;
    private byte PlayerCurrentColumn;
    private Timer ScrollDelay;


    public test() {
        playRetryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GameIsOver){
                    initGame();
                }
                else{
                    stopGame();
                }
            }
        });
        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //System.out.println("btn2");
            }
        });

        p1.addKeyListener(new KeyAdapter() {
            int keyvalue;
            boolean IsScrolling=false,IsJumping=false;
            @Override
            public void keyPressed(KeyEvent e) {
                keyvalue = e.getKeyCode();
                switch (keyvalue) {
                    case KeyEvent.VK_UP:
                        if (!IsScrolling)ScrollDelay.start();
                        break;
                    case KeyEvent.VK_LEFT:
                        if(!IsJumping){
                            if(PlayerCurrentColumn%2==1)
                        }
                        break;
                    case KeyEvent.VK_RIGHT:

                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e){
                keyvalue = e.getKeyCode();
                switch (keyvalue){
                    case KeyEvent.VK_UP:
                        ScrollDelay.stop();
                        break;
                }
            }
        });
        ScrollDelay=new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bgy++;
                p1.repaint();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setContentPane(new test().p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(768, 512);
        frame.setTitle("CIRCDemo2016");
        frame.setVisible(true);

    }
    private class rendering extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(bg,bgx,bgy,null);
            g.drawImage(c1,c1x,c1y,null);
        }
    }
    private void SpawnBalls(){
        int BallColumn=(((int)(1000*Math.random()))%7)+1;

    }
    private void initGame(){
        ScrollDelay.stop();
        ImageIcon tmp = new ImageIcon("c1.png");
        c1=tmp.getImage();
        tmp = new ImageIcon("bg.png");
        bg=tmp.getImage();
        bgx=0;
        bgy=-2048+512;
        c1x=0;
        c1y=512-150;
        PlayerCurrentColumn=1;
    }
    private void stopGame(){

    }
}

