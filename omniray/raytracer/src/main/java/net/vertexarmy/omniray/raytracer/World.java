package net.vertexarmy.omniray.raytracer;

import com.hackoeur.jglm.Vec3;
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

    public void render(ImageBuilder builder) {
        builder.begin();

        Ray ray = new Ray(new Vec3(0, 0, 0), new Vec3(0, 0, 1));
        for (int r = -viewPlane.getWidth() / 2; r < viewPlane.getWidth() / 2; ++r) {
            for (int c = -viewPlane.getHeight() / 2; c < viewPlane.getHeight() / 2; ++c) {
                // create the ray
                ray.setOrigin(new Vec3(r, c, 0));
                ray.setDirection(Vec3.UNIT_Z);

                // trace the ray
                HitResult closestHitResult = null;
                for (GeometricObject object : objects) {
                    HitResult result = object.hit(ray);
                    if (result.isHit() && (closestHitResult == null || result.getRayHitLocation() < closestHitResult.getRayHitLocation())) {
                        closestHitResult = result;
                    }
                }

                // calculate the color
                int color;
                if (closestHitResult == null) {
                    color = 0x323232;
                } else {
                    color = ((int) closestHitResult.getColor().getX() << 16) + ((int) closestHitResult.getColor().getY() << 8) + (int) closestHitResult.getColor().getZ();
                }

                // render the result
                builder.setColor(r + viewPlane.getWidth() / 2, c + viewPlane.getHeight() / 2, color);
            }
        }

        builder.end();
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
