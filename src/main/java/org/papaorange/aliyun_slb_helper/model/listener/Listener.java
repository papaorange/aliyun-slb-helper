package org.papaorange.aliyun_slb_helper.model.listener;

public abstract class Listener {

	private Integer listenerPort;
	private Integer backendServerPort;
	private String protocol;
	private String realServerType;
	private String realServerId;
	private String isForwarded;
	private String status;
	private Integer bandwidth;
	private String scheduler;
	private String healthCheck;
	private String healthCheckType;
	private Integer healthyThreshold;
	private Integer unhealthyThreshold;
	private Integer healthCheckConnectTimeout;
	private Integer healthCheckInterval;
	private String healthCheckDomain;
	private String healthCheckURI;
	private String healthCheckHttpCode;
	private String vServerGroupId;
	private String materSlaveServerGroupId;
	private Integer healthCheckConnectPort;
	private String accessControlStatus;
	private String sourceItems;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getRealServerType() {
		return realServerType;
	}

	public void setRealServerType(String realServerType) {
		this.realServerType = realServerType;
	}

	public String getRealServerId() {
		return realServerId;
	}

	public void setRealServerId(String realServerId) {
		this.realServerId = realServerId;
	}

	public String getIsForwarded() {
		return isForwarded;
	}

	public void setIsForwarded(String isForwarded) {
		this.isForwarded = isForwarded;
	}

	public Integer getListenerPort() {
		return listenerPort;
	}

	public void setListenerPort(Integer listenerPort) {
		this.listenerPort = listenerPort;
	}

	public Integer getBackendServerPort() {
		return backendServerPort;
	}

	public void setBackendServerPort(Integer backendServerPort) {
		this.backendServerPort = backendServerPort;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getScheduler() {
		return scheduler;
	}

	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

	public String getHealthCheck() {
		return healthCheck;
	}

	public void setHealthCheck(String healthCheck) {
		this.healthCheck = healthCheck;
	}

	public String getHealthCheckType() {
		return healthCheckType;
	}

	public void setHealthCheckType(String healthCheckType) {
		this.healthCheckType = healthCheckType;
	}

	public Integer getHealthyThreshold() {
		return healthyThreshold;
	}

	public void setHealthyThreshold(Integer healthyThreshold) {
		this.healthyThreshold = healthyThreshold;
	}

	public Integer getUnhealthyThreshold() {
		return unhealthyThreshold;
	}

	public void setUnhealthyThreshold(Integer unhealthyThreshold) {
		this.unhealthyThreshold = unhealthyThreshold;
	}

	public Integer getHealthCheckInterval() {
		return healthCheckInterval;
	}

	public void setHealthCheckInterval(Integer healthCheckInterval) {
		this.healthCheckInterval = healthCheckInterval;
	}

	public String getHealthCheckDomain() {
		return healthCheckDomain;
	}

	public void setHealthCheckDomain(String healthCheckDomain) {
		this.healthCheckDomain = healthCheckDomain;
	}

	public String getHealthCheckURI() {
		return healthCheckURI;
	}

	public void setHealthCheckURI(String healthCheckURI) {
		this.healthCheckURI = healthCheckURI;
	}

	public String getHealthCheckHttpCode() {
		return healthCheckHttpCode;
	}

	public void setHealthCheckHttpCode(String healthCheckHttpCode) {
		this.healthCheckHttpCode = healthCheckHttpCode;
	}

	public String getvServerGroupId() {
		return vServerGroupId;
	}

	public void setvServerGroupId(String vServerGroupId) {
		this.vServerGroupId = vServerGroupId;
	}

	public String getMaterSlaveServerGroupId() {
		return materSlaveServerGroupId;
	}

	public void setMaterSlaveServerGroupId(String materSlaveServerGroupId) {
		this.materSlaveServerGroupId = materSlaveServerGroupId;
	}

	public Integer getHealthCheckConnectTimeout() {
		return healthCheckConnectTimeout;
	}

	public void setHealthCheckConnectTimeout(
			Integer healthCheckConnectTimeout) {
		this.healthCheckConnectTimeout = healthCheckConnectTimeout;
	}

	public Integer getHealthCheckConnectPort() {
		return healthCheckConnectPort;
	}

	public void setHealthCheckConnectPort(Integer healthCheckConnectPort) {
		this.healthCheckConnectPort = healthCheckConnectPort;
	}

	public String getAccessControlStatus() {
		return accessControlStatus;
	}

	public void setAccessControlStatus(String accessControlStatus) {
		this.accessControlStatus = accessControlStatus;
	}

	public String getSourceItems() {
		return sourceItems;
	}

	public void setSourceItems(String sourceItems) {
		this.sourceItems = sourceItems;
	}

}
