package org.papaorange.aliyun_slb_helper.model;

import java.util.List;
import com.aliyuncs.slb.model.v20140515.DescribeVServerGroupAttributeResponse.BackendServer;

public class VServerGroup {
	private String vServerGroupId;

	private String vServerGroupName;

	private List<BackendServer> backendServer;

	public String getVServerGroupId() {
		return this.vServerGroupId;
	}

	public void setVServerGroupId(String vServerGroupId) {
		this.vServerGroupId = vServerGroupId;
	}

	public String getVServerGroupName() {
		return this.vServerGroupName;
	}

	public void setVServerGroupName(String vServerGroupName) {
		this.vServerGroupName = vServerGroupName;
	}

	public List<BackendServer> getBackendServer() {
		return backendServer;
	}

	public void setBackendServer(List<BackendServer> backendServer) {
		this.backendServer = backendServer;
	}
}
