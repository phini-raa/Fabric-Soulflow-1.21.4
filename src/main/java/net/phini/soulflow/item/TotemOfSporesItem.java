package net.phini.soulflow.item;

import com.mojang.authlib.GameProfile;
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

public class TotemOfSporesItem extends Item {

    public static final Identifier SPORES_KEY = Identifier.of("soulflow");

    public TotemOfSporesItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(
            ItemStack stack,
            PlayerEntity user,
            LivingEntity entity,
            Hand hand
    ) {
        // Server-only
        if (user.getWorld().isClient) return ActionResult.PASS;
        if (!(user instanceof ServerPlayerEntity serverUser)) return ActionResult.PASS;

        // Must click another player
        if (!(entity instanceof ServerPlayerEntity target)) {
            user.sendMessage(Text.translatable("item.soulflow.totem_of_spores.message.OtherMob"), true);
            return ActionResult.PASS;
        }

        // No self-targeting
        if (target == serverUser) {
            return ActionResult.FAIL;
        }

        // Cooldown check
        if (user.getItemCooldownManager().isCoolingDown(stack)) {
            return ActionResult.FAIL;
        }

        // Resolve final profile (respect impersonation)
        GameProfile profileToCopy = target.getGameProfile();
        Impersonator targetImpersonator = Impersonator.get(target);

        if (targetImpersonator.isImpersonating()) {
            GameProfile impersonated = targetImpersonator.getImpersonatedProfile();
            if (impersonated != null) {
                profileToCopy = impersonated;
            }
        }

        // Apply impersonation
        Impersonator.get(serverUser)
                .impersonate(SPORES_KEY, profileToCopy);

        // Particles
        ServerWorld serverWorld = serverUser.getServerWorld();
        serverWorld.spawnParticles(
                ParticleTypes.SOUL,
                user.getX(),
                user.getY() + 1.0,
                user.getZ(),
                40,
                0.4, 0.6, 0.4,
                0.02
        );

        // Consume item
        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
        }
        user.getWorld().playSound(null,user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_MOOSHROOM_CONVERT, SoundCategory.PLAYERS,1.0f,1.0f);
        // Cooldown (30 seconds)
        user.getItemCooldownManager().set(stack, 30 * 20);
        user.sendMessage(Text.translatable("item.soulflow.totem_of_spores.message.user"), true);
        target.sendMessage(Text.translatable("item.soulflow.totem_of_spores.message.targetplayer"), true);
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(
            ItemStack stack,
            TooltipContext context,
            List<Text> tooltip,
            TooltipType type
    ) {
        tooltip.add(Text.translatable("tooltip.soulflow.totem_of_spores.line1"));
        tooltip.add(Text.translatable("tooltip.soulflow.totem_of_spores.line2"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
