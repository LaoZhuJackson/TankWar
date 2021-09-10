package laozhu;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerOne extends Tank{
    //定义局部变量,用于判断键盘事件
    boolean left,right,up,down;
    //构造函数
    public PlayerOne(String img,int x,int y,GamePanel gamePanel,String upImg,String leftImg,String rightImg,String downImg){
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
    }
    //获取键盘移动事件
    public void keyPressed(KeyEvent e){
        int key=e.getKeyCode();
        switch (key){
            case KeyEvent.VK_A:
                left=true;
                break;
            case KeyEvent.VK_S:
                down=true;
                break;
            case KeyEvent.VK_D:
                right=true;
                break;
            case KeyEvent.VK_W:
                up=true;
                break;
            case  KeyEvent.VK_J:
                attack();//执行Tank.java里的函数
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e){
        int key=e.getKeyCode();
        switch (key){
            case KeyEvent.VK_A:
                left=false;
                break;
            case KeyEvent.VK_S:
                down=false;
                break;
            case KeyEvent.VK_D:
                right=false;
                break;
            case KeyEvent.VK_W:
                up=false;
                break;
            default:
                break;
        }
    }

    public void move(){
        if(left)
            leftward();
        else if(right)
            rightward();
        else if(up)
            upward();
        else if(down)
            downward();
    }
    @Override
    public void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
    }
    @Override
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
}
