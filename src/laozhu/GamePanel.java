package laozhu;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JFrame {
    //窗口长宽
    int width = 800;
    int height = 610;

    //指针图片
    Image select = Toolkit.getDefaultToolkit().getImage("images/selectTank.gif");
    //指针初始纵坐标（横坐标不需要变化）
    int y = 160;

    //定义双缓存图片
    Image offScreenImage = null;

    //游戏模式 0 游戏未开始，1 单人模式，2 双人模式
    int state = 0;
    //a用来暂存玩家的选择，此时玩家还未确定模式
    int a = 1;
    //重绘次数
    int count=0;
    //已生成敌人数量
    int enemyCount=0;

    //游戏元素列表,毕竟不可能只发送一个子弹，用列表储存多个子弹
    ArrayList<Bullet>bulletList=new ArrayList<Bullet>();
    ArrayList<enemyTank>enemyTanksList=new ArrayList<enemyTank>();
    ArrayList<Bullet>removeList=new ArrayList<>();//为消除子弹单独建立一个列表用于实现子弹消失
    ArrayList<Tank>playerList=new ArrayList<Tank>();//玩家列表
    ArrayList<Wall>wallList=new ArrayList<Wall>();//墙体列表

    //PlayerOne
    PlayerOne playerOne = new PlayerOne("images/p1tankU.gif", 125, 510, this, "images/p1tankU.gif", "images/p1tankL.gif", "images/p1tankR.gif", "images/p1tankD.gif");

    //main
    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        gp.launch();
    }
    //窗口启动方法
    public void launch() {
        setTitle("坦克大战");
        setSize(width, height);
        setLocationRelativeTo(null);//屏幕居中
        setDefaultCloseOperation(3);//添加关闭事件
        setResizable(false);//不允许调整窗口大小
        setVisible(true);//使窗口可见
        this.addKeyListener(new GamePanel.KeyMonitor());//添加键盘监视器
        //打印围墙
        for(int i=0;i<14;i++){
            wallList.add(new Wall("images/walls.gif",i*60,170,this));
        }
        wallList.add(new Wall("images/walls.gif",305,560,this));
        wallList.add(new Wall("images/walls.gif",305,500,this));
        wallList.add(new Wall("images/walls.gif",365,500,this));
        wallList.add(new Wall("images/walls.gif",425,500,this));
        wallList.add(new Wall("images/walls.gif",425,560,this));
        //重绘
        while (true) {
            //添加电脑坦克
            if(count %100==1&&enemyCount<10){//控制坦克生成的速度和数量
                Random random=new Random();
                int rnum=random.nextInt(800);//随机生成敌方坦克的横坐标
                enemyTanksList.add(new enemyTank("images/enemy1U.gif",rnum,110,this,"images/enemy1U.gif","images/enemy1L.gif","images/enemy1R.gif","images/enemy1D.gif"));
                enemyCount++;
            }
            repaint();//重绘会删除所有元素重新绘制，造成闪烁、缓慢，利用双缓存解决
            try {
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //paint()方法
    @Override
    public void paint(Graphics g) {
        //创建和容器一样大小的Image图片
        if (offScreenImage == null)
            offScreenImage = this.createImage(width, height);
        //获取该图片的画笔
        Graphics gImage = offScreenImage.getGraphics();
        gImage.setColor(Color.BLACK);//设置画笔颜色
        gImage.fillRect(0, 0, width, height);//实心矩形
        gImage.setColor(Color.WHITE);

        gImage.setFont(new Font("仿宋", Font.BOLD, 50));
        //state=0，游戏未开始
        if (state == 0) {
            gImage.drawString("选择游戏模式", 220, 100);
            gImage.drawString("单人模式", 220, 200);
            gImage.drawString("双人模式", 220, 300);
            //绘制指针
            gImage.drawImage(select, 160, y, null);
        }
        //state==0/1，游戏开始
        else if (state == 1 || state == 2) {
            gImage.drawString("游戏开始", 220, 100);
            if (state == 1) {
                gImage.drawString("单人模式", 220, 200);
            } else {
                gImage.drawString("双人模式", 220, 200);
            }

            //绘制游戏元素
            //绘制墙体
            for(Wall wall:wallList){
                wall.paintSelf(gImage);
            }
            //playerOne.paintSelf(gImage);该方法不适合双人模式使用
            for(Tank player:playerList){
                player.paintSelf(gImage);//利用循环来绘制玩家坦克（解决了上一个注释的问题）
            }
            //enemy
            //enemyTank enemytank=new enemyTank("images/enemy1U.gif",500,110,this,"images/enemy1U.gif","images/enemy1L.gif","images/enemy1R.gif","images/enemy1D.gif");
            //利用循环列表来绘制坦克
            for(enemyTank enemytank:enemyTanksList){
                enemytank.paintSelf(gImage);
            }
            bulletList.removeAll(removeList);//在下一次遍历子弹列表前遍历一边要删除的子弹列表实现删除子弹
            for(Bullet bullet:bulletList){
                bullet.paintSelf(gImage);//循环输出列表中的所有子弹
            }
            //重绘一次
            count++;
        }
        /*将缓存区绘制好的图片（offScreenImage）绘制到容器的画布（g）中*/
        g.drawImage(offScreenImage, 0, 0, null);
    }

    //键盘监视器
    class KeyMonitor extends KeyAdapter {
        //按下按键
        @Override
        public void keyPressed(KeyEvent e) {//注意keyPressed里面的大小写，写错会导致@Override报错
            //返回键值
            //System.out.println(e.getKeyChar());测试用
            int key = e.getKeyCode();//获取玩家从键盘键入的值
            switch (key) {//选择模式
                case KeyEvent.VK_1 -> {
                    a = 1;
                    y = 160;//同时改变选择指针纵坐标位置
                }
                case KeyEvent.VK_2-> {
                    a = 2;
                    y = 260;
                }
                case KeyEvent.VK_ENTER ->{
                    //按下回车时给state赋值
                    state = a;
                    playerList.add(playerOne);//将玩家1加入玩家列表
                    //playerTwo
                }
                default ->{
                    //调用p1的键盘事件
                    playerOne.keyPressed(e);
                    playerOne.move();
                }

            }
        }
        @Override
        public void keyReleased(KeyEvent e){
            //调用p1的键盘事件
            playerOne.keyReleased(e);
            playerOne.move();
        }
    }
}
