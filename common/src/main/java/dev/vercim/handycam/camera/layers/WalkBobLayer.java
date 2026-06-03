package dev.vercim.handycam.camera.layers;

import dev.vercim.handycam.camera.CameraOffset;
import dev.vercim.handycam.camera.PlayerState;
import dev.vercim.handycam.camera.ShakeLayer;
import dev.vercim.handycam.camera.math.FractalNoise;
import dev.vercim.handycam.config.HandycamConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class WalkBobLayer implements ShakeLayer {

    private static final float TWO_PI = (float) (2.0 * Math.PI);
    private static final float TICK_DT = 1f / 20f;

    // Multiple noise sources for layered, organic feel
    private final FractalNoise verticalNoise = new FractalNoise(0xDEADBEEFL, 3, 0.5f, 0.5f);
    private final FractalNoise lateralNoise  = new FractalNoise(0xCAFEBABEL, 3, 0.4f, 0.5f);
    private final FractalNoise rollNoise     = new FractalNoise(0xBEEFC0DEL, 2, 0.3f, 0.6f);

    private float bobPhase = 0f;

    @Override
    public void tick(PlayerState state) {
        HandycamConfig cfg = HandycamConfig.get();
        if (!cfg.walkBobEnabled) return;

        float speed = state.horizontalSpeed;
        if (speed < 0.05f) return;

        // Phase advances at fixed rate, scaled by speed
        bobPhase += speed * cfg.walkBobFrequency * TWO_PI * TICK_DT;
    }

    @Override
    public CameraOffset compute(PlayerState state, float time, float dt) {
        HandycamConfig cfg = HandycamConfig.get();
        if (!cfg.walkBobEnabled) return CameraOffset.ZERO;

        float speed = state.horizontalSpeed;
        if (speed < 0.05f) return CameraOffset.ZERO;

        // Primary bobbing pattern: abs-sine (two bumps per cycle) + fractal overlay
        float baseBob = (float) Math.abs(Math.sin(bobPhase));
        float verticalNoise = this.verticalNoise.get(bobPhase * 0.5f);
        float verticalBob = -(baseBob * 0.7f + verticalNoise * 0.3f)
                            * cfg.walkBobIntensity * speed;

        // Lateral sway at 2x frequency: smoother side-to-side + fractal variation
        float baseSway = (float) Math.sin(bobPhase * 2f);
        float lateralNoise = this.lateralNoise.get(bobPhase * 0.7f);
        float lateralBob = (baseSway * 0.7f + lateralNoise * 0.3f)
                          * cfg.walkBobIntensity * speed * 0.4f;

        // Roll component: subtle camera tilt matching footstep cadence
        float rollNoise = this.rollNoise.get(bobPhase * 0.6f);
        float rollBob = (float) Math.sin(bobPhase * 2f) * 0.5f * rollNoise
                       * cfg.walkBobIntensity * speed * 0.2f;

        // Noise deformation for extra irregularity
        float noiseDeform = this.verticalNoise.get(bobPhase * 0.3f)
                           * cfg.walkBobIntensity * speed * cfg.walkNoiseAmount;

        float master = cfg.masterIntensity;
        return new CameraOffset(
            (verticalBob + noiseDeform) * master,
            lateralBob                  * master,
            rollBob                     * master
        );
    }
}
