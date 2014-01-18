package org.vertexarmy.omniray.raytracer.sampling;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.omniray.jglm.Vec3;

import java.util.List;

/**
 * User: Alex
 * Date: 1/18/14
 */
public class NullSampler extends Sampler {
    public NullSampler(int sampleCount) {
        super(1);
    }

    @Override
    protected List<Vec3> generateSamples(int samplesPerSet, int setCount) {
        List<Vec3> sampleList = Lists.newArrayList();
        Vec3 origin = new Vec3(0, 0, 0);

        for (int set = 0; set < setCount; ++set) {
            sampleList.add(origin);
        }

        return sampleList;
    }
}
