package org.vertexarmy.omniray.raytracer.material.texture;

import org.vertexarmy.omniray.jglm.support.FastMath;
import org.vertexarmy.omniray.raytracer.material.ColorToolkit;

/**
 * User: Alex
 * Date: 1/16/14
 */
public class RadialGradientTexture implements Texture {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;

    private final int startColor;
    private final int stopColor;

    public RadialGradientTexture(int startColor, int stopColor) {
        this.startColor = startColor;
        this.stopColor = stopColor;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

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
