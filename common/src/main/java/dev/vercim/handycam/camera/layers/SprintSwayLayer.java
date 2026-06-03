package dev.vercim.handycam.camera.layers;

import dev.vercim.handycam.camera.CameraOffset;
import dev.vercim.handycam.camera.PlayerState;
import dev.vercim.handycam.camera.ShakeLayer;
import dev.vercim.handycam.camera.math.SpringSimulator;
import dev.vercim.handycam.config.HandycamConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class SprintSwayLayer implements ShakeLayer {

    // Roll: reacts to horizontal turning.
    private final SpringSimulator rollSpring     = new SpringSimulator(120f, 22f);
    // Yaw lag: camera yaw trails behind head rotation (left-right inertia).
    private final SpringSimulator yawLagSpring   = new SpringSimulator(60f,  15f);
    // Pitch lag: camera pitch trails behind vertical movement (up-down inertia).
    // Looser spring so the lag lingers longer — feels like camera has weight.
    private final SpringSimulator pitchLagSpring = new SpringSimulator(40f,  12f);

    @Override
    public CameraOffset compute(PlayerState state, float time, float dt) {
        HandycamConfig cfg = HandycamConfig.get();
        if (!cfg.sprintEnabled) {
            rollSpring.reset();
            yawLagSpring.reset();
            pitchLagSpring.reset();
            return CameraOffset.ZERO;
        }

        float sprintMult = state.isSprinting ? 1.5f : 1.0f;

        // ── Horizontal sway (turning) ──────────────────────────────────────
        float turnContrib = state.turnRate * cfg.turnSway * sprintMult;
        float targetRoll  = Math.max(-cfg.maxTurnRoll, Math.min(cfg.maxTurnRoll, turnContrib));
        float targetYaw   = -state.turnRate * cfg.swayYawLag * sprintMult;

        // ── Vertical sway (Y movement inertia) ────────────────────────────
        // Camera lags behind vertical motion: jumping up → camera briefly pitched down,
        // falling fast → camera briefly pitched up.
        float targetPitch = -state.verticalVelocity * cfg.swayPitchLag;

        float roll      = rollSpring.update(targetRoll,   dt) * cfg.masterIntensity;
        float yawLag    = yawLagSpring.update(targetYaw,   dt) * cfg.masterIntensity;
        float pitchLag  = pitchLagSpring.update(targetPitch, dt) * cfg.masterIntensity;

        return new CameraOffset(pitchLag, yawLag, roll);
    }
}
