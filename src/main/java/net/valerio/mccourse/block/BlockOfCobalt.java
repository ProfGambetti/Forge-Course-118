package net.valerio.mccourse.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import java.util.Collections;
import java.util.List;

public class BlockOfCobalt extends Block {

    public BlockOfCobalt(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter world, BlockPos pos) {
        ItemStack stack = player.getMainHandItem();

        if (stack.getItem() instanceof TieredItem tieredItem) {
            int level = tieredItem.getTier().getLevel();
            if (level == 3) { // diamante
                return super.getDestroyProgress(state, player, world, pos) * 1.4f;
            } else if (level == 4) { // netherite
                return super.getDestroyProgress(state, player, world, pos) * 1.6f;
            }
        }
        return super.getDestroyProgress(state, player, world, pos);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        ItemStack stack = player.getMainHandItem();
        return stack.getItem() instanceof TieredItem tieredItem && tieredItem.getTier().getLevel() >= 3;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        // 🔥 SEMPLICE: droppa sempre se stesso
        return Collections.singletonList(new ItemStack(this));
    }
}


