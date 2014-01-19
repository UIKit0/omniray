package org.vertexarmy.omniray.raytracer.sampling;

import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.jglm.support.FastMath;

import java.util.List;

/**
 * User: Alex
 * Date: 1/17/14
 */

/**
 * Abstract class used for generating patterns of samples.
 */
public abstract class Sampler {
    final static int SAMPLE_PATTERN_COUNT = 83;
    private final List<Vec3> samples;
    private int sampleCount;

    private int sampleIterator;
    private int sampleIteratorBase;

    /**
     * Creates a sampler with a number of samples per pattern.
     *
     * @param sampleCount the number of samples in a pattern
     */
    public Sampler(int sampleCount) {
        this.sampleCount = sampleCount;
        samples = generateSamples(sampleCount, SAMPLE_PATTERN_COUNT);

        sampleIterator = 0;
        sampleIteratorBase = 0;
    }

    /**
     * Returns the number of samples in a pattern.
     *
     * @return the number of samples in a pattern
     */
    public int sampleCount() {
        return sampleCount;
    }

    /**
     * Returns the next sample in the current pattern.
     *
     * @return a sample offset in the range unit square
     */
    public Vec3 nextSample() {
        if (sampleIterator + 1 == samples.size()) {
            sampleIterator = 0;
            sampleIteratorBase = (int) (FastMath.random() * SAMPLE_PATTERN_COUNT) * sampleCount;
        }

        return samples.get((sampleIteratorBase + sampleIterator++) % samples.size());
    }

    /**
     * Used by the subclasses to generate patterns of samples.
     *
     * @param samplesPerPattern the number of samples in a set
     * @param patternCount      the number of sets in the sampler
     * @return a linear list with all the generated samples
     */
    abstract protected List<Vec3> generateSamples(int samplesPerPattern, int patternCount);
}
