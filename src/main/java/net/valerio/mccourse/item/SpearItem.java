package net.valerio.mccourse.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class SpearItem extends Item {
    public SpearItem(Properties props) {
        super(props.durability(0));
    }

    // Called when player right-clicks
    @Override
    public InteractionResult<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack spearStack = player.getItemInHand(hand);

        // Play throw sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);

        if (!level.isClientSide) {
            // Create spear projectile
            SpearEntity spearEntity = new SpearEntity(level, player);
            spearEntity.setItem(spearStack);
            spearEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 1.0F);
            level.addFreshEntity(spearEntity);

            // Consume spear if not in creative
            if (!player.getAbilities().instabuild)
                spearStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(spearStack, level.isClientSide());
    }
}
