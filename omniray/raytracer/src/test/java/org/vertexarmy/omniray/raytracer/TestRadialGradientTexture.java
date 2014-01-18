package org.vertexarmy.omniray.raytracer;

import org.testng.annotations.Test;
import org.vertexarmy.omniray.raytracer.material.ColorToolkit;
import org.vertexarmy.omniray.raytracer.material.texture.RadialGradientTexture;
import org.vertexarmy.omniray.raytracer.material.texture.TextureSampler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * User: Alex
 * Date: 1/16/14
 */
public class TestRadialGradientTexture {

    @Test
    public void testRadiantGradientTexture() {
        final int width = 600;
        final int height = 400;
        final RadialGradientTexture texture = new RadialGradientTexture(ColorToolkit.fromRGB(150, 150, 150), ColorToolkit.fromRGB(50, 50, 50));
        final TextureSampler sampler = new TextureSampler(texture, TextureSampler.FilterType.NEAREST);

        final BufferedImage internalBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                internalBuffer.setRGB(i, j, sampler.getColor((float) i / (float) width, (float) j / (float) height));
            }
        }

        JFrame outputFrame = new JFrame() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(internalBuffer, 0, 0, null);
            }
        };

        outputFrame.setTitle("TextureSampler Test");
        outputFrame.setSize(width, height);
        outputFrame.setVisible(true);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
        }
    }
}
