package pl.asie.tweaks.core;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import pl.asie.tweaks.util.ClassTransformer;

public class BCPipeRFSupport extends ClassTransformer implements IClassTransformer {
	public byte[] transform(String className, String newName, byte[] bytecode) {
		/*if (className.equals("buildcraft.transport.pipes.PipePowerWood")) {
			String[] classes = {"receiveEnergy", "extractEnergy__nil", "iWantToSayHiToRF", "canInterface", "getEnergyStored", "getMaxEnergyStored"};
			String oldClassName = "pl.asie.tweaks.core.PipePowerWoodPatch";
			
			System.out.println("[AsieTweaksCore] Patching " + className);
			ClassNode bcPipe = getClassNode(bytecode);
			ClassNode bcPipePatch = getClassNode(oldClassName);
			if(bcPipePatch != null) {
				for(MethodNode mn: bcPipePatch.methods) {
					for(String cl : classes) {
						if(mn.name.equals(cl)) {
							System.out.println("[AsieTweaksCore] - injecting method " + mn.name);
							mn.name = cl.split("__")[0];
							mn.localVariables.get(0).signature = "L"+className+";";
							Iterator<AbstractInsnNode> i = mn.instructions.iterator();
							while(i.hasNext()) {
								AbstractInsnNode ain = i.next();
								if(ain instanceof FieldInsnNode) {
									FieldInsnNode fin = (FieldInsnNode)ain;
									if(fin.name.equals("container")) {
										fin.desc = "Lbuildcraft/transport/TileGenericPipe;";
										fin.owner = "buildcraft/transport/Pipe";
									}
									if(fin.owner.equals(oldClassName.replace('.', '/')))
										fin.owner = className.replace('.', '/');
								}
								if(ain instanceof MethodInsnNode) {
									MethodInsnNode min = (MethodInsnNode)ain;
									if(min.owner.equals(oldClassName.replace('.', '/')))
										min.owner = className.replace('.', '/');
								}	
							}
							bcPipe.methods.add(mn);
						}
					}
				}
				for(MethodNode mn: bcPipe.methods) {
					if(mn.name.equals("updateEntity")) {
						System.out.println("[AsieTweaksCore] - patching method updateEntity");
						AbstractInsnNode f1 = mn.instructions.getFirst();
						mn.instructions.insertBefore(f1, new VarInsnNode(Opcodes.ALOAD, 0));
						mn.instructions.insertBefore(f1, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "buildcraft/transport/pipes/PipePowerWood", "iWantToSayHiToRF", "()V"));
					}
				}
				bcPipe.interfaces.add("cofh/api/energy/IEnergyHandler");
				return writeBytecode(bcPipe);
			} else return bytecode;
		}
		if (className.equals("buildcraft.transport.PipeTransportPower")) {
			System.out.println("[AsieTweaksCore] Patching " + className);
			ClassNode bcPipe = getClassNode(bytecode);
			for(MethodNode mn: bcPipe.methods) {
				if(mn.name.equals("canPipeConnect")) {
					System.out.println("[AsieTweaksCore] - patching method canPipeConnect");
					AbstractInsnNode f1 = mn.instructions.getFirst();
					LabelNode ln = newLabelNode();
					mn.instructions.insertBefore(f1, new VarInsnNode(Opcodes.ALOAD, 1));
					mn.instructions.insertBefore(f1, new TypeInsnNode(Opcodes.INSTANCEOF, "cofh/api/energy/IEnergyHandler"));
					mn.instructions.insertBefore(f1, new JumpInsnNode(Opcodes.IFEQ, ln));
					mn.instructions.insertBefore(f1, new InsnNode(Opcodes.ICONST_1));
					mn.instructions.insertBefore(f1, new InsnNode(Opcodes.IRETURN));
					mn.instructions.insertBefore(f1, ln);
				}
			}
			return writeBytecode(bcPipe);
		}
		if (className.equals("buildcraft.transport.TileGenericPipe")) {
			String[] classes = {"receiveEnergy", "extractEnergy", "canInterface", "getEnergyStored", "getMaxEnergyStored"};
			String oldClassName = "pl.asie.tweaks.core.TileGenericPipePatch";
			
			System.out.println("[AsieTweaksCore] Patching " + className);
			ClassNode bcPipe = getClassNode(bytecode);
			ClassNode bcPipePatch = getClassNode(oldClassName);
			if(bcPipePatch != null) {
				for(MethodNode mn: bcPipePatch.methods) {
					for(String cl : classes) {
						if(mn.name.equals(cl)) {
							System.out.println("[AsieTweaksCore] - injecting method " + mn.name);
							mn.name = cl.split("__")[0];
							mn.localVariables.get(0).signature = "L"+className+";";
							Iterator<AbstractInsnNode> i = mn.instructions.iterator();
							while(i.hasNext()) {
								AbstractInsnNode ain = i.next();
								if(ain instanceof FieldInsnNode) {
									FieldInsnNode fin = (FieldInsnNode)ain;
									if(fin.owner.equals(oldClassName.replace('.', '/')))
										fin.owner = className.replace('.', '/');
									if(fin.name.equals("pipe")) {
										fin.desc = "Lbuildcraft/transport/Pipe;";
									}
								}
								if(ain instanceof MethodInsnNode) {
									MethodInsnNode min = (MethodInsnNode)ain;
									if(min.owner.equals(oldClassName.replace('.', '/')))
										min.owner = className.replace('.', '/');
								}	
							}
							bcPipe.methods.add(mn);
						}
					}
				}
				bcPipe.interfaces.add("cofh/api/energy/IEnergyHandler");
				return writeBytecode(bcPipe);
			} else return bytecode;
		}*/
		return bytecode;
	}
}
