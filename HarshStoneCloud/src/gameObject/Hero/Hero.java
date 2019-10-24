/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObject.Hero;

import Controller.PathBuilder;
import gameObject.Card;
import gameObject.CardDeck;
import gameObject.CardDeck.WarriorDeck;
import gameObject.GameObject;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import utils.DelayCounter;
import values.ImagePath;

/**
 *
 * @author frank61003
 */
public class Hero extends GameObject{

    protected int health;
    private static final int[] ACT = {0,1,2,1};
    protected int direction;
    protected int act;
    protected int originalx;
    protected int originaly;
    protected boolean moved;
    protected HeroHelper herohelper;
    protected DelayCounter delaycounter;
    protected int delay;
    protected Timer timer;
    protected CardDeck cards;
    
    
    public Hero(int x, int y, int width, int height, String name, int health,int act){
        super(x, y, width, height, name);
        this.health = health;
        direction = 2;
        originalx = x;
        originaly = y;
        moved = false;
        image = irc.tryGetImage(PathBuilder.getHero(ImagePath.ACTOR1));
        cards = new WarriorDeck();
        herohelper = new HeroHelper(act);
//        delaycounter = new DelayCounter(20, new DelayCounter.Action() {
//
//            @Override
//            public void action() {
//                int act = 0;
//                act = ++act % 4; 
//            }
//        });
        timer = new Timer(40, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               update();
            }
        });
        timer.start();
        
        
        
    }
    
    public Hero(int x, int y, int width, int height, String name, int health){
        super(x, y, width, height, name);
        this.health = health;
        this.act = 5;
        image = irc.tryGetImage(PathBuilder.getHero(ImagePath.ACTOR1));
        
    }
    
    public int gethealth(){
        return health;
    }
    
    public void sethealth(int health){
        this.health = health;
    }
    
    public void changeDirection(int direction){
        this.direction = direction;
    } 
    
    public void update(){
        act = ++act % 4;
        
    }
    public void move(){
        int tempx;
        if(!moved){
            if(direction == 1){    
                tempx = x - 30;
                if(tempx <= 180){
                    x = 180;
                    direction = 2;
                }
                else{
                    x = tempx;
                }
            }
            else{
                tempx = x + 30;
                if(tempx >= originalx){
                    x = originalx;
                    direction = 1;
                    moved = true;
                }
                else{
                    x = tempx;
                }
            }
    
        }   
        
    }
    
    public void setMoved(boolean moved){
        this.moved = moved;
    }
    
    public boolean getMoved(){
        return moved;
    }
    
    
    
    
    public void paint(Graphics g){
        
        herohelper.paint(g, x, y, width, height, ACT[act], direction);
        
    }
}
