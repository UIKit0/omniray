package org.vertexarmy.omniray.raytracer.material.texture;

/**
 * User: Alex
 * Date: 1/16/14
 */

/**
 * Interface used for holding textures.
 * <p/>
 * Textures may be loaded from images or generated.
 */
public interface Texture {

    /**
     * The width of the texture.
     *
     * @return the width of the texture
     */
    public int getWidth();

    /**
     * The height of the texture.
     *
     * @return the height of the texture
     */
    public int getHeight();

    /**
     * The color of the texture at coordinates x and y.
     * Results are undefined if the values are outside the valid range.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the value of the texture at coordinate (x, y)
     */
    public int getColor(int x, int y);
}
