package personal.ui.lingchen.uizview.UI.Snowflake;

import android.graphics.Rect;

import java.util.List;

/**
 * Created by ozner_67 on 2017/1/10.
 * 邮箱：xinde.zhang@cftcn.com
 */

public abstract class ParticleFactory {
    public abstract List<Particle> generateParticles(int num, Rect bound);
}
