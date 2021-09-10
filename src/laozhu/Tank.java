package laozhu;

import java.awt.*;

public abstract class Tank extends GameObject {
    //尺寸，设置为public类型方便被继承
    public int width = 40;
    public int height = 50;
    //速度
    private int speed = 5;
    //方向，通过枚举类实现,默认向上
    private Direction direction = Direction.UP;
    //四个方向的图片
    private String upImg;
    private String leftImg;
    private String rightImg;
    private String downImg;

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
