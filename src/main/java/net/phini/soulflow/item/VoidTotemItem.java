package net.phini.soulflow.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.phini.soulflow.gamerules.ModGameRules;
import org.ladysnake.impersonate.Impersonator;

import java.util.List;
import java.util.stream.Collectors;

public class VoidTotemItem extends Item {
    public static final Identifier IMPERSONATION_KEY = Identifier.of("soulflow");

    public VoidTotemItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) return ActionResult.PASS;

        ServerPlayerEntity serverUser = (ServerPlayerEntity) user;

        // Filter target players in the same dimension and within 75 blocks!
        List<ServerPlayerEntity> possibleTargets = serverUser.getServer().getPlayerManager()
                .getPlayerList().stream()
                .filter(p ->
                        p != serverUser &&
                                p.getWorld().getRegistryKey() == serverUser.getWorld().getRegistryKey() &&
                                p.squaredDistanceTo(serverUser) <= 75 * 75
                )
                .collect(Collectors.toList());



        if (possibleTargets.isEmpty()) {
            user.sendMessage(Text.translatable("item.soulflow.void_totem.message.NooneNearby"), true);
            return ActionResult.FAIL;
        };

        // Pick a random target
        ServerPlayerEntity targetPlayer = possibleTargets.get(world.random.nextInt(possibleTargets.size()));

        Impersonator userImp = Impersonator.get(user);
        Impersonator targetImp = Impersonator.get(targetPlayer);

        // Capture current profiles
        var userCurrent = userImp.isImpersonating() ? userImp.getImpersonatedProfile() : null;
        var targetCurrent = targetImp.isImpersonating() ? targetImp.getImpersonatedProfile() : null;

        ItemStack holdingstack = user.getStackInHand(Hand.MAIN_HAND);
        if (holdingstack.getItem() == ModItems.VOID_TOTEM) {
            if (!user.getAbilities().creativeMode) {
                holdingstack.decrement(1);
            }

        }

        // Unsit Players
        Entity vehicle = user.getVehicle();
        if (vehicle != null) {
            vehicle.stopRiding();
        }
        Entity vehicleTarget = targetPlayer.getVehicle();
        if (vehicleTarget != null) {
            vehicleTarget.stopRiding();
        }

        // Swap impersonations
        if (userCurrent != null && targetCurrent == null) {
            userImp.impersonate(IMPERSONATION_KEY, targetPlayer.getGameProfile());
            targetImp.impersonate(IMPERSONATION_KEY, userCurrent);
        } else if (userCurrent == null && targetCurrent != null) {
            userImp.impersonate(IMPERSONATION_KEY, targetCurrent);
            targetImp.impersonate(IMPERSONATION_KEY, user.getGameProfile());
        } else if (userCurrent != null && targetCurrent != null) {
            userImp.impersonate(IMPERSONATION_KEY, targetCurrent);
            targetImp.impersonate(IMPERSONATION_KEY, userCurrent);
        } else {
            userImp.impersonate(IMPERSONATION_KEY, targetPlayer.getGameProfile());
            targetImp.impersonate(IMPERSONATION_KEY, user.getGameProfile());
        }

        // Swap positions
        double userX = serverUser.getX(), userY = serverUser.getY(), userZ = serverUser.getZ();
        float userYaw = serverUser.getYaw(), userPitch = serverUser.getPitch();

        double targetX = targetPlayer.getX(), targetY = targetPlayer.getY(), targetZ = targetPlayer.getZ();
        float targetYaw = targetPlayer.getYaw(), targetPitch = targetPlayer.getPitch();

        // Spawn particles
        ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SOUL, userX, userY, userZ, 20, 0.0, 0.5, 0.0, 0.02);
        ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SOUL, targetX, targetY, targetZ, 20, 0.2, 0.5, 0.0, 0.02);

        serverUser.networkHandler.requestTeleport(targetX, targetY, targetZ, targetYaw, targetPitch);
        targetPlayer.networkHandler.requestTeleport(userX, userY, userZ, userYaw, userPitch);

        // Swap inventories


        ServerWorld GameServerWorld = (ServerWorld) user.getWorld();


        if (ModGameRules.getExchangeInventoriesRule(GameServerWorld)) {
            swapInventory(serverUser, targetPlayer);
            swapArmor(serverUser, targetPlayer);
            swapOffhand(serverUser, targetPlayer);
        }

        if (ModGameRules.getExchangeEnderchestsRule(GameServerWorld)) {
            swapEnderChest(serverUser, targetPlayer);
        }
        if (ModGameRules.getExchangeHealthRule(GameServerWorld)) {
            swapHealth(serverUser, targetPlayer);
        }
        if (ModGameRules.getExchangeEffectsRule(GameServerWorld)) {
            swapEffects(serverUser, targetPlayer);
        }
        user.getWorld().playSound(null,user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS,1.0f,1.0f);

        user.getItemCooldownManager().set(user.getStackInHand(hand), 30*20);

        targetPlayer.sendMessage(Text.translatable("item.soulflow.void_totem.message.targetplayer"), true);
        user.sendMessage(Text.translatable("item.soulflow.void_totem.message.user"), true);

        return ActionResult.SUCCESS;
    }

    private void swapInventory(ServerPlayerEntity a, ServerPlayerEntity b) {
        DefaultedList<ItemStack> invA = a.getInventory().main;
        DefaultedList<ItemStack> invB = b.getInventory().main;
        for (int i = 0; i < invA.size(); i++) {
            ItemStack temp = invA.get(i);
            invA.set(i, invB.get(i));
            invB.set(i, temp);
        }
    }

    private void swapArmor(ServerPlayerEntity a, ServerPlayerEntity b) {
        for (int i = 0; i < 4; i++) {
            ItemStack temp = a.getInventory().armor.get(i);
            a.getInventory().armor.set(i, b.getInventory().armor.get(i));
            b.getInventory().armor.set(i, temp);
        }
    }

    private void swapOffhand(ServerPlayerEntity a, ServerPlayerEntity b) {
        ItemStack temp = a.getInventory().offHand.get(0);
        a.getInventory().offHand.set(0, b.getInventory().offHand.get(0));
        b.getInventory().offHand.set(0, temp);
    }

    private void swapEnderChest(ServerPlayerEntity a, ServerPlayerEntity b) {
        var enderA = a.getEnderChestInventory();
        var enderB = b.getEnderChestInventory();
        for (int i = 0; i < enderA.size(); i++) {
            ItemStack temp = enderA.getStack(i);
            enderA.setStack(i, enderB.getStack(i));
            enderB.setStack(i, temp);
        }
        enderA.markDirty();
        enderB.markDirty();
    }

    private void swapHealth(ServerPlayerEntity user, ServerPlayerEntity target) {
        ServerWorld world = (ServerWorld) user.getWorld();
        if (ModGameRules.getExchangeHealthRule(world)) {
            float userHealth = user.getHealth();
            float targetHealth = target.getHealth();
            float userAbsorption = user.getAbsorptionAmount();
            float targetAbsorption = target.getAbsorptionAmount();

            user.setHealth(Math.min(targetHealth, user.getMaxHealth()));
            user.setAbsorptionAmount(targetAbsorption);

            target.setHealth(Math.min(userHealth, target.getMaxHealth()));
            target.setAbsorptionAmount(userAbsorption);
        }

    }

    private void swapEffects(ServerPlayerEntity user, ServerPlayerEntity target) {
        ServerWorld world = (ServerWorld) user.getWorld();
        if (ModGameRules.getExchangeEffectsRule(world)) {
            var userEffects = user.getStatusEffects().stream().toList();
            var targetEffects = target.getStatusEffects().stream().toList();

            user.clearStatusEffects();
            target.clearStatusEffects();

            for (var effect : targetEffects) {
                user.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(effect));
            }
            for (var effect : userEffects) {
                target.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(effect));
            }
        }


    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.soulflow.void_totem.line1"));
        tooltip.add(Text.translatable("tooltip.soulflow.void_totem.line2"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
