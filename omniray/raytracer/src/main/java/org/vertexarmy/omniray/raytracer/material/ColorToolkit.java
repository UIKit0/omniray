package org.vertexarmy.omniray.raytracer.material;

import org.vertexarmy.omniray.jglm.support.FastMath;

/**
 * User: Alex
 * Date: 1/16/14
 */

/**
 * A toolkit class used for creating and manipulating ARGB colors represented by 32bit integers with 4 channels.
 * <p/>
 * The reasons we avoid having a Color class are:
 * 1. creating instances of a class is time consuming, and we will need a lot of color instances.
 * 2. integers are very easy to serialize and move around.
 */
public final class ColorToolkit {
    /**
     * Creates a new color from the red, green and blue channels.
     *
     * @param red   the red value of the color
     * @param green the green value of the color
     * @param blue  the blue value of the color
     * @return a 32bit integer containing the red, green and blue values
     */
    static public int fromRGB(int red, int green, int blue) {
        red = saturate(red, 0, 255);
        green = saturate(green, 0, 255);
        blue = saturate(blue, 0, 255);
        return (0xff << 24) + ((red & 0xff) << 16) + ((green & 0xff) << 8) + (blue & 0xff);
    }

    /**
     * Returns the value of the red channel.
     *
     * @param color the color to extract the channel from
     * @return the red value of the color
     */
    static public int getRed(int color) {
        return (color >> 16) & 0xff;
    }

    /**
     * Returns the value of the green channel.
     *
     * @param color the color to extract the channel from
     * @return the green value of the color
     */
    static public int getGreen(int color) {
        return (color >> 8) & 0xff;
    }

    /**
     * Returns the value of the blue channel.
     *
     * @param color the color to extract the channel from
     * @return the blue value of the color
     */
    static public int getBlue(int color) {
        return color & 0xff;
    }

    /**
     * Multiplies a color by a value. Channels are multiplied individually.
     *
     * @param color the color to multiply
     * @param v     the value with which to multiply the color
     * @return the multiplied color
     */
    public static int mult(int color, float v) {
        return fromRGB((int) (getRed(color) * v), (int) (getGreen(color) * v), (int) (getBlue(color) * v));
    }

    /**
     * Saturates a value between a minimum and maximum. The saturated value is clamped between min and max.
     *
     * @param value the value to saturate
     * @param min   the minimum value
     * @param max   the maximum value
     * @return the saturate value
     */
    public static int saturate(int value, int min, int max) {
        return FastMath.min(FastMath.max(value, min), max);
    }

    /**
     * Returns a string representation of the color.
     *
     * @param color the color to represent
     * @return a string representation of the color
     */
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
