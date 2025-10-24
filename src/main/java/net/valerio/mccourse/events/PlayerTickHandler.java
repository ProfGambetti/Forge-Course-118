package net.valerio.mccourse.events;

import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.valerio.mccourse.item.MagicLanternItem;

@Mod.EventBusSubscriber
public class PlayerTickHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.getLevel(); // prendi il livello dal player

        // Main hand
        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() instanceof MagicLanternItem lantern) {
            lantern.onHeldTick(level, player, mainHand);
        }

        // Offhand (mano secondaria / slot scudo)
        ItemStack offhand = player.getOffhandItem();
        if (offhand.getItem() instanceof MagicLanternItem lantern) {
            lantern.onHeldTick(level, player, offhand);
        }
    }
}

