package net.filippo.mccourse.init;

import net.filippo.mccourse.MainMod;
import net.filippo.mccourse.items.TreeChopperAxe;
import net.filippo.mccourse.items.TunnelPickaxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainMod.MODID);

    public static final RegistryObject<Item> TREE_CHOPPER_AXE = ITEMS.register("tree_chopper_axe",
            TreeChopperAxe::new);

    public static final RegistryObject<Item> TUNNEL_PICKAXE = ITEMS.register("tunnel_pickaxe",
            () -> new TunnelPickaxeItem(Tiers.DIAMOND, 1, -2.8F,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

}