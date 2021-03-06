/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObject.Hero;

import Controller.ImageResourceController;
import Controller.PathBuilder;
import gameObject.Card.Card;
import gameObject.Card.CardDeck;
import gameObject.Card.CardDeck.SocererDeck;
import gameObject.Card.CardDeck.WarriorDeck;
import gameObject.GameObject;
import gameObject.Hero.HeroState.*;
import gameObject.NumberIcon;
import gameObject.NumberIconMoveState.NumberMoveStop;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.media.AudioClip;
import utils.DelayCounter;
import utils.Global;
import values.ImagePath;


/**
 *
 * @author frank61003
 */
public class Hero extends GameObject implements Serializable{
    private static final long serialVersionUID = 44444;
    protected int health;
    private static final int[] ACT = {0, 1, 2, 1};
    protected int direction;
    protected int actor;
    protected int act;
    protected int originalx;
    protected int originaly;
    protected boolean moved;
    protected HeroHelper herohelper;
    protected DelayCounter delaycounter;
    protected int delay;
    protected CardDeck herodeck;
    private int defense;
    private int lasthealth;
    private boolean recordhealth;
    private HeroState heroState;
    private NumberIcon attackedanimation;
    private NumberIcon healthnumber;
    private NumberIcon defensenumber;
    private AudioClip steps;
    private BufferedImage defenseicon;

    public Hero(int x, int y, int width, int height, String name, int health, int actor) {

        super(x, y, width, height, name);
        this.health = health;
        direction = 2;
        defense = 0;
        originalx = x;
        originaly = y;
        this.actor = actor;
        moved = false;
        image = irc.tryGetImage(PathBuilder.getHero(ImagePath.ACTOR1));
//        herodeck = new WarriorDeck(x, y, width, height, name);
        steps = acrc.tryGetAudioClip(PathBuilder.getAudio("/steps.mp3"));
        if(name.equals("Actor1")){
        herodeck = new WarriorDeck(40, 700, Global.CARDDECKWIDTH, Global.CARDDECKHEIGHT, "牌組");
        }else{
        herodeck = new SocererDeck(40, 700, Global.CARDDECKWIDTH, Global.CARDDECKHEIGHT, "牌組");
        }
        
        herohelper = new HeroHelper(actor);
        attackedanimation = new NumberIcon(x + (int)(Math.random()*width), y + (int)(Math.random() * height),"攻擊動畫", 0, 0.4f);
        attackedanimation.setNumberIconMoveState(new NumberMoveStop());
        float healthrate = 0.2f;
        healthnumber = new NumberIcon(x + (int)((Global.HEROWIDTH - ((2*Global.NUMBER_X_OFFSET - Global.NUMBER_DELTAX)*healthrate))/2), y + Global.HEROHEIGHT - 2, "", health, 0.2f);
        defenseicon = ImageResourceController.getInstance().tryGetImage(PathBuilder.getIcon(ImagePath.DEFENSEICON));
        
        defensenumber = new NumberIcon(x - Global.ICON_X_OFFSET, y + Global.ICON_Y_OFFSET, "防禦數值",0, 0.4f);
        
        delaycounter = new DelayCounter(10, new DelayCounter.Action() {

            @Override
            public void action() {
                int act = 0;
                act = ++act % 4;
            }
        });
        heroState = new NoMove();
//        Global.hero = this;
    }

    public Hero(int x, int y, int width, int height, String name, int health) {
        super(x, y, width, height, name);
        this.health = health;
        this.act = 5;
        image = irc.tryGetImage(PathBuilder.getHero(ImagePath.ACTOR1));
        //need to be changed
        herodeck = new WarriorDeck(400, 700, Global.CARDDECKWIDTH, Global.CARDDECKHEIGHT, "牌組");
    }

    public void recover() {
        health = lasthealth;
    }

    public boolean getRecordHealth() {
        return recordhealth;
    }

    public void setState(HeroState heroState) {
        this.heroState = heroState;
    }

    public HeroState getState() {
        return heroState;
    }

    public void setRecordHealth(boolean recordhealth) {
        this.recordhealth = recordhealth;
    }

    public CardDeck getHeroDeck() {
        return herodeck;
    }

    public void setHeroDeck(CardDeck herodeck) {
        this.herodeck = herodeck;
    }
    
    public void setDirection(int direction){
        this.direction = direction;
    }
    
    
    public void saveCardDeckRecord() {
        herodeck.createCardRecord();
    }
    
    public void saveHeroRecord(){
        
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("HeroRecord.txt"));
            bw.write("" + health);
            bw.newLine();
            bw.write("" + actor);
            bw.newLine();
            bw.write("" + Global.CURRENTSTAGE);
            bw.flush();
            bw.close();
        }   
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public static Hero loadHeroRecord(){
        ArrayList<String> str = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("HeroRecord.txt"));
            str.add(br.readLine());
           
         while(br.ready()){
                str.add(br.readLine());
            }
            br.close();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(JavaApplication40Filemanage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(JavaApplication40Filemanage.class.getName()).log(Level.SEVERE, null, ex);
        }
        Hero temp = new Hero(Global.JOB1X, Global.JOBY, 128, 128, "Actor1", Integer.valueOf(str.get(0)), Integer.valueOf(str.get(1)));
        Global.CURRENTSTAGE = Integer.valueOf(str.get(2));
        return temp;
        
    }
    
    
    public int gethealth() {
        return health;
    }

    public void sethealth(int health) {
        this.health = health;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void changeDirection(int direction) {
        this.direction = direction;
    }

    public void setAttackedAnimation(NumberIcon attackedanimation) {
        this.attackedanimation = attackedanimation;
    }
    
    public NumberIcon getAttackedAnimation() {
        return attackedanimation;
    }
    
    public void updateNumberIcon() {  
        defensenumber.setX(x - Global.ICON_X_OFFSET);
        defensenumber.setY(y + Global.ICON_X_OFFSET);
        defensenumber.setNumber(defense);
        
    }


    public void update() {
        if (delaycounter.delayupdate()) {
            act = ++act % 4;
//            heroState.action(this);
//            System.out.println("英雄往右移");
        }
    }
    
    public void move() {

        if (delaycounter.delayupdate()) {
            heroState.action(this);
        }
        attackedanimation.move();
        //應該有其他更好的判斷條件attackedanimation.getNumberIconMoveState() instanceof NumberMoveStop
        if(attackedanimation.getNumberIconMoveState() instanceof NumberMoveStop){
            int temp1 = x + (int)(Math.random()*width);
            int temp2 = y + (int)(Math.random()*height);
            attackedanimation.setX(temp1);
            attackedanimation.setOrginalx(temp1);
            attackedanimation.setY(temp2);
            attackedanimation.setOrginaly(temp2);
        }
        healthnumber.setNumber(health);
        healthnumber.setX(x + (int)((Global.HEROWIDTH - ((2*Global.NUMBER_X_OFFSET - Global.NUMBER_DELTAX)*healthnumber.getRate()))/2));
        healthnumber.setY(Global.HEROY+Global.HEROHEIGHT-2);
        
        
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean getMoved() {
        return moved;
    }

    public void paint(Graphics g) {
        herohelper.paint(g, x, y, width, height, ACT[act], direction, health);
        if(defense > 0){
            g.drawImage(defenseicon, x - Global.ICON_X_OFFSET, y, Global.ICON_X_OFFSET, Global.ICON_Y_OFFSET, null);
            updateNumberIcon();
            defensenumber.paint(g);
        }
        attackedanimation.paint(g);
        if(health != 0){
            healthnumber.paint(g);
        }
        update();
    }
}
