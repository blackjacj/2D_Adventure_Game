package Objects;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;

        name = "Red Potion";
        type = type_consumable;
        value = 5;
        down1 = setUp("objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[" + name + "]" + "/nHeals you by " + value + "/npoints.";
    }

    public void use(Entity entity) {

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drank the " + name + "!/n" +
                    "Your life has been recovered by " + value + " points.";
        entity.life += value;
        gp.playSE(2);
    }
}
