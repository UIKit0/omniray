package org.vertexarmy.omniray.client.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class Canvas extends JPanel {
    private final int width;
    private final int height;
    private BufferedImage internalBuffer;

    public Canvas(int width, int height){
        this.width = width;
        this.height = height;
        internalBuffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
    }

    public void setColor(int x, int y, int color){
        internalBuffer.setRGB(x, y, color);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(internalBuffer, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }
}
