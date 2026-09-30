package Entities;

import Main.GamePanel;
import Main.KeyHandler;
import Objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea = new Rectangle(12, 18, 24, 28);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

//        attackArea.width = 36;
//        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    public void setDefaultValues()  {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
//        worldX = gp.tileSize * 10;
//        worldY = gp.tileSize * 13;
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = 4;
        ammo = 10;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        beehives = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setItems() {

        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Beehive(gp));
    }

    public int getAttack() {

        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {

        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage()    {

        up1 = setUp("player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setUp("player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setUp("player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setUp("player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setUp("player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setUp("player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setUp("player/boy_right_2", gp.tileSize, gp.tileSize);

    }

    public void getPlayerAttackImage() {

        if (currentWeapon.type == type_sword) {
            attackUp1 = setUp("player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setUp("player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setUp("player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setUp("player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setUp("player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setUp("player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setUp("player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setUp("player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == type_axe) {
            attackUp1 = setUp("player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setUp("player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setUp("player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setUp("player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setUp("player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setUp("player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setUp("player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setUp("player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }

    }

    public void update()    {

        if (attacking) {
            attacking();
        }
        else if ((keyH.upPressed) || (keyH.downPressed) || (keyH.leftPressed) || (keyH.rightPressed) || (keyH.enterPressed)){

            if (keyH.upPressed) {
                direction = "up";
            }
            else if (keyH.downPressed)  {
                direction = "down";
            }
            else if (keyH.leftPressed)  {
                direction = "left";
            }
            else if (keyH.rightPressed) {
                direction = "right";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNpc(npcIndex);

            //CHECK MONSTER COLLISION
            int monIndex = gp.cChecker.checkEntity(this, gp.mon);
            contactMonster(monIndex);


            //CHECK EVENT
            gp.eHandler.checkEvent();

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn && !keyH.enterPressed) {

                switch (direction) {
                    case "up": worldY -= speed;
                        break;
                    case "down": worldY += speed;
                        break;
                    case "left": worldX -= speed;
                        break;
                    case "right": worldX += speed;
                        break;
                }
            }

            if (keyH.enterPressed && !gp.player.attackCanceled) {
                attacking = true;
                spriteCounter = 0;
            }
            gp.player.attackCanceled = false;

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }

        if (keyH.shootKeyPressed && !projectile.alive &&
                shotAvailableCounter == 30 && projectile.haveResource(this)) {

            // SET DEFAULT COORDINATES, DIRECTION, AND USER
            projectile.set(worldX, worldY, direction, true, this);

            // SUBTRACT RESOURCE
            projectile.useResource(this);
            // ADD PROJECTILE TO THE LIST
            gp.projectileList.add(projectile);

            shotAvailableCounter = 0;

            gp.playSE(10);
        }

        // THIS NEEDS TO BE OUTSIDE OF KEY IF STATEMENT
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

        if (life > maxLife) {
            life = maxLife;
        }

        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void attacking() {

        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;

            // SAVE THE CURRENT WORLDX, WORLDY, SOLIDAREA
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // ADJUST PLAYERS WORLDX/WORLDY FOR THE ATTACK AREA
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            // ATTACK AREA BECOME SOLID AREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // CHECK IF NEW SOLID AREA HIT MONSTER
            int monsterIndex = gp.cChecker.checkEntity(this, gp.mon);
            damageMonster(monsterIndex, attack);

            // AFTER CHECKING COLLISION, RESTORE THE ORIGINAL DATA
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {

            // PICKUP ONLY ITEMS
            if (gp.obj[i].type == type_pickupOnly) {

                gp.obj[i].use(this);
                gp.obj[i] = null;
            }

            // INVENTORY ITEMS
            else {
                String text;

                if (inventory.size() != maxInventorySize) {

                    inventory.add(gp.obj[i]);
                    gp.playSE(1);
                    text = "Got a " + gp.obj[i].name + "!";
                }
                else {
                    text = "You cannot carry any more items!";
                }
                gp.ui.addMessage(text);
                gp.obj[i] = null;
            }
        }
    }

    public void interactNpc(int i) {

        if (keyH.enterPressed) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && !gp.mon[i].dying) {
                gp.playSE(6);
                int damage = gp.mon[i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack) {

        if (i != 999) {

            if (!gp.mon[i].invincible) {
                gp.playSE(5);
                int damage = attack - gp.mon[i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.mon[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.mon[i].invincible = true;
                gp.mon[i].damageReaction();

                if (gp.mon[i].life <= 0) {
                    gp.mon[i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.mon[i].name + "!");
                    gp.ui.addMessage("XP +" + gp.mon[i].exp);
                    exp += gp.mon[i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp() {

        if (exp >= nextLevelExp) {
            level++;
            gp.playSE(8);
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            life += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " now!/n"
                    + "You feel stronger!";
        }
    }

    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot();
        if (itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_axe || selectedItem.type == type_sword) {

                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == type_shield) {

                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == type_consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    public void draw(Graphics2D g2)  {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                if (attacking) {
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                if (attacking) {
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                break;
        }
        // LOWERS OPACITY IF PLAYER IS HIT
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // RESET OPACITY AFTER HIT
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        // DEBUG
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible: " + invincibleCounter, 10, 400);

    }
}
