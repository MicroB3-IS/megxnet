package net.megx.ui.table;

import net.megx.ui.table.demo.TableRestService;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected void registerExtensions(JCRApplication app) {
		getBundleContext().registerService(TableRestService.class.getName(),
				new TableRestService(), null);
		app.regExtension("dataTable", new DataTableExtension());
	}

	@Override
	protected String getName() {
		return "net.megx.ui.table";
	}
	
}
