/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.megx.chon.core;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import net.megx.chon.core.model.impl.LoginModule;
import net.megx.chon.core.model.impl.WOA_Module;
import net.megx.chon.core.model.impl.WOD_Module;
import net.megx.chon.core.model.impl.blast.Blast_Module;
import net.megx.chon.core.renderers.ModuleNodeRenderer;
import net.megx.chon.core.rest.TestRest;
import net.megx.chon.core.ui.GenomesExtension;
import net.megx.chon.core.ui.MetagenomesExtension;
import net.megx.chon.core.ui.PhagesExtension;
import net.megx.chon.core.ui.RRNAExtension;
import net.megx.chon.core.ui.SamplesExtension;
import net.megx.chon.core.ui.SitesExtension;
import net.megx.chon.core.ui.TagCloudExtenstion;
import net.megx.chon.core.ui.VersioningExtension;
import net.megx.chon.core.ui.WOA5Extension;
import net.megx.chon.core.ui.WOD5Extension;

import org.chon.cms.content.utils.ChonTypeUtils;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected String getName() {
		return "net.megx.chon.core";
	}

	@Override
	protected void registerExtensions(JCRApplication app) {
		
		getBundleContext().registerService(TestRest.class.getName(),
				new TestRest(), null);

		//ServiceReference ref = getBundleContext().getServiceReference(MixsWsService.class.getName());
		//MixsWsService svc = (MixsWsService) getBundleContext().getService(ref);
		
		
		
		// register factory
		//getBundleContext().registerService(IContentNodeFactory.class.getName(),
		//		new MegxModuleContentNodeFactory(), null);

		try {
			ContentModel contentModel = app.createContentModelInstance(getName());
			
			//moved to mapserver bundle
			// ---
			removeType(contentModel, "megx.module.wms");
			removeNodeIfExists(contentModel, "/www/wms");
			// ---
			
			
			registerModuleType(contentModel, "megx.module.blast", Blast_Module.class);
			
			//registerModuleType(contentModel, "megx.module.wms", WMS_Module.class);
			registerModuleType(contentModel, "megx.module.woa", WOA_Module.class);
			registerModuleType(contentModel, "megx.module.wod", WOD_Module.class);
			registerModuleType(contentModel, "megx.module.login", LoginModule.class);
			
			//register common renderer for all above
			ChonTypeUtils.registerNodeRenderer(getBundleContext(), ModuleNodeRenderer.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// register ModuleNodeRenderer
		//Hashtable<String, String> props = new Hashtable<String, String>();
		//props.put("renderer", ModuleNodeRenderer.class.getName());
		//getBundleContext().registerService(INodeRenderer.class.getName(),
		//		new ModuleNodeRenderer(), props);

		app.regExtension("tagCloud", new TagCloudExtenstion(this.getBundleContext()));
		app.regExtension("genomes", new GenomesExtension(this.getBundleContext()));
		app.regExtension("viruses", new PhagesExtension(this.getBundleContext()));
		app.regExtension("metagenomes", new MetagenomesExtension(this.getBundleContext()));
		app.regExtension("rrna", new RRNAExtension(this.getBundleContext()));
		app.regExtension("sites", new SitesExtension(this.getBundleContext()));
		
		
		app.regExtension("samples", new SamplesExtension(this.getBundleContext()));
		app.regExtension("woa5", new WOA5Extension(this.getBundleContext()));
		app.regExtension("wod5", new WOD5Extension(this.getBundleContext()));
		
		app.regExtension("test_svc", new TestServicesExtension(this.getBundleContext()));
		app.regExtension("version", new VersioningExtension(this.getBundleContext()));
	}

	private void removeNodeIfExists(ContentModel contentModel, String path) throws RepositoryException {
		IContentNode node = contentModel.getContentNode(path);
		if(node != null) {
			node.getNode().remove();
			contentModel.getSession().save();
		}
	}

	private void registerModuleType(ContentModel contentModel, String typeName, Class<? extends IContentNode> clz) {
		try {
			//remove previous /etc/types/typeName node
			removeType(contentModel, typeName);
			// Create New /etc/types/typeName node (with updated refactoring if any)
			ChonTypeUtils.registerType(contentModel, typeName, clz, ModuleNodeRenderer.class, null, null);
			// finally, register content node type in OSGi registry
			ChonTypeUtils.registerContnetNodeClass(getBundleContext(), clz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void removeType(ContentModel contentModel, String typeName) throws Exception {
		Node node = ChonTypeUtils.getType(contentModel, typeName, false);
		if(node != null) node.remove();
	}
}
