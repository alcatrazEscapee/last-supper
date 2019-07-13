package com.alcatrazescapee.lastsupper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.alcatrazescapee.lastsupper.capability.PlayerData;
import com.alcatrazescapee.lastsupper.items.ModItems;
import com.alcatrazescapee.lastsupper.util.Helpers;

public class ForgeEventHandler
{
    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickBlock event)
    {
        if (event.getItemStack().getItem() == Helpers.getCatalystItem())
        {
            BlockPos pos = event.getPos();
            World world = event.getWorld();

            List<ItemEntity> itemsToUse = new ArrayList<>(25);
            List<Ingredient> ingredientsRemaining = Helpers.getIngredients();

            for (ItemEntity entity : world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos.add(-2, -1, -2), pos.add(2, 1, 2))))
            {
                // Try and find a matching ingredient
                Ingredient matchingIngredient = null;
                for (Ingredient ingredient : ingredientsRemaining)
                {
                    if (ingredient.test(entity.getItem()))
                    {
                        matchingIngredient = ingredient;
                        itemsToUse.add(entity);
                        break;
                    }
                }

                // Remove it from the list if satisfied
                if (matchingIngredient != null)
                {
                    ingredientsRemaining.remove(matchingIngredient);
                }
            }

            // The recipe should complete if all ingredients have matched
            if (itemsToUse.size() == 0 && !world.isRemote() && world instanceof ServerWorld)
            {
                // Flashy effects and sound!
                LightningBoltEntity lightningBolt = new LightningBoltEntity(world, pos.getX(), pos.getY(), pos.getZ(), false);
                ((ServerWorld) world).addLightningBolt(lightningBolt);
                world.playSound(null, pos, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.PLAYERS, 1.0f, 1.0f);

                // Consume catalyst and ingredients
                event.getItemStack().shrink(1);
                for (ItemEntity entity : itemsToUse)
                {
                    entity.getItem().shrink(1);
                    if (entity.getItem().isEmpty())
                    {
                        entity.remove();
                    }
                }

                // Create last supper item
                ItemEntity lastSupperEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, new ItemStack(ModItems.LAST_SUPPER));
                lastSupperEntity.setMotion(0, 0, 0);
                lastSupperEntity.setPickupDelay(60);
                lastSupperEntity.setInvulnerable(true);
                lastSupperEntity.setNoGravity(true);
                lastSupperEntity.setNoDespawn();

                world.addEntity(lastSupperEntity);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            event.player.getCapability(PlayerData.CAPABILITY).ifPresent(cap -> {
                if (cap.hasEatenLastSupper())
                {
                    event.player.getFoodStats().setFoodLevel(20);
                }
            });
        }
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getObject();
            if (!player.getCapability(PlayerData.CAPABILITY).isPresent())
            {
                event.addCapability(PlayerData.KEY, new PlayerData.Provider());
            }
        }
    }
}
