package org.vertexarmy.omniray.client;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
import net.vertexarmy.omniray.raytracer.World;
import net.vertexarmy.omniray.raytracer.geometry.Sphere;
import org.vertexarmy.omniray.client.components.OutputWindow;
import org.vertexarmy.omniray.client.laf.HalloweenLookAndFeel;

import java.util.Random;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class Launcher {
    public static void main(String[] argv){

        final float radius = 180;

        HalloweenLookAndFeel.install();

        OutputWindow window = new OutputWindow();

        World world = new World();
        world.addObject(new Sphere(new Vec3(0, 0, 250), radius));

        Random random = new Random();

        for (int i = 0; i < 240; ++i){
            double a1 = random.nextFloat() * FastMath.PI * 2;
            double a2 = random.nextFloat() * FastMath.PI * 2;
            Vec3 origin = new Vec3(
                    (float)(FastMath.cos(a2) * FastMath.sin(a1) * radius),
                    (float)(FastMath.sin(a2) * radius),
                    (float)(FastMath.cos(a2) * FastMath.cos(a1) * radius) + 250);
            float r = (random.nextFloat() + 0.2f) * 40;

            world.addObject(new Sphere(origin, r));
        }

        world.render(window.getCanvas().getImageBuilder());
    }
}
