package org.vertexarmy.omniray.raytracer.material.texture;

import lombok.Getter;
import org.vertexarmy.omniray.raytracer.material.ColorToolkit;

/**
 * User: Alex
 * Date: 1/16/14
 */
public class TextureSampler {
    @Getter
    private final Texture texture;
    @Getter
    private final FilterType filterType;

    public TextureSampler(Texture texture, FilterType filterType) {
        this.texture = texture;
        this.filterType = filterType;
    }

    public int getColor(float u, float v) {
        switch (filterType) {
            case NEAREST:
                return sampleNearest(u, v);
            case LINEAR:
                return sampleLinear(u, v);
        }

        throw new RuntimeException("Unknown filter type.");
    }

    private int sampleNearest(float u, float v) {
        int nearestX = (int) (u * texture.getWidth());
        int nearestY = (int) (v * texture.getWidth());
        return texture.getColor(nearestX, nearestY);
    }

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

    public static enum FilterType {
        NEAREST,
        LINEAR
    }
}
