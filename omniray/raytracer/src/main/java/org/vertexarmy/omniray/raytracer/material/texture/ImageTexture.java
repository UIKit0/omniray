package org.vertexarmy.omniray.raytracer.material.texture;

import java.awt.image.BufferedImage;

/**
 * User: Alex
 * Date: 1/16/14
 */
public class ImageTexture implements Texture {
    private final BufferedImage internalImage;

    public ImageTexture(BufferedImage image) {
        internalImage = image;
    }

    @Override
    public int getWidth() {
        return internalImage.getWidth(null);
    }

    @Override
    public int getHeight() {
        return internalImage.getHeight(null);
    }

    @Override
    public int getColor(int x, int y) {
        return internalImage.getRGB(x, y);
    }
}
