package net.phini.soulflow;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.phini.soulflow.block.ModBlocks;
import net.phini.soulflow.block.entity.ModBlockEntities;
import net.phini.soulflow.command.SoultransformCommand;
import net.phini.soulflow.gamerules.ModGameRules;
import net.phini.soulflow.item.ModItemGroups;
import net.phini.soulflow.item.ModItems;
import net.phini.soulflow.recipe.ModRecipes;
import net.phini.soulflow.screen.ModScreenHandlers;
import net.phini.soulflow.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Soulflow implements ModInitializer {
	public static final String MOD_ID = "soulflow";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModLootTableModifiers.modifyLootTables();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
		ModRecipes.registerRecipes();
		ModGameRules.registerGamerules();

		// Wandering Trader Trades
		TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 8),
					new ItemStack(ModItems.TRANSFERENCE_TOTEM, 1), 3, 7, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 8),
					new ItemStack(ModItems.SUNKEN_GRIMOIRE, 1), 3, 7, 0.04f));


		});

		// Soultransform cmd

		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) -> {
					SoultransformCommand.register(dispatcher);
				}
		);
	}
}