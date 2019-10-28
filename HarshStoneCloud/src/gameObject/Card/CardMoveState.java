/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObject.Card;

import utils.Global;

/**
 *
 * @author frank61003
 */
public interface CardMoveState {
    public void move(Card card);
    
    
    public class Movable implements CardMoveState{


        @Override
        public void move(Card card) {
            
        }
    }
    
    
    
    public class MoveBack implements CardMoveState{

        @Override
        public void move(Card card) {
            if(card.getLeft()){    
                int tempx = card.getX() + 30;
                
                if(tempx <= card.getOrginalX()){
                    card.setX(tempx);
                }
                else{
                    card.setX(card.getOrginalX());
                }
            }
            else{
                int tempx = card.getX() - 30;
                
                if(tempx >= card.getOrginalX()){
                    card.setX(tempx);
                }
                else{
                    card.setX(card.getOrginalX());
                }
            }
            if(card.getUP()){
                int tempy = card.getY() + 30;
                
                if(tempy <= card.getOrginalY()){
                    card.setY(tempy);
                }
                else{
                    card.setY(card.getOrginalY());
                }
            }
            else{
                int tempy = card.getY() - 30;
                
                if(tempy >= card.getOrginalY()){
                    card.setY(tempy);
                }
                else{
                    card.setY(card.getOrginalY());
                }
            
            }    
        }
    }
    
    
    public class MoveToDiscard implements CardMoveState{


        @Override
        public void move(Card card) {

            int x = card.getX();
            int y = card.getY();
            int tempx = card.getX() + 30;
            int tempy = card.getY() + 30;
            if(x <= 1300 || y < 700){    
 
                if(tempx <= 1300){
                    card.setX(tempx);
                }
                else{
                    card.setX(1300);
                }
                if(tempy < 700){                    
                    card.setY(tempy); 
                }              
                else{
                    card.setY(700);
                }
            }
            else{
                card.cardmovestate = new MoveStop();
            }
        
        
        }
    }
    
    public class MoveToHandDeck implements CardMoveState{


        @Override
        public void move(Card card) {
            int x = card.getX();
            int tempx = card.getX() + 30;
            int limit = 300 + (Global.CARDWIDTH + 50) * (card.getHandDeckPoisition() - 1);
            
            
            if(x <= limit){    
 
                if(tempx <= limit){
                    card.setX(tempx);
                }
                else{
                    card.setX(limit);
                }  
            }
        }
    }
    
    
    public class EndTurnMove implements CardMoveState{


        @Override
        public void move(Card card) {
            int tempx = card.getX() + 30;
            if(tempx <= 1300){
                card.setX(tempx);
            }
            else{
                card.setX(1300);
            }
            
        }
    }
    
    public class MoveStop implements CardMoveState{

        @Override
        public void move(Card card) {
            System.out.print(card.getX());
            System.out.print(" ++++++++++++++++ ");
            System.out.print(card.getY());
        }
    }
   
}
