package net.phini.soulflow.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderLayer;
import net.phini.soulflow.Soulflow;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AlchemyTableScreen extends HandledScreen<AlchemyTableScreenHandler> {
    private static final Identifier GUI_TEXTURE =
            Identifier.of(Soulflow.MOD_ID, "textures/gui/alchemy_table/alchemy_table_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.of(Soulflow.MOD_ID, "textures/gui/alchemy_table/arrow_progress_soul.png");
    private static final Identifier TOTEM_TEXTURE =
            Identifier.of(Soulflow.MOD_ID, "textures/gui/alchemy_table/hollow_totem_outline.png");
    private static final Identifier SOUL_TEXTURE =
            Identifier.of(Soulflow.MOD_ID, "textures/gui/alchemy_table/soul_outline.png");
    public AlchemyTableScreen(AlchemyTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(RenderLayer::getGuiTextured, GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderLayer::getGuiTextured, TOTEM_TEXTURE, x+53, y+34, 0, 0, 16, 16, 16, 16);
        context.drawTexture(RenderLayer::getGuiTextured, SOUL_TEXTURE, x+71, y+34, 0, 0, 16, 16, 16, 16);
        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(
                    RenderLayer::getGuiTextured,
                    ARROW_TEXTURE,
                    x + 90,
                    y + 35,
                    0,
                    0,
                    handler.getScaledArrowProgress(),
                    16,
                    24,
                    16
            );
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}