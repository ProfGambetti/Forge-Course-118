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

    // Metodo per il click destro su un'entità (FREEZE COMPLETO)
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.level.isClientSide) {
            // APPLICA EFFETTO FREEZE COMPLETO
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 255)); // Livello 255 = completamente immobile
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 255)); // Non può scavare
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0)); // Cecità per effetto visivo
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0)); // Illuminato per vedere meglio

            // BLOCCA COMPLETAMENTE IL MOVIMENTO
            target.setDeltaMovement(0, 0, 0);
            target.setNoGravity(true); // Sospeso in aria come congelato

            // IMPEDISCE ALL'ENTITÀ DI AGIRE
            target.setSilent(true); // Silenziosa

            // PARTICOLARE EFFETTO VISIVO - ghiaccio intorno
            target.setTicksFrozen(200); // Effetto visivo di congelamento

            // Messaggio al giocatore
            player.displayClientMessage(new net.minecraft.network.chat.TextComponent("❄️ Hai congelato completamente il mob!"), true);

            // Danno magico leggero per feedback
            target.hurt(net.minecraft.world.damagesource.DamageSource.MAGIC, 1.0F);

            // PROGRAMMA LO SCONGELAMENTO AUTOMATICO dopo 5 secondi
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (target.isAlive()) {
                                unfreezeEntity(target);
                            }
                        }
                    },
                    10000 // 10 secondi
            );
        }

        return InteractionResult.sidedSuccess(player.level.isClientSide);
    }

    // Metodo per scongelare l'entità
    private void unfreezeEntity(LivingEntity target) {
        if (!target.level.isClientSide) {
            // RIMUOVI TUTTI GLI EFFETTI DI FREEZE
            target.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            target.removeEffect(MobEffects.DIG_SLOWDOWN);
            target.removeEffect(MobEffects.BLINDNESS);
            target.removeEffect(MobEffects.GLOWING);

            // RIPRISTINA IL MOVIMENTO
            target.setNoGravity(false);
            target.setSilent(false);

            // SOLO DANNO LEGGERO AL RISCALDAMENTO (SENZA FUOCO)
            target.hurt(net.minecraft.world.damagesource.DamageSource.MAGIC, 2.0F);
        }
    }

    // Metodo per l'attacco (click sinistro) - SOLO FUOCO QUANDO ATTACCHI
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level.isClientSide) {
            // Se il bersaglio è congelato, rompi il ghiaccio con più danni
            boolean wasFrozen = target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) &&
                    target.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() == 255;

            if (wasFrozen) {
                // DANNI EXTRA SE ERA CONGELATO
                target.hurt(net.minecraft.world.damagesource.DamageSource.mobAttack(attacker), 12.0F);
                unfreezeEntity(target);
                if (attacker instanceof Player player) {
                    player.displayClientMessage(new net.minecraft.network.chat.TextComponent("💥 Hai frantumato il mob congelato!"), true);
                }
            } else {
                // ATTACCO NORMALE CON FUOCO
                target.hurt(net.minecraft.world.damagesource.DamageSource.mobAttack(attacker), 6.0F);
                target.setSecondsOnFire(5); // 🔥 SOLO QUI APPLICA IL FUOCO

                if (attacker instanceof Player player) {
                    player.displayClientMessage(new net.minecraft.network.chat.TextComponent("🔥 Hai attaccato e infuocato il nemico!"), true);
                }
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
            player.displayClientMessage(new net.minecraft.network.chat.TextComponent("Spada Magica - Click destro: Congela | Click sinistro: Attacca con fuoco"), true);
        }

        return InteractionResultHolder.success(stack);
    }
}