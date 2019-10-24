/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObject.Monster;

import Controller.PathBuilder;
import gameObject.GameObject;
import gameObject.Hero.Hero;
import gameObject.Skill.Skill;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import utils.DelayCounter;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author frank61003
 */
public class Monster extends GameObject{
    protected int health;
    private static final int[] ACT = {0,1,2,1};
    protected int direction;
    protected int act;
    protected int originalx;
    protected int originaly;
    protected boolean moved;
    protected boolean useskill;
    protected MonsterHelper monsterhelper;
    protected DelayCounter delaycounter;
    protected int delay;
    protected Skill skill;
    protected MonsterState monsterstate;
    protected Hero hero;
    public Monster(int x, int y, int width, int height, String name, int health,int act){
        super(x, y, width, height, name);
        this.health = health;
        direction = 1;
        originalx = x;
        originaly = y;
        moved = false;
        useskill = false;
        image = irc.tryGetImage(PathBuilder.getMonster(ImagePath.MONSTER1));
        
        monsterhelper = new MonsterHelper(act);
        delaycounter = new DelayCounter(10, new DelayCounter.Action() {

            @Override
            public void action() {
                int act = 0;
                act = ++act % 4; 
            }
        });

        skill = new Skill(Global.HEROX - Global.SKILLDELTA,Global.HEROY - Global.SKILLDELTA,Global.SKILLWIDTH,Global.SKILLHEIGHT,"ss");
        monsterstate = new MonsterState.MoveLeft();
        
    }
    
    //10/22
    public void setHero(Hero hero){
        this.hero = hero;
    }
    
    
    public Monster(int x, int y, int width, int height, String name, int health){
        super(x, y, width, height, name);
        this.health = health;
        this.act = 0;
        image = irc.tryGetImage(PathBuilder.getMonster(ImagePath.MONSTER1));
        
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
        if(delaycounter.delayupdate()){
        
            act = ++act % 4;
    
        }
    }
    public void move(){
        int tempx;
//        if(!moved){
//            if(direction == 1){    
//                tempx = x - 30;
//                
//                if(skill.getSkillend()){
//                    useskill = false;
//                    direction = 2;
//                    skill.setSkillend(false);    
//                }                    
//                
//                
//                if(tempx <= Global.HEROX +Global.HEROWIDTH + 2 * Global.HEALTHX){
//                    x = Global.HEROX + Global.HEROWIDTH +2 * Global.HEALTHX;
//                    useskill = true;
//                }
//                else{
//                    x = tempx;
//                }
//            }
//            else{
//                tempx = x + 30;
//                if(tempx >= originalx){
//                    x = originalx;
//                    direction = 1;
//                    moved = true;
//                }
//                else{
//                    x = tempx;
//                }
//            }
//    
//        }   
        monsterstate.action(this, hero);
    }
    
    public void setMoved(boolean moved){
        this.moved = moved;
    }
    
    public boolean getMoved(){
        return moved;
    }
    
    public int getDirection(){
        return direction;
    }
    
    
    public Skill getSkill(){
        return skill;
    }
    
    
    
    public void paint(Graphics g){
        if(act == 5){
            g.drawImage(image, x, y, width, height, null);
        }
        else{
            monsterhelper.paint(g, x, y, width, height, ACT[act], direction, health);
            if(monsterstate instanceof MonsterState.Attack){
                skill.paint(g);
            }
           
            
        }
    
    }
}
    
    
    
    
    
    
    
    

