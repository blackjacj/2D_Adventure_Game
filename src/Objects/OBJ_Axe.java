package Objects;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp) {
        super(gp);

        name = "Woodcutter's Axe";
        type = type_axe;
        down1 = setUp("objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]" + "/nA bit rusty but /ncan still cut trees.";
    }
}
