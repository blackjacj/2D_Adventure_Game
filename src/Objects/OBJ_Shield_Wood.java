package Objects;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        name = "Wood Shield";
        type = type_shield;
        down1 = setUp("objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]" + "/nAn old wooden /nshield.";
    }
}
