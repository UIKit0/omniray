package org.vertexarmy.omniray.raytracer.sampling;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.jglm.support.FastMath;

import java.util.List;

/**
 * User: Alex
 * Date: 1/18/14
 */
public class RandomSampler extends Sampler {
    public RandomSampler(int sampleCount) {
        super(sampleCount);
    }

    @Override
    protected List<Vec3> generateSamples(int samplesPerSet, int setCount) {
        List<Vec3> samples = Lists.newArrayList();

        for (int set = 0; set < setCount; ++set) {
            for (int sample = 0; sample < samplesPerSet; ++sample) {
                samples.add(new Vec3((float) FastMath.random(), (float) FastMath.random(), 0));
            }
        }

        return samples;
    }
}
