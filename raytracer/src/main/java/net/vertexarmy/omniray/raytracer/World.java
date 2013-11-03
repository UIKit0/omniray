package net.vertexarmy.omniray.raytracer;

import lombok.Getter;
import net.vertexarmy.omniray.raytracer.geometry.GeometricObject;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 12:17 PM
 */
public class World {
    @Getter
    private ViewPlane viewPlane;
    @Getter
    private List<GeometricObject> objects;

    public World() {
        viewPlane = new ViewPlane(800, 600, 1, 1.0);
        objects = new ArrayList<GeometricObject>();
    }

    public void render() {
    }

    public void addObject(GeometricObject object) {
        objects.add(object);
    }

    public void removeObject(GeometricObject object) {
    }

    private class ViewPlane {
        @Getter
        private final int width;
        @Getter
        private final int height;
        @Getter
        private final double pixelSize;
        @Getter
        private final double gamma;

        private ViewPlane(int width, int height, double pixelSize, double gamma) {
            this.width = width;
            this.height = height;
            this.pixelSize = pixelSize;
            this.gamma = gamma;
        }
    }
}
