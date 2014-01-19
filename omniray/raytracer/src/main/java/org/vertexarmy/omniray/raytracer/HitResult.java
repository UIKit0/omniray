package org.vertexarmy.omniray.raytracer;

import lombok.Getter;
import lombok.experimental.Builder;
import org.vertexarmy.omniray.jglm.Vec3;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 12:13 PM
 */

/**
 * The HitResult class holds the result of a ray intersection.
 * The result contains information such as whether or not a hit occurred,
 * the exact point along the ray and inside the world of the intersection,
 * the material and surface normal of the object at the point of intersection.
 * <p/>
 * Instances of this class are immutable.
 */
@Builder
public class HitResult {
    public static final HitResult NO_HIT = builder().hit(false).build();

    /**
     * Whether or not there was a hit.
     */
    @Getter
    private final boolean hit;
    @Getter

    /**
     * The world coordinates of the hit.
     */
    private final Vec3 localHitLocation;

    /**
     * The point along the ray where the hit occurred.
     */
    @Getter
    private final float rayHitLocation;

    /**
     * The surface normal of the object at the hit location.
     */
    @Getter
    private final Vec3 normal;

    /**
     * The color of the object at the hit location.
     */
    @Getter
    private final int color;

    /**
     * Constructs an immutable hit result
     *
     * @param hit              indicates whether or not this is a valid hit
     * @param localHitLocation world coordinates of the hit location
     * @param rayHitLocation   ray coordinates of the hit location
     * @param normal           surface normal at the hit location
     * @param color            surface color at the hit location
     */
    public HitResult(boolean hit, Vec3 localHitLocation, float rayHitLocation, Vec3 normal, int color) {
        this.hit = hit;
        this.localHitLocation = localHitLocation;
        this.rayHitLocation = rayHitLocation;
        this.normal = normal;
        this.color = color;
    }
}
