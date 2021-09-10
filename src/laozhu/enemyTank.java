package laozhu;

import java.awt.*;

//敌方坦克类
public class enemyTank extends Tank{
    public enemyTank(String img,int x,int y,GamePanel gamePanel, String upImg, String leftImg, String rightImg,String downImg){
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
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
