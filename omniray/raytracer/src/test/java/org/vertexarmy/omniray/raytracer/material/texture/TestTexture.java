package org.vertexarmy.omniray.raytracer.material.texture;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * User: Alex
 * Date: 1/19/14
 */
public class TestTexture {
    @Test
    public void testImageTexture() {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(new Color(0xff323232));
        g.fillRect(0, 0, 32, 32);

        ImageTexture imageTexture = new ImageTexture(image);

        Assert.assertEquals(32, imageTexture.getWidth());
        Assert.assertEquals(32, imageTexture.getHeight());

        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                Assert.assertEquals(0xff323232, imageTexture.getColor(i, j));
            }
        }
    }
}