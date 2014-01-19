package org.vertexarmy.omniray.raytracer.material.texture;

import lombok.Getter;
import org.vertexarmy.omniray.raytracer.material.ColorToolkit;

/**
 * User: Alex
 * Date: 1/16/14
 */

/**
 * A texture sampler has the responsibility of sampling textures.
 * First, it maps the texture size inside the unit square. Thus,
 * all access to the texture data is done regardless of the texture size.
 * <p/>
 * Second, it may choose to sample multiple points inside the texture to
 * compensate for minification or magnification.
 * <p/>
 * Third, it chooses what happens when the coordinates are outside the
 * unit square.
 */
public class TextureSampler {
    @Getter
    private final Texture texture;
    @Getter
    private final FilterType filterType;

    /**
     * Creates a sampler for a texture by specifying a sampling type
     *
     * @param texture    the texture to be sampled
     * @param filterType the sampling type
     */
    public TextureSampler(Texture texture, FilterType filterType) {
        this.texture = texture;
        this.filterType = filterType;
    }

    /**
     * Returns a sampled color at coordinates (u, v).
     *
     * @param u the U coordinate of the sample
     * @param v the V coordinate of the sample
     * @return the color of the sample at coordinates (u, v)
     */
    public int getColor(float u, float v) {
        switch (filterType) {
            case NEAREST:
                return sampleNearest(u, v);
            case LINEAR:
                return sampleLinear(u, v);
        }

        throw new RuntimeException("Unknown filter type.");
    }

    /**
     * Samples the texture using the NEAREST algorithm.
     *
     * @param u the U coordinate of the sample
     * @param v the V coordinate of the sample
     * @return the color of the sample at coordinates (u, v)
     */
    private int sampleNearest(float u, float v) {
        int nearestX = (int) (u * texture.getWidth());
        int nearestY = (int) (v * texture.getWidth());
        return texture.getColor(nearestX, nearestY);
    }

    /**
     * Samples the texture using the LINEAR algorithm.
     *
     * @param u the U coordinate of the sample
     * @param v the V coordinate of the sample
     * @return the color of the sample at coordinates (u, v)
     */
    private int sampleLinear(float u, float v) {
        int nearestX = (int) (u * texture.getWidth());
        int nearestY = (int) (v * texture.getWidth());

        int colors[] = new int[]{
                ColorToolkit.mult(texture.getColor(nearestX, nearestY), 4f / 16f),
                ColorToolkit.mult(texture.getColor(nearestX - 1, nearestY), 2f / 16f),
                ColorToolkit.mult(texture.getColor(nearestX + 1, nearestY), 2f / 16f),
                ColorToolkit.mult(texture.getColor(nearestX, nearestY - 1), 2f / 16f),
                ColorToolkit.mult(texture.getColor(nearestX, nearestY + 1), 2f / 16f),
                ColorToolkit.mult(texture.getColor(nearestX - 1, nearestY - 1), 1f / 16f),
                ColorToolkit.mult(texture.getColor(nearestX - 1, nearestY + 1), 1f / 16f),
                ColorToolkit.mult(texture.getColor(nearestX + 1, nearestY - 1), 1f / 16f),
                ColorToolkit.mult(texture.getColor(nearestX + 1, nearestY + 1), 1f / 16f)};

        int red = 0, green = 0, blue = 0;
        for (int color : colors) {
            red += ColorToolkit.getRed(color);
            green += ColorToolkit.getRed(color);
            blue += ColorToolkit.getRed(color);
        }

        return ColorToolkit.fromRGB(red, green, blue);
    }

    /**
     * This enum represents the filtering algorithms that can be used
     * by the sampler
     */
    public static enum FilterType {
        NEAREST,    /* Nearest neighbour algorithm */
        LINEAR      /* Linear interpolation algorithm */
    }
}
