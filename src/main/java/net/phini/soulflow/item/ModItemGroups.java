package net.phini.soulflow.item;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.phini.soulflow.Soulflow;
import net.phini.soulflow.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup SOULFLOW_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Soulflow.MOD_ID,"soulflow_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.TRANSFERENCE_TOTEM))
                    .displayName(Text.translatable("itemgroup.soulflow.soulflow_items"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.TRANSFERENCE_TOTEM);
                        entries.add(ModItems.SPORE_TOTEM);
                        entries.add(ModItems.SUNKEN_GRIMOIRE);
                        entries.add(ModItems.VOID_TOTEM);
                        entries.add(ModItems.HOLLOW_TOTEM);
                        entries.add(ModItems.SOUL);
                        entries.add(ModBlocks.ALCHEMY_TABLE);
                    }))
                    .build());
    public static void registerItemGroups() {
        Soulflow.LOGGER.info("Registering Item Groups for " + Soulflow.MOD_ID);
    }
}