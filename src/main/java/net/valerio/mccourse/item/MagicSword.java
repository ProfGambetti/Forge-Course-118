package net.valerio.mccourse.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicSword extends Item {

    public MagicSword(Properties props) {
        super(props.durability(500));
    }

    // Metodo per il click destro su un'entità (freeze)
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.level.isClientSide) {
            // Applica effetti di freeze
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 255));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 255));
            target.setDeltaMovement(0, 0, 0);

            // Messaggio al giocatore
            player.displayClientMessage(new net.minecraft.network.chat.TextComponent("❄️ Hai congelato il mob!"), true);

            // Danno magico leggero
            target.hurt(net.minecraft.world.damagesource.DamageSource.MAGIC, 1.0F);
        }

        return InteractionResult.sidedSuccess(player.level.isClientSide);
    }

    // Metodo per l'attacco (click sinistro)
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level.isClientSide) {
            // Applica danni normali
            target.hurt(net.minecraft.world.damagesource.DamageSource.mobAttack(attacker), 6.0F);

            // Applica fuoco
            target.setSecondsOnFire(5);

            // Messaggio al giocatore
            if (attacker instanceof Player player) {
                player.displayClientMessage(new net.minecraft.network.chat.TextComponent("🔥 Hai attaccato e infuocato il nemico!"), true);
            }

            // Consuma durabilità
            stack.hurtAndBreak(1, attacker, (entity) -> {
                entity.broadcastBreakEvent(attacker.getUsedItemHand());
            });
        }

        return true;
    }

    // Metodo per il click destro nell'aria
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            player.displayClientMessage(new net.minecraft.network.chat.TextComponent("Spada Magica equipaggiata - Click destro per congelare, sinistro per attaccare con fuoco!"), true);
        }

        return InteractionResultHolder.success(stack);
    }
}