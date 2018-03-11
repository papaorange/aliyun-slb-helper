package org.papaorange.aliyun_slb_helper.model.listener;

public class UDPListener extends Listener {
	private Integer	presistenceTimeout;
	private String	healthCheckExp;
	private String	healthCheckReq;

	public Integer getPersistenceTimeout() {
		return presistenceTimeout;
	}

	public void setPersistenceTimeout(Integer persistenceTimeout) {
		this.presistenceTimeout = persistenceTimeout;
	}

	public String getHealthCheckExp() {
		return healthCheckExp;
	}

	public void setHealthCheckExp(String healthCheckExp) {
		this.healthCheckExp = healthCheckExp;
	}

	public String getHealthCheckReq() {
		return healthCheckReq;
	}

	public void setHealthCheckReq(String healthCheckReq) {
		this.healthCheckReq = healthCheckReq;
	}

}
