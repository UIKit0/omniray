package org.vertexarmy.omniray.raytracer.material.texture;

import org.vertexarmy.omniray.jglm.support.FastMath;
import org.vertexarmy.omniray.raytracer.material.ColorToolkit;

/**
 * User: Alex
 * Date: 1/16/14
 */

/**
 * A texture containing a radial gradient.
 */
public class RadialGradientTexture implements Texture {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;

    private final int startColor;
    private final int stopColor;

    /**
     * Constructs a radial gradient texture from the start and stop color.
     *
     * @param startColor the start color
     * @param stopColor  the stop color
     */
    public RadialGradientTexture(int startColor, int stopColor) {
        this.startColor = startColor;
        this.stopColor = stopColor;
    }

    /**
     * The width of the texture.
     *
     * @return the width of the texture
     */
    @Override
    public int getWidth() {
        return WIDTH;
    }

    /**
     * The height of the texture.
     *
     * @return the height of the texture
     */
    @Override
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * The color of the texture at coordinates x and y.
     * The radiant texture returns a linear interpolation of the start and stop color
     * from the center of the texture (start color) towards the edge of the texture (stop color).
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the value of the texture at coordinate (x, y)
     */
    @Override
    public int getColor(int x, int y) {
        float u = (float) x / WIDTH;
        float v = (float) y / HEIGHT;

        float gradientPosition = (float) FastMath.sqrt((u - 0.5f) * (u - 0.5f) + (v - 0.5f) * (v - 0.5f)) / 0.7071f;

        return ColorToolkit.fromRGB(
                FastMath.mix(ColorToolkit.getRed(startColor), ColorToolkit.getRed(stopColor), gradientPosition),
                FastMath.mix(ColorToolkit.getGreen(startColor), ColorToolkit.getGreen(stopColor), gradientPosition),
                FastMath.mix(ColorToolkit.getBlue(startColor), ColorToolkit.getBlue(stopColor), gradientPosition)
        );
    }
}
