package net.valerio.mccourse.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

// Creo una nuova classe figlia della classe Item e la chiamo MagicStick (bastone magico)
public class MagicStick extends Item {

    // Uso il costruttore della classe madre
    public MagicStick(Properties props) {
        super(props);

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
}


