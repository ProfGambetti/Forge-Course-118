package net.valerio.mccourse.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class CobaltSword extends SwordItem {

    public CobaltSword(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Applica l’effetto di “freeze” (rallentamento massimo + niente salto)
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false)); // 1 secondo = 20 tick
        target.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, 128, false, false));

        // (facoltativo) Particelle o suono da effetto freeze
        // target.level().playSound(null, target.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        target.setDeltaMovement(0, 0, 0);
        target.hurtMarked = true;
        return super.hurtEnemy(stack, target, attacker);
    }
}
