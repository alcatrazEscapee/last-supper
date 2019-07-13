/*
 * Part of the Ore Veins Mod by alcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.lastsupper.items;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;

import com.alcatrazescapee.lastsupper.capability.PlayerData;

@ParametersAreNonnullByDefault
public class LastSupperItem extends Item
{
    LastSupperItem()
    {
        super(new Properties().maxStackSize(1).group(ItemGroup.FOOD).rarity(Rarity.EPIC).food(new Food.Builder().setAlwaysEdible().hunger(0).saturation(0).build()));
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity)
    {
        entity.getCapability(PlayerData.CAPABILITY).ifPresent(cap -> cap.setEatenLastSupper(true));
        return super.onItemUseFinish(stack, world, entity);
    }
}
