package net.phini.soulflow.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.phini.soulflow.Soulflow;
import net.phini.soulflow.block.ModBlocks;
import net.phini.soulflow.block.entity.custom.AlchemyTableBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<AlchemyTableBlockEntity> ALCHEMY_TABLE_BE =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(Soulflow.MOD_ID, "alchemy_table_be"),
                    FabricBlockEntityTypeBuilder.create(
                            AlchemyTableBlockEntity::new,
                            ModBlocks.ALCHEMY_TABLE
                    ).build()
            );



    public static void registerBlockEntities() {
        Soulflow.LOGGER.info("Registering Block Entities for " + Soulflow.MOD_ID);
    }
}