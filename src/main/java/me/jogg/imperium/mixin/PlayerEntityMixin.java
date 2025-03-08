package me.jogg.imperium.mixin;

import me.jogg.imperium.Imperium;
import me.jogg.imperium.modules.Speed;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "getMovementSpeed", at = @At("HEAD"), cancellable = true)
    void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        if (Imperium.isEnabled(Speed.class)) {
            cir.setReturnValue(Speed.movementSpeed);
            cir.cancel();
        }
    }
}
