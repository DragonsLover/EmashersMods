package emasher.sockets.modules;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import emasher.api.SideConfig;
import emasher.api.SocketModule;
import emasher.api.SocketTileAccess;
import emasher.sockets.SocketsMod;

public class ModLatchToggle extends SocketModule
{

	public ModLatchToggle(int id)
	{
		super(id, "sockets:TOGGLE_0");
	}

	@Override
	public String getLocalizedName()
	{
		return "Redstone Latch Toggle";
	}
	
	@Override
	public void getToolTip(List l)
	{
		l.add("Toggles the state of selected");
		l.add("internal RS latches");
	}
	
	@Override
	public void getIndicatorKey(List l)
	{
		l.add(SocketsMod.PREF_DARK_PURPLE + "RS latches to toggle");
	}
	
	@Override
	public void addRecipe()
	{
		GameRegistry.addShapedRecipe(new ItemStack(SocketsMod.module, 1, moduleID), "tgr", " b ", Character.valueOf('t'), Blocks.redstone_torch, Character.valueOf('r'), Items.redstone,
				Character.valueOf('g'), Blocks.lever, Character.valueOf('b'), new ItemStack(SocketsMod.module, 1, 16));
	}
	
	@Override
	public boolean hasLatchIndicator() { return true; }

    @SideOnly(Side.CLIENT)
    public String getInternalTexture(SocketTileAccess ts, SideConfig config, ForgeDirection side)
    {
        if(config.meta == 0) return "sockets:inner_redstone_inactive";
        return "sockets:inner_redstone_active";
    }

    @SideOnly(Side.CLIENT)
    public String[] getAllInternalTextures()
    {
        return new String[] {
                "sockets:inner_redstone_inactive",
                "sockets:inner_redstone_active"
        };
    }
	
	@Override
	public boolean isRedstoneInterface() { return true; }
	
	@Override
	public void updateRestone(boolean on, SideConfig config, SocketTileAccess ts)
	{
		for(int i = 0; i < 3; i++)
		{
			if(config.rsLatch[i])
			{
				if(on)
				{
					if(ts.getRSLatch(i)) ts.modifyLatch(i, false);
					else ts.modifyLatch(i, true);
					config.meta = 1;
				}
				else
				{
					config.meta = 0;
				}
			}
		}
	}
	
}
