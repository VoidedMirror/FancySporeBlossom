package voidedmirror.FancySporeBlossom.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import voidedmirror.FancySporeBlossom.ForgeFancySporeBlossom;

public class ForgeFancySporeBlossomBlockEntity extends AbstractFancySporeBlossomBlockEntity {
    public ForgeFancySporeBlossomBlockEntity(BlockPos pos, BlockState state) {
        super(ForgeFancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK_ENTITY.get(), pos, state);
    }
}
