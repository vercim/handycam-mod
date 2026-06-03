package dev.vercim.handycam.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.vercim.handycam.config.HandycamConfig;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    // bobView applies the vanilla view-bob matrix transform each frame.
    // Cancel it when our own WalkBobLayer is handling the effect.
    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void handycam$cancelVanillaBob(PoseStack poseStack, float partialTick, CallbackInfo ci) {
        if (HandycamConfig.get().disableVanillaBob) {
            ci.cancel();
        }
    }
}
