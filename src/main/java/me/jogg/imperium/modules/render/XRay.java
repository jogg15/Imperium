package me.jogg.imperium.modules.render;

import me.jogg.imperium.modules.Cheat;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;

import java.util.HashSet;
import java.util.Set;

public class XRay extends Cheat {
    private static final Set<Block> interestingBlocks = new HashSet<>();

    static {
        // STONE ORES
        addInterestingBlock(Blocks.COAL_ORE);
        addInterestingBlock(Blocks.IRON_ORE);
        addInterestingBlock(Blocks.GOLD_ORE);
        addInterestingBlock(Blocks.COPPER_ORE);
        addInterestingBlock(Blocks.EMERALD_ORE);
        addInterestingBlock(Blocks.LAPIS_ORE);
        addInterestingBlock(Blocks.DIAMOND_ORE);
        addInterestingBlock(Blocks.REDSTONE_ORE);

        // DEEPSLATE ORES
        addInterestingBlock(Blocks.DEEPSLATE_COAL_ORE);
        addInterestingBlock(Blocks.DEEPSLATE_IRON_ORE);
        addInterestingBlock(Blocks.DEEPSLATE_GOLD_ORE);
        addInterestingBlock(Blocks.DEEPSLATE_COPPER_ORE);
        addInterestingBlock(Blocks.DEEPSLATE_EMERALD_ORE);
        addInterestingBlock(Blocks.DEEPSLATE_LAPIS_ORE);
        addInterestingBlock(Blocks.DEEPSLATE_DIAMOND_ORE);
        addInterestingBlock(Blocks.DEEPSLATE_REDSTONE_ORE);
    }

    private static void addInterestingBlock(Block block) {
        interestingBlocks.add(block);
    }

    @Override
    public void toggle() {
        super.toggle();
        MinecraftClient.getInstance().worldRenderer.reload();
    }

    public static boolean isInteresting(Block block) {
        return interestingBlocks.contains(block);
    }
}
