package net.phini.soulflow.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.phini.soulflow.Soulflow;

public class ModItems {

    /* -------------------- REGISTRY KEYS -------------------- */

    public static final RegistryKey<Item> TRANSFERENCE_TOTEM_KEY =
            RegistryKey.of(RegistryKeys.ITEM,
                    Identifier.of(Soulflow.MOD_ID, "totem_of_transference"));

    public static final RegistryKey<Item> SUNKEN_GRIMOIRE_KEY =
            RegistryKey.of(RegistryKeys.ITEM,
                    Identifier.of(Soulflow.MOD_ID, "sunken_grimoire"));

    public static final RegistryKey<Item> VOID_TOTEM_KEY =
            RegistryKey.of(RegistryKeys.ITEM,
                    Identifier.of(Soulflow.MOD_ID, "void_totem"));

    public static final RegistryKey<Item> SPORE_TOTEM_KEY =
            RegistryKey.of(RegistryKeys.ITEM,
                    Identifier.of(Soulflow.MOD_ID, "totem_of_spores"));

    public static final RegistryKey<Item> HOLLOW_TOTEM_KEY =
            RegistryKey.of(RegistryKeys.ITEM,
                    Identifier.of(Soulflow.MOD_ID, "hollow_totem"));

    public static final RegistryKey<Item> SOUL_KEY =
            RegistryKey.of(RegistryKeys.ITEM,
                    Identifier.of(Soulflow.MOD_ID, "soul"));
    /* -------------------- ITEMS -------------------- */

    public static final Item TRANSFERENCE_TOTEM = registerItem(
            TRANSFERENCE_TOTEM_KEY,
            new TransferenceTotemItem(
                    new Item.Settings()
                            .registryKey(TRANSFERENCE_TOTEM_KEY)
                            .maxCount(1)
                            .rarity(Rarity.UNCOMMON)
            )
    );


    public static final Item HOLLOW_TOTEM = registerItem(
            HOLLOW_TOTEM_KEY,
            new Item(
                    new Item.Settings()
                            .registryKey(HOLLOW_TOTEM_KEY)
                            .maxCount(1)
                            .rarity(Rarity.UNCOMMON)

            )
    );

    public static final Item SOUL = registerItem(
            SOUL_KEY,
            new Item(
                    new Item.Settings()
                            .registryKey(SOUL_KEY)
                            .maxCount(64)
            )
    );

    public static final Item SPORE_TOTEM = registerItem(
            SPORE_TOTEM_KEY,
            new TotemOfSporesItem(
                    new Item.Settings()
                            .registryKey(SPORE_TOTEM_KEY)
                            .maxCount(1)
                            .rarity(Rarity.UNCOMMON)
            )
    );

    public static final Item VOID_TOTEM = registerItem(
            VOID_TOTEM_KEY,
            new VoidTotemItem(
                    new Item.Settings()
                            .registryKey(VOID_TOTEM_KEY)
                            .maxCount(1)
                            .rarity(Rarity.UNCOMMON)
            )
    );

    public static final Item SUNKEN_GRIMOIRE = registerItem(
            SUNKEN_GRIMOIRE_KEY,
            new SunkenGrimoireItem(
                    new Item.Settings()
                            .registryKey(SUNKEN_GRIMOIRE_KEY)
                            .maxCount(1)
                            .rarity(Rarity.RARE)
            )
    );



    /* -------------------- REGISTRATION -------------------- */

    private static Item registerItem(RegistryKey<Item> key, Item item) {
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModItems() {
        Soulflow.LOGGER.info("Registering Mod Items for " + Soulflow.MOD_ID);
    }
}
