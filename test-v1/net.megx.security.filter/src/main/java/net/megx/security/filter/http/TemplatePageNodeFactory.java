package net.megx.security.filter.http;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.cms.model.content.IContentNodeFactory;

public abstract class TemplatePageNodeFactory implements IContentNodeFactory{

	protected Log log = LogFactory.getLog(getClass());
	private Map<String, Class<? extends IContentNode>> definitions = new HashMap<String, Class<? extends IContentNode>>();
	
	public TemplatePageNodeFactory() {
		initialize();
	}
	
	@Override
	public boolean canCreate(String className) {
		return definitions.containsKey(className);
	}

	@Override
	public IContentNode createIntance(String classame, ContentModel cm,
			Node node, IContentNode contentNode) {
		
		Class<? extends IContentNode> clz = definitions.get(classame);
		
		try {
			Constructor<? extends IContentNode> constructor = 
					clz.getConstructor(ContentModel.class, Node.class, IContentNode.class);
			
			IContentNode instance = constructor.newInstance(cm,node,contentNode);
			return instance;
		} catch (Exception e){
			log.error(e);
		}
		
		return null;
	}
	
	protected abstract void initialize();
	
	protected void register(Class<? extends IContentNode> clz){
		definitions.put(clz.getName(), clz);
	}
}
