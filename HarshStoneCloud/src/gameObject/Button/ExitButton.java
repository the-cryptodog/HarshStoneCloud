/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObject.Button;

import Controller.PathBuilder;
import gameObject.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import values.ImagePath;

/**
 *
 * @author User
 */
public class ExitButton extends Button{
     protected BufferedImage image;

    public  ExitButton(int x, int y, int width, int height, String name) {
        super(x, y, width, height, name);
        this.image = irc.getInstance().tryGetImage(PathBuilder.getButton(ImagePath.EXIT));
    }

    public void paint(Graphics g) {
        g.drawImage(image, x, y, width, height, null);

    }
}
