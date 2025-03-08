package me.jogg.imperium.modules.movement;

import me.jogg.imperium.mixin.PlayerMoveC2SPacketAccessor;
import me.jogg.imperium.modules.Cheat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Flight extends Cheat {
    private static final double FALL_DIST = 0.4;
    private static final int MAX_FLOATING_TICKS = 10;
    private double previousY;
    private int floatingTicks;

    @Override
    public void toggle() {
        super.toggle();

        var player = MinecraftClient.getInstance().player;
        if (player == null) return;

        player.getAbilities().allowFlying = this.enabled;
        if (!this.enabled) {
            player.getAbilities().flying = false;
        }
        this.previousY = player.getY();
        this.floatingTicks = 0;
    }

    @Override
    public boolean modifyPacket(Packet<?> packet) {
        if (!(packet instanceof PlayerMoveC2SPacket)) return false;

        if (floatingTicks >= MAX_FLOATING_TICKS) {
            ((PlayerMoveC2SPacketAccessor) packet).setY(this.previousY - FALL_DIST);

            floatingTicks = 0;
        }

        return false;
    }

    @Override
    public void tick() {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return;

        player.getAbilities().allowFlying = this.enabled;

        if (!player.getAbilities().flying) return;

        if (player.getY() >= this.previousY - FALL_DIST)
            this.floatingTicks++;

        this.previousY = player.getY();
    }
}
