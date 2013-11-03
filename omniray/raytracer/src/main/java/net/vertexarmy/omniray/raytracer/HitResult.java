package net.vertexarmy.omniray.raytracer;

import com.hackoeur.jglm.Vec3;
import lombok.Getter;
import lombok.experimental.Builder;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 12:13 PM
 */
@Builder
public class HitResult {
    public static final HitResult NO_HIT = HitResult.builder().hit(false).build();
    @Getter
    private final boolean hit;
    @Getter
    private final Vec3 localHitLocation;
    @Getter
    private final float rayHitLocation;
    @Getter
    private final Vec3 normal;
    @Getter
    private final Vec3 color;
    @Getter
    private final World world;

    public HitResult(boolean hit, Vec3 localHitLocation, float rayHitLocation, Vec3 normal, Vec3 color, World world) {
        this.hit = hit;
        this.localHitLocation = localHitLocation;
        this.rayHitLocation = rayHitLocation;
        this.normal = normal;
        this.color = color;
        this.world = world;
    }
}
