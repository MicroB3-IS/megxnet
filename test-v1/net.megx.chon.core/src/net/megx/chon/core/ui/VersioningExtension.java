package net.megx.chon.core.ui;

import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.osgi.framework.BundleContext;

public class VersioningExtension extends AbstractMegxUIExtension{

	public VersioningExtension(BundleContext bundleContext) {
		super(bundleContext);
	}

	@Override
	public Object getTplObject(Request request, Response resonse, IContentNode node) {
		String version = System.getProperty("app.version","0.0.0");
		String [] values = version.split("\\.");
		int major = 0;
		int minor = 0;
		int patch = 0;
		if(values.length > 0)
			major = Integer.parseInt(values[0]);
		if(values.length > 1)
			minor = Integer.parseInt(values[1]);
		if(values.length > 2)
			patch = Integer.parseInt(values[2]);
		return new Version(major, minor, patch);
	}
	
	
	public class Version{
		private int major;
		private int minor;
		private int patch;
		
		
		
		public Version(int major, int minor, int patch) {
			this.major = major;
			this.minor = minor;
			this.patch = patch;
		}

		public String getFullVersion(){
			return String.format("%d.%d.%d", major, minor, patch);
		}

		public int getMinor() {
			return minor;
		}

		public int getMajor() {
			return major;
		}

		public int getPatch() {
			return patch;
		}
	}
}
