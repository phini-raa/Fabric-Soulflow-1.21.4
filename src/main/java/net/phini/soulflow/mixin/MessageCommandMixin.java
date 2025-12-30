package net.phini.soulflow.mixin;

import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.phini.soulflow.gamerules.ModGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(MessageCommand.class)
public abstract class MessageCommandMixin {

    @Inject(
            method = "execute",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void blockPrivateMessages(
            ServerCommandSource source,
            Collection<ServerPlayerEntity> targets,
            SignedMessage message,
            CallbackInfo ci
    ) {
        if (source.getPlayer() != null) {
            ServerWorld world = source.getWorld();

            if (ModGameRules.getdisableMSGsRule(world)) {
                ServerPlayerEntity player = source.getPlayer();
                if (player != null) {
                    player.sendMessage(Text.translatable("commands.soulflow.disabledmsgs.message"), true);
                }
            }
        }
    }
}
