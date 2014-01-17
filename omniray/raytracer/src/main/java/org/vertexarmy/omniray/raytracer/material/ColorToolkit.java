package org.vertexarmy.omniray.raytracer.material;

import org.vertexarmy.omniray.jglm.support.FastMath;

/**
 * User: Alex
 * Date: 1/16/14
 */
public final class ColorToolkit {
    static public int fromRGB(int red, int green, int blue) {
        red = saturate(red, 0, 255);
        green = saturate(green, 0, 255);
        blue = saturate(blue, 0, 255);
        return (0xff << 24) + ((red & 0xff) << 16) + ((green & 0xff) << 8) + (blue & 0xff);
    }

    static public int getRed(int color) {
        return (color >> 16) & 0xff;
    }

    static public int getGreen(int color) {
        return (color >> 8) & 0xff;
    }

    static public int getBlue(int color) {
        return color & 0xff;
    }

    public static int mult(int color, float v) {
        return fromRGB((int) (getRed(color) * v), (int) (getGreen(color) * v), (int) (getBlue(color) * v));
    }

    public static int saturate(int value, int min, int max) {
        return FastMath.min(FastMath.max(value, min), max);
    }

    public static String toString(int color) {
        return new StringBuilder()
                .append("Color[")
                .append(getRed(color))
                .append(",")
                .append(getGreen(color))
                .append(",")
                .append(getBlue(color))
                .append("]")
                .toString();
    }
}
