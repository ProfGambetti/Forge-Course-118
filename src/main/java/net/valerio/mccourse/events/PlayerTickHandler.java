package net.valerio.mccourse.events;

import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.valerio.mccourse.item.MagicLanternItem;

@Mod.EventBusSubscriber
public class PlayerTickHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHand = player.getMainHandItem();

        // Controlla se il giocatore tiene in mano la MagicLantern
        if (mainHand.getItem() instanceof MagicLanternItem lantern) {
            lantern.onHeldTick(player.level, player, mainHand);
        }
    }
}
