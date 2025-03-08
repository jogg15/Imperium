package me.jogg.imperium.modules;

import me.jogg.imperium.Imperium;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.packet.Packet;
import org.lwjgl.glfw.GLFW;

public abstract class Cheat {
    protected boolean enabled = false;
    public final KeyBinding keyBinding;;

    public Cheat() {
        this.keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cheats." + this.getClass().getSimpleName().toLowerCase(),
                GLFW.GLFW_KEY_UNKNOWN,
                "category.cheats.cheats"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (this.keyBinding.wasPressed())
                Imperium.toggle(this.getClass());
        });
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean modifyPacket(Packet<?> packet) {
        return false;
    }

    public void tick() { }
    public void postTick() { }

}
