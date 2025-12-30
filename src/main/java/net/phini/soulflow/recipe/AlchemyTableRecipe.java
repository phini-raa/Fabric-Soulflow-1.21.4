package net.phini.soulflow.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AlchemyTableRecipe implements Recipe<CraftingRecipeInput> {

    private final Ingredient inputA;
    private final Ingredient inputB;
    private final Ingredient inputC;
    private final ItemStack output;
    private final Identifier id;

    public AlchemyTableRecipe(Ingredient inputA, Ingredient inputB,
                           Ingredient inputC, ItemStack output,
                           Identifier id) {
        this.inputA = inputA;
        this.inputB = inputB;
        this.inputC = inputC;
        this.output = output;
        this.id = id;
    }

    public Ingredient getInputA() { return inputA; }
    public Ingredient getInputB() { return inputB; }
    public Ingredient getInputC() { return inputC; }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        if (input.size() < 3) return false;
        return inputA.test(input.getStackInSlot(0)) &&
                inputB.test(input.getStackInSlot(1)) &&
                inputC.test(input.getStackInSlot(2));
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    public boolean fits(int width, int height) {
        return width * height >= 3;
    }

    public ItemStack getOutput() {
        return output;
    }
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<? extends Recipe<CraftingRecipeInput>> getSerializer() {
        return AlchemyTableRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<CraftingRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    public static class Type implements RecipeType<AlchemyTableRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "alchemy_table";
    }
}
