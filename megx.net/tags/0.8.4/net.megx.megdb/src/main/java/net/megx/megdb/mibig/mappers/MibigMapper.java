package net.megx.megdb.mibig.mappers;

import net.megx.model.mibig.BgcDetailSubmission;
import net.megx.model.mibig.MibigSubmission;

public interface MibigMapper {
	
	public void storeMibigSubmission(MibigSubmission mibig);
	
	public void storeGeneInfo(BgcDetailSubmission bgc);
	
	public void storeNrpsInfo(BgcDetailSubmission bgc);
}
