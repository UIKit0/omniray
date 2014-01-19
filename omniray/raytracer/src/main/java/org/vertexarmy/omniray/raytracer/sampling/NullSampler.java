package org.vertexarmy.omniray.raytracer.sampling;

import org.vertexarmy.omniray.jglm.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Alex
 * Date: 1/18/14
 */

/**
 * A sampler that only generates one sample per set, with an offset of [0, 0, 0]
 */
public class NullSampler extends Sampler {

    /**
     * Creates a null sampler with one sample per pattern.
     */
    public NullSampler() {
        super(1);
    }

    /**
     * Generates one sample per pattern in the origin: (0, 0, 0)
     *
     * @param samplesPerPattern the number of samples in a pattern
     * @param patternCount      the number of patterns in the sampler
     * @return a list of origin samples
     */
    @Override
    protected List<Vec3> generateSamples(int samplesPerPattern, int patternCount) {
        List<Vec3> sampleList = new ArrayList<Vec3>();
        Vec3 origin = new Vec3(0, 0, 0);

        for (int set = 0; set < patternCount; ++set) {
            sampleList.add(origin);
        }

        return sampleList;
    }
}
