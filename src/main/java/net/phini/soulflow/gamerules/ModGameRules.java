package net.phini.soulflow.gamerules;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

public class ModGameRules {

    public static final GameRules.Key<GameRules.BooleanRule> EXCHANGE_INVENTORIES_RULE =
            GameRuleRegistry.register(
                    "soulflow:exchangeInventories",                   // gamerule name
                    GameRules.Category.PLAYER,             // category
                    GameRuleFactory.createBooleanRule(true) // default value
            );
    public static final GameRules.Key<GameRules.BooleanRule> EXCHANGE_ENDERCHESTS_RULE =
            GameRuleRegistry.register(
                    "soulflow:exchangeEnderchests",                   // gamerule name
                    GameRules.Category.PLAYER,             // category
                    GameRuleFactory.createBooleanRule(true) // default value
            );
    public static final GameRules.Key<GameRules.BooleanRule> EXCHANGE_HEALTH_RULE =
            GameRuleRegistry.register(
                    "soulflow:exchangeHealth",                   // gamerule name
                    GameRules.Category.PLAYER,             // category
                    GameRuleFactory.createBooleanRule(true) // default value
            );
    public static final GameRules.Key<GameRules.BooleanRule> EXCHANGE_EFFECTS_RULE =
            GameRuleRegistry.register(
                    "soulflow:exchangeEffects",                   // gamerule name
                    GameRules.Category.PLAYER,             // category
                    GameRuleFactory.createBooleanRule(true) // default value
            );
    public static final GameRules.Key<GameRules.BooleanRule> DISABLE_MSGS_RULE =
            GameRuleRegistry.register(
                    "soulflow:disablePrivateMessages",                   // gamerule name
                    GameRules.Category.CHAT,             // category
                    GameRuleFactory.createBooleanRule(true) // default value

            );

    public static boolean getExchangeInventoriesRule(ServerWorld world) {
        return world.getGameRules().get(EXCHANGE_INVENTORIES_RULE).get();
    }
    public static boolean getExchangeEnderchestsRule(ServerWorld world) {
        return world.getGameRules().get(EXCHANGE_ENDERCHESTS_RULE).get();
    }
    public static boolean getExchangeHealthRule(ServerWorld world) {
        return world.getGameRules().get(EXCHANGE_HEALTH_RULE).get();
    }
    public static boolean getExchangeEffectsRule(ServerWorld world) {
        return world.getGameRules().get(EXCHANGE_EFFECTS_RULE).get();
    }
    public static boolean getdisableMSGsRule(ServerWorld world) {
        return world.getGameRules().get(DISABLE_MSGS_RULE).get();
    }
    public static void registerGamerules() {

    }
}
