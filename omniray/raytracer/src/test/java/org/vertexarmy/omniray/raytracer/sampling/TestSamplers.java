package org.vertexarmy.omniray.raytracer.sampling;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.vertexarmy.omniray.jglm.Vec3;

/**
 * User: Alex
 * Date: 1/19/14
 */
public class TestSamplers {
    @Test
    public void testNullSampler() {
        Sampler sampler = new NullSampler();
        Vec3 origin = new Vec3(0, 0, 0);

        Assert.assertEquals(1, sampler.sampleCount());
        for (int i = 0; i < Sampler.SAMPLE_PATTERN_COUNT; ++i) {
            Assert.assertEquals(origin, sampler.nextSample());
        }
    }

    @Test
    public void testRandomSampler() {
        Sampler sampler = new RandomSampler(8);

        Assert.assertEquals(8, sampler.sampleCount());
        for (int i = 0; i < Sampler.SAMPLE_PATTERN_COUNT; ++i) {
            Vec3 sampleOffset = sampler.nextSample();
            Assert.assertTrue(sampleOffset.getX() >= 0);
            Assert.assertTrue(sampleOffset.getX() <= 1);
            Assert.assertTrue(sampleOffset.getY() >= 0);
            Assert.assertTrue(sampleOffset.getY() <= 1);
            Assert.assertTrue(sampleOffset.getZ() == 0);
        }
    }
}
