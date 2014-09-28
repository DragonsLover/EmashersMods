package emasher.gas.block;

import java.util.Random;

import emasher.core.EmasherCore;
import emasher.gas.EmasherGas;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockNetherGas extends Block
{

	public BlockNetherGas(int id)
	{
		super(Material.rock);
		this.setCreativeTab(null);
	}
	
	
	@Override
	public void registerBlockIcons(IIconRegister ir)
	{
		this.blockIcon = ir.registerIcon("netherrack");
	}

    //TODO Check if this needs to be specified in some way
    // @Override
	/*public int idDropped(int par1, Random rand, int par3)
	{
		return 0;
	}*/
	
	
	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
		par1World.setBlock(par2, par3, par4, EmasherGas.plasma.blockID);
    }
}
