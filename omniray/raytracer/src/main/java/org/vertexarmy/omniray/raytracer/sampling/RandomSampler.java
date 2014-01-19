package org.vertexarmy.omniray.raytracer.sampling;

import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.jglm.support.FastMath;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Alex
 * Date: 1/18/14
 */

/**
 * A sampler that generates n*n random samples in the unit square.
 */
public class RandomSampler extends Sampler {

    /**
     * Constructs a random sampler
     *
     * @param sampleCount the number of samples in a pattern
     */
    public RandomSampler(int sampleCount) {
        super(sampleCount);
    }

    /**
     * Generates random samples.
     *
     * @param samplesPerPattern the number of samples in a set
     * @param patternCount      the number of sets in the sampler
     * @return a list of random samples
     */
    @Override
    protected List<Vec3> generateSamples(int samplesPerPattern, int patternCount) {
        List<Vec3> samples = new ArrayList<Vec3>();

        for (int set = 0; set < patternCount; ++set) {
            for (int sample = 0; sample < samplesPerPattern; ++sample) {
                samples.add(new Vec3((float) FastMath.random(), (float) FastMath.random(), 0));
            }
        }

        return samples;
    }
}
