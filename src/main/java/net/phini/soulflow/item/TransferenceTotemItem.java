package net.phini.soulflow.item;

import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import org.ladysnake.impersonate.Impersonator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.item.tooltip.TooltipType;
import net.phini.soulflow.gamerules.ModGameRules;

import com.mojang.authlib.GameProfile;

import java.util.List;
import java.util.UUID;

public class TransferenceTotemItem extends Item {
    public static final Identifier IMPERSONATION_KEY = Identifier.of("soulflow");

    private static final UUID NOTCH_UUID = UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5");
    private static final GameProfile NOTCH_PROFILE = new GameProfile(NOTCH_UUID, "Notch");

    public TransferenceTotemItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getWorld().isClient) return ActionResult.PASS;

        if (!(entity instanceof ServerPlayerEntity targetPlayer)) {
            if (user != null) {
                user.sendMessage(Text.translatable("item.soulflow.totem_of_transference.message.OtherMob"), true);
            }
            return ActionResult.PASS;
        };

        if (user.getItemCooldownManager().isCoolingDown(stack)) return ActionResult.FAIL;

       // Unsit Players
        Entity vehicle = user.getVehicle();
        if (vehicle != null) {
            vehicle.stopRiding();
        }
        Entity vehicleTarget = targetPlayer.getVehicle();
        if (vehicleTarget != null) {
            vehicleTarget.stopRiding();
        }

        swapImpersonations(user, targetPlayer);
        swapPositions(user, targetPlayer);
        spawnParticles(user, targetPlayer);
        swapInventories((ServerPlayerEntity) user, targetPlayer);
        swapEnderChest((ServerPlayerEntity) user, targetPlayer);
        swapHealth((ServerPlayerEntity) user, targetPlayer);
        swapEffects((ServerPlayerEntity) user, targetPlayer);
        user.getWorld().playSound(null,user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS,1.0f,1.0f);
        user.getWorld().playSound(null,user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WITCH_CELEBRATE, SoundCategory.PLAYERS,1.0f,1.0f);
        user.getItemCooldownManager().set(stack, 30 * 20);

        targetPlayer.sendMessage(Text.translatable("item.soulflow.totem_of_transference.message.targetplayer"), true);
        user.sendMessage(Text.translatable("item.soulflow.totem_of_transference.message.targetplayer"), true);
        return ActionResult.SUCCESS;
    }

    private void swapImpersonations(PlayerEntity user, ServerPlayerEntity target) {
        Impersonator userImp = Impersonator.get(user);
        Impersonator targetImp = Impersonator.get(target);

        GameProfile userCurrent = userImp.isImpersonating() ? userImp.getImpersonatedProfile() : null;
        GameProfile targetCurrent = targetImp.isImpersonating() ? targetImp.getImpersonatedProfile() : null;

        if (userCurrent != null && targetCurrent == null) {
            userImp.impersonate(IMPERSONATION_KEY, target.getGameProfile());
            targetImp.impersonate(IMPERSONATION_KEY, userCurrent);
        } else if (userCurrent == null && targetCurrent != null) {
            userImp.impersonate(IMPERSONATION_KEY, targetCurrent);
            targetImp.impersonate(IMPERSONATION_KEY, user.getGameProfile());
        } else if (userCurrent != null && targetCurrent != null) {
            userImp.impersonate(IMPERSONATION_KEY, targetCurrent);
            targetImp.impersonate(IMPERSONATION_KEY, userCurrent);
        } else {
            userImp.impersonate(IMPERSONATION_KEY, target.getGameProfile());
            targetImp.impersonate(IMPERSONATION_KEY, user.getGameProfile());
        }
    }

    private void swapPositions(PlayerEntity user, ServerPlayerEntity target) {
        ServerPlayerEntity serverUser = (ServerPlayerEntity) user;

        double userX = serverUser.getX(), userY = serverUser.getY(), userZ = serverUser.getZ();
        float userYaw = serverUser.getYaw(), userPitch = serverUser.getPitch();

        double targetX = target.getX(), targetY = target.getY(), targetZ = target.getZ();
        float targetYaw = target.getYaw(), targetPitch = target.getPitch();

        serverUser.networkHandler.requestTeleport(targetX, targetY, targetZ, targetYaw, targetPitch);
        target.networkHandler.requestTeleport(userX, userY, userZ, userYaw, userPitch);
    }

    private void spawnParticles(PlayerEntity user, ServerPlayerEntity target) {
        ServerWorld world = (ServerWorld) user.getWorld();

        world.spawnParticles(ParticleTypes.SOUL, user.getX(), user.getY(), user.getZ(), 20, 0.0, 0.5, 0.0, 0.2);
        world.spawnParticles(ParticleTypes.SOUL, target.getX(), target.getY(), target.getZ(), 20, 0.2, 0.5, 0.0, 0.2);

        ItemStack holding = user.getStackInHand(Hand.MAIN_HAND);
        if (holding.getItem() == ModItems.TRANSFERENCE_TOTEM && !user.getAbilities().creativeMode) {
            holding.decrement(1);
        }
    }

    private void swapInventories(ServerPlayerEntity user, ServerPlayerEntity target) {
        ServerWorld world = (ServerWorld) user.getWorld();
        if (ModGameRules.getExchangeInventoriesRule(world)) {
            swapList(user.getInventory().main, target.getInventory().main);
            swapList(user.getInventory().armor, target.getInventory().armor);
            swapOffhand(user, target);
        }

    }

    private void swapList(DefaultedList<ItemStack> list1, DefaultedList<ItemStack> list2) {
        for (int i = 0; i < list1.size(); i++) {
            ItemStack temp = list1.get(i);
            list1.set(i, list2.get(i));
            list2.set(i, temp);
        }
    }

    private void swapOffhand(ServerPlayerEntity user, ServerPlayerEntity target) {
        ItemStack temp = user.getInventory().offHand.get(0);
        user.getInventory().offHand.set(0, target.getInventory().offHand.get(0));
        target.getInventory().offHand.set(0, temp);
    }

    private void swapEnderChest(ServerPlayerEntity user, ServerPlayerEntity target) {
        ServerWorld world = (ServerWorld) user.getWorld();
        if (ModGameRules.getExchangeEnderchestsRule(world)) {
            var userEnder = user.getEnderChestInventory();
            var targetEnder = target.getEnderChestInventory();
            for (int i = 0; i < userEnder.size(); i++) {
                ItemStack temp = userEnder.getStack(i);
                userEnder.setStack(i, targetEnder.getStack(i));
                targetEnder.setStack(i, temp);
            }
            userEnder.markDirty();
            targetEnder.markDirty();
        }
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
        tooltip.add(Text.translatable("tooltip.soulflow.totem_of_transference.line1"));
        tooltip.add(Text.translatable("tooltip.soulflow.totem_of_transference.line2"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
