package net.valerio.mccourse.yoyo.registry;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        // Non serve per le entity di progetto
    }
}