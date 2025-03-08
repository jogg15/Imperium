package me.jogg.imperium.modules;

import me.jogg.imperium.Imperium;
import net.minecraft.network.packet.Packet;

public class Listener extends Cheat {
    @Override
    public boolean modifyPacket(Packet<?> packet) {
        Imperium.LOGGER.info("Sent Packet: " + packet.getClass());

        return false;
    }
}
