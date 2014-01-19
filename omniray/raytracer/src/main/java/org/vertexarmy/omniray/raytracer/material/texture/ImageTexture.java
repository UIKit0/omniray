package org.vertexarmy.omniray.raytracer.material.texture;

import java.awt.image.BufferedImage;

/**
 * User: Alex
 * Date: 1/16/14
 */

/**
 * The image texture holds a BufferedImage.
 */
public class ImageTexture implements Texture {
    private final BufferedImage internalImage;

    /**
     * Creates an ImageTexture from a buffered image
     *
     * @param image the source image
     */
    public ImageTexture(BufferedImage image) {
        internalImage = image;
    }

    /**
     * Returns the width of the texture
     *
     * @return the width of the texture
     */
    @Override
    public int getWidth() {
        return internalImage.getWidth(null);
    }

    /**
     * Returns the height of the texture
     *
     * @return the height of the texture
     */
    @Override
    public int getHeight() {
        return internalImage.getHeight(null);
    }

    /**
     * The color of the texture at coordinates x and y.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the value of the source image at coordinate (x, y)
     */
    @Override
    public int getColor(int x, int y) {
        return internalImage.getRGB(x, y);
    }
}
