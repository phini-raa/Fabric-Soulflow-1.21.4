package net.phini.soulflow.item;

import net.minecraft.entity.LivingEntity;
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
import org.ladysnake.impersonate.Impersonator;

import java.util.List;

public class SunkenGrimoireItem extends Item {
    public static final Identifier IMPERSONATION_KEY = Identifier.of("soulflow");
    public static final Identifier DEFAULT_IMPERSONATION_KEY = Identifier.of("impersonate:command");
    public SunkenGrimoireItem(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getWorld().isClient) return ActionResult.PASS;

        if (!(entity instanceof net.minecraft.entity.mob.ZombieEntity ||
                entity instanceof net.minecraft.entity.mob.SkeletonEntity ||
                entity instanceof net.minecraft.entity.mob.WitherSkeletonEntity ||
                entity instanceof net.minecraft.entity.mob.ZombieVillagerEntity ||
                entity instanceof net.minecraft.entity.mob.ZombifiedPiglinEntity ||
                entity instanceof net.minecraft.entity.mob.ZoglinEntity ||
                entity instanceof net.minecraft.entity.boss.WitherEntity ||
                entity instanceof net.minecraft.entity.mob.SkeletonHorseEntity ||
                entity instanceof net.minecraft.entity.mob.ZombieHorseEntity
                )) {
            user.sendMessage(Text.translatable("item.soulflow.sunken_grimoire.message.OtherMob"), true);
            return ActionResult.PASS;
        }

        // Cooldown check
        if (user.getItemCooldownManager().isCoolingDown(stack)) {
            return ActionResult.FAIL;
        }

        Impersonator impersonator = Impersonator.get(user);

        // If the player is not impersonating anyone, do nothing
        if (!impersonator.isImpersonating()) {
            user.sendMessage(Text.translatable("item.soulflow.sunken_grimoire.message.NotImpersonating"), true);
            return ActionResult.PASS;
        }

        ServerWorld world = (ServerWorld) user.getWorld();

        // Kill Mob
        entity.kill(world);

        // TP Player to that mobs position
        if (user instanceof ServerPlayerEntity serverUser) {
            double targetX = entity.getX();
            double targetY = entity.getY();
            double targetZ = entity.getZ();
            float targetYaw = entity.getYaw();
            float targetPitch = entity.getPitch();

            serverUser.networkHandler.requestTeleport(targetX, targetY, targetZ, targetYaw, targetPitch);
        }


        // Stop impersonation
        Impersonator.get(user).stopImpersonation(IMPERSONATION_KEY);

        // Stop impersonation
        Impersonator.get(user).stopImpersonation(DEFAULT_IMPERSONATION_KEY);

        // Particles for feedback

        world.spawnParticles(
                ParticleTypes.SOUL,
                user.getX(),
                user.getY() + 1.0,
                user.getZ(),
                25,
                0.3, 0.5, 0.3,
                0.1
        );

        // Optional: consume item
        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        user.getWorld().playSound(null,user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_CONDUIT_ACTIVATE, SoundCategory.PLAYERS,1.0f,1.0f);

        user.sendMessage(Text.translatable("item.soulflow.sunken_grimoire.message.user"), true);
        // Cooldown
        user.getItemCooldownManager().set(stack, 30*20);

        return ActionResult.SUCCESS;
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.soulflow.sunken_grimoire.line1"));
        tooltip.add(Text.translatable("tooltip.soulflow.sunken_grimoire.line2"));

        super.appendTooltip(stack, context, tooltip, type);
    }
}
