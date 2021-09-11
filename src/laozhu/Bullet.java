package laozhu;

import java.awt.*;
import java.util.ArrayList;

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
        this.hitWall();//在移动中进行碰撞检测
        this.moveToBorder();
        this.hitBase();
    }
    //玩家子弹与敌方坦克的碰撞检测
    public void hitEnemy(){
        ArrayList<EnemyTank> EnemyTanks =this.gamePanel.enemyTanksList;//将坦克列表存入另一个列表
        for(EnemyTank enemyTank: EnemyTanks){
            if(this.getRec().intersects(enemyTank.getRec())){//如果玩家子弹矩形与敌方坦克矩形重叠，即碰撞
                this.gamePanel.blastList.add(new Blast(enemyTank.x-34,enemyTank.y-14,this.gamePanel));//给敌方添加爆炸动画
                this.gamePanel.enemyTanksList.remove(enemyTank);//将被击中的坦克从enemyTanksList中remove
                this.gamePanel.removeList.add(this);//敌方子弹加入消失列表removeList
                break;
            }
        }
    }
    //子弹与基地的碰撞检测
    public void hitBase(){
        ArrayList<Base>baseList=this.gamePanel.baseList;
        for(Base base:baseList){
            if(this.getRec().intersects(base.getRec())){
                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }
    //子弹与墙壁碰撞检测
    public void hitWall(){
        //围墙列表
        ArrayList<Wall>walls=this.gamePanel.wallList;
        for(Wall wall:walls){
            if(this.getRec().intersects(wall.getRec())){
                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    //子弹出界删除
    public void moveToBorder(){
        //左右边界
        if (x<0||x+width>this.gamePanel.getWidth()){
            this.gamePanel.removeList.add(this);//将子弹加入删除列表
        }
        //上下边界
        if(y<20||y+height>this.gamePanel.getHeight()){
            this.gamePanel.removeList.add(this);
        }
    }

    //子弹类的方法
    @Override
    public void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
        this.go();//调用go方法移动子弹
        this.hitEnemy();//将被击中方法加入
    }
    @Override
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
}
