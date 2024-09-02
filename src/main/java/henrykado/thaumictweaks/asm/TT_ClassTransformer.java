package henrykado.thaumictweaks.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import henrykado.thaumictweaks.TT_Config;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.world.World;
import thaumcraft.common.blocks.world.ore.ShardType;

public class TT_ClassTransformer implements IClassTransformer, Opcodes {
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (basicClass == null)
			return basicClass;
		
		if ("thaumcraft.common.world.ThaumcraftWorldGenerator".equals(transformedName))
		{
			ClassNode classNode = new ClassNode();
			new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);
			
			for(MethodNode method : classNode.methods) 
			{
				if ("generateOres".equals(method.name))
				{
					LabelNode label = new LabelNode();
					for (AbstractInsnNode node : method.instructions.toArray())
					{
						/*if (TT_Config.crystalOreDensity != -1 && node instanceof FieldInsnNode && ((FieldInsnNode)node).name.equals("regen_crystals"))
						{
							InsnList insnList = new InsnList();
							insnList.add(new FieldInsnNode(GETSTATIC, "henrykado/thaumictweaks/TT_Config", "crystalOreDensity", "I"));
							insnList.add(new VarInsnNode(FSTORE, 7));
							method.instructions.insert(node.getNext().getNext(), insnList);
						}
						else */if (node instanceof LineNumberNode && ((LineNumberNode)node).line == 290)
						{
							InsnList insnList = new InsnList();
							
							if (TT_Config.crystalOreDensity != -1) {
								insnList.add(new FieldInsnNode(GETSTATIC, "henrykado/thaumictweaks/TT_Config", "crystalOreDensity", "I"));
								insnList.add(new InsnNode(I2F));
								insnList.add(new LdcInsnNode(100.0F));
								insnList.add(new InsnNode(FDIV));
								insnList.add(new VarInsnNode(FSTORE, 7));
							}
							if (TT_Config.GENERATION.enableDimensionBlacklist) {
								insnList.add(new VarInsnNode(ALOAD, 1));
								insnList.add(new MethodInsnNode(INVOKESTATIC, "henrykado/thaumictweaks/asm/TT_ClassTransformer", "crystalBlacklist", "(Lnet/minecraft/world/World;)Z", false)); 
								insnList.add(new JumpInsnNode(IFEQ, label));
							}
							
							if (insnList.size() > 0) method.instructions.insert(node, insnList);
						}
						else if (node instanceof LineNumberNode && ((LineNumberNode)node).line == 304)
						{
							if (TT_Config.GENERATION.enableSpecificCrystalBlacklist)
							{
								InsnList insnList = new InsnList();
								insnList.add(new VarInsnNode(ALOAD, 1));
								insnList.add(new VarInsnNode(ILOAD, 16));
								insnList.add(new MethodInsnNode(INVOKESTATIC, "henrykado/thaumictweaks/asm/TT_ClassTransformer", "specificCrystalBlacklist", "(Lnet/minecraft/world/World;I)Z", false));
								insnList.add(new JumpInsnNode(IFEQ, label));
								method.instructions.insert(node, insnList);
							}
							
							InsnList retnList = new InsnList();
							retnList.add(label);
							retnList.add(new InsnNode(RETURN));
							method.instructions.insert(method.instructions.getLast(), retnList);
							break;
						}
					}
					break;
				}
			}
			
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(writer);
			return writer.toByteArray();
		}
		else if (TT_Config.useCustomCrystalModel && transformedName.equals("thaumcraft.proxies.ProxyBlock"))
		{
			ClassNode classNode = new ClassNode();
			new ClassReader(basicClass).accept(classNode, 0);
			
			for(MethodNode method : classNode.methods) 
			{
				if ("setupBlocksClient".equals(method.name))
				{
					LabelNode label = new LabelNode();
					boolean addedJump = false;
					for (AbstractInsnNode node : method.instructions.toArray())
					{
						if (!addedJump && node instanceof MethodInsnNode && "thaumcraft/common/blocks/world/ore/ShardType".equals(((MethodInsnNode)node).owner))
						{
							addedJump = true;
							method.instructions.insertBefore(node.getPrevious().getPrevious(), new JumpInsnNode(GOTO, label));
						}
						else if (node instanceof FieldInsnNode && ((FieldInsnNode)node).getOpcode() == GETSTATIC && "thaumcraft/api/blocks/BlocksTC".equals(((FieldInsnNode)node).owner) && "banners".equals(((FieldInsnNode)node).name))
						{
							method.instructions.insertBefore(node.getPrevious().getPrevious(), label);
						}
					}
				}
			}
			
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(writer);
			return writer.toByteArray();
		}
		else if (TT_Config.useCustomCrystalModel && "thaumcraft.proxies.ProxyBlock$BakeBlockEventHandler".equals(transformedName))
		{
			ClassNode classNode = new ClassNode();
			new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);
			
			classNode.methods.clear();
			
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(writer);
			return writer.toByteArray();
		}
		
		return basicClass;
	}
	
	public static boolean crystalBlacklist(World world)
	{
		if (!TT_ClassTransformer.isValidDimension(TT_Config.GENERATION.crystalsDimensionBlacklist, world))
		{
			return false;
		}
		return true;
	}
	
	public static boolean specificCrystalBlacklist(World world, int md)
	{
		switch (ShardType.byMetadata(md))
        {
        	case AIR:
        		if (!isValidDimension(TT_Config.GENERATION.airCrystalDimensionBlacklist, world)) 
        			return false;
        		break;
        	case EARTH:
        		if (!isValidDimension(TT_Config.GENERATION.earthCrystalDimensionBlacklist, world)) 
        			return false;
        		break;
        	case ENTROPY:
        		if (!isValidDimension(TT_Config.GENERATION.entropyCrystalDimensionBlacklist, world)) 
        			return false;
        		break;
        	case FIRE:
        		if (!isValidDimension(TT_Config.GENERATION.fireCrystalDimensionBlacklist, world)) 
        			return false;
        		break;
        	case ORDER:
        		if (!isValidDimension(TT_Config.GENERATION.orderCrystalDimensionBlacklist, world)) 
        			return false;
        		break;
        	default:
        		if (!isValidDimension(TT_Config.GENERATION.waterCrystalDimensionBlacklist, world)) 
        			return false;
        		break;
        }
		return true;
	}
	
	
	public static boolean isValidDimension(String[] blacklist, World world)
	{
		if (blacklist.length > 0)
		{
			boolean isWhitelist = blacklist[0] == "*";
			for (int i = (isWhitelist ? 1 : 0); i < blacklist.length; i++)
			{
				if (!isWhitelist)
				{
					if (world.provider.getDimension() == Integer.valueOf(blacklist[i]))
						return false;
				}
				else 
				{
					if (world.provider.getDimension() != Integer.valueOf(blacklist[i]))
						return false;
				}
			}
		}
		return true;
	}
}
