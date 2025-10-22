package net.valerio.mccourse.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MagicLanternItem extends Item {

    // Mappa per tenere traccia dei mostri immuni
    private final Map<UUID, Integer> immuneMap = new HashMap<>();

    public MagicLanternItem(Properties props)
    {
        super(props.durability(15));

        // Durabilità totale: 30 minuti in tick
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.NONE; // nessuna animazione particolare
    }

    // Metodo chiamato ogni tick del giocatore che tiene l'oggetto in mano
    public void onHeldTick(Level level, Player player, ItemStack stack)
    {
        if (level.isClientSide()) return;
        //Luce temporanea sopra la testa

        BlockPos pos = player.blockPosition().above();
        if (level.getBlockState(pos).getBlock() == Blocks.LIGHT)
        {
            level.removeBlock(pos, false);
        }
        level.setBlock(pos, Blocks.LIGHT.defaultBlockState(), 3);

        //Cecità mostri vicini

        int radius = 5;
        level.getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(radius))
                .forEach(monster ->
                {
                    UUID id = monster.getUUID();
                    int currentTick = (int) level.getGameTime();
                    boolean immune = immuneMap.containsKey(id) && immuneMap.get(id) > currentTick;

                    if (!immune)
                    {
                        monster.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
                        immuneMap.put(id, currentTick + 600);
                    }
                });

        //Durabilità: perde 1 punto ogni 2 minuti

        int tick = (int) level.getGameTime();
        if (tick % 2400 == 0)
        {
            stack.hurt(1, player.getRandom(), null);
        }

        // Se la durabilità arriva a 0, l'oggetto si rompe
        if (stack.getDamageValue() >= stack.getMaxDamage())
        {
            player.getInventory().removeItem(stack);
            player.broadcastBreakEvent(player.getUsedItemHand());
        }


    }
}
