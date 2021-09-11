package laozhu;

import java.awt.*;

public class Blast extends GameObject{
    //爆炸图集
    static Image[]imgs=new Image[8];
    int explodeCount=0;//选择图片

    static {
        for (int i=0;i<8;i++){
            imgs[i]=Toolkit.getDefaultToolkit().getImage("images/blast"+(i+1)+".gif");//存入爆炸图片到数组
        }
    }

    public Blast( int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
    }

    @Override
    public void paintSelf(Graphics g) {
        if(explodeCount<8){
            g.drawImage(imgs[explodeCount],x,y,null);
            explodeCount++;
        }
    }

    @Override
    public Rectangle getRec() {
        return null;
    }
}
