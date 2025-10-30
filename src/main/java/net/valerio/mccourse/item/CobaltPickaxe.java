package net.valerio.mccourse.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.valerio.mccourse.block.ModBlocks;

import java.util.Random;

public class CobaltPickaxe extends PickaxeItem {

    private static final Random RANDOM = new Random();

    public CobaltPickaxe(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {
        boolean result = super.mineBlock(stack, world, state, pos, entity);

        if (!world.isClientSide) {
            if (isOreBlock(state.getBlock())) {
                int bonusDrops = RANDOM.nextInt(6); // 0–5 drop extra
                for (int i = 0; i < bonusDrops; i++) {
                    Block.dropResources(state, world, pos, null, entity, stack);
                }
            }
        }

        return result;
    }

    private boolean isOreBlock(Block block) {
        return block == Blocks.COAL_ORE ||
                block == Blocks.IRON_ORE ||
                block == Blocks.GOLD_ORE ||
                block == Blocks.DIAMOND_ORE ||
                block == Blocks.LAPIS_ORE ||
                block == Blocks.REDSTONE_ORE ||
                block == Blocks.EMERALD_ORE ||
                block == Blocks.COPPER_ORE ||
                block == ModBlocks.COBALT_ORE.get(); // il tuo minerale personalizzato
    }
}





