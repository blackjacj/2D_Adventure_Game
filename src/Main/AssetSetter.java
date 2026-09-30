package Main;

import Entities.NPC_Oldman;
import Monster.MON_Greenslime;
import Objects.*;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        int i = 0;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.tileSize * 23;
        gp.obj[i].worldY = gp.tileSize * 26;
        i++;

        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.tileSize * 11;
        gp.obj[i].worldY = gp.tileSize * 10;
        i++;

        gp.obj[i] = new OBJ_Health(gp);
        gp.obj[i].worldX = gp.tileSize * 34;
        gp.obj[i].worldY = gp.tileSize * 8;
        i++;

        gp.obj[i] = new OBJ_ManaCrystal(gp);
        gp.obj[i].worldX = gp.tileSize * 23;
        gp.obj[i].worldY = gp.tileSize * 38;
        i++;

        gp.obj[i] = new OBJ_Beehive(gp);
        gp.obj[i].worldX = gp.tileSize * 23;
        gp.obj[i].worldY = gp.tileSize * 7;
        i++;

        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize * 33;
        gp.obj[i].worldY = gp.tileSize * 21;
        i++;

        gp.obj[i] = new OBJ_Shield_Blue(gp);
        gp.obj[i].worldX = gp.tileSize * 35;
        gp.obj[i].worldY = gp.tileSize * 21;
        i++;

        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.tileSize * 37;
        gp.obj[i].worldY = gp.tileSize * 21;
        i++;
    }

    public void setNpc() {

        int i = 0;

        gp.npc[i] = new NPC_Oldman(gp);
        gp.npc[i].worldX = gp.tileSize * 21;
        gp.npc[i].worldY = gp.tileSize * 21;

    }

    public void setMon() {

        int i = 0;

        gp.mon[i] = new MON_Greenslime(gp);
        gp.mon[i].worldX = gp.tileSize * 23;
        gp.mon[i].worldY = gp.tileSize * 36;
        i++;

        gp.mon[i] = new MON_Greenslime(gp);
        gp.mon[i].worldX = gp.tileSize * 34;
        gp.mon[i].worldY = gp.tileSize * 42;
        i++;

        gp.mon[i] = new MON_Greenslime(gp);
        gp.mon[i].worldX = gp.tileSize * 23;
        gp.mon[i].worldY = gp.tileSize * 37;
        i++;

        gp.mon[i] = new MON_Greenslime(gp);
        gp.mon[i].worldX = gp.tileSize * 9;
        gp.mon[i].worldY = gp.tileSize * 10;
        i++;

        gp.mon[i] = new MON_Greenslime(gp);
        gp.mon[i].worldX = gp.tileSize * 35;
        gp.mon[i].worldY = gp.tileSize * 17;
        i++;

        gp.mon[i] = new MON_Greenslime(gp);
        gp.mon[i].worldX = gp.tileSize * 36;
        gp.mon[i].worldY = gp.tileSize * 42;
        i++;

        gp.mon[i] = new MON_Greenslime(gp);
        gp.mon[i].worldX = gp.tileSize * 24;
        gp.mon[i].worldY = gp.tileSize * 37;
        i++;



//        gp.mon[0] = new MON_Greenslime(gp);
//        gp.mon[0].worldX = gp.tileSize * 11;
//        gp.mon[0].worldY = gp.tileSize * 10;
//
//        gp.mon[1] = new MON_Greenslime(gp);
//        gp.mon[1].worldX = gp.tileSize * 11;
//        gp.mon[1].worldY = gp.tileSize * 11;

    }
}
