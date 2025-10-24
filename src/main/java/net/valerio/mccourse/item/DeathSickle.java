package net.valerio.mccourse.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Explosion;
import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.world.damagesource.DamageSource;


public class DeathSickle extends Item {

    public DeathSickle(Properties props) {
        super(props.durability(0));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        if (player == null) return InteractionResult.FAIL;

        if (context.getLevel().isClientSide) {
            player.displayClientMessage(new TextComponent("Hai usato la falce della morte"), false);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) {
            player.displayClientMessage(new TextComponent("Hai usato la falce della morte!"), false);
        }
        return InteractionResultHolder.success(stack);

    }

    //In questo metodo, che viene richiamato quando colpisco un'entità vivente con la falce, faccio levitare l'entità
    //per alcuni secondi e successivamente creo un'esplosione alle sue coordinate: questa esplosione
    //non danneggia blocchi, player o altre entità vicine ma serve solo come effetto visivo. L'entità
    //in realtà scompare, facendo sembrare che sia morta dall'esplosione
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level.isClientSide) {
            Level level = attacker.level;
            BlockPos pos = target.blockPosition();

            int durata = 200; // 10 secondi
            int livello = 1;  // Levitation II

            MobEffectInstance lev = new MobEffectInstance(MobEffects.LEVITATION, durata, livello);
            boolean applied = target.addEffect(lev);

            if (attacker instanceof Player player) {
                player.displayClientMessage(new TextComponent("✨ Hai fatto levitare ed esplodere il nemico ✨"), true);
            }
            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    level.explode(
                            target,                    // entità che causa l’esplosione
                            target.getX(),             // coordinata X
                            target.getY(),             // coordinata Y
                            target.getZ(),             // coordinata Z
                            6.0F,                      // potenza dell’esplosione (4.0 = TNT)
                            Explosion.BlockInteraction.NONE        // tipo di esplosione (TNT, NONE, DESTROY)
                    );
                    target.hurt(DamageSource.explosion(attacker), Float.MAX_VALUE);
                }
            }, 3200);//Timer dopo il quale eseguire l'esplosione(3200 = 3,20 secondi)
        }
        return true;
    }
}
