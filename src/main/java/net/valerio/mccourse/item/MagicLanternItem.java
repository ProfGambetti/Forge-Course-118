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

    // la luce dinamica in realta funzione+a attraverso un blocco luminoso invisibile che viene creato sopra la
    //testa del giocatore, quindi è necessario tracciare il giocatore per sapere dove piazzarlo, questa riga si
    //di questo
    private final Map<UUID, Integer> immuneMap = new HashMap<>();

    //questa serve invece per tracciare il blocco luminoso appena piazzato cosi che quando il giocatore si sposta
    //venga distrutto il blocco piazzato precedentemente
    private final Map<UUID, BlockPos> lastLightPos = new HashMap<>();


    //questo metodo serve a determinare la "vita" dell'item cosi che a un certo punto si distrugga
    //cosi da dare effetto olio terminato o fiamma spenta
    public MagicLanternItem(Properties props)
    {
        super(props.durability(15));

        // la "vita" dell oggetto è di 15
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.NONE; //per animare l'oggetto ma non c'è nessuna animazione particolare
    }

    // Metodo chiamato ogni tick, verifica se sopra il giocatore è presente un blocco luminoso
    public void onHeldTick(Level level, Player player, ItemStack stack)
    {
        if (level.isClientSide()) return;
        //Luce temporanea sopra la testa

        UUID playerId = player.getUUID();
        BlockPos currentPos = player.blockPosition().above();

// Rimuove il vecchio blocco luce se esiste
        if (lastLightPos.containsKey(playerId)) {
            BlockPos oldPos = lastLightPos.get(playerId);
            if (!oldPos.equals(currentPos) && level.getBlockState(oldPos).getBlock() == Blocks.LIGHT) {
                level.removeBlock(oldPos, false);
            }
        }

// Piazza il nuovo blocco luce sopra la testa
        level.setBlock(currentPos, Blocks.LIGHT.defaultBlockState(), 3);
        lastLightPos.put(playerId, currentPos);


        //se i mostri vengono toccati dalla luce prendono fuoco

        int radius = 5;
        level.getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(radius))
                .forEach(monster ->
                {
                    UUID id = monster.getUUID();
                    int currentTick = (int) level.getGameTime();
                    boolean immune = immuneMap.containsKey(id) && immuneMap.get(id) > currentTick;

                    if (!immune)
                    {
                        // Far prendere fuoco il mob per 10 secondi (100 tick)
                        monster.setSecondsOnFire(10);
                        immuneMap.put(id, currentTick + 600); // mantiene comunque un cooldown
                    }

                });

        //Durabilità: perde 1 punto ogni 2 minuti cosi che dopo 30 minuti l'item si distrugga

        int tick = (int) level.getGameTime();
        if (tick % 2400 == 0)
        {
            stack.hurt(1, player.getRandom(), null);
        }

        // Se la durabilità arriva a 0, l'oggetto si rompe
        if (stack.getDamageValue() >= stack.getMaxDamage())
        {
            // Rimuove anche l’ultimo blocco luce
            BlockPos last = lastLightPos.remove(player.getUUID());
            if (last != null && level.getBlockState(last).getBlock() == Blocks.LIGHT) {
                level.removeBlock(last, false);
            }

            // Rimuove l’oggetto dall’inventario
            player.getInventory().removeItem(stack);
            player.broadcastBreakEvent(player.getUsedItemHand());

            // Messaggio nella chat quando l'item si rompe
            if (!level.isClientSide()) {
                        player.displayClientMessage(new net.minecraft.network.chat.TextComponent("Fiamma spenta!"), true);

            }
        }


    }
}
