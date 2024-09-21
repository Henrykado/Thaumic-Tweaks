package henrykado.thaumictweaks.mixin;

import henrykado.thaumictweaks.ThaumicTweaks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.common.container.ContainerResearchTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ContainerResearchTable.class)
public abstract class MixinContainerResearchTable extends Container {
    @Inject(method = "transferStackInSlot", at = @At("RETURN"), cancellable = true)
    public void transferStackInSlotInject(EntityPlayer par1EntityPlayer, int slot, CallbackInfoReturnable<ItemStack> cir) {
        ContainerResearchTable container = ( ContainerResearchTable ) ( Object ) this;

        if (slot > 1 && cir.getReturnValue() == ItemStack.EMPTY)
        {
            Slot slotObject = (Slot)container.inventorySlots.get(slot);
            if (slotObject != null && slotObject.getHasStack()) {
                ItemStack stackInSlot = slotObject.getStack();
                ItemStack stack = stackInSlot.copy();
                if ((container.tileEntity.isItemValidForSlot(0, stackInSlot) || container.tileEntity.isItemValidForSlot(1, stackInSlot)) && this.mergeItemStack(stackInSlot, 0, 2, false)) {
                    if (stackInSlot.getCount() == 0) {
                        slotObject.putStack(ItemStack.EMPTY);
                    } else {
                        slotObject.onSlotChanged();
                    }

                    cir.setReturnValue(stack);
                    return;
                }
            }
        }

        cir.setReturnValue(cir.getReturnValue());
    }
}
