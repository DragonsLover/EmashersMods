package emasher.gas.item;

import java.util.List;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emasher.core.EmasherCore;
import emasher.gas.CommonProxy;
import emasher.gas.EmasherGas;
import net.minecraft.world.biome.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.potion.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fluids.*;

public class ItemGasVialFilled extends Item
{
	@SideOnly(Side.CLIENT)
	public Icon[] textures;
	
	public ItemGasVialFilled(int id) 
	{
		super(id);
		
		setCreativeTab(EmasherGas.tabGasCraft);
		setMaxStackSize(1);
		setUnlocalizedName("gasVialFilled");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage)
	{
		return textures[damage];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		textures = new Icon[6];
		
		textures[0] = ir.registerIcon("gascraft:naturalGasVial");
		textures[1] = ir.registerIcon("gascraft:propellentVial");
		textures[2] = ir.registerIcon("gascraft:hydrogenVial");
		textures[3] = ir.registerIcon("gascraft:smokeVial");
		textures[4] = ir.registerIcon("gascraft:toxicGasVial");
		textures[5] = ir.registerIcon("gascraft:neurotoxinVial");
		
		this.itemIcon = textures[0];
	}
	
	public FluidStack getFluid(ItemStack stack)
	{
		int meta = stack.getItemDamage();
		int v = FluidContainerRegistry.BUCKET_VOLUME;
		
		switch(meta)
		{
		case 0: return new FluidStack(EmasherGas.fluidNaturalGas, v);
		case 1: return new FluidStack(EmasherGas.fluidPropellent, v);
		case 2: return new FluidStack(EmasherGas.fluidHydrogen, v);
		case 3: return new FluidStack(EmasherGas.fluidSmoke, v);
		case 4: return new FluidStack(EmasherGas.fluidToxicGas, v);
		case 5: return new FluidStack(EmasherGas.fluidNeurotoxin, v);
		default: return new FluidStack(EmasherGas.fluidNaturalGas, v);
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	 {
		String name = "";
		switch(itemstack.getItemDamage()) 
		{
		case 0:
			name = "naturalGas";
			break;
		case 1:
			name = "propellent";
			break;
		case 2:
			name = "hydrogen";
			break;
		case 3:
			name = "smoke";
			break;
		case 4:
			name = "toxicGas";
			break;
		case 5:
			name = "neurotoxin";
			break;
		}
		return getUnlocalizedName() + "." + name;
	 }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{	
		for(int i = 0; i < 6; i++) par3List.add(new ItemStack(par1, 1, i));
    }

	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		float var4 = 1.0F;
        double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)var4;
        double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)var4 + 1.62D - (double)par3EntityPlayer.yOffset;
        double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)var4;
        MovingObjectPosition var12 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
        
        if (var12 == null)
        {
            return par1ItemStack;
        }
        else if (var12.typeOfHit == EnumMovingObjectType.TILE)
        {
        	int var13 = var12.blockX;
            int var14 = var12.blockY;
            int var15 = var12.blockZ;
            
            int BlockID = par2World.getBlockId(var13, var14, var15);
            
            
        	if (var12.sideHit == 0)
            {
                --var14;
            }

            if (var12.sideHit == 1)
            {
                ++var14;
            }

            if (var12.sideHit == 2)
            {
                --var15;
            }

            if (var12.sideHit == 3)
            {
                ++var15;
            }

            if (var12.sideHit == 4)
            {
                --var13;
            }

            if (var12.sideHit == 5)
            {
                ++var13;
            }
            
            int i = par2World.getBlockId(var13, var14, var15);

            if (i == 0 && !(Block.blocksList[BlockID] instanceof IFluidHandler))
            {
                par2World.playSoundEffect((double)var13 + 0.5D, (double)var14 + 0.5D, (double)var15 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                par2World.setBlock(var13, var14, var15, getFluid(par1ItemStack).getFluid().getBlockID());
                
                if(! par3EntityPlayer.capabilities.isCreativeMode)
                {
	    			par1ItemStack.stackSize--;
	    			return (new ItemStack(EmasherGas.vial, 1, 0));
                }
                else
                {
                	return par1ItemStack;
                }
                
            }
            
        }
        
        return par1ItemStack;
	}

	
}