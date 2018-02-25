package org.papaorange.aliyun_slb_helper.model.listener;

public class TCPListener extends Listener {

  private Integer persistenceTimeout;
  private Integer establishedTimeout;

  public Integer getPersistenceTimeout() {
    return persistenceTimeout;
  }

  public void setPersistenceTimeout(Integer persistenceTimeout) {
    this.persistenceTimeout = persistenceTimeout;
  }

  public Integer getEstablishedTimeout() {
    return establishedTimeout;
  }

  public void setEstablishedTimeout(Integer establishedTimeout) {
    this.establishedTimeout = establishedTimeout;
  }

}
