package me.jogg.imperium.gui;

import me.jogg.imperium.Imperium;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;

public class CheatScreen extends Screen {
    public CheatScreen() {
        super(Text.translatable("cheats.title"));
    }

    @Override
    protected void init() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder =  gridWidget.createAdder(4);

        for (var cheat : Imperium.getCheats()) {
            adder.add(new ToggleCheatButton(cheat.getClass()));
        }

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, this.height / 6 - 12, this.width, this.height, 0.5f, 0.0f);
        gridWidget.forEachChild(this::addDrawableChild);
    }
}
