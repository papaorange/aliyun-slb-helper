package org.papaorange.aliyun_slb_helper.api;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.slb.model.v20140515.AddBackendServersRequest;
import com.aliyuncs.slb.model.v20140515.AddBackendServersResponse;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerHTTPListenerRequest;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerHTTPListenerResponse;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerHTTPSListenerRequest;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerHTTPSListenerResponse;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerRequest;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerResponse;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerTCPListenerRequest;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerTCPListenerResponse;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerUDPListenerRequest;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerUDPListenerResponse;
import com.aliyuncs.slb.model.v20140515.CreateMasterSlaveVServerGroupRequest;
import com.aliyuncs.slb.model.v20140515.CreateMasterSlaveVServerGroupResponse;
import com.aliyuncs.slb.model.v20140515.CreateVServerGroupRequest;
import com.aliyuncs.slb.model.v20140515.CreateVServerGroupResponse;
import com.aliyuncs.slb.model.v20140515.DescribeCACertificatesResponse;
import com.aliyuncs.slb.model.v20140515.DescribeListenerAccessControlAttributeRequest;
import com.aliyuncs.slb.model.v20140515.DescribeListenerAccessControlAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeRequest;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerHTTPListenerAttributeRequest;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerHTTPListenerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerHTTPSListenerAttributeRequest;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerHTTPSListenerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerTCPListenerAttributeRequest;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerTCPListenerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerUDPListenerAttributeRequest;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerUDPListenerAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancersRequest;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancersResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancersResponse.LoadBalancer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliyuncs.slb.model.v20140515.DescribeMasterSlaveVServerGroupAttributeRequest;
import com.aliyuncs.slb.model.v20140515.DescribeMasterSlaveVServerGroupAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeMasterSlaveVServerGroupsRequest;
import com.aliyuncs.slb.model.v20140515.DescribeMasterSlaveVServerGroupsResponse;
import com.aliyuncs.slb.model.v20140515.DescribeRegionsRequest;
import com.aliyuncs.slb.model.v20140515.DescribeRegionsResponse;
import com.aliyuncs.slb.model.v20140515.DescribeRulesRequest;
import com.aliyuncs.slb.model.v20140515.DescribeRulesResponse;
import com.aliyuncs.slb.model.v20140515.DescribeServerCertificatesResponse;
import com.aliyuncs.slb.model.v20140515.DescribeVServerGroupAttributeRequest;
import com.aliyuncs.slb.model.v20140515.DescribeVServerGroupAttributeResponse;
import com.aliyuncs.slb.model.v20140515.DescribeVServerGroupsRequest;
import com.aliyuncs.slb.model.v20140515.DescribeVServerGroupsResponse;

public class SLBHelperAPI {

  private static Logger logger = LogManager.getLogger(SLBHelperAPI.class);

