package Objects;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Beehive extends Entity {

    public OBJ_Beehive(GamePanel gp) {
        super(gp);

        name = "Beehive";
        down1 = setUp("objects/beehive", gp.tileSize, gp.tileSize);
        description = "[" + name + "]" + "/nA beehive full/nof good honey.";
    }
}
