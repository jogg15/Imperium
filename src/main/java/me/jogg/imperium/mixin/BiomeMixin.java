package me.jogg.imperium.mixin;

import me.jogg.imperium.Imperium;
import me.jogg.imperium.modules.NoWeather;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BiomeMixin {
    @Inject(method = "hasPrecipitation", at = @At("HEAD"), cancellable = true)
    void hasPrecipitation(CallbackInfoReturnable<Boolean> cir) {
        if (Imperium.isEnabled(NoWeather.class)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
