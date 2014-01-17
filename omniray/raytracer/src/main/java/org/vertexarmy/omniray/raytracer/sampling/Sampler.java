package org.vertexarmy.omniray.raytracer.sampling;

import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.jglm.support.FastMath;

import java.util.List;

/**
 * User: Alex
 * Date: 1/17/14
 */
public abstract class Sampler {
    private final static int SAMPLE_SET_COUNT = 83;
    private final List<Vec3> samples;
    private int sampleCount;

    private int sampleIterator;
    private int sampleIteratorBase;

    public Sampler(int sampleCount) {
        this.sampleCount = sampleCount;
        samples = generateSamples(sampleCount, SAMPLE_SET_COUNT);

        sampleIterator = 0;
        sampleIteratorBase = 0;
    }

    public int sampleCount() {
        return sampleCount;
    }

    public Vec3 nextSample() {
        if (sampleIterator + 1 == samples.size()) {
            sampleIterator = 0;
            sampleIteratorBase = (int) (FastMath.random() * SAMPLE_SET_COUNT) * sampleCount;
        }

        return samples.get((sampleIteratorBase + sampleIterator++) % samples.size());
    }

    abstract protected List<Vec3> generateSamples(int samplesPerSet, int setCount);
}
