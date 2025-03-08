package me.jogg.imperium.mixin;

import me.jogg.imperium.Imperium;
import me.jogg.imperium.modules.movement.Jesus;
import me.jogg.imperium.modules.render.XRay;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow public abstract FluidState getFluidState();
    @Shadow public abstract Block getBlock();

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    void getCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (getFluidState().isEmpty()) return;

        var client = MinecraftClient.getInstance();
        if (client == null) return;
        if (client.options.sneakKey.isPressed()) return;

        var player = client.player;
        if (player == null) return;
        if (player.isTouchingWater()) return;

        if (Imperium.isEnabled(Jesus.class)) {
            cir.setReturnValue(VoxelShapes.fullCube());
            cir.cancel();
            }
        }
    @Inject(method="getLuminance", at=@At("HEAD"), cancellable = true)
    private void getLuminance(CallbackInfoReturnable<Integer> cir) {
        if (Imperium.isEnabled(XRay.class) && !XRay.isInteresting(this.getBlock())) {
            cir.setReturnValue(15);
            cir.cancel();
        }
    }
}
