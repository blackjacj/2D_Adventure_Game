package Objects;

import Entities.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Health extends Entity {

    GamePanel gp;

    public OBJ_Health(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "Health";
        value = 2;
        down1 = setUp("objects/heart_full", gp.tileSize, gp.tileSize);
        image = setUp("objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setUp("objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setUp("objects/heart_blank", gp.tileSize, gp.tileSize);
    }

    public void use(Entity entity) {

        gp.playSE(2);
        gp.ui.addMessage("Life + " + value);
        entity.life += value;
    }
}
