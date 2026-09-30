package Objects;

import Entities.Entity;
import Main.GamePanel;


public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setUp("objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]" + "/nCan be used to /nunlock doors.";
    }
}
