package org.papaorange.aliyun_slb_helper.model;

import java.util.List;
import org.papaorange.aliyun_slb_helper.model.listener.Listener;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeResponse.BackendServer;

public class LoadBalancerObject {

  private String                       id;
  private String                       name;
  private String                       address;
  private String                       addrType;
  private String                       internetChargeType;
  private String                       payType;
  private String                       vSwitchId;
  private String                       vpcId;
  private String                       networkType;
  private Integer                      bandwidth;
  private String                       resourceGroupId;
  private String                       masterZoneId;
  private String                       salveZoneId;
  private String                       regionIdAlias;
  private String                       regionId;
  private String                       createTime;
  private String                       endTime;
  private List<Listener>               listeners;
  private List<BackendServer>          backendServers;
  private List<VServerGroup>           vServerGroups;
  private List<MasterSlaveServerGroup> masterSlaveGroups;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String ipAddr) {
    this.address = ipAddr;
  }

  public String getAddrType() {
    return addrType;
  }

  public void setAddrType(String addrType) {
    this.addrType = addrType;
  }

  public List<Listener> getListeners() {
    return listeners;
  }

  public void setListeners(List<Listener> listeners) {
    this.listeners = listeners;
  }

  public List<BackendServer> getBackendServers() {
    return backendServers;
  }

  public void setBackendServers(List<BackendServer> backendServers) {
    this.backendServers = backendServers;
  }

  public List<VServerGroup> getvServerGroups() {
    return vServerGroups;
  }

  public void setvServerGroups(List<VServerGroup> list) {
    this.vServerGroups = list;
  }

  public List<MasterSlaveServerGroup> getMasterSlaveGroups() {
    return masterSlaveGroups;
  }

  public void setMasterSlaveGroups(List<MasterSlaveServerGroup> list) {
    this.masterSlaveGroups = list;
  }

  public String getInternetChargeType() {
    return internetChargeType;
  }

  public void setInternetChargeType(String internetChargeType) {
    this.internetChargeType = internetChargeType;
  }

  public String getPayType() {
    return payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public String getvSwitchId() {
    return vSwitchId;
  }

  public void setvSwitchId(String vSwitchId) {
    this.vSwitchId = vSwitchId;
  }

  public String getVpcId() {
    return vpcId;
  }

  public void setVpcId(String vpcId) {
    this.vpcId = vpcId;
  }

  public String getNetworkType() {
    return networkType;
  }

  public void setNetworkType(String networkType) {
    this.networkType = networkType;
  }

  public Integer getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(Integer bandwidth) {
    this.bandwidth = bandwidth;
  }

  public String getResourceGroupId() {
    return resourceGroupId;
  }

  public void setResourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
  }

  public String getMasterZoneId() {
    return masterZoneId;
  }

  public void setMasterZoneId(String masterZoneId) {
    this.masterZoneId = masterZoneId;
  }

  public String getSalveZoneId() {
    return salveZoneId;
  }

  public void setSalveZoneId(String salveZoneId) {
    this.salveZoneId = salveZoneId;
  }

  public String getRegionId() {
    return regionId;
  }

  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getRegionIdAlias() {
    return regionIdAlias;
  }

  public void setRegionIdAlias(String region) {
    this.regionIdAlias = region;
  }

}
