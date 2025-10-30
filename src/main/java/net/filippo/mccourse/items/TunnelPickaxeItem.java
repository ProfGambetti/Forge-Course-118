package net.filippo.mccourse.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TunnelPickaxeItem extends PickaxeItem {

    public TunnelPickaxeItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            mineTunnel(level, pos, player);
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }

    private void mineTunnel(Level level, BlockPos startPos, Player player) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(startPos.getX(), startPos.getY(), startPos.getZ());
        Direction dir = player.getDirection();
        boolean foundCavity = false;

        int maxLength = 200;
        int depth = 0;

        while (!foundCavity && depth < maxLength) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    BlockPos targetPos;

                    // Decidi il piano 3x3 in base alla direzione
                    switch (dir) {
                        case NORTH:
                        case SOUTH:
                            // piano X-Y, movimento su Z
                            targetPos = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ());
                            break;

                        case EAST:
                        case WEST:
                            // piano Z-Y, movimento su X
                            targetPos = new BlockPos(pos.getX(), pos.getY() + j, pos.getZ() + i);
                            break;

                        case UP:
                        case DOWN:
                            // piano X-Z, movimento su Y
                            targetPos = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
                            break;

                        default:
                            targetPos = pos.immutable();
                            break;
                    }

                    BlockState state = level.getBlockState(targetPos);
                    if (state.getMaterial().isSolid() && state.getBlock() != Blocks.BEDROCK) {
                        level.destroyBlock(targetPos, true, player);
                    } else if (state.isAir()) {
                        foundCavity = true;
                    }
                }
            }

            pos.move(dir);
            depth++;
        }
    }
}