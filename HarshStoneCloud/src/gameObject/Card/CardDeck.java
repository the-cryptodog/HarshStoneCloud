/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObject.Card;

import Controller.ImageResourceController;
import Controller.PathBuilder;
import gameObject.Card.Card;
import gameObject.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import values.ImagePath;

/**
 *
 * @author frank61003
 */
public class CardDeck extends GameObject{
    protected ArrayList<Card> cards;
    protected CardFactory cardfactory;
    protected BufferedImage image;
    
    
    
    public CardDeck(int x, int y, int width, int height, String name,ArrayList<Card> cards){
        super(x,y,width,height,name);
        this.cards = cards;
        cardfactory = new CardFactory();
        image = ImageResourceController.getInstance().tryGetImage(PathBuilder.getImage(ImagePath.CARDBECK));
    }
    
    public CardDeck(int x, int y, int width, int height, String name){
        super(x,y,width,height,name);
        cards = new ArrayList<Card>();
        cardfactory = new CardFactory();
        image = ImageResourceController.getInstance().tryGetImage(PathBuilder.getImage(ImagePath.CARDBECK));
    }
    
    
    
    
    
    public static class WarriorDeck extends CardDeck{
        public WarriorDeck(int x, int y, int width, int height, String name){
            super(x, y, width, height, name);
            for(int i = 0; i < 5;i++){
                cards.add(cardfactory.genCard(0));            
            }
            for(int i = 0; i < 4;i++){
                cards.add(cardfactory.genCard(2));            
            } 
            cards.add(cardfactory.genCard(4));
        }  
    }
    
    
    
    
    public void shuffle(){
        for(int i = 0; i < cards.size(); i++){
            int r = (int)(Math.random()*cards.size() - i) + i;
            swap(i,r);
        } 
    }
    
    
    public void swap(int i1, int i2){
        Card temp = cards.get(i1);
        cards.set(i1, cards.get(i2));
        cards.set(i2, temp);
    }
    
    public ArrayList<Card> getCards(){
        return cards;
    }
    
    public String toString(){
        if(cards.size()==0){
            return "牌堆沒牌";
        }
        String str = name+ "\n";
        for(int i = 0; i < cards.size();i++){
            str += i+ "." +cards.get(i).getName() + " ";
        }
        return str;
    }


//應該要有move
    
    public void paint(Graphics g){
        
        g.drawImage(image, x, y, width,height,null);
        
    }
    
    
    
}