package voidedmirror.FancySporeBlossom.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import voidedmirror.FancySporeBlossom.FabricFancySporeBlossom;

public class FabricFancySporeBlossomBlockEntity extends AbstractFancySporeBlossomBlockEntity {
    public FabricFancySporeBlossomBlockEntity(BlockPos pos, BlockState state) {
        super(FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK_ENTITY, pos, state);
    }
}
