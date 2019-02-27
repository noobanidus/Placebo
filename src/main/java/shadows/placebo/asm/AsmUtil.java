package shadows.placebo.asm;

import java.util.function.Predicate;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Helper class for Coremod related activities.  Can be loaded in the coremod classloader without issues.
 * @author Shadows
 *
 */
public class AsmUtil {

	public static MethodNode findMethod(ClassNode node, Predicate<MethodNode> finder) {
		for (MethodNode m : node.methods) {
			if (finder.test(m)) return m;
		}
		return null;
	}

}
