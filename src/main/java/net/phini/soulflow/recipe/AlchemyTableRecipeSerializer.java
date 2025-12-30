package net.phini.soulflow.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;

public class AlchemyTableRecipeSerializer implements RecipeSerializer<AlchemyTableRecipe> {

    public static final AlchemyTableRecipeSerializer INSTANCE = new AlchemyTableRecipeSerializer();

    private AlchemyTableRecipeSerializer() {}

    // MapCodec handles JSON deserialization (and also network sync internally)
    public static final MapCodec<AlchemyTableRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("inputA").forGetter(AlchemyTableRecipe::getInputA),
                    Ingredient.CODEC.fieldOf("inputB").forGetter(AlchemyTableRecipe::getInputB),
                    Ingredient.CODEC.fieldOf("inputC").forGetter(AlchemyTableRecipe::getInputC),
                    ItemStack.CODEC.fieldOf("output").forGetter(AlchemyTableRecipe::getOutput)
            ).apply(instance, (a, b, c, output) -> new AlchemyTableRecipe(a, b, c, output, null))
    );

    @Override
    public MapCodec<AlchemyTableRecipe> codec() {
        return MAP_CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, AlchemyTableRecipe> packetCodec() {
        // Just return null — Minecraft will handle network automatically for standard recipes
        return null;
    }
}
