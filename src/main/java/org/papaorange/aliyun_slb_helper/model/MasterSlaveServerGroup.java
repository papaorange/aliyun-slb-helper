package org.papaorange.aliyun_slb_helper.model;

import java.util.List;
import com.aliyuncs.slb.model.v20140515.DescribeMasterSlaveVServerGroupAttributeResponse.MasterSlaveBackendServer;

public class MasterSlaveServerGroup {
	private String masterSlaveServerGroupId;

	private String masterSlaveServerGroupName;

	private List<MasterSlaveBackendServer> masterSlaveBackendServers;

	public String getMasterSlaveServerGroupId() {
		return this.masterSlaveServerGroupId;
	}

	public void setMasterSlaveServerGroupId(String masterSlaveServerGroupId) {
		this.masterSlaveServerGroupId = masterSlaveServerGroupId;
	}

	public String getMasterSlaveServerGroupName() {
		return this.masterSlaveServerGroupName;
	}

	public void setMasterSlaveServerGroupName(
			String masterSlaveServerGroupName) {
		this.masterSlaveServerGroupName = masterSlaveServerGroupName;
	}

	public List<MasterSlaveBackendServer> getMasterSlaveBackendServers() {
		return masterSlaveBackendServers;
	}

	public void setMasterSlaveBackendServers(
			List<MasterSlaveBackendServer> masterSlaveBackendServers) {
		this.masterSlaveBackendServers = masterSlaveBackendServers;
	}

}
