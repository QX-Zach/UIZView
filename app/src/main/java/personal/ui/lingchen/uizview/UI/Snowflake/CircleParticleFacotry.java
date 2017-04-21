package personal.ui.lingchen.uizview.UI.Snowflake;

import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ozner_67 on 2017/1/10.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class CircleParticleFacotry extends ParticleFactory {
    private Random random;

    @Override
    public List<Particle> generateParticles(int num, Rect bound) {
        random = new Random();
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            float cx = bound.left + (random.nextFloat() - 0.5f) * bound.width() * 0.1f + bound.width() * 0.25f;
            while (cx > bound.width() / 2 || cx <= 0) {
                cx = bound.left + (random.nextFloat() - 0.5f) * bound.width() * 0.1f + bound.width() * 0.25f;
            }
            float cy = bound.top + random.nextFloat() * bound.height();
            Particle particle = new CircleParticle(Color.WHITE, cx, cy, bound);
            particles.add(particle);
        }
        return particles;
    }
}
