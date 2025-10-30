package net.filippo.mccourse;

import net.filippo.mccourse.init.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MainMod.MODID)
public class MainMod {
    public static final String MODID = "mccourse";

    public MainMod() {
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}