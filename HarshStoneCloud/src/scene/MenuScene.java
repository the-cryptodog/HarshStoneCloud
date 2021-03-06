/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;


import Controller.SceneController;
import PopUpWindow.Incidence;
import gameObject.Button.Button;
import gameObject.Card.CardDeckRecord;
import gameObject.Hero.Hero;
import gameObject.NumberIcon;
import io.CommandSolver;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import static java.util.Arrays.stream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author User
 */
public class MenuScene extends Scene {

    private BufferedImage img;
    private ArrayList<Button> buttons;
    private SelectJobSceneState menuscenestate;
    private boolean startPressed;
    private Incidence incidence;
    private Clip clip;

    //開始遊戲(進入選角畫面)
    //結束遊戲(關閉視窗)
    public MenuScene(SceneController scenecontroller) {
        super(scenecontroller);

        Global.background = acrc.tryGetAudioClip("/resources/Audio/MENU.mp3");
        Global.background .setVolume(0.2d);      
        Global.background .play();

        startPressed = false;
        Global.CURRENTSTAGE = 1;
//        start = new StartButton(690, 300, 200, 100, "開始遊戲");
        buttons = new ArrayList<>();
        buttons.add(new Button(50, 50, 220, 50, "NEWGAME"));
        buttons.add(new Button(50, 110, 220, 50, "CONTINUE"));
        buttons.add(new Button(50, 170, 85, 50, "EXIT"));

        img = irc.tryGetImage("/resources/Background/MENU.png");
        mousecommandlistener = new CommandSolver.MouseCommandListener() {
            @Override
            public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
//                  if (state != null) {
//                    System.out.println(state);
//                }else{
//                    System.out.println("沒狀態");
//            }
                
                if (state == CommandSolver.MouseState.RELEASED) {

//                    startPressed = false;

                }

                if (state == CommandSolver.MouseState.CLICKED) {
                    
                    if (buttons.get(0).isCollision(e.getX(), e.getY())) {
//                        background.play();
                     
                        scenecontroller.changeScene(new SelectJobScene(scenecontroller));
                         
                    }
//                    if (buttons.get(2).isCollision(e.getX(), e.getY())) {
////                        background.play();
//                        scenecontroller.changeScene(new MainScene(scenecontroller,new MapScene(scenecontroller)));
//                         
//                    }
//                    if (buttons.get(1).isCollision(e.getX(), e.getY())) {
//
//                        incidence = new Incidence(384, 216, 1152, 648, "AWARD");
//                        incidence.setCommandListener(mousecommandlistener);
//                        if (incidence.getButton().isCollision(e.getX(), e.getY())) {
//                            incidence = null;
//                        }
//                    }
//上面兩行為測試用，按CONTINUE可以直接進入戰鬥

                    if(buttons.get(1).isCollision(e.getX(),e.getY())){
                        Global.hero = Hero.loadHeroRecord();
                        CardDeckRecord temp1 = new CardDeckRecord();
                        Global.hero.setHeroDeck(temp1.getCardDeck());
                        MapScene temp2 = new MapScene(scenecontroller);
                        temp2.loadRedCrossList();
                        temp2.loadCurrentRedCross();
                        scenecontroller.changeScene(temp2);
                        

                    }

                }

                if (state == CommandSolver.MouseState.PRESSED) {
                    System.out.println("按到座標" + e.getX());
                    if (buttons.get(0).isCollision(e.getX(), e.getY())) {
                        startPressed = true;
                        System.out.println(startPressed);
                    }
                }

                if (state == CommandSolver.MouseState.DRAGGED) {

                }
                if (state == CommandSolver.MouseState.ENTERED) {

                }
                if (state == CommandSolver.MouseState.EXITED) {

                }

                if (state == CommandSolver.MouseState.MOVED) {
                    for (int i = 0; i < buttons.size(); i++) {
                        if (buttons.get(i).isCollision(e.getX(), e.getY())) {
                            System.out.println("hover!!!!!!!");
                            buttons.get(i).hover(e.getX(), e.getY());
                        }
                    }
                }
            }
        };
    }


    

            

    public void sceneBegin() {

    }

    @Override
    public void sceneUpdate() {
        
    }

    @Override
    public void sceneEnd() {
       
    }

    @Override
    public void paint(Graphics g) {

        
        g.drawImage(img, 0, 0, 1920, 1050, null);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).paint(g);
        }
        if (incidence != null) {
            incidence.paint(g);
        }
        
        System.out.println( 20 );
        System.out.println( 20 + ((Global.NUMBER_X_OFFSET) * 1));
        System.out.println( 20 + ((Global.NUMBER_X_OFFSET + Global.NUMBER_DELTAX) * 1));
        System.out.println( 20 + ((2 * Global.NUMBER_X_OFFSET + Global.NUMBER_DELTAX) * 1));
        
        
        System.out.println( Global.NUMBER_X_OFFSET);
        System.out.println( Global.NUMBER_DELTAX);
        System.out.println("d" + (20 + (int)((Global.NUMBER_X_OFFSET + Global.NUMBER_DELTAX) * 1)));
        
        System.out.println("f" + (20 + (int)((2 * Global.NUMBER_X_OFFSET + Global.NUMBER_DELTAX) * 1)));
        
    }
}
