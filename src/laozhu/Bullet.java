package laozhu;

import java.awt.*;

//子弹类
public class Bullet extends GameObject{
    //尺寸
    int width=10;
    int height=10;
    //速度
    int speed =7;
    //方向
    Direction direction;

    //构造函数
    public Bullet(String img,int x,int y,GamePanel gamePanel,Direction direction){
        super(img, x, y, gamePanel);
        this.direction=direction;//给当前类的direction赋值
    }

    //四个方向的移动方法,子弹方向要与坦克方向一致，通过go（）实现
    public void leftward(){
        x-=speed;
    }
    public void rightward(){
        x+=speed;
    }
    public void upward(){
        y-=speed;
    }
    public void downward(){
        y+=speed;
    }

    public void go(){
        switch (direction){
            case LEFT -> leftward();
            case RIGHT -> rightward();
            case UP -> upward();
            case DOWN -> downward();
        }
    }

    //子弹类的方法
    @Override
    public void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
        this.go();//调用go方法移动子弹
    }
    @Override
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
}
