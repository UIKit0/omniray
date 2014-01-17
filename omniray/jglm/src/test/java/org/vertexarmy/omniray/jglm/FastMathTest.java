package org.vertexarmy.omniray.jglm;

import org.junit.Assert;
import org.junit.Test;
import org.vertexarmy.omniray.jglm.support.FastMath;

/**
 * User: Alex
 * Date: 1/16/14
 */
public class FastMathTest {
    @Test
    public void testMix() {
        Assert.assertEquals(0.3, FastMath.mix(0, 1, 0.3), 0.00001);
        Assert.assertEquals(6, FastMath.mix(4, 8, 0.5f));
        Assert.assertEquals(32, FastMath.mix(0, 100, 0.32f));
    }
}
