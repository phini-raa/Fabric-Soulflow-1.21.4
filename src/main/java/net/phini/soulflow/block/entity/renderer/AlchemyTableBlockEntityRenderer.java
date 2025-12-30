package net.phini.soulflow.block.entity.renderer;

import net.minecraft.item.ModelTransformationMode;
import net.phini.soulflow.block.entity.custom.AlchemyTableBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class AlchemyTableBlockEntityRenderer implements BlockEntityRenderer<AlchemyTableBlockEntity> {
    public AlchemyTableBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(AlchemyTableBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getStack(3);

        matrices.push();
        matrices.translate(0.5f, 1.5f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getRenderingRotation()));

        itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        BlockPos lightPos = pos.up();
        int bLight = world.getLightLevel(LightType.BLOCK, lightPos);
        int sLight = world.getLightLevel(LightType.SKY, lightPos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}