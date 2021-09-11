package laozhu;

import java.awt.*;

//可继承的抽象类父类，加上abstract关键字
public abstract class GameObject {
    //图片,设置为public类型方便被继承，下同
    public Image img;
    //坐标
    public int x;
    public int y;
    //界面,元素需要在同一个游戏界面生成
    public GamePanel gamePanel;

    //定义元素的构造函数
    public GameObject(String img, int x, int y, GamePanel gamePanel) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);//将图片参数从Image类型转换成String类型
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
    }

    public GameObject(int x, int y, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
    }

    //定义共通方法
    //绘制方法，将自身绘制出来，参数为一个画布
    public abstract void paintSelf(Graphics g);

    //返回自身矩形，通过判断矩形是否相交实现碰撞检测
    public abstract Rectangle getRec();
}
