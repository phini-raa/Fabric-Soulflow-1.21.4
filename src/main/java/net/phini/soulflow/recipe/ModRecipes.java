package net.phini.soulflow.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.phini.soulflow.Soulflow;

public class ModRecipes {

    // -------------------
    // Three-Item Recipe
    // -------------------
    public static final RecipeSerializer<AlchemyTableRecipe> ALCHEMY_TABLE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(Soulflow.MOD_ID, "alchemy_table"),
            AlchemyTableRecipeSerializer.INSTANCE
    );

    public static final RecipeType<AlchemyTableRecipe> ALCHEMY_TABLE_TYPE = Registry.register(
            Registries.RECIPE_TYPE,
            Identifier.of(Soulflow.MOD_ID, "alchemy_table"),
            new RecipeType<AlchemyTableRecipe>() {
                @Override
                public String toString() {
                    return "alchemy_table";
                }
            }
    );

    // -------------------
    // Register Method
    // -------------------
    public static void registerRecipes() {
        Soulflow.LOGGER.info("Registering Custom Recipes for " + Soulflow.MOD_ID);
        // All registration is already done above; this method can log or do future setup
    }
}
