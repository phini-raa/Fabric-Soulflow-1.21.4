package net.phini.soulflow;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.phini.soulflow.block.entity.ModBlockEntities;
import net.phini.soulflow.block.entity.renderer.AlchemyTableBlockEntityRenderer;
import net.phini.soulflow.screen.ModScreenHandlers;
import net.phini.soulflow.screen.custom.AlchemyTableScreen;

public class SoulflowClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register the client-side screen for the Alchemy Table
        HandledScreens.register(
                ModScreenHandlers.ALCHEMY_TABLE_SCREEN_HANDLER,
                AlchemyTableScreen::new
        );

        BlockEntityRendererFactories.register(ModBlockEntities.ALCHEMY_TABLE_BE, AlchemyTableBlockEntityRenderer::new);
    }
}
