package net.valerio.mccourse.item;

import net.valerio.mccourse.yoyo.entity.YoYoEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class YoYoItem extends Item {
    public YoYoItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            YoYoEntity yoyoEntity = new YoYoEntity(level, player);
            yoyoEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(yoyoEntity);

            // Consuma durability
            itemstack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
        }

        player.getCooldowns().addCooldown(this, 10); // Cooldown di 0.5 secondi

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}