package laozhu;

import java.awt.*;

//敌方子弹类
public class EnemyBullet extends Bullet{
    public EnemyBullet(String img,int x,int y,GamePanel gamePanel,Direction direction) {
        super(img, x, y, gamePanel,direction);
    }

    public void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
        this.go();
    }
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
}
