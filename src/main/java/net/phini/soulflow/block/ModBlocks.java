package net.phini.soulflow.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.phini.soulflow.Soulflow;
import net.phini.soulflow.block.custom.AlchemyTableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {

    // ---- DECLARE BLOCKS ----
    // ---- DECLARE BLOCKS ----
    public static final Block ALCHEMY_TABLE = register(
            "alchemy_table",
            AlchemyTableBlock::new,
            AbstractBlock.Settings.create()
                    .strength(4.0f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.GILDED_BLACKSTONE)
    );

    private static Block register(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        final Identifier identifier = Identifier.of("soulflow", path);
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);

        final Block block = Blocks.register(registryKey, factory, settings);
        Items.register(block);
        return block;
    }

    // ---- INIT METHOD ----
    public static void registerModBlocks() {
        Soulflow.LOGGER.info("Registering Mod Blocks for " + Soulflow.MOD_ID);
    }
}
