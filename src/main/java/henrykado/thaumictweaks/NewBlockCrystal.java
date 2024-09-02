package henrykado.thaumictweaks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.blocks.world.ore.BlockCrystal;


public class NewBlockCrystal extends BlockCrystal
{
    public static PropertyInteger RANDOM_ROTATION;
    public static PropertyDirection FACING;
    
    public NewBlockCrystal(String name, Aspect aspect) {
        super(name, aspect);
        setDefaultState(blockState.getBaseState().withProperty((IProperty)BlockCrystal.SIZE, (Comparable)0).withProperty((IProperty)BlockCrystal.GENERATION, (Comparable)1).withProperty(NewBlockCrystal.RANDOM_ROTATION, 0).withProperty(NewBlockCrystal.FACING, EnumFacing.DOWN));
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.SOLID;
    }
    
    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        return BlockRenderLayer.CUTOUT == layer;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState bs, IBlockAccess iblockaccess, BlockPos pos) {
    	IBlockState state = getActualState(bs, iblockaccess, pos);
    	switch (state.getValue(NewBlockCrystal.FACING))
    	{
	    	case DOWN:
				return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
    		case UP:
    			return new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);
    		case NORTH:
    			return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
    		case SOUTH:
    			return new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0);
    		case EAST:
    			return new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0);
    		default: // WEST
    			return new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0);
    	}
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {BlockCrystal.SIZE, BlockCrystal.GENERATION, NewBlockCrystal.RANDOM_ROTATION, NewBlockCrystal.FACING});
    }
    
    private boolean drawAt(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        IBlockState fbs = worldIn.getBlockState(pos);
        return fbs.getMaterial() == Material.ROCK && fbs.getBlock().isSideSolid(fbs, worldIn, pos, side.getOpposite());
    }
    
    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	int randRotation = (Integer.lowestOneBit(pos.getX()) + Integer.lowestOneBit(pos.getY()) + Integer.lowestOneBit(pos.getZ())) % 4;
    	
    	EnumFacing enumFacing;
    	if (drawAt(worldIn, pos.down(), EnumFacing.DOWN))
    	{
    		enumFacing = EnumFacing.DOWN;
    	}
    	else if (drawAt(worldIn, pos.up(), EnumFacing.UP))
		{
			enumFacing = EnumFacing.UP;
		}
		else if (drawAt(worldIn, pos.east(), EnumFacing.EAST))
		{
			enumFacing = EnumFacing.EAST;
		}
		else if (drawAt(worldIn, pos.west(), EnumFacing.WEST))
		{
			enumFacing = EnumFacing.WEST;
		}
		else if (drawAt(worldIn, pos.south(), EnumFacing.SOUTH))
		{
			enumFacing = EnumFacing.SOUTH;
		}
		else
		{
			enumFacing = EnumFacing.NORTH;
		}
    	
        return state.withProperty(NewBlockCrystal.RANDOM_ROTATION, randRotation).withProperty(NewBlockCrystal.FACING, enumFacing);
    }
    
    static {
    	FACING = PropertyDirection.create("facing");
        RANDOM_ROTATION = PropertyInteger.create("rand_rot", 0, 3);
    }
}
