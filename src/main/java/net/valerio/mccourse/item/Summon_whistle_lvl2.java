package net.valerio.mccourse.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

import java.util.List;
import java.util.Random;

public class Summon_whistle_lvl2 extends Item{

    public Summon_whistle_lvl2(Properties pProperties) {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1));
    }



    private static final EntityType<?>[] HOSTILE_MOBS = {
            EntityType.ZOMBIE,
            EntityType.SKELETON,
            EntityType.SPIDER,
            EntityType.ENDERMAN
    };


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            Random random = new Random();

            for (int i = 0; i < 3; i++) {
                EntityType<?> mobType = HOSTILE_MOBS[random.nextInt(HOSTILE_MOBS.length)];
                Mob mob = (Mob) mobType.create(level);
                if (mob != null) {
                    // Posiziona il mob vicino al giocatore
                    mob.moveTo(player.getX() + random.nextInt(6) - 3,
                            player.getY(),
                            player.getZ() + random.nextInt(6) - 3,
                            0, 0);


                    // Aggiungi tag identificativo
                    mob.addTag("summoned_by_whistle");

                    makeMobFriendlyToPlayer(mob);


                    //Impedisci che attacchi gli altri mob generati
                    mob.addTag("summoned_by_whistle");
                    mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, Mob.class, 10, true, false, (target) -> {
                        return !(target instanceof Player) && !target.getTags().contains("summoned_by_whistle");
                    }));

                    //Se è uno scheletro, dagli un arco
                    if (mob instanceof Skeleton skeleton) {
                        skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                        skeleton.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
                    }

                    // Spawna il mob nel mondo
                    level.addFreshEntity(mob);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }



    /**
     * Questo metodo cambia il comportamento dei mob spawnati in maniera di renderli pacifici verso il giocatore.
     * Cambia l'ostilita` dei mob ad escludere il giocatore, ma uccidere qualsiasi altro mob nelle vicinanze.
     */
    private void makeMobFriendlyToPlayer(Mob mob) {
        // Rimuove gli "obbiettivi "
        mob.targetSelector.removeAllGoals();

        // Re-add only custom targeting logic that ignores players
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, Mob.class, 10, true, false, (target) -> {

            return !(target instanceof Player) && !target.getTags().contains("summoned_by_whistle");
        }));

        // Clear any current player target if one exists
        if (mob.getTarget() instanceof Player) {
            mob.setTarget(null);
        }

        //commit & push prova 2
    }

}
