package me.jogg.imperium.mixin;

import me.jogg.imperium.Imperium;
import me.jogg.imperium.modules.Step;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "getStepHeight", at = @At("HEAD"), cancellable = true)
    void getStepHeight(CallbackInfoReturnable<Float> cir) {
        if(Imperium.isEnabled(Step.class)) {
            cir.setReturnValue(Step.stepHeight);
            cir.cancel();
        }
    }
}
