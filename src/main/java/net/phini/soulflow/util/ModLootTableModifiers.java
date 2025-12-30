package net.phini.soulflow.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.phini.soulflow.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {

    // Totem of Transference
    private static final Identifier WITCH_ID
            = Identifier.of("minecraft", "entities/witch");
    private static final Identifier WOODLAND_MANSION_ID
            = Identifier.of("minecraft", "chests/woodland_mansion");
    private static final Identifier PILLAGER_OUTPOST_ID
            = Identifier.of("minecraft", "chests/pillager_outpost");

    // Sunken Grimoire
    private static final Identifier DROWNED_ID
            = Identifier.of("minecraft", "entities/drowned");
    private static final Identifier BURIED_TREASURE_ID
            = Identifier.of("minecraft", "chests/buried_treasure");
    private static final Identifier SHIPWRECK_ID
            = Identifier.of("minecraft", "chests/shipwreck_treasure");


    // Void Totem
    private static final Identifier END_CITY_ID
            = Identifier.of("minecraft", "chests/end_city_treasure");
    private static final Identifier STRONGHOLD_CORRIDOR_ID
            = Identifier.of("minecraft", "chests/stronghold_corridor");

    // Spore Totem
    private static final Identifier MOOSHROOM_ID
            = Identifier.of("minecraft", "entities/mooshroom");

    // Hollow Totem

    private static final Identifier MINESHAFT_ID
            = Identifier.of("minecraft", "chests/abandoned_mineshaft");
    private static final Identifier DUNGEON_ID
            = Identifier.of("minecraft", "chests/simple_dungeon");
    private static final Identifier TOOLSMITH_ID
            = Identifier.of("minecraft", "chests/village/village_toolsmith");
    private static final Identifier BASTION_ID
            = Identifier.of("minecraft", "chests/bastion_treasure");
    private static final Identifier RUINED_PORTAL_ID
            = Identifier.of("minecraft", "chests/ruined_portal");



    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registry) -> {
            // Totem of Transference
                if(WITCH_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.2f))
                            .with(ItemEntry.builder(ModItems.TRANSFERENCE_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
                if(WOODLAND_MANSION_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.2f))
                            .with(ItemEntry.builder(ModItems.TRANSFERENCE_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
                if(PILLAGER_OUTPOST_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.2f))
                            .with(ItemEntry.builder(ModItems.TRANSFERENCE_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }

            // Sunken Grimoire
                if(DROWNED_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.025f))
                            .with(ItemEntry.builder(ModItems.SUNKEN_GRIMOIRE))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
                if(BURIED_TREASURE_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.50f))
                            .with(ItemEntry.builder(ModItems.SUNKEN_GRIMOIRE))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }

                if(SHIPWRECK_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.25f))
                            .with(ItemEntry.builder(ModItems.SUNKEN_GRIMOIRE))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }

            // Totem of Spores
                if(MOOSHROOM_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.25f))
                            .with(ItemEntry.builder(ModItems.SPORE_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }

            // Totem of the Void
                if(END_CITY_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.1f))
                            .with(ItemEntry.builder(ModItems.VOID_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
                if(STRONGHOLD_CORRIDOR_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.2f))
                            .with(ItemEntry.builder(ModItems.VOID_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }

            // Hollow Totem
                if(MINESHAFT_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.22f))
                            .with(ItemEntry.builder(ModItems.HOLLOW_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
                if(DUNGEON_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.25f))
                            .with(ItemEntry.builder(ModItems.HOLLOW_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
                if(RUINED_PORTAL_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.3f))
                            .with(ItemEntry.builder(ModItems.HOLLOW_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
                if(TOOLSMITH_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.1f))
                            .with(ItemEntry.builder(ModItems.HOLLOW_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
                if(WOODLAND_MANSION_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.2f))
                            .with(ItemEntry.builder(ModItems.HOLLOW_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
                if(PILLAGER_OUTPOST_ID.equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.4f))
                            .with(ItemEntry.builder(ModItems.HOLLOW_TOTEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }

        });
    }
}