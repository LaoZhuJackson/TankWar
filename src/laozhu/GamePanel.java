package laozhu;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JFrame {
    //窗口长宽
    int width = 800;
    int height = 610;

    //指针图片
    Image select = Toolkit.getDefaultToolkit().getImage("images/selectTank.gif");
    //指针初始纵坐标（横坐标不需要变化）
    int y = 150;

    //定义双缓存图片
    Image offScreenImage = null;

    //游戏模式 0 游戏未开始，1 单人模式，2 双人模式
    int state = 0;
    //a用来暂存玩家的选择，此时玩家还未确定模式
    int a = 1;

    //PlayerOne
    PlayerOne playerOne = new PlayerOne("images/p1tankU.gif", 125, 510, this, "images/p1tankU.gif", "images/p1tankL.gif", "images/p1tankR.gif", "images/p1tankD.gif");

    //main
    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        gp.launch();
    }

    public void launch() {
        setTitle("坦克大战");
        setSize(width, height);
        setLocationRelativeTo(null);//屏幕居中
        setDefaultCloseOperation(3);//添加关闭事件
        setResizable(false);//不允许调整窗口大小
        setVisible(true);//使窗口可见
        this.addKeyListener(new GamePanel.KeyMonitor());//添加键盘监视器

        //重绘
        while (true) {
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

            //添加游戏元素
            playerOne.paintSelf(gImage);
        }
        /**将缓存区绘制好的图片（offScreenImage）绘制到容器的画布（g）中**/
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
                    y = 150;//同时改变选择指针纵坐标位置
                }
                case KeyEvent.VK_2-> {
                    a = 2;
                    y = 250;
                }
                case KeyEvent.VK_ENTER ->
                        //按下回车时给state赋值
                        state = a;
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
