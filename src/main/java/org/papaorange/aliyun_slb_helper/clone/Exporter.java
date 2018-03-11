package org.papaorange.aliyun_slb_helper.clone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.papaorange.aliyun_slb_helper.api.SLBHelperAPI;
import org.papaorange.aliyun_slb_helper.model.LoadBalancerObject;
import org.papaorange.aliyun_slb_helper.model.MasterSlaveServerGroup;
import org.papaorange.aliyun_slb_helper.model.VServerGroup;
import org.papaorange.aliyun_slb_helper.model.listener.HTTPListener;
import org.papaorange.aliyun_slb_helper.model.listener.HTTPSListener;
import org.papaorange.aliyun_slb_helper.model.listener.Listener;
import org.papaorange.aliyun_slb_helper.model.listener.TCPListener;
import org.papaorange.aliyun_slb_helper.model.listener.UDPListener;
import com.aliyuncs.slb.model.v20140515.DescribeListenerAccessControlAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerHTTPListenerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerHTTPSListenerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerTCPListenerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerUDPListenerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeMasterSlaveVServerGroupAttributeResponse.MasterSlaveBackendServer;
import com.aliyuncs.slb.model.v20140515.DescribeRulesResponse.Rule;
import com.aliyuncs.slb.model.v20140515.DescribeVServerGroupAttributeResponse.BackendServer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Exporter {

  public static String exportLoadBalancer(String region, String lbid, boolean genfile)
      throws IOException {

    LoadBalancerObject lbobj = new LoadBalancerObject();
    List<Listener> listeners = new ArrayList<>();

    DescribeLoadBalancerAttributeResponse response =
        SLBHelperAPI.describeLoadBalancerAttr(region, lbid);

    lbobj.setId(response.getLoadBalancerId());
    lbobj.setAddress(response.getAddress());
    lbobj.setName(response.getLoadBalancerName());
    lbobj.setAddrType(response.getAddressType());
    lbobj.setRegionId(response.getRegionId());
    lbobj.setRegionIdAlias(response.getRegionIdAlias());
    lbobj.setBackendServers(response.getBackendServers());
    lbobj.setNetworkType(response.getNetworkType());
    lbobj.setBandwidth(response.getBandwidth());
    lbobj.setCreateTime(response.getCreateTime());
    lbobj.setEndTime(response.getEndTime());
    lbobj.setInternetChargeType(response.getInternetChargeType());
    lbobj.setPayType(response.getPayType());
    lbobj.setMasterZoneId(response.getMasterZoneId());
    lbobj.setSalveZoneId(response.getSlaveZoneId());
    lbobj.setVpcId(response.getVpcId());
    lbobj.setvSwitchId(response.getVSwitchId());
    lbobj.setLoadBalancerSpec(response.getLoadBalancerSpec());
    List<VServerGroup> vGroups = new ArrayList<>();
    VServerGroup vGroup = new VServerGroup();
    SLBHelperAPI.describeVServerGroups(lbobj.getRegionIdAlias(), lbid).getVServerGroups()
        .forEach(group -> {
          vGroup.setVServerGroupId(group.getVServerGroupId());
          vGroup.setVServerGroupName(group.getVServerGroupName());
          List<BackendServer> backendServers = SLBHelperAPI
              .describeVServerGroupsAttr(lbobj.getRegionIdAlias(), group.getVServerGroupId())
              .getBackendServers();
          vGroup.setBackendServer(backendServers);
          vGroups.add(vGroup);
        });
    lbobj.setvServerGroups(vGroups);

    List<MasterSlaveServerGroup> masterSlaveServerGroups = new ArrayList<>();
    MasterSlaveServerGroup masterSlaveServerGroup = new MasterSlaveServerGroup();
    SLBHelperAPI.describeMasterSlaveVServerGroups(lbobj.getRegionIdAlias(), lbid)
        .getMasterSlaveVServerGroups().forEach(group -> {
          masterSlaveServerGroup.setMasterSlaveServerGroupId(group.getMasterSlaveVServerGroupId());
          masterSlaveServerGroup
              .setMasterSlaveServerGroupName(group.getMasterSlaveVServerGroupName());
          List<MasterSlaveBackendServer> masterSlaveVServerBackendServers =
              SLBHelperAPI.describeMasterSlaveVServerGroupAttr(lbobj.getRegionIdAlias(),
                  group.getMasterSlaveVServerGroupId()).getMasterSlaveBackendServers();
          masterSlaveServerGroup.setMasterSlaveBackendServers(masterSlaveVServerBackendServers);
          masterSlaveServerGroups.add(masterSlaveServerGroup);
        });
    lbobj.setMasterSlaveGroups(masterSlaveServerGroups);

    response.getListenerPortsAndProtocol().forEach(protocolAndPort -> {
      Listener listener = null;
      String protocol = protocolAndPort.getListenerProtocol();
      int port = protocolAndPort.getListenerPort();

      switch (protocol) {
        case "tcp": {
          listener = new TCPListener();
          DescribeLoadBalancerTCPListenerAttributeResponse attr =
              SLBHelperAPI.describeLoadBalancerTCPListenerAttr(
                  SLBHelperAPI.describeLoadBalancer(lbobj.getRegionIdAlias(), lbid), port);
          if (attr.getMasterSlaveServerGroupId() != null) {
            listener.setRealServerType("masterSlaveGroup");
            listener.setRealServerId(attr.getMasterSlaveServerGroupId());
          } else if (attr.getVServerGroupId() != null) {
            listener.setRealServerType("vServerGroup");
            listener.setRealServerId(attr.getVServerGroupId());
          } else {
            listener.setRealServerType("backendServers");
            attr.setBackendServerPort(attr.getBackendServerPort());
            listener.setBackendServerPort(attr.getBackendServerPort());
          }

          listener.setListenerPort(port);
          listener.setProtocol(protocol);
          listener.setBandwidth(attr.getBandwidth());
          listener.setScheduler(attr.getScheduler());
          listener.setHealthCheck(attr.getHealthCheck());
          listener.setHealthCheckType(attr.getHealthCheckType());
          listener.setHealthCheckConnectPort(attr.getHealthCheckConnectPort());
          listener.setHealthyThreshold(attr.getHealthyThreshold());
          listener.setHealthCheckConnectTimeout(attr.getHealthCheckConnectTimeout());
          listener.setHealthCheckInterval(attr.getHealthCheckInterval());
          listener.setHealthCheckDomain(attr.getHealthCheckDomain());
          listener.setHealthCheckURI(attr.getHealthCheckURI());
          listener.setHealthCheckHttpCode(attr.getHealthCheckHttpCode());
          listener.setUnhealthyThreshold(attr.getUnhealthyThreshold());
          ((TCPListener) listener).setEstablishedTimeout(attr.getEstablishedTimeout());
          ((TCPListener) listener).setPersistenceTimeout(attr.getPersistenceTimeout());

        }
          break;
        case "udp": {
          listener = new UDPListener();

          DescribeLoadBalancerUDPListenerAttributeResponse attr =
              SLBHelperAPI.describeLoadBalancerUDPListenerAttr(
                  SLBHelperAPI.describeLoadBalancer(lbobj.getRegionIdAlias(), lbid), port);
          if (attr.getMasterSlaveServerGroupId() != null) {
            listener.setRealServerType("masterSlaveGroup");
            listener.setRealServerId(attr.getMasterSlaveServerGroupId());
          } else if (attr.getVServerGroupId() != null) {
            listener.setRealServerType("vServerGroup");
            listener.setRealServerId(attr.getVServerGroupId());
          } else {
            listener.setRealServerType("backendServers");
            attr.setBackendServerPort(attr.getBackendServerPort());
            listener.setBackendServerPort(attr.getBackendServerPort());
          }

          listener.setListenerPort(port);
          listener.setProtocol(protocol);
          listener.setBandwidth(attr.getBandwidth());
          listener.setScheduler(attr.getScheduler());
          listener.setHealthCheckConnectPort(attr.getHealthCheckConnectPort());
          listener.setHealthCheck(attr.getHealthCheck());
          listener.setHealthyThreshold(attr.getHealthyThreshold());
          listener.setHealthCheckConnectTimeout(attr.getHealthCheckConnectTimeout());
          listener.setHealthCheckInterval(attr.getHealthCheckInterval());
          listener.setUnhealthyThreshold(attr.getUnhealthyThreshold());
          ((UDPListener) listener).setPersistenceTimeout(attr.getPersistenceTimeout());
          ((UDPListener) listener).setHealthCheckExp(attr.getHealthCheckExp());
          ((UDPListener) listener).setHealthCheckReq(attr.getHealthCheckReq());

        }

          break;
        case "http": {
          listener = new HTTPListener();
          DescribeLoadBalancerHTTPListenerAttributeResponse attr =
              SLBHelperAPI.describeLoadBalancerHTTPListenerAttr(
                  SLBHelperAPI.describeLoadBalancer(lbobj.getRegionIdAlias(), lbid), port);

          if (attr.getVServerGroupId() != null) {
            listener.setRealServerType("vServerGroup");
            listener.setRealServerId(attr.getVServerGroupId());
          } else {
            listener.setRealServerType("backendServers");
            attr.setBackendServerPort(attr.getBackendServerPort());
            listener.setBackendServerPort(attr.getBackendServerPort());
          }

          listener.setListenerPort(port);
          listener.setProtocol(protocol);
          listener.setBandwidth(attr.getBandwidth());
          listener.setScheduler(attr.getScheduler());
          listener.setHealthCheck(attr.getHealthCheck());
          listener.setHealthCheckConnectPort(attr.getHealthCheckConnectPort());
          listener.setHealthyThreshold(attr.getHealthyThreshold());
          listener.setHealthCheckConnectTimeout(attr.getHealthCheckTimeout());
          listener.setHealthCheckInterval(attr.getHealthCheckInterval());
          listener.setHealthCheckDomain(attr.getHealthCheckDomain());
          listener.setHealthCheckURI(attr.getHealthCheckURI());
          listener.setHealthCheckHttpCode(attr.getHealthCheckHttpCode());
          listener.setUnhealthyThreshold(attr.getUnhealthyThreshold());
          ((HTTPListener) (listener)).setCookie(attr.getCookie());
          ((HTTPListener) (listener)).setCookieTimeout(attr.getCookieTimeout());
          ((HTTPListener) (listener)).setGzip(attr.getGzip());
          ((HTTPListener) (listener)).setStickySessionType(attr.getStickySessionType());
          ((HTTPListener) (listener)).setxForwardedFor(attr.getXForwardedFor());
          ((HTTPListener) (listener)).setxForwardedFor_proto(attr.getXForwardedFor_proto());
          ((HTTPListener) (listener)).setxForwardedFor_SLBIP(attr.getXForwardedFor_SLBIP());
          ((HTTPListener) (listener)).setxForwardedFor_SLBID(attr.getXForwardedFor_SLBID());

          List<Rule> rules =
              SLBHelperAPI.describeRules(lbobj.getRegionIdAlias(), lbid, port).getRules();
          ((HTTPListener) (listener)).setRules(rules);
        }
          break;
        case "https": {
          listener = new HTTPSListener();
          DescribeLoadBalancerHTTPSListenerAttributeResponse attr =
              SLBHelperAPI.describeLoadBalancerHTTPSListenerAttr(
                  SLBHelperAPI.describeLoadBalancer(lbobj.getRegionIdAlias(), lbid), port);
          if (attr.getVServerGroupId() != null) {
            listener.setRealServerType("vServerGroup");
            listener.setRealServerId(attr.getVServerGroupId());
          } else {
            listener.setRealServerType("backendServers");
            attr.setBackendServerPort(attr.getBackendServerPort());
            listener.setBackendServerPort(attr.getBackendServerPort());
          }

          listener.setListenerPort(port);
          listener.setProtocol(protocol);
          listener.setBandwidth(attr.getBandwidth());
          listener.setScheduler(attr.getScheduler());
          listener.setHealthCheck(attr.getHealthCheck());
          listener.setHealthCheckConnectPort(attr.getHealthCheckConnectPort());
          listener.setHealthyThreshold(attr.getHealthyThreshold());
          listener.setHealthCheckConnectTimeout(attr.getHealthCheckTimeout());
          listener.setHealthCheckInterval(attr.getHealthCheckInterval());
          listener.setHealthCheckDomain(attr.getHealthCheckDomain());
          listener.setHealthCheckURI(attr.getHealthCheckURI());
          listener.setHealthCheckHttpCode(attr.getHealthCheckHttpCode());
          listener.setUnhealthyThreshold(attr.getUnhealthyThreshold());
          ((HTTPSListener) (listener)).setCookie(attr.getCookie());
          ((HTTPSListener) (listener)).setCookieTimeout(attr.getCookieTimeout());
          ((HTTPSListener) (listener)).setGzip(attr.getGzip());
          ((HTTPSListener) (listener)).setStickySessionType(attr.getStickySessionType());
          ((HTTPSListener) (listener)).setxForwardedFor(attr.getXForwardedFor());
          ((HTTPSListener) (listener)).setxForwardedFor_proto(attr.getXForwardedFor_proto());
          ((HTTPSListener) (listener)).setxForwardedFor_SLBIP(attr.getXForwardedFor_SLBIP());
          ((HTTPSListener) (listener)).setxForwardedFor_SLBID(attr.getXForwardedFor_SLBID());
          ((HTTPSListener) (listener)).setServerCertificateId(attr.getServerCertificateId());
          List<Rule> rules =
              SLBHelperAPI.describeRules(lbobj.getRegionIdAlias(), lbid, port).getRules();
          ((HTTPSListener) (listener)).setRules(rules);
        }
          break;
        default:
          break;
      }

      DescribeListenerAccessControlAttributeResponse laca =
          SLBHelperAPI.describeListenerAccessControlAttr(lbobj.getRegionIdAlias(), lbid, port);
      listener.setAccessControlStatus(laca.getAccessControlStatus());
      listener.setSourceItems(laca.getSourceItems());
      listeners.add(listener);
      lbobj.setListeners(listeners);
    });

    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.setSerializationInclusion(Include.NON_EMPTY);
    mapper.enableDefaultTyping(); // default to using DefaultTyping.OBJECT_AND_NON_CONCRETE
    // mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    String jsonlist = null;
    try {
      jsonlist = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lbobj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println(jsonlist);

    if (genfile) {
      FileUtils.write(
          new File(
              "slbs/" + lbobj.getRegionIdAlias() + "/" + lbid + "-" + lbobj.getAddress() + ".json"),
          jsonlist, "UTF-8", false);
    }

    return jsonlist;
  }

  public static String exportLoadBalancer(String ip) {

    return null;
  }

}
