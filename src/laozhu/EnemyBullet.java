package laozhu;

import java.awt.*;
import java.util.ArrayList;

//敌方子弹类
public class EnemyBullet extends Bullet{
    public EnemyBullet(String img,int x,int y,GamePanel gamePanel,Direction direction) {
        super(img, x, y, gamePanel,direction);
    }

    public void hitPlayer(){
        ArrayList<Tank> players=this.gamePanel.playerList;//读取玩家列表
        for(Tank player:players){
            if(this.getRec().intersects(player.getRec())){//如果敌方子弹矩形与玩家坦克矩形重叠
                this.gamePanel.playerList.remove(player);//将被击中的坦克从playerList中remove
                this.gamePanel.removeList.add(this);//敌方子弹加入消失列表removeList
                break;
            }
        }
    }

    public void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
        this.go();
        this.hitPlayer();
    }
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
}
