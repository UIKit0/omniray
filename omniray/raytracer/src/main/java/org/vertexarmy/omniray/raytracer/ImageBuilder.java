package org.vertexarmy.omniray.raytracer;

/**
 * User: Alex
 * Date: 11/4/13
 */
public interface ImageBuilder {
    void begin();

    void setColor(int x, int y, int color);

    void end();
}
