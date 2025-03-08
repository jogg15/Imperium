package me.jogg.imperium;

import me.jogg.imperium.modules.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Imperium implements ModInitializer {
    public static final String MOD_ID = "imperium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final List<Cheat> CHEATS = new ArrayList<>();

    @Override
    public void onInitialize() {
        CHEATS.add(new FullBright());
        CHEATS.add(new Jesus());
        CHEATS.add(new XRay());
        CHEATS.add(new Step());
        CHEATS.add(new Speed());
        CHEATS.add(new NoWeather());
        CHEATS.add(new SuperJump());


        ClientTickEvents.START_CLIENT_TICK.register(client ->
                CHEATS.forEach(cheat -> {
                    if (cheat.isEnabled())
                        cheat.tick();
                })
        );
        ClientTickEvents.END_CLIENT_TICK.register(client ->
                CHEATS.forEach(cheat -> {
                    if (cheat.isEnabled())
                        cheat.postTick();
                })
        );

        HudRenderCallback.EVENT.register((drawContext, delta) -> {
            if (MinecraftClient.getInstance().getDebugHud().shouldShowDebugHud()) return;
        });

        LOGGER.info("Imperium initialized.");
    }

    private static void drawHud(DrawContext drawContext) {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return;

        var textRenderer = MinecraftClient.getInstance().textRenderer;

        drawWatermark(drawContext, textRenderer, player.age);

        var index = 1;
        for (var cheat : getCheats()) {
            if (!cheat.isEnabled()) continue;

            float hue = ((float) player.age / 200f + (float) index / (float) (numCheats() + 1)) % 1f;
            var color = MathHelper.hsvToRgb(hue, 1f, 1f);

            drawContext.drawText(
                    textRenderer,
                    getTranslatableText(cheat.getClass()),
                    10, 25 + index * 10,
                    color, true
            );

            index++;

        }
    }

    private static void drawWatermark(DrawContext drawContext, TextRenderer textRenderer, int age) {
        float hue = ((float) age / 200f) % 1f;
        var color = MathHelper.hsvToRgb(hue, 1f, 1f);

        var scale = 1.5f;
        var matrices = drawContext.getMatrices().peek().getPositionMatrix();
        matrices.scale(scale);

        textRenderer.draw(
                Text.translatable("cheats.title"),
                10, 10,
                color, true,
                matrices,
                MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers(),
                TextRenderer.TextLayerType.NORMAL,
                0, 0xF000F0
        );

        matrices.scale(1f / scale);
        drawContext.draw();
    }

    public static <T extends Cheat> T getCheat(Class<T> cheatClass) {
        for (var cheat : getCheats()) {
            if (cheat.getClass() == cheatClass) {
                return cheatClass.cast(cheat);
            }
        }
        return null;
    }

    public static boolean isEnabled(Class<? extends Cheat> cheatClass) {
        var cheat = getCheat(cheatClass);
        return cheat != null && cheat.isEnabled();
    }

    public static void toggle(Class<? extends Cheat> cheatClass) {
        var cheat = getCheat(cheatClass);
        if (cheat != null) {
            cheat.toggle();
        }
    }

    public static int numCheats() {
        return getCheats().size();
    }

    public static List<Cheat> getCheats() {
        return CHEATS;
    }

    public static Text getTranslatableText(Class<? extends Cheat> cheatClass) {
        return Text.translatable("cheats." + cheatClass.getSimpleName().toLowerCase());
    }
}