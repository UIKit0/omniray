package org.vertexarmy.omniray.raytracer.material.texture;

/**
 * User: Alex
 * Date: 1/16/14
 */
public interface Texture {
    public int getWidth();

    public int getHeight();

    public int getColor(int x, int y);
}
