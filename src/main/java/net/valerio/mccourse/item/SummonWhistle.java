package net.valerio.mccourse.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;



// Creo una nuova classe figlia della classe Item e la chiamo MagicStick (bastone magico)
public class SummonWhistle extends Item {


    // Uso il costruttore della classe madre
    public SummonWhistle(Properties props) {
        super(props.durability(500)); // l'oggetto può venire usato 500 volte

    }

    // Posso sbizzarrirmi a fare l'override di tutti i metodi disponibili per gli Item

    // in questo caso riscivo il metodo useOn che viene chiamato quando il player
    // preme il tasto destro su un blocco mentre utilizza l'oggetto MagicStick

    @Override
    public InteractionResult useOn(UseOnContext context) {
        // creo un oggetto di tipo player che contenga l'interazione appena avvenuta
        // context contiene tutte le informazioni sull'interazione appena avvenuta
        // chi ha cliccato (Player), su quale blocco (BlockPos), in quale mondo (Level), su quale lato del blocco (Direction)
        // etc. etc.

        System.out.println("Sto usando il metodo useOn"); // Questo messaggio comparirà nella console della finestra RUN di Intellij

        // context contiene tutte le informazioni sull’interazione: chi ha cliccato (Player), su quale blocco (BlockPos)
        // il lato del blocco (Direction), nel mondo (Level), etc.

        Player player = context.getPlayer(); // Recupera l’oggetto Player che ha effettuato l’interazione dal contesto
        if (player == null) return InteractionResult.FAIL; // sicurezza

        // Mostro il messaggio lato client
        if (context.getLevel().isClientSide) {
            player.displayClientMessage(new TextComponent("Hai usato il bastone magico"), false);
        }

        return InteractionResult.SUCCESS;
        // Il metodo useOn ritorna il tipo InteractionResult che può assumere i valori SUCCESS, FAIL, PASS
    }


    /*
    // Il metodo use è invece invocato quando si clicca con il tasto destro nell'aria
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand)
    {
        System.out.println("Sto usando il metodo use");
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) {
            player.displayClientMessage(new TextComponent("Hai usato il bastone magico!"), false);
        }

        return InteractionResultHolder.success(stack);
    }
     */


    // array che uso per spawnare randomicamente 3 mob attorno il giocatore
    private static final EntityType<?>[] PASSIVE_MOBS = {
            EntityType.COW,
            EntityType.SHEEP,
            EntityType.PIG,
            EntityType.CHICKEN
    };


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            Random random = new Random();//creazione di un oggetto randomico con costruttore vuoto

            // Spawna 3 mob
            for (int i = 0; i < 3; i++) {
                EntityType<?> mobType = PASSIVE_MOBS[random.nextInt(PASSIVE_MOBS.length)]; //sceglie 3 mob passivi tra l'array di mob passivi
                Mob mob = (Mob) mobType.create(level);
                if (mob != null) {
                    mob.moveTo(player.getX() + random.nextInt(5) - 2, //le seguenti 3 righe descrivono a quanti blocchi di
                                                                                 // distanza spawnano i mob dal giocatore
                            player.getY(),
                            player.getZ() + random.nextInt(5) - 2,
                            0, 0);
                    level.addFreshEntity(mob);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }






}


