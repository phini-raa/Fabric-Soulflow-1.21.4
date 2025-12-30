package net.phini.soulflow.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.text.Text;
import org.ladysnake.impersonate.Impersonator;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class SoultransformCommand {

    public static final Identifier IMPERSONATION_KEY =
            Identifier.of("soulflow");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("soultransform")
                .requires(source -> source.hasPermissionLevel(2))


                        .requires(source -> source.hasPermissionLevel(2))

                        .then(literal("into")
                                .then(argument("disguise", GameProfileArgumentType.gameProfile())

                                        // Self
                                        .executes(ctx -> startImpersonation(
                                                ctx.getSource(),
                                                GameProfileArgumentType.getProfileArgument(ctx, "disguise"),
                                                Collections.singleton(ctx.getSource().getPlayer())
                                        ))

                                        .then(argument("targets", EntityArgumentType.players())
                                                .requires(source -> source.hasPermissionLevel(2))

                                                // Targets
                                                .executes(ctx -> startImpersonation(
                                                        ctx.getSource(),
                                                        GameProfileArgumentType.getProfileArgument(ctx, "disguise"),
                                                        EntityArgumentType.getPlayers(ctx, "targets")
                                                ))
                                        )
                                )
                        )

                        .then(literal("clear")

                                // Self
                                .executes(ctx -> stopImpersonation(
                                        ctx.getSource(),
                                        Collections.singleton(ctx.getSource().getPlayer())
                                ))

                                .then(argument("targets", EntityArgumentType.players())
                                        .requires(source -> source.hasPermissionLevel(2))

                                        // Targets
                                        .executes(ctx -> stopImpersonation(
                                                ctx.getSource(),
                                                EntityArgumentType.getPlayers(ctx, "targets")
                                        ))
                                )
                        )
        );
    }

    private static int startImpersonation(
            ServerCommandSource source,
            Collection<GameProfile> profiles,
            Collection<ServerPlayerEntity> players
    ) throws CommandSyntaxException {

        Iterator<GameProfile> it = profiles.iterator();
        GameProfile disguise = it.next();
        if (it.hasNext()) {
            throw EntityArgumentType.TOO_MANY_PLAYERS_EXCEPTION.create();
        }

        int count = 0;
        stopImpersonation(source, players);

        for (ServerPlayerEntity player : players) {
            Impersonator.get(player).impersonate(IMPERSONATION_KEY, disguise);
            sendFeedback(source, player, disguise, "start");
            count++;
        }
        return count;
    }

    private static int stopImpersonation(
            ServerCommandSource source,
            Collection<ServerPlayerEntity> players
    ) {
        int count = 0;
        for (ServerPlayerEntity player : players) {
            GameProfile impersonated =
                    Impersonator.get(player).stopImpersonation(IMPERSONATION_KEY);

            if (impersonated != null) {
                sendFeedback(source, player, impersonated, "clear");
                count++;
            }
        }
        return count;
    }

    private static void sendFeedback(
            ServerCommandSource source,
            ServerPlayerEntity player,
            GameProfile impersonated,
            String action
    ) {
        if (action.equals("start")) {
            if (source.getEntity() == player) {
                // Self
                source.sendMessage(
                        Text.translatable(
                                "command.soulflow.soultransform.start.self",
                                impersonated.getName()
                        )
                );
            } else {
                // Other
                source.sendMessage(
                        Text.translatable(
                                "command.soulflow.soultransform.start.other",
                                player.getName().getString(),
                                impersonated.getName()
                        )
                );
            }
        } else if (action.equals("clear")) {
            if (source.getEntity() == player) {
                // Self
                source.sendMessage(
                        Text.translatable(
                                "command.soulflow.soultransform.clear.self",
                                impersonated.getName()
                        )
                );
            } else {
                // Other
                source.sendMessage(
                        Text.translatable(
                                "command.soulflow.soultransform.clear.other",
                                player.getName().getString()
                        )
                );
            }
        }
    }

}
