package laozhu;

import java.awt.*;
import java.util.Random;

//敌方坦克类
public class enemyTank extends Tank{

    int moveTime=0;//定义移动次数，通过判定移动次数改变方向

    public enemyTank(String img,int x,int y,GamePanel gamePanel, String upImg, String leftImg, String rightImg,String downImg){
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
    }
    //随机生成的方向
    public Direction getRandomDirection(){
        Random random=new Random();
        int rnum=random.nextInt(4);
        //利用switch，根据随机数确定方向
        switch (rnum){
            case 0:
                return Direction.LEFT;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.UP;
            case 3:
                return Direction.DOWN;
            default:
                return null;
        }
    }
    //移动函数
    public void go(){
        attack();//实现边移动边射击
        if(moveTime>=20){
            direction=getRandomDirection();
            moveTime=0;
        }
        else
            moveTime++;
        switch (direction){
            case LEFT -> leftward();
            case RIGHT -> rightward();
            case UP -> upward();
            case DOWN -> downward();
        }
    }

    public void attack(){
        Point p =getHeadPoint();
        Random random =new Random();
        int rnum=random.nextInt(100);
        if(rnum<4){//有4%的几率发射子弹
            //将子弹添加到
            this.gamePanel.bulletList.add(new EnemyBullet("images/enemymissile.gif",p.x,p.y,this.gamePanel,this.direction));
        }
    }

    @Override
    public void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
        go();//自动移动
    }

    @Override
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
}
