package net.megx.security.api;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.osgi.framework.BundleContext;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected void registerExtensions(JCRApplication app) {
		
	}

	@Override
	protected String getName() {
		return "net.megx.security.api";
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		System.out.println("security>api>bundle> START");
		Reader reader = Resources.getResourceAsReader("my-batis-config.xml");
		System.out.println("security>api>bundle> READER: " + reader);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
		System.out.println("security>api>bundle> SQL FACTORY: " + factory);
		
	}
	
}
