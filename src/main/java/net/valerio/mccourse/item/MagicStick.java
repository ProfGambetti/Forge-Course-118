package net.valerio.mccourse.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class MagicStick extends Item {

    public MagicStick(Properties props) {
        super(props.durability(0));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.FAIL;

        System.out.println("Sto usando il metodo useOn");

        if (context.getLevel().isClientSide) {
            player.displayClientMessage(new TextComponent("Hai usato il bastone magico"), false);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        System.out.println("Sto usando il metodo use - SPARO PROIETTILE");
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            // Crea un proiettile (Snowball) e imposta la sua posizione e velocità
            Snowball projectile = new Snowball(world, player) {
                @Override
                protected void onHitEntity(net.minecraft.world.phys.EntityHitResult result) {
                    if (!this.level.isClientSide) {
                        if (result.getEntity() instanceof LivingEntity target) {
                            // Trasforma il mob colpito in un blocco d'oro
                            BlockPos pos = target.blockPosition();

                            // Rimuove l'entità colpita
                            target.discard();

                            // Sostituisce con un blocco d'oro
                            world.setBlock(pos, Blocks.GOLD_BLOCK.defaultBlockState(), 3);

                            // Messaggio al giocatore
                            player.displayClientMessage(new TextComponent("✨ Hai trasformato un nemico in oro a distanza!"), true);
                        }
                    }
                    // Rimuovi il proiettile dopo l'impatto
                    this.discard();
                }

                @Override
                protected void onHit(net.minecraft.world.phys.HitResult result) {
                    super.onHit(result);
                    // Se colpisce un blocco o altro, rimuovi comunque il proiettile
                    if (!this.level.isClientSide) {
                        this.discard();
                    }
                }
            };

            // Calcola la direzione in cui sparare il proiettile
            Vec3 lookVec = player.getLookAngle();
            projectile.setPos(
                    player.getX(),
                    player.getEyeY() - 0.1,
                    player.getZ()
            );
            projectile.shoot(lookVec.x, lookVec.y, lookVec.z, 1.5F, 1.0F);

            // Aggiungi il proiettile al mondo
            world.addFreshEntity(projectile);

            // Aggiungi un cooldown per evitare spam
            player.getCooldowns().addCooldown(this, 20); // 1 secondo di cooldown
        }

        if (world.isClientSide) {
            player.displayClientMessage(new TextComponent("Hai sparato un incantesimo magico!"), false);
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level.isClientSide) {
            Level level = attacker.level;
            BlockPos pos = target.blockPosition();

            // Rimuove l'entità colpita
            target.discard();

            // Sostituisce con un blocco d'oro
            level.setBlock(pos, Blocks.GOLD_BLOCK.defaultBlockState(), 3);

            // Messaggio al giocatore
            if (attacker instanceof Player player) {
                player.displayClientMessage(new TextComponent("✨ Hai trasformato un nemico in oro!"), true);
            }
        }

        return true;
    }
}