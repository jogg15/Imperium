package me.jogg.imperium.modules.player;

import me.jogg.imperium.modules.Cheat;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class AntiHunger extends Cheat {

    @Override
    public boolean modifyPacket(Packet<?> packet) {
        if (!(packet instanceof ClientCommandC2SPacket commandPacket)) return false;

        return commandPacket.getMode() == ClientCommandC2SPacket.Mode.START_SPRINTING;
    }
}
