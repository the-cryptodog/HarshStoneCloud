/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObject.Hero;

import Controller.ImageResourceController;
import Controller.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author frank61003
 */
public class HeroHelper {

    private BufferedImage img;
    private int heroposition;

    public HeroHelper(int hero) {
        img = getHero(hero);
        heroposition = hero % 8;
    }

    private BufferedImage getHero(int hero) {
        ImageResourceController irc = ImageResourceController.getInstance();
        if (hero >= 0 || hero < 8) {
            return irc.tryGetImage(PathBuilder.getHero(ImagePath.ACTOR1));
        }
//        if(hero >= 8 || hero < 16){
//            return irc.tryGetImage(PathBuilder.getHero(ImagePath.MONSTER2));
//        }

        return null;

    }

    public void paint(Graphics g, int x, int y, int width, int height, int act, int direction) {
        if (img == null) {
            return;
        }
        int dx = 96 * (heroposition % 4);
        int dy = 128 * (heroposition / 4);

        g.drawImage(img, x, y, x + width, y + height, dx + act * Global.IMG_X_OFFSET, dy + direction * Global.IMG_Y_OFFSET, dx + 32 + act * Global.IMG_X_OFFSET, dy + 32 + direction * Global.IMG_Y_OFFSET, null);

    }
}
