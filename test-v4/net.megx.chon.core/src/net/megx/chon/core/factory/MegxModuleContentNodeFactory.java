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
package net.megx.chon.core.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import net.megx.chon.core.model.impl.LoginModule;
import net.megx.chon.core.model.impl.WMS_Module;
import net.megx.chon.core.model.impl.WOA_Module;

import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.cms.model.content.IContentNodeFactory;

public class MegxModuleContentNodeFactory implements IContentNodeFactory {
	private Map<String, Class<? extends IContentNode>> cnTypesMap = new HashMap<String, Class<? extends IContentNode>>();

	public MegxModuleContentNodeFactory() {
		cnTypesMap.put(WMS_Module.class.getName(), WMS_Module.class);
		cnTypesMap.put(WOA_Module.class.getName(), WOA_Module.class);
		cnTypesMap.put(LoginModule.class.getName(), LoginModule.class);
	}

	@Override
	public IContentNode createIntance(String contentNodeClass,
			ContentModel contentModel, Node node, IContentNode typeDesc) {

		Class<? extends IContentNode> clzz = cnTypesMap.get(contentNodeClass);
		try {
			Constructor<? extends IContentNode> ctor = clzz.getConstructor(
					ContentModel.class, Node.class, IContentNode.class);
			return ctor.newInstance(contentModel, node, typeDesc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean canCreate(String contentNodeClass) {
		return cnTypesMap.containsKey(contentNodeClass);
	}
}