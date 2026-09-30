package Objects;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        name = "Blue Shield";
        type = type_shield;
        down1 = setUp("objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = "[" + name + "]" + "/nA nice blue /nshield.";
    }
}