  public static List<String> describeRegions() {
    logger.debug("method:describeRegions");
    IAcsClient client = new SLBHelperAPIClient().getDefaultClient();
    List<String> regions = new ArrayList<String>();
    DescribeRegionsRequest request = new DescribeRegionsRequest();
    try {
      DescribeRegionsResponse response = client.getAcsResponse(request);
      response.getRegions().forEach(x -> regions.add(x.getRegionId()));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return regions;
  }

  public static List<LoadBalancer> describeLoadBalancers(String region) {
    logger.debug("method:describeLoadBalancers");
    IAcsClient client = new SLBHelperAPIClient().getClient(region);
    List<LoadBalancer> lbs = new ArrayList<LoadBalancer>();
    DescribeLoadBalancersRequest request = new DescribeLoadBalancersRequest();
    request.setRegionId(region);
    logger.debug("method:describeLoadBalancers:region:" + region);

    try {
      DescribeLoadBalancersResponse response = client.getAcsResponse(request);
      response.getLoadBalancers().forEach(x -> {
        lbs.add(x);
        logger.debug("method:describeLoadBalancers:responselbid:" + x.getLoadBalancerId());
      });
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return lbs;
  }

  public static LoadBalancer describeLoadBalancer(String region, String lbid) {
    logger.debug("method:describeLoadBalancer");

    IAcsClient client = new SLBHelperAPIClient().getClient(region);
    LoadBalancer lb = null;
    DescribeLoadBalancersRequest request = new DescribeLoadBalancersRequest();
    request.setRegionId(region);
    request.setLoadBalancerId(lbid);
    logger.debug("method:describeLoadBalancer:region:" + region + "lbid:" + lbid);

    try {
      DescribeLoadBalancersResponse response = client.getAcsResponse(request);
      lb = response.getLoadBalancers().get(0);
    } catch (Exception e) {
      e.printStackTrace();
      logger.fatal(e);
      return null;
    }
    return lb;
  }

  public static DescribeLoadBalancerAttributeResponse describeLoadBalancerAttr(String region,
      String lbid) {
    logger.debug("method:describeLoadBalancerAttr");

    IAcsClient client = new SLBHelperAPIClient().getDefaultClient();
    // 设置参数
    DescribeLoadBalancerAttributeRequest request = new DescribeLoadBalancerAttributeRequest();
    request.setLoadBalancerId(lbid);
    request.setRegionId(region);
    logger.debug("method:describeLoadBalancerAttr:lbid:" + lbid);

    DescribeLoadBalancerAttributeResponse response = null;
    // 发起请求
    try {
      response = client.getAcsResponse(request);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeLoadBalancerAttributeResponse describeLoadBalancerAttr(LoadBalancer lb) {
    IAcsClient client = new SLBHelperAPIClient().getClient(lb.getRegionId().toString());
    // 设置参数
    DescribeLoadBalancerAttributeRequest request = new DescribeLoadBalancerAttributeRequest();
    request.setLoadBalancerId(lb.getLoadBalancerId());
    DescribeLoadBalancerAttributeResponse response = null;
    // 发起请求
    try {
      response = client.getAcsResponse(request);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeLoadBalancerTCPListenerAttributeResponse describeLoadBalancerTCPListenerAttr(
      LoadBalancer lb, int port) {
    IAcsClient client = new SLBHelperAPIClient().getClient(lb.getRegionId().toString());
    // 设置参数
    DescribeLoadBalancerTCPListenerAttributeRequest request =
        new DescribeLoadBalancerTCPListenerAttributeRequest();
    request.setLoadBalancerId(lb.getLoadBalancerId());
    request.setListenerPort(port);
    DescribeLoadBalancerTCPListenerAttributeResponse response = null;
    // 发起请求
    try {
      response = client.getAcsResponse(request);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeLoadBalancerUDPListenerAttributeResponse describeLoadBalancerUDPListenerAttr(
      LoadBalancer lb, int port) {
    IAcsClient client = new SLBHelperAPIClient().getClient(lb.getRegionId().toString());
    // 设置参数
    DescribeLoadBalancerUDPListenerAttributeRequest request =
        new DescribeLoadBalancerUDPListenerAttributeRequest();
    request.setLoadBalancerId(lb.getLoadBalancerId());
    request.setListenerPort(port);
    DescribeLoadBalancerUDPListenerAttributeResponse response = null;
    // 发起请求
    try {
      response = client.getAcsResponse(request);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeLoadBalancerHTTPListenerAttributeResponse describeLoadBalancerHTTPListenerAttr(
      LoadBalancer lb, int port) {
    IAcsClient client = new SLBHelperAPIClient().getClient(lb.getRegionId().toString());
    // 设置参数
    DescribeLoadBalancerHTTPListenerAttributeRequest request =
        new DescribeLoadBalancerHTTPListenerAttributeRequest();
    request.setLoadBalancerId(lb.getLoadBalancerId());
    request.setListenerPort(port);
    DescribeLoadBalancerHTTPListenerAttributeResponse response = null;
    // 发起请求
    try {
      response = client.getAcsResponse(request);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeLoadBalancerHTTPSListenerAttributeResponse describeLoadBalancerHTTPSListenerAttr(
      LoadBalancer lb, int port) {
    IAcsClient client = new SLBHelperAPIClient().getClient(lb.getRegionId().toString());
    // 设置参数
    DescribeLoadBalancerHTTPSListenerAttributeRequest request =
        new DescribeLoadBalancerHTTPSListenerAttributeRequest();
    request.setLoadBalancerId(lb.getLoadBalancerId());
    request.setListenerPort(port);
    DescribeLoadBalancerHTTPSListenerAttributeResponse response = null;
    // 发起请求
    try {
      response = client.getAcsResponse(request);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeVServerGroupsResponse describeVServerGroups(String region, String lbid) {
    IAcsClient client = new SLBHelperAPIClient().getClient(region);
    DescribeVServerGroupsResponse response = null;
    // 设置参数
    DescribeVServerGroupsRequest request = new DescribeVServerGroupsRequest();
    request.setLoadBalancerId(lbid);
    request.setRegionId(region);

    // 发起请求
    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeMasterSlaveVServerGroupsResponse describeMasterSlaveVServerGroups(
      String region, String lbid) {
    IAcsClient client = new SLBHelperAPIClient().getClient(region);
    DescribeMasterSlaveVServerGroupsResponse response = null;
    // 设置参数
    DescribeMasterSlaveVServerGroupsRequest request = new DescribeMasterSlaveVServerGroupsRequest();
    request.setLoadBalancerId(lbid);
    request.setRegionId(region);

    // 发起请求
    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeVServerGroupAttributeResponse describeVServerGroupsAttr(String region,
      String groupId) {
    IAcsClient client = new SLBHelperAPIClient().getClient(region);
    DescribeVServerGroupAttributeResponse response = null;
    // 设置参数
    DescribeVServerGroupAttributeRequest request = new DescribeVServerGroupAttributeRequest();
    request.setVServerGroupId(groupId);
    request.setRegionId(region);

    // 发起请求
    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeMasterSlaveVServerGroupAttributeResponse describeMasterSlaveVServerGroupAttr(
      String region, String groupId) {
    IAcsClient client = new SLBHelperAPIClient().getClient(region);
    DescribeMasterSlaveVServerGroupAttributeResponse response = null;
    // 设置参数
    DescribeMasterSlaveVServerGroupAttributeRequest request =
        new DescribeMasterSlaveVServerGroupAttributeRequest();
    request.setMasterSlaveVServerGroupId(groupId);
    request.setRegionId(region);

    // 发起请求
    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeRulesResponse describeRules(String region, String lbid, Integer port) {
    IAcsClient client = new SLBHelperAPIClient().getClient(region);
    DescribeRulesResponse response = null;

    // 设置参数
    DescribeRulesRequest describeRules = new DescribeRulesRequest();
    describeRules.setRegionId(region);
    describeRules.setLoadBalancerId(lbid);
    describeRules.setListenerPort(port);

    // 发起请求
    try {
      response = client.getAcsResponse(describeRules);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static DescribeListenerAccessControlAttributeResponse describeListenerAccessControlAttr(
      String region, String lbid, Integer port) {
    IAcsClient client = new SLBHelperAPIClient().getDefaultClient();
    DescribeListenerAccessControlAttributeResponse response = null;

    // 设置参数
    DescribeListenerAccessControlAttributeRequest describeListenerAccessControlAttribute =
        new DescribeListenerAccessControlAttributeRequest();
    describeListenerAccessControlAttribute.setLoadBalancerId(lbid);
    describeListenerAccessControlAttribute.setListenerPort(port);
    describeListenerAccessControlAttribute.setRegionId(region);

    // 发起请求
    try {
      response = client.getAcsResponse(describeListenerAccessControlAttribute);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return response;
  }

  public static DescribeServerCertificatesResponse describeServerCertificates(String region) {
    // TODO
    return null;
  }

  public static DescribeCACertificatesResponse describeCACertificates(String region) {
    // TODO
    return null;
  }

  public static CreateLoadBalancerResponse createLoadBalancer(String loadBalancerName,
      String loadBalancerSpec, String regionId, String masterZoneId, String slaveZoneId,
      String addressType, String vpcId, String vSwitchId, String internetChargeType,
      Integer bandwidth) {
    CreateLoadBalancerResponse response = null;
    IAcsClient client = new SLBHelperAPIClient().getClient(regionId);

    CreateLoadBalancerRequest request = new CreateLoadBalancerRequest();

    request.setLoadBalancerName(loadBalancerName);
    request.setLoadBalancerSpec(loadBalancerSpec);
    request.setRegionId(regionId);
    request.setMasterZoneId(masterZoneId);
    request.setSlaveZoneId(slaveZoneId);
    request.setAddressType(addressType);
    request.setVpcId(vpcId);
    request.setVSwitchId(vSwitchId);
    request.setInternetChargeType(internetChargeType);
    request.setBandwidth(bandwidth > 5000 ? 5000 : bandwidth);

    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return response;

  }

  public static CreateLoadBalancerHTTPListenerResponse createLoadBalancerHTTPListener(
      String regionId, String loadBalancerId, Integer listenerPort, Integer backendServerPort,
      String realServerType, String realServerId, Integer bandwidth, String scheduler,
      Integer connectTimeout, String healthCheck, Integer healthyThreshold,
      Integer unhealthyThreshold, Integer healthCheckConnectTimeout, Integer healthCheckInterval,
      String healthCheckDomain, String healthCheckURI, String healthCheckHttpCode,
      Integer healthCheckConnectPort, String cookie, Integer cookieTimeout, String stickySession,
      String stickySessionType, String gzip, String xForwardedFor_SLBIP, String xForwardedFor_SLBID,
      String xForwardedFor_proto, String xForwardedFor) {

    IAcsClient client = new SLBHelperAPIClient().getClient(regionId);
    CreateLoadBalancerHTTPListenerResponse response = null;
    CreateLoadBalancerHTTPListenerRequest request = new CreateLoadBalancerHTTPListenerRequest();
    request.setRegionId(regionId);
    request.setLoadBalancerId(loadBalancerId);
    request.setListenerPort(listenerPort);
    request.setBackendServerPort(backendServerPort);
    request.setBandwidth(bandwidth);
    request.setScheduler(scheduler);

    switch (realServerType) {
      case "vServerGroup":
        request.setVServerGroupId(realServerId);
        break;
      // case "masterSlaveGroup":
      // request.setMasterSlaveServerGroupId(realServerId);
      // break;
      case "backendServers":
        break;
      default:
        break;
    }
    request.setHealthyThreshold(healthyThreshold);
    request.setUnhealthyThreshold(unhealthyThreshold);
    request.setHealthCheck(healthCheck);
    request.setHealthCheckDomain(healthCheckDomain);
    request.setHealthCheckURI(healthCheckURI);
    request.setHealthCheckConnectPort(healthCheckConnectPort);
    request.setConnectTimeout(connectTimeout);
    request.setHealthCheckTimeout(healthCheckConnectTimeout);
    request.setHealthCheckInterval(healthCheckInterval);
    request.setHealthCheckHttpCode(healthCheckHttpCode);
    request.setCookie(cookie);
    request.setCookieTimeout(cookieTimeout);
    request.setStickySession(stickySession);
    request.setStickySessionType(stickySessionType);
    request.setGzip(gzip);
    request.setXForwardedFor_SLBIP(xForwardedFor_SLBIP);
    request.setXForwardedFor_SLBID(xForwardedFor_SLBID);
    request.setXForwardedFor_proto(xForwardedFor_proto);
    request.setXForwardedFor(xForwardedFor);
    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }


  public static CreateLoadBalancerHTTPSListenerResponse createLoadBalancerHTTPSListener(
      String regionId, String loadBalancerId, Integer listenerPort, Integer backendServerPort,
      String realServerType, String realServerId, Integer bandwidth, String scheduler,
      Integer connectTimeout, String healthCheck, Integer healthyThreshold,
      Integer unhealthyThreshold, Integer healthCheckConnectTimeout, Integer healthCheckInterval,
      String healthCheckDomain, String healthCheckURI, String healthCheckHttpCode,
      Integer healthCheckConnectPort, String cookie, Integer cookieTimeout, String stickySession,
      String stickySessionType, String gzip, String xForwardedFor_SLBIP, String xForwardedFor_SLBID,
      String xForwardedFor_proto, String xForwardedFor) {

    IAcsClient client = new SLBHelperAPIClient().getClient(regionId);
    CreateLoadBalancerHTTPSListenerResponse response = null;
    CreateLoadBalancerHTTPSListenerRequest request = new CreateLoadBalancerHTTPSListenerRequest();
    request.setRegionId(regionId);
    request.setLoadBalancerId(loadBalancerId);
    request.setListenerPort(listenerPort);
    request.setBackendServerPort(backendServerPort);
    request.setBandwidth(bandwidth);
    request.setScheduler(scheduler);

    switch (realServerType) {
      case "vServerGroup":
        request.setVServerGroupId(realServerId);
        break;
      // case "masterSlaveGroup":
      // request.setMasterSlaveServerGroupId(realServerId);
      // break;
      case "backendServers":
        break;
      default:
        break;
    }
    request.setHealthCheck(healthCheck);
    request.setConnectTimeout(connectTimeout);
    request.setHealthyThreshold(healthyThreshold);
    request.setUnhealthyThreshold(unhealthyThreshold);
    request.setHealthCheckDomain(healthCheckDomain);
    request.setHealthCheckURI(healthCheckURI);
    request.setHealthCheckConnectPort(healthCheckConnectPort);
    request.setConnectTimeout(healthCheckConnectTimeout);
    request.setHealthCheckInterval(healthCheckInterval);
    request.setHealthCheckHttpCode(healthCheckHttpCode);
    request.setCookie(cookie);
    request.setCookieTimeout(cookieTimeout);
    request.setStickySession(stickySession);
    request.setStickySessionType(stickySessionType);
    request.setGzip(gzip);
    request.setXForwardedFor_SLBIP(xForwardedFor_SLBIP);
    request.setXForwardedFor_SLBID(xForwardedFor_SLBID);
    request.setXForwardedFor_proto(xForwardedFor_proto);
    request.setXForwardedFor(xForwardedFor);
    // todo 证书
    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static CreateLoadBalancerTCPListenerResponse createLoadBalancerTCPListener(String regionId,
      String loadBalancerId, Integer listenerPort, Integer backendServerPort, String realServerType,
      String realServerId, Integer bandwidth, String scheduler, Integer connectTimeout,
      String healthCheckType, Integer healthyThreshold, Integer unhealthyThreshold,
      Integer healthCheckConnectTimeout, Integer healthCheckInterval, String healthCheckDomain,
      String healthCheckURI, String healthCheckHttpCode, Integer presistenceTimeout,
      Integer establishedTimeout, Integer healthCheckConnectPort) {
    IAcsClient client = new SLBHelperAPIClient().getClient(regionId);
    CreateLoadBalancerTCPListenerResponse response = null;
    CreateLoadBalancerTCPListenerRequest request = new CreateLoadBalancerTCPListenerRequest();
    request.setRegionId(regionId);
    request.setLoadBalancerId(loadBalancerId);
    request.setListenerPort(listenerPort);
    request.setBackendServerPort(backendServerPort);
    request.setBandwidth(bandwidth);
    request.setScheduler(scheduler);

    switch (realServerType) {
      case "vServerGroup":
        request.setVServerGroupId(realServerId);
        break;
      case "masterSlaveGroup":
        request.setMasterSlaveServerGroupId(realServerId);
        break;
      case "backendServers":
        break;
      default:
        break;
    }
    request.setConnectTimeout(connectTimeout);
    request.setPersistenceTimeout(presistenceTimeout);
    request.setEstablishedTimeout(establishedTimeout);
    request.setHealthCheckType(healthCheckType);
    request.setHealthyThreshold(healthyThreshold);
    request.setUnhealthyThreshold(unhealthyThreshold);
    request.setHealthCheckDomain(healthCheckDomain);
    request.setHealthCheckURI(healthCheckURI);
    request.setHealthCheckConnectPort(healthCheckConnectPort);
    request.setHealthCheckConnectTimeout(healthCheckConnectTimeout);
    request.setHealthCheckInterval(healthCheckInterval);
    request.setHealthCheckHttpCode(healthCheckHttpCode);
    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static CreateLoadBalancerUDPListenerResponse createLoadBalancerUDPListener(String regionId,
      String loadBalancerId, Integer listenerPort, Integer backendServerPort, String realServerType,
      String realServerId, Integer bandwidth, String scheduler, Integer connectTimeout,
      Integer healthyThreshold, Integer unhealthyThreshold, Integer healthCheckConnectTimeout,
      Integer healthCheckInterval, Integer healthCheckConnectPort, Integer persistenceTimeout,
      String healthCheckExp, String healthCheckReq) {

    IAcsClient client = new SLBHelperAPIClient().getClient(regionId);
    CreateLoadBalancerUDPListenerResponse response = null;

    CreateLoadBalancerUDPListenerRequest request = new CreateLoadBalancerUDPListenerRequest();
    request.setConnectTimeout(connectTimeout);
    request.setRegionId(regionId);
    request.setLoadBalancerId(loadBalancerId);
    request.setListenerPort(listenerPort);
    request.setBackendServerPort(backendServerPort);
    request.setBandwidth(bandwidth);
    request.setScheduler(scheduler);

    switch (realServerType) {
      case "vServerGroup":
        request.setVServerGroupId(realServerId);
        break;
      case "masterSlaveGroup":
        request.setMasterSlaveServerGroupId(realServerId);
        break;
      case "backendServers":
        break;
      default:
        break;
    }

    request.setPersistenceTimeout(persistenceTimeout);
    request.setHealthyThreshold(healthyThreshold);
    request.setUnhealthyThreshold(unhealthyThreshold);
    request.setHealthCheckConnectPort(healthCheckConnectPort);
    request.setHealthCheckConnectTimeout(healthCheckConnectTimeout);
    request.setHealthCheckInterval(healthCheckInterval);
    request.setHealthCheckExp(healthCheckExp);
    request.setHealthCheckReq(healthCheckReq);
    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static AddBackendServersResponse addBackendServers(String loadBalancerId, String regionId,
      List<com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeResponse.BackendServer> backendServers) {
    IAcsClient client = new SLBHelperAPIClient().getClient(regionId);
    ObjectMapper mapper = new ObjectMapper();
    AddBackendServersResponse response = null;
    String bsString = null;
    try {
      bsString = mapper.writeValueAsString(backendServers);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    AddBackendServersRequest request = new AddBackendServersRequest();

    request.setBackendServers(bsString);
    request.setLoadBalancerId(loadBalancerId);
    request.setRegionId(regionId);
    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }


  public static CreateVServerGroupResponse createVServerGroup(String loadBalancerId,
      String vServerGroupName, String regionId,
      List<com.aliyuncs.slb.model.v20140515.DescribeVServerGroupAttributeResponse.BackendServer> backendServers) {
    IAcsClient client = new SLBHelperAPIClient().getClient(regionId);
    ObjectMapper mapper = new ObjectMapper();
    CreateVServerGroupResponse response = null;
    String bsString = null;
    try {
      bsString = mapper.writeValueAsString(backendServers);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    CreateVServerGroupRequest request = new CreateVServerGroupRequest();
    request.setVServerGroupName(vServerGroupName);
    request.setBackendServers(bsString);
    request.setRegionId(regionId);
    request.setLoadBalancerId(loadBalancerId);

    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public static CreateMasterSlaveVServerGroupResponse createMasterSlaveVServerGroup(
      String loadBalancerId, String msgName, String regionId,
      List<com.aliyuncs.slb.model.v20140515.DescribeMasterSlaveVServerGroupAttributeResponse.MasterSlaveBackendServer> msgBackendServers) {
    IAcsClient client = new SLBHelperAPIClient().getClient(regionId);
    ObjectMapper mapper = new ObjectMapper();
    CreateMasterSlaveVServerGroupResponse response = null;
    String bsString = null;

    List<com.aliyuncs.slb.model.v20140515.CreateMasterSlaveServerGroupResponse.MasterSlaveBackendServer> masterSlaveBackendServersCreate =
        new ArrayList<>();

    msgBackendServers.forEach(msg -> {

      com.aliyuncs.slb.model.v20140515.CreateMasterSlaveServerGroupResponse.MasterSlaveBackendServer tmp =
          new com.aliyuncs.slb.model.v20140515.CreateMasterSlaveServerGroupResponse.MasterSlaveBackendServer();

      tmp.setPort(msg.getPort());
      tmp.setServerId(msg.getServerId());
      tmp.setWeight(msg.getWeight());
      tmp.setServerType(msg.getIsBackup() == 1 ? "Slave" : "Master");
      masterSlaveBackendServersCreate.add(tmp);
    });

    try {
      bsString = mapper.writeValueAsString(masterSlaveBackendServersCreate);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    CreateMasterSlaveVServerGroupRequest request = new CreateMasterSlaveVServerGroupRequest();
    request.setMasterSlaveVServerGroupName(msgName);
    request.setMasterSlaveBackendServers(bsString);
    request.setRegionId(regionId);
    request.setLoadBalancerId(loadBalancerId);



    try {
      response = client.getAcsResponse(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

}
