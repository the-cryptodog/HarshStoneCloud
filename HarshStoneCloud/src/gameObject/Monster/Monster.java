/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObject.Monster;

import Controller.PathBuilder;
import gameObject.GameObject;
import gameObject.Hero.Hero;
import gameObject.NumberIcon;
import gameObject.Skill.Skill;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.DelayCounter;
import values.ImagePath;

/**
 *
 * @author frank61003
 */
public class Monster extends GameObject implements Cloneable{

    protected int health;
    private static final int[] ACT = {0, 1, 2, 1};

    protected int direction;
    protected int act;
    protected int originalx;
    protected int originaly;
    protected int originalhealth;
    private int lasthealth;
    private boolean recordhealth;
    protected boolean moved;
    protected boolean useskill;
    protected MonsterHelper monsterhelper;
    protected DelayCounter delaycounter;
    protected int delay;
    protected MonsterState monsterstate;
    protected Hero hero;
    protected int defense;
    protected int attack;
    protected NumberIcon number1;
    protected NumberIcon number2;
    private int yPosition;
    private int skillIndex;
    private Skill selfSkill;
    private ArrayList<MonsterAbnormalState> monsterabnormalstates;
    private int weak;
    private int frozen;
    private int poison;
    private int actnumber;
    private boolean isboss;
    
    
    public Monster(int x, int y, int width, int height, String name, int health, int yPosition, int actnumber, int skillIndex, Boolean isboss) {
        super(x, y, width, height, name);
        this.health = health;
        originalhealth = health;
        direction = 1;
        originalx = x;
        originaly = y;
        defense = 0;
        attack = 0;
        weak = 0;
        frozen = 0;
        poison = 0;
        moved = false;
        useskill = false;
        this.isboss = isboss;
        this.actnumber = actnumber;
        if(isboss == false){
            image = irc.tryGetImage(PathBuilder.getMonster(ImagePath.MONSTER1));
        }else{
            image = irc.tryGetImage(PathBuilder.getMonster(ImagePath.EVIL));
        } 
        recordhealth = false;
        this.yPosition = yPosition;
        this.skillIndex = skillIndex;
        monsterabnormalstates = new ArrayList<MonsterAbnormalState>();
//        monsterabnormalstates.add(new Poison(0,0,30,30,"",poison));
        monsterhelper = new MonsterHelper(actnumber);
        delaycounter = new DelayCounter(10, new DelayCounter.Action() {

            @Override
            public void action() {
                int act = 0;
                act = ++act % 4;
            }
        });

        monsterstate = new MonsterState.DecideMove();
        number1 = new NumberIcon(x + width, y + 100, 50, 50, "", 0);
        number2 = new NumberIcon(x + width + 40, y + 100, 50, 50, "", 0);
    }

    //10/22
    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Monster(int x, int y, int width, int height, String name, int health) {
        super(x, y, width, height, name);
        this.health = health;
        this.act = 0;
        image = irc.tryGetImage(PathBuilder.getMonster(ImagePath.MONSTER1));

    }
    @Override
        public boolean isCollision(GameObject gameobject){
        if(gameobject.getX() + gameobject.getWidth()< this.x-30 || gameobject.getX() > this.x + width){
            return false;
        }
         if(gameobject.getY() + gameobject.getHeight() < this.y+30 || gameobject.getY() > this.y + height-30){
            return false;
        }
        return true;
    }

    public void recover() {
        health = lasthealth;
    }

    public boolean getRecordHealth() {
        return recordhealth;
    }

    public void setRecordHealth(boolean recordhealth) {
        this.recordhealth = recordhealth;
    }

    public void setSkill(Skill selfskill) {
        this.selfSkill = selfskill;
    }

    public int getskillIndex() {
        return skillIndex;
    }
    public Skill getSelfSkill (){
        return selfSkill;
    }

    public int getOriginalHealth() {
        return originalhealth;
    }

    public void setOriginalHealth(int originalhealth) {
        this.originalhealth = originalhealth;
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

    public int getAttack() {
        return attack;
    }

    public int getYposition() {
        return yPosition;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setPoison(int poison){
        this.poison = poison;
    }
    
    public int getPoison(){
        return poison;
    }
   
    public void setWeak(int weak){
        this.weak = weak;
    }
    
    public int getWeak(){
        return weak;
    }
    public void setFrozen(int frozen){
        this.frozen = frozen;
    }
    
    public int getFrozen(){
        return frozen;
    }
    
    public void setIsBoss(Boolean isboss){
        this.isboss = isboss;
    }
    
    public boolean getIsBoss(){
        return isboss;
    }
    
    public int getActNumber(){
        return actnumber;
    }
    
    public void setMonsterState(MonsterState monsterstate) {
        this.monsterstate = monsterstate;
    }

    public MonsterState getMonsterState() {
        return monsterstate;
    }
    
    
    public ArrayList<MonsterAbnormalState> getMonsterAbnormalStates() {
        return monsterabnormalstates;
    }
      
    public void changeDirection(int direction) {
        this.direction = direction;
    }

    public void update() {
        if (delaycounter.delayupdate()) {
            act = ++act % 4;
        }
        
//        if(poison != 0){
//            monsterabnormalstates.add(new Poison(0,0,30,30,"",poison));
//        }
        

    }
    
    
    public Monster clone() {
    
        Monster clone = new Monster(x, y, width, height, name, health, yPosition, actnumber, skillIndex, isboss);
        clone.attack = this.attack;
        clone.defense = this.defense;
        clone.hero = hero;
        clone.originalhealth = this.originalhealth;
        clone.weak = this.weak;
        clone.frozen = this.frozen;
        clone.poison = this.poison;
        
        
        
        if (defense > 0){
            clone.monsterstate = new MonsterState.EndMove();
        }
        if(attack > 0){
            clone.monsterstate = new MonsterState.MoveLeft();
        }
        for(int i = 0;i < this.monsterabnormalstates.size();i++){
            MonsterAbnormalState temp = this.monsterabnormalstates.get(i);
            if(temp instanceof Poison){
                clone.monsterabnormalstates.add(new Poison(0,0,32,32," ",temp.continueturn));
            }
            else if(temp instanceof Frozen){
                clone.monsterabnormalstates.add(new Frozen(0,0,32,32," ",temp.continueturn));
            }
            else if(temp instanceof Weak){
                clone.monsterabnormalstates.add(new Weak(0,0,32,32," ",temp.continueturn));
            }  
        }
    
        return clone;
    }
    public void move() {
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
        if (recordhealth == false) {
            lasthealth = health;
            recordhealth = false;
        }
        
        monsterstate.action(this, hero);
        if (attack > 0) {
            number1.setNumber(attack / 10);
            number2.setNumber(attack % 10);

        } else if (defense > 0) {
            number1.setNumber(defense / 10);
            number2.setNumber(defense % 10);

        }

    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean getMoved() {
        return moved;
    }

    public int getDirection() {
        return direction;
    }
    //怪物受到傷害更新護甲
    public void updateNumberIcon() {  
        if(attack == 0){
            number1.setNumber(defense / 10);
            number2.setNumber(defense % 10);
        }
        else{
            number1.setNumber(attack / 10);
            number2.setNumber(attack % 10);
        }
    }
    
    
    public String toString() {
        return "攻擊力" + attack + "防禦力" + defense;

    }

    public void paint(Graphics g) {
//        if (act == 5) {
//            g.drawImage(image, x, y, width, height, null);
//        } else {
            monsterhelper.paint(g, x, y, width, height, ACT[act], direction, health, originalhealth, attack, defense, monsterabnormalstates, poison);
            number1.setX(x + width);
            number2.setX(x + width + 40);
            updateNumberIcon();
            if(number1.getNumber() != 0 || number2.getNumber() != 0){
                number1.paint(g);
                number2.paint(g);
            }
            if (monsterstate instanceof MonsterState.Attack) {
                selfSkill.paint(g);//畫出攻擊技能
            }
//        }
    update();
    }
}
