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
public class test extends JComponent{
    private JPanel p1;
    private JPanel p2;
    private JButton playRetryButton;
    private JButton scoreboardButton;
    private Image bg,c1;
    private int bgx=0,bgy=-2048+512;
    private int c1x,c1y;
    private int[] columnPX={55,150,300,390,535,620};
    private boolean GameIsOver=true;
    private boolean IsScrolling=false,IsJumping=false,IsSwitching=false;
    private byte PlayerCurrentColumn;
    private Timer ScrollDelay,LeftJumpDelay,RightJumpDelay;
    private ArrayList<Integer> scoreRecordArrayList=new ArrayList<>();
    private ImageIcon tmp;
    public test(){
        playRetryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GameIsOver){
                    initGame();
                }
                else{
                    playRetryButton.requestFocus(true);
                }
            }
        });
        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        p1.addKeyListener(new KeyAdapter() {
            int keyvalue;

            @Override
            public void keyPressed(KeyEvent e) {
                keyvalue = e.getKeyCode();
                switch (keyvalue) {
                    case KeyEvent.VK_UP:
                        if (!IsScrolling) {
                            IsScrolling=true;
                            ScrollDelay.start();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if(!IsJumping&&!IsSwitching){
                            if(PlayerCurrentColumn%2==1){
                                IsSwitching=true;
                                tmp=new ImageIcon("c1L.png");
                                c1=tmp.getImage();
                                c1x=columnPX[--PlayerCurrentColumn];
                                p1.repaint();
                            }
                            else{
                                LeftJump();
                            }
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(!IsJumping&&!IsSwitching){
                            if(PlayerCurrentColumn%2==0){
                                IsSwitching=true;
                                tmp=new ImageIcon("c1R.png");
                                c1=tmp.getImage();
                                c1x=columnPX[++PlayerCurrentColumn];
                                p1.repaint();
                            }
                            else{
                                RightJump();
                            }
                        }
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e){
                keyvalue = e.getKeyCode();
                switch (keyvalue){
                    case KeyEvent.VK_UP:
                        if(IsScrolling)ScrollDelay.stop();
                        break;
                    case KeyEvent.VK_LEFT:
                        if (IsSwitching)IsSwitching=false;
                        if (IsJumping)IsJumping=false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (IsSwitching)IsSwitching=false;
                        if (IsJumping)IsJumping=false;
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
        LeftJumpDelay=new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                c1x++;
                p1.repaint();
            }
        });
        RightJumpDelay=new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                c1x--;
                p1.repaint();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setContentPane(new test().p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(768,768);
        frame.setTitle("CIRCDemo2016");
        frame.setVisible(true);

    }
    private void SpawnBalls(){
        byte BallColumn=(byte)((((int) (1000*Math.random()))%6)+1);

    }
    private void initGame(){
        ScrollDelay.stop();
        tmp = new ImageIcon("c1L.png");
        c1=tmp.getImage();
        tmp = new ImageIcon("bg.png");
        bg=tmp.getImage();
        bgx=0;
        bgy=-2048+768;
        c1y=768-320;
        PlayerCurrentColumn=0;
        c1x=columnPX[PlayerCurrentColumn];
        p1.repaint();
        p1.requestFocus();
        System.out.println("initgame");
    }
    private void stopGame(){

    }
    private void RightJump(){
        IsJumping=true;
        LeftJumpDelay.start();
        while(c1x<columnPX[PlayerCurrentColumn+1]){}
        LeftJumpDelay.stop();
        PlayerCurrentColumn++;
        IsJumping=false;
    }
    private void LeftJump(){
        IsJumping=true;
        RightJumpDelay.start();
        while(c1x>columnPX[PlayerCurrentColumn-1]){}
        RightJumpDelay.stop();
        PlayerCurrentColumn--;
    }

    private void createUIComponents() {
        p1=new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(bg,bgx,bgy,null);
                g.drawImage(c1,c1x,c1y,null);
            }
        };
    }
}

