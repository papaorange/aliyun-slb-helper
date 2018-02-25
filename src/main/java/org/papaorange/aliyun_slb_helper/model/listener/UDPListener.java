package org.papaorange.aliyun_slb_helper.model.listener;

public class UDPListener extends Listener {
	private Integer persistenceTimeout;
	private String HealthCheckExp;
	private String HealthCheckReq;

	public Integer getPersistenceTimeout() {
		return persistenceTimeout;
	}

	public void setPersistenceTimeout(Integer persistenceTimeout) {
		this.persistenceTimeout = persistenceTimeout;
	}

	public String getHealthCheckExp() {
		return HealthCheckExp;
	}

	public void setHealthCheckExp(String healthCheckExp) {
		HealthCheckExp = healthCheckExp;
	}

	public String getHealthCheckReq() {
		return HealthCheckReq;
	}

	public void setHealthCheckReq(String healthCheckReq) {
		HealthCheckReq = healthCheckReq;
	}

}
