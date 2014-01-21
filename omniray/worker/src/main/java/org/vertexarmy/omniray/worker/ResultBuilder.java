package org.vertexarmy.omniray.worker;

import org.vertexarmy.omniray.raytracer.Datastructures;
import org.vertexarmy.omniray.raytracer.ImageBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Alex
 * Date: 1/21/14
 */
public class ResultBuilder implements ImageBuilder {

    Datastructures.ColorBuffer.Builder colorBufferBuilder;
    private final List<Integer> internalBuffer;
    private int width;
    private int height;

    public ResultBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        colorBufferBuilder = Datastructures.ColorBuffer.newBuilder();
        internalBuffer = new ArrayList<Integer>(width * height);
    }

    @Override
    public void begin() {
    }

    @Override
    public void setColor(int x, int y, int color) {
        internalBuffer.add(x * width + y, color);
    }

    @Override
    public void end() {
    }

    public Datastructures.ColorBuffer getColorBufferResult(Datastructures.Rect size) {
        return colorBufferBuilder
                .addAllValue(internalBuffer)
                .setSize(size)
                .build();
    }
}
