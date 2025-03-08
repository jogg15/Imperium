package me.jogg.imperium.mixin;

import me.jogg.imperium.Imperium;
import me.jogg.imperium.modules.Jesus;
import me.jogg.imperium.modules.SuperJump;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "canWalkOnFluid", at=@At("HEAD"), cancellable = true)
    void canWalkOnFluid(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance().options.sneakKey.isPressed()) return;

        if (Imperium.isEnabled(Jesus.class)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method= "getJumpBoostVelocityModifier", at = @At("HEAD"), cancellable = true)
    void getJumpBoostVelocityModifier(CallbackInfoReturnable<Float> cir) {
        if (Imperium.isEnabled(SuperJump.class)) {
            cir.setReturnValue(SuperJump.jumpBoostMultiplier);
            cir.cancel();
        }
    }
}
