package org.vertexarmy.omniray.raytracer;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.vertexarmy.omniray.raytracer.material.ColorToolkit;

/**
 * User: Alex
 * Date: 1/16/14
 */
public class TestColorToolkit {
    @Test
    public void testColorMultiplication() {
        int color = ColorToolkit.fromRGB(100, 20, 40);
        Assert.assertEquals(ColorToolkit.fromRGB(50, 10, 20), ColorToolkit.mult(color, 0.5f));
        Assert.assertEquals(ColorToolkit.fromRGB(200, 40, 80), ColorToolkit.mult(color, 2f));
        Assert.assertEquals(ColorToolkit.fromRGB(10, 2, 4), ColorToolkit.mult(color, 0.1f));
    }

    @Test
    public void testColorCreation() {
        Assert.assertEquals(0xFF323232, ColorToolkit.fromRGB(50, 50, 50));
        Assert.assertEquals(0xFF000000, ColorToolkit.fromRGB(0, 0, 0));
        Assert.assertEquals(0xFFFFFFFF, ColorToolkit.fromRGB(255, 255, 255));
        Assert.assertEquals(0xFFFFFFFF, ColorToolkit.fromRGB(2055, 2550, 1000));
        Assert.assertEquals(0xFFFF0000, ColorToolkit.fromRGB(255, 0, 0));
        Assert.assertEquals(0xFFFF0000, ColorToolkit.fromRGB(2005, -100, -1000));
        Assert.assertEquals(0xFF00FF00, ColorToolkit.fromRGB(0, 255, 0));
        Assert.assertEquals(0xFF0000FF, ColorToolkit.fromRGB(0, 0, 255));
        Assert.assertEquals(0xFF224268, ColorToolkit.fromRGB(34, 66, 104));
    }

    @Test
    public void testColorStringRepresentation() {
        Assert.assertEquals("Color[50,50,50]", ColorToolkit.toString(ColorToolkit.fromRGB(50, 50, 50)));
        Assert.assertEquals("Color[142,0,255]", ColorToolkit.toString(ColorToolkit.fromRGB(142, 0, 255)));
    }
}