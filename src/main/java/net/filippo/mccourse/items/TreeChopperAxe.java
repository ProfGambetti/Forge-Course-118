package net.filippo.mccourse.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class TreeChopperAxe extends AxeItem {

    public TreeChopperAxe() {
        super(Tiers.DIAMOND, 8.0f, -3.1f, new Properties().tab(CreativeModeTab.TAB_TOOLS));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!world.isClientSide() && state.getBlock() instanceof RotatedPillarBlock) {
            chopTree(world, pos);
        }
        return super.mineBlock(stack, world, state, pos, entity);
    }

    private void chopTree(Level world, BlockPos pos) {
        Queue<BlockPos> toCheck = new LinkedList<>();
        Set<BlockPos> chopped = new HashSet<>();
        toCheck.add(pos);

        while (!toCheck.isEmpty()) {
            BlockPos current = toCheck.poll();
            BlockState state = world.getBlockState(current);
            Block block = state.getBlock();

            if (block instanceof RotatedPillarBlock && !chopped.contains(current)) {
                world.destroyBlock(current, true); // true = drop item
                chopped.add(current);

                // blocchi vicini sopra e lateralmente
                toCheck.add(current.above());
                toCheck.add(current.north());
                toCheck.add(current.south());
                toCheck.add(current.east());
                toCheck.add(current.west());
            }
        }
    }
}