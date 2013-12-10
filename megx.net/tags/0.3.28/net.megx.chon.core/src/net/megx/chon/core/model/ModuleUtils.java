package net.megx.chon.core.model;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import net.megx.chon.core.model.impl.GenericModuleNode;
import net.megx.chon.core.renderers.ModuleNodeRenderer;

import org.chon.cms.content.utils.ChonTypeUtils;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.osgi.framework.BundleContext;

public class ModuleUtils {
	public static void registerModuleType(BundleContext bundleContext, ContentModel contentModel, String typeName, Class<? extends IContentNode> clz) {
		try {
			//remove previous /etc/types/typeName node
			removeType(contentModel, typeName);
			// Create New /etc/types/typeName node (with updated refactoring if any)
			ChonTypeUtils.registerType(contentModel, typeName, clz, ModuleNodeRenderer.class, null, null);
			// finally, register content node type in OSGi registry
			ChonTypeUtils.registerContnetNodeClass(bundleContext, clz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removeType(ContentModel contentModel, String typeName) throws Exception {
		Node node = ChonTypeUtils.getType(contentModel, typeName, false);
		if(node != null) node.remove();
	}
	
	public static void removeNodeIfExists(ContentModel contentModel, String path) throws RepositoryException {
		IContentNode node = contentModel.getContentNode(path);
		if(node != null) {
			node.getNode().remove();
			contentModel.getSession().save();
		}
	}
	
	private static Map<String, IModule> instanceModules = new HashMap<String, IModule>();
	
	public static void registerModuleInstance(BundleContext bundleContext, ContentModel contentModel, String typeName, IModule module) {
		registerModuleType(bundleContext, contentModel, typeName, GenericModuleNode.class);
		instanceModules.put(typeName, module);
	}
	
	public static IModule findIModule(GenericModuleNode node) {
		return instanceModules.get(node.getType());
	}
}

	
