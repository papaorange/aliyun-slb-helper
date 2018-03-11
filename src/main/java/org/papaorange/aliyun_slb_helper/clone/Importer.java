package org.papaorange.aliyun_slb_helper.clone;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.papaorange.aliyun_slb_helper.api.SLBHelperAPI;
import org.papaorange.aliyun_slb_helper.model.LoadBalancerObject;
import org.papaorange.aliyun_slb_helper.model.listener.HTTPListener;
import org.papaorange.aliyun_slb_helper.model.listener.Listener;
import org.papaorange.aliyun_slb_helper.model.listener.TCPListener;
import org.papaorange.aliyun_slb_helper.model.listener.UDPListener;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerResponse;
import com.aliyuncs.slb.model.v20140515.CreateMasterSlaveVServerGroupResponse;
import com.aliyuncs.slb.model.v20140515.CreateVServerGroupResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Importer {
	public static Integer importLoadbalancer(String filename, String newLoadBalancerName, String newPayType)
			throws IOException {
		Integer errorCode = 0;
		String newLoadBalancerId = null;
		String loadBalancerName = null;
		String loadBalancerSpec = null;
		String regionId = null;
		String masterZoneId = null;
		String slaveZoneId = null;
		String lbJsonStr = FileUtils.readFileToString(new File(filename));
		String addressType = null;
		String vpcId = null;
		String vSwitchId = null;
		String internetChargeType = null;
		Integer bandwidth = 0;
		// String payType = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();

		LoadBalancerObject loadBalancerObject = mapper.readValue(lbJsonStr, LoadBalancerObject.class);

		if (newLoadBalancerName == null) {
			loadBalancerName = loadBalancerObject.getName();
		}

		// if (newPayType == null) {
		// payType = loadBalancerObject.getPayType();
		// }
		loadBalancerSpec = loadBalancerObject.getLoadBalancerSpec();
		regionId = loadBalancerObject.getRegionIdAlias();
		masterZoneId = loadBalancerObject.getMasterZoneId();
		slaveZoneId = loadBalancerObject.getSalveZoneId();
		addressType = loadBalancerObject.getAddrType();
		vpcId = loadBalancerObject.getVpcId();
		vSwitchId = loadBalancerObject.getvSwitchId();
		internetChargeType = loadBalancerObject.getInternetChargeType();
		bandwidth = loadBalancerObject.getBandwidth();
		// 创建lb
		CreateLoadBalancerResponse response = SLBHelperAPI.createLoadBalancer(loadBalancerName, loadBalancerSpec,
				regionId, masterZoneId, slaveZoneId, addressType, vpcId, vSwitchId, internetChargeType, bandwidth);

		newLoadBalancerId = response.getLoadBalancerId();
		// 添加后端服务器

		SLBHelperAPI.addBackendServers(newLoadBalancerId, regionId, loadBalancerObject.getBackendServers());

		// 创建虚拟服务器组
		Map<String, String> gMap = new HashMap<>();
		loadBalancerObject.getvServerGroups().forEach(vsg -> {
			CreateVServerGroupResponse vsgResponse = SLBHelperAPI.createVServerGroup(response.getLoadBalancerId(),
					vsg.getVServerGroupName(), loadBalancerObject.getRegionIdAlias(), vsg.getBackendServer());
			gMap.put(vsg.getVServerGroupId(), vsgResponse.getVServerGroupId());

			System.out.println("addMAPvsg" + vsg.getVServerGroupId() + ":" + vsgResponse.getVServerGroupId());
		});

		// 创建主备服务器组
		loadBalancerObject.getMasterSlaveGroups().forEach(msg -> {
			CreateMasterSlaveVServerGroupResponse msgResponse = SLBHelperAPI.createMasterSlaveVServerGroup(
					response.getLoadBalancerId(), msg.getMasterSlaveServerGroupName(),
					loadBalancerObject.getRegionIdAlias(), msg.getMasterSlaveBackendServers());
			gMap.put(msg.getMasterSlaveServerGroupId(), msgResponse.getMasterSlaveVServerGroupId());

		});

		// 创建监听
		List<Listener> listeners = loadBalancerObject.getListeners();

		listeners.forEach(listen -> {

			// SLBHelperAPI.createLoadBalancerTCPListener(loadBalancerObject.getRegionIdAlias(),
			// response.getLoadBalancerId(), listen.getListenerPort(),
			// listen.getBackendServerPort(),
			// listen.getBandwidth(), listen.getScheduler(), listen.getvServerGroupId(),
			// listen.getMaterSlaveServerGroupId(), ((TCPListener)
			// listen).getPersistenceTimeout(),
			// ((TCPListener) listen).getEstablishedTimeout(), listen.getHealthCheckType(),
			// listen.getHealthCheckDomain(), listen.getHealthCheckURI(),
			// listen.getHealthCheckConnectPort(), listen.getHealthyThreshold(),
			// listen.getUnhealthyThreshold(), listen.getHealthCheckConnectTimeout(),
			// listen.getHealthCheckInterval(), listen.getHealthCheckHttpCode());

			switch (listen.getProtocol()) {
			case "tcp":

				SLBHelperAPI.createLoadBalancerTCPListener(loadBalancerObject.getRegionIdAlias(),
						response.getLoadBalancerId(), listen.getListenerPort(), listen.getBackendServerPort(),
						listen.getRealServerType(), gMap.get(listen.getRealServerId()), listen.getBandwidth(),
						listen.getScheduler(), listen.getHealthCheckType(), listen.getHealthyThreshold(),
						listen.getUnhealthyThreshold(), listen.getHealthCheckConnectTimeout(),
						listen.getHealthCheckInterval(), listen.getHealthCheckDomain(), listen.getHealthCheckURI(),
						listen.getHealthCheckHttpCode(), ((TCPListener) listen).getPersistenceTimeout(),
						((TCPListener) listen).getEstablishedTimeout(), listen.getHealthCheckConnectPort());

				break;
			case "udp":
				SLBHelperAPI.createLoadBalancerUDPListener(loadBalancerObject.getRegionIdAlias(),
						response.getLoadBalancerId(), listen.getListenerPort(), listen.getBackendServerPort(),
						listen.getRealServerType(), gMap.get(listen.getRealServerId()), listen.getBandwidth(),
						listen.getScheduler(), listen.getHealthyThreshold(), listen.getUnhealthyThreshold(),
						listen.getHealthCheckConnectTimeout(), listen.getHealthCheckInterval(),
						listen.getHealthCheckConnectPort(), ((UDPListener) listen).getPersistenceTimeout(),
						((UDPListener) listen).getHealthCheckExp(), ((UDPListener) listen).getHealthCheckReq());
				break;

			case "http":
				System.err.println(gMap.get(listen.getRealServerId()));
				SLBHelperAPI.createLoadBalancerHTTPListener(loadBalancerObject.getRegionIdAlias(),
						response.getLoadBalancerId(), listen.getListenerPort(), listen.getBackendServerPort(),
						listen.getRealServerType(), gMap.get(listen.getRealServerId()), listen.getBandwidth(),
						listen.getScheduler(), listen.getHealthCheck(), listen.getHealthyThreshold(),
						listen.getUnhealthyThreshold(), listen.getHealthCheckConnectTimeout(),
						listen.getHealthCheckInterval(), listen.getHealthCheckDomain(), listen.getHealthCheckURI(),
						listen.getHealthCheckHttpCode(), listen.getHealthCheckConnectPort(),
						((HTTPListener) listen).getCookie(), ((HTTPListener) listen).getCookieTimeout(),
						((HTTPListener) listen).getStickySession(), ((HTTPListener) listen).getStickySessionType(),
						((HTTPListener) listen).getGzip(), ((HTTPListener) listen).getxForwardedFor_SLBIP(),
						((HTTPListener) listen).getxForwardedFor_SLBID(),
						((HTTPListener) listen).getxForwardedFor_proto(), ((HTTPListener) listen).getxForwardedFor());
				break;
			case "https":
				break;
			default:
				break;
			}

		});

		// 设置转发规则

		System.out.println(response.getLoadBalancerId());

		return errorCode;
	}

	public static void main(String[] args) {
		try {
			System.out.println(
					importLoadbalancer("slbs/cn-beijing/lb-2ze4d63ksjmxbw6xwznfo-172.17.196.215.json", "", ""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
