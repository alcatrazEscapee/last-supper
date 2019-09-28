/*
 * Part of the Last Supper Mod by AlcatrazEscapee
 * Work under Copyright. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.lastsupper.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import com.alcatrazescapee.lastsupper.util.Helpers;

import static com.alcatrazescapee.lastsupper.LastSupper.MOD_ID;

public class PlayerData
{
    @CapabilityInject(IPlayerData.class)
    public static final Capability<IPlayerData> CAPABILITY = Helpers.notNull();
    public static final ResourceLocation KEY = new ResourceLocation(MOD_ID, "player_data");

    public static class Storage implements Capability.IStorage<IPlayerData>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side)
        {
            return new ByteNBT((byte) (instance.hasEatenLastSupper() ? 1 : 0));
        }

        @Override
        public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side, INBT nbt)
        {
            if (nbt instanceof ByteNBT)
            {
                instance.setEatenLastSupper(((ByteNBT) nbt).getByte() == 1);
            }
        }
    }

    public static class Provider implements IPlayerData, ICapabilitySerializable<ByteNBT>
    {
        private final LazyOptional<IPlayerData> capability;
        private boolean eatenLastSupper;

        public Provider()
        {
            this.capability = LazyOptional.of(() -> this);
        }

        @Override
        public boolean hasEatenLastSupper()
        {
            return eatenLastSupper;
        }

        @Override
        public void setEatenLastSupper(boolean value)
        {
            this.eatenLastSupper = value;
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            return capability.cast();
        }

        @Override
        public ByteNBT serializeNBT()
        {
            return (ByteNBT) CAPABILITY.writeNBT(this, null);
        }

        @Override
        public void deserializeNBT(ByteNBT nbt)
        {
            CAPABILITY.readNBT(this, null, nbt);
        }
    }
}
