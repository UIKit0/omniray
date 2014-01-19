package org.vertexarmy.omniray.raytracer.sampling;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.vertexarmy.omniray.jglm.Vec3;

/**
 * User: Alex
 * Date: 1/19/14
 */
public class SamplingTests {
    @Test
    public void testNullSampler() {
        Sampler sampler = new NullSampler();
        Vec3 origin = new Vec3(0, 0, 0);

        Assert.assertEquals(1, sampler.sampleCount());
        for (int i = 0; i < Sampler.SAMPLE_PATTERN_COUNT; ++i) {
            Assert.assertEquals(origin, sampler.nextSample());
        }
    }
}
