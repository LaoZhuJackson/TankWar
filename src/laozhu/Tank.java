package laozhu;

import java.awt.*;

public abstract class Tank extends GameObject {
    //尺寸，设置为public类型方便被继承
    public int width = 44;
    public int height = 44;
    //速度
    private int speed = 5;
    //方向，通过枚举类实现,默认向上
    private Direction direction = Direction.UP;
    //四个方向的图片
    private String upImg;
    private String leftImg;
    private String rightImg;
    private String downImg;

    //攻击冷却状态
    private boolean attackCoolDown =true;
    //攻击冷却时间为1000ms
    private int attackCoolDownTime=200;

    //坦克类的构造函数，坦克的游戏图片，界面，坐标直接继承父类
    public Tank(String img, int x, int y, GamePanel gamePanel, String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, gamePanel);
        //给四个方向的图片赋值
        this.upImg = upImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.downImg = downImg;
    }

    //坦克移动
    public void leftward(){
        x-=speed;
        setImg(leftImg);
        direction=Direction.LEFT;
    }
    public void upward(){
        y-=speed;
        setImg(upImg);
        direction=Direction.UP;
    }
    public void rightward(){
        x+=speed;
        setImg(rightImg);
        direction=Direction.RIGHT;
    }
    public void downward(){
        y+=speed;
        setImg(downImg);
        direction=Direction.DOWN;
    }

    //初始化发射子弹函数
    public void attack(){
        if(attackCoolDown){
            Point p=this.getHeadPoint();
            Bullet bullet=new Bullet("images/tankmissile.gif",p.x,p.y,this.gamePanel,this.direction);
            this.gamePanel.bulletList.add(bullet);//把初始化的新子弹添加到列表里
            //攻击完后进入冷却，线程开始
            new AttackCD().start();
        }
    }

    //新建一个内部类，利用线程实现攻击冷却时间
    class AttackCD extends Thread{
        public void run(){
            attackCoolDown=false;//开始时初始化为还没开始冷却
            try{
                Thread.sleep(attackCoolDownTime);//线程休眠一秒
            }catch (Exception e){
                e.printStackTrace();//打印出错信息
            }
            //进入冷却状态
            attackCoolDown=true;
            this.stop();//线程终止
        }
    }
    //获取坦克头部坐标
    public Point getHeadPoint(){
        switch (direction){
            case LEFT :
                return new Point(x-12,y+height/2);//横坐标不变，纵坐标加半个坦克长度
            case  RIGHT:
                return new Point(x+width+12,y+height/2);
            case UP:
                return new Point(x+width/2,y-12);
            case DOWN:
                return new Point(x+width/2,y+height+12);
            default:
                return null;
        }
    }
    //定义setImg函数
    public void setImg(String img){
        this.img=Toolkit.getDefaultToolkit().getImage(img);
    }

    //tank类方法，继承父类后再继承给子类（坦克之下的我方坦克，敌方坦克等）
    @Override
    public abstract void paintSelf(Graphics g);

    @Override
    public abstract Rectangle getRec();
}
