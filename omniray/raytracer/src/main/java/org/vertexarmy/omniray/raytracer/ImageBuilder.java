package org.vertexarmy.omniray.raytracer;

/**
 * User: Alex
 * Date: 11/4/13
 */

/**
 * Interface used for constructing images.
 */
public interface ImageBuilder {
    /**
     * Called before the construction begins.
     */
    void begin();

    /**
     * Called for each pixel in the image.
     *
     * @param x     the column of the pixel
     * @param y     the row of the pixel
     * @param color the color of the pixel
     */
    void setColor(int x, int y, int color);

    /**
     * Called to indicate the building has ended.
     */
    void end();
}
