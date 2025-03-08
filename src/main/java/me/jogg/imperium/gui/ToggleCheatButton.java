package me.jogg.imperium.gui;

import me.jogg.imperium.Imperium;
import me.jogg.imperium.modules.Cheat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.math.ColorHelper;

public class ToggleCheatButton extends ButtonWidget {
    private final Class<? extends Cheat> cheatClass;

    public ToggleCheatButton(Class<? extends Cheat> cheatClass) {
        super(0,0,
                DEFAULT_WIDTH_SMALL, DEFAULT_HEIGHT,
                Imperium.getTranslatableText(cheatClass),
                button -> Imperium.toggle(cheatClass),
                DEFAULT_NARRATION_SUPPLIER
        );

        this.cheatClass = cheatClass;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(
                this.getX(), this.getY(),
                this.getX() + this.getWidth(), this.getY() + DEFAULT_HEIGHT,
                this.color()
        );

        this.drawMessage(context, MinecraftClient.getInstance().textRenderer, 0xFFFFFFFF);
    }

    private int color () {
        int color = Imperium.isEnabled(this.cheatClass) ? 255 : 0;
        return ColorHelper.getArgb(255, 0, 0, color);
    }
}
