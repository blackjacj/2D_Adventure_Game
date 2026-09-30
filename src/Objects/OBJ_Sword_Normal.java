package Objects;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        name = "Normal Sword";
        type = type_sword;
        down1 = setUp("objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]" + "/nAn old sword.";
    }
}
