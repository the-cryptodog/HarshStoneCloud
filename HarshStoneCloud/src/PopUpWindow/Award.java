/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUpWindow;

import Controller.PathBuilder;
import gameObject.Card.Card;
import gameObject.Card.CardFactory;
import gameObject.GameObject;
import io.CommandSolver;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utils.Global;

/**
 *
 * @author User
 */
public class Award extends GameObject {

    protected CommandSolver.MouseCommandListener mousecommandlistener;
    private BufferedImage img;
    private CardFactory rareCardFactory;
    private ArrayList<Card> awards;
    private int count;
    private int[] rareCardIndex;

    public Award(int x, int y, int width, int height, String name) {
        super(x, y, width, height, name);
        img = irc.tryGetImage(PathBuilder.getIncidence("/" + name + ".png"));
        awards = new ArrayList<>();
        rareCardFactory = new CardFactory();
        rareCardFactory.readRareCardData();
        rareCardIndex = new int[3];
        count = 0;
        System.out.print("目前關卡="+Global.CURRENTSTAGE+")");
        
        switch (Global.CURRENTSTAGE) {
            case 1:
                for (int i = 0; i < 3; i++) {
                    rareCardIndex[count++] = i;
                    break;
                }
            case 2:
                for (int i = 3; i < 6; i++) {
                    rareCardIndex[count++] = i;
                    break;
                }
            case 3:
                for (int i = 6; i < 9; i++) {
                    rareCardIndex[count++] = i;
                    break;
                }
            case 4:
                for (int i = 9; i < 12; i++) {
                    System.out.println("count = "+count);
                    System.out.println("長度 = "+rareCardIndex.length);
                    rareCardIndex[0] = i;
                    break;
                }
                
        }
        for (int i = 0; i < 3; i++) {
            System.out.print(awards.size());
            awards.add(rareCardFactory.genCard(rareCardIndex[i],true));
            awards.get(i).setX(300+(i*600));
            awards.get(i).setY(540);

            awards.get(i).setY(540);
//                    awards.get(i).setX();
        }
        count=0;
       
    }

    public void setCommandListener(CommandSolver.MouseCommandListener mcl) {
        this.mousecommandlistener = mcl;
    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, 1920, 1080, null);
                for (int i = 0; i < 3; i++) {
            awards.get(i).paint(g);
        }
    }
}