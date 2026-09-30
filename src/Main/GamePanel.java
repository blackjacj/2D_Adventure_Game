package Main;

import Entities.Entity;
import Entities.Player;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16 X 16 TILE
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 X 48 TILE
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 PIXELS
    public final int screenHeight = tileSize * maxScreenRow; // 576 PIXELS

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public EventHandler eHandler = new EventHandler(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity mon[] = new Entity[20];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int titleScreen = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterStatus = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame() {

        aSetter.setObject();
        aSetter.setNpc();
        aSetter.setMon();
        //playMusic(0);
        gameState = titleScreen;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000.0 / FPS; // 0.0.1666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public void update()    {

        if (gameState == playState) {
            // PLAYER
            player.update();
            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            for (int i = 0; i < mon.length; i++) {
                if (mon[i] != null) {
                    if (mon[i].alive && !mon[i].dying) {
                        mon[i].update();
                    }
                    if (!mon[i].alive) {
                        mon[i] = null;
                    }
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }
        }
        if (gameState == pauseState) {
            // NOTHING
        }


    }

    public void paintComponent(Graphics g)    {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN STATE
        if (gameState == titleScreen) {
            ui.draw(g2);
        }

        // OTHER
        else {
            // TILE
            tileM.draw(g2);

            // ADD ENTITIES TO THE LIST
            entityList.add(player);
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            for (int i = 0; i < mon.length; i++) {
                if (mon[i] != null) {
                    entityList.add(mon[i]);
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {

                    int result = Integer.compare(o1.worldY, o2.worldY);
                    return result;
                }
            });
            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            // EMPTY ENTITY LIST
            entityList.clear();

            // UI
            ui.draw(g2);
        }

        // DEBUG
        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passed, 10, 400);
            System.out.println("Draw time: " + passed);
        }

        g2.dispose();
    }
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
}
