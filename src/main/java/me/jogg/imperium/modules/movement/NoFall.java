package me.jogg.imperium.modules.movement;

import me.jogg.imperium.mixin.PlayerMoveC2SPacketAccessor;
import me.jogg.imperium.modules.Cheat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Cheat {
    @Override
    public boolean modifyPacket(Packet<?> packet) {
        if (!(packet instanceof PlayerMoveC2SPacket)) return false;

        var player = MinecraftClient.getInstance().player;
        if (player == null) return false;

        if (player.getAbilities().flying) {
            ((PlayerMoveC2SPacketAccessor) packet).setOnGround(true);
        } else {
            if (player.getVelocity().getY() < -0.5) return false;
            ((PlayerMoveC2SPacketAccessor) packet).setOnGround(true);
        }

        return false;
    }
}
