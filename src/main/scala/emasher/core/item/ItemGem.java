package emasher.core.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emasher.core.EmasherCore;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemGem extends Item
{
	@SideOnly(Side.CLIENT)
	public IIcon[] textures;

	public ItemGem()
	{
		super();
		this.setCreativeTab(EmasherCore.tabEmasher);
		setHasSubtypes(true);
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
        if(textures != null && damage >= 0)
            return textures[damage];
        return null;
	}
	
	@Override
	public void registerIcons(IIconRegister ir)
	{
		textures = new IIcon[3];
		
		textures[0] = ir.registerIcon("emashercore:gemEmery");
		textures[1] = ir.registerIcon("emashercore:gemRuby");
		textures[2] = ir.registerIcon("emashercore:gemSapphire");
		
		this.itemIcon = textures[0];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	 {
		String name = "";
		switch(itemstack.getItemDamage()) 
		{
		case 0:
			name = "e_gemEmery";
			break;
		case 1:
			name = "e_gemRuby";
			break;
		case 2:
			name = "e_gemSapphire";
			break;
		}
		return getUnlocalizedName() + "." + name;
	 }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{	
		for(int i = 0; i < 3; i++) par3List.add(new ItemStack(par1, 1, i));
    }

}
