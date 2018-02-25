package org.papaorange.aliyun_slb_helper.model.listener;

public class HTTPSListener extends HTTPListener {

  private String serverCertificateId;
  private String caCertificateId;

  public String getServerCertificateId() {
    return serverCertificateId;
  }

  public void setServerCertificateId(String serverCertificateId) {
    this.serverCertificateId = serverCertificateId;
  }

  public String getCaCertificateId() {
    return caCertificateId;
  }

  public void setCaCertificateId(String caCertificateId) {
    this.caCertificateId = caCertificateId;
  }


}
