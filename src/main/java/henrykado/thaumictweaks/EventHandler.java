package henrykado.thaumictweaks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.items.IScribeTools;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.tiles.crafting.TileResearchTable;

public class EventHandler {
	@SubscribeEvent
	public void interactHook(PlayerInteractEvent.RightClickBlock event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		EntityPlayer player = event.getEntityPlayer();
		
		IBlockState state = world.getBlockState(pos);
		
		if (player.isSneaking() && state.getBlock().getRegistryName().getPath().equals("research_table") && state.getBlock().getRegistryName().getNamespace().equals("thaumcraft")) {
			TileResearchTable tile = (TileResearchTable)world.getTileEntity(pos);
			
			ItemStack itemHeld = player.getHeldItem(player.getActiveHand());
			if (itemHeld == ItemStack.EMPTY) {
				if (player.addItemStackToInventory(tile.getStackInSlot(0)))
					tile.removeStackFromSlot(0);
				
				if (player.addItemStackToInventory(tile.getStackInSlot(1)))
					tile.removeStackFromSlot(1);
			}
			/*else if (itemHeld.getItem() instanceof IScribeTools && tile.getStackInSlot(0) == ItemStack.EMPTY) {
				tile.setInventorySlotContents(0, itemHeld);
				itemHeld.setCount(0);
			}
			else if (itemHeld.getItem() == Items.PAPER) {
				ItemStack stack = tile.getStackInSlot(1);
				if (stack == ItemStack.EMPTY) {
					itemHeld.setCount(0);
					tile.setInventorySlotContents(1, stack);
				}
				else if (stack.getCount() != 64) {
					int newCount = itemHeld.getCount() + stack.getCount();
					
					if (newCount > 64)
					{
						itemHeld.setCount(newCount - 64);
						newCount = 64;
					}
					else itemHeld.setCount(0);
					
					stack.setCount(newCount);
					tile.setInventorySlotContents(1, stack);
				}
			}*/
			
			event.setCancellationResult(EnumActionResult.SUCCESS);
			event.setCanceled(true);
		}
	}
}
