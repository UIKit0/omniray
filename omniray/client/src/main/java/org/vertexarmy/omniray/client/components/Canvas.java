package org.vertexarmy.omniray.client.components;

import lombok.Getter;
import net.vertexarmy.omniray.raytracer.ImageBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class Canvas extends JPanel {
    private final int BATCH_SIZE = 64;

    private final int width;
    private final int height;
    private int operationCount;
    private BufferedImage internalBuffer;
    @Getter
    private net.vertexarmy.omniray.raytracer.ImageBuilder imageBuilder;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        operationCount = 0;
        internalBuffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

        imageBuilder = new ImageBuilder() {
            @Override
            public void begin() {
            }

            @Override
            public void setColor(int x, int y, int color) {
                Canvas.this.setColor(x, y, color);
            }

            @Override
            public void end() {
                repaint();
            }
        };
    }

    public void setColor(int x, int y, int color) {
        internalBuffer.setRGB(x, y, color);
        //updateOperations();
    }

    private void updateOperations() {
        operationCount += 1;
        if (operationCount > BATCH_SIZE) {
            operationCount = 0;
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(internalBuffer, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
