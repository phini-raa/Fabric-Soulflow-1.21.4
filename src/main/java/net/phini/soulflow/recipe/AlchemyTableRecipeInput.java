package net.phini.soulflow.recipe;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AlchemyTableRecipeInput {

    private final ItemStack inputA;
    private final ItemStack inputB;
    private final ItemStack inputC;

    public AlchemyTableRecipeInput(ItemStack inputA, ItemStack inputB, ItemStack inputC) {
        this.inputA = inputA;
        this.inputB = inputB;
        this.inputC = inputC;
    }

    public ItemStack getInputA() {
        return inputA;
    }

    public ItemStack getInputB() {
        return inputB;
    }

    public ItemStack getInputC() {
        return inputC;
    }


    public List<ItemStack> getInputs() {
        return List.of(inputA, inputB, inputC);
    }

    public boolean matches(AlchemyTableRecipe recipe) {
        return recipe.getInputA().test(inputA) &&
                recipe.getInputB().test(inputB) &&
                recipe.getInputC().test(inputC);
    }

    public boolean isEmpty() {
        return inputA.isEmpty() && inputB.isEmpty() && inputC.isEmpty();
    }

    @Nullable
    public ItemStack getStack(int index) {
        return switch (index) {
            case 0 -> inputA;
            case 1 -> inputB;
            case 2 -> inputC;
            default -> null;
        };
    }

    public int size() {
        return 3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlchemyTableRecipeInput other)) return false;
        return ItemStack.areEqual(inputA, other.inputA) &&
                ItemStack.areEqual(inputB, other.inputB) &&
                ItemStack.areEqual(inputC, other.inputC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputA, inputB, inputC);
    }
}