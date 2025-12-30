package net.phini.soulflow.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.phini.soulflow.Soulflow;
import net.phini.soulflow.screen.custom.AlchemyTableScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {

    public static final ScreenHandlerType<AlchemyTableScreenHandler> ALCHEMY_TABLE_SCREEN_HANDLER =
            Registry.register(
                    Registries.SCREEN_HANDLER,
                    Identifier.of(Soulflow.MOD_ID, "alchemy_table_screen_handler"),
                    new ExtendedScreenHandlerType<>(
                            AlchemyTableScreenHandler::new,
                            BlockPos.PACKET_CODEC.cast()
                    )
            );

    public static void registerScreenHandlers() {
        Soulflow.LOGGER.info("Registering Screen Handlers for " + Soulflow.MOD_ID);
    }
}