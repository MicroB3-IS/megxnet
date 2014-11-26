package net.megx.mibig.model;

import java.util.List;

public class BgcDetailRawSubmission {

	private String test;
	private String tablename;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	@Override
	public String toString() {
		return "BgcDetailRawSubmission [test=" + test + ", tablename="
				+ tablename + "]";
	}

}
