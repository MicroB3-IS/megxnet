package net.megx.model;

import java.util.Date;

public interface SimpleAudit {

	public Date getCreated();

	public String getCreatedBy();

	public Date getUpdated();

	public String getUpdatedBy();

	public void setCreatedBy(String createdBy);

	public void setUpdated(Date updated);

	public void setUpdatedBy(String updatedBy);

}