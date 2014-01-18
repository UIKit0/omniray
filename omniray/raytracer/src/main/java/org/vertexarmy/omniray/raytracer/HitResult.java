package org.vertexarmy.omniray.raytracer;

import lombok.Getter;
import lombok.experimental.Builder;
import org.vertexarmy.omniray.jglm.Vec3;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 12:13 PM
 */
@Builder
public class HitResult {
    public static final HitResult NO_HIT = builder().hit(false).build();

    @Getter
    private final boolean hit;
    @Getter
    private final Vec3 localHitLocation;
    @Getter
    private final float rayHitLocation;
    @Getter
    private final Vec3 normal;
    @Getter
    private final int color;

    public HitResult(boolean hit, Vec3 localHitLocation, float rayHitLocation, Vec3 normal, int color) {
        this.hit = hit;
        this.localHitLocation = localHitLocation;
        this.rayHitLocation = rayHitLocation;
        this.normal = normal;
        this.color = color;
    }
}
