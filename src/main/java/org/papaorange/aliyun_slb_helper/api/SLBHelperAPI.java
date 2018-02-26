package org.papaorange.aliyun_slb_helper.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aliyuncs.IAcsClient;
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

	public static DescribeLoadBalancerAttributeResponse describeLoadBalancerAttr(String region, String lbid) {
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

	public static DescribeLoadBalancerTCPListenerAttributeResponse describeLoadBalancerTCPListenerAttr(LoadBalancer lb,
			int port) {
		IAcsClient client = new SLBHelperAPIClient().getClient(lb.getRegionId().toString());
		// 设置参数
		DescribeLoadBalancerTCPListenerAttributeRequest request = new DescribeLoadBalancerTCPListenerAttributeRequest();
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

	public static DescribeLoadBalancerUDPListenerAttributeResponse describeLoadBalancerUDPListenerAttr(LoadBalancer lb,
			int port) {
		IAcsClient client = new SLBHelperAPIClient().getClient(lb.getRegionId().toString());
		// 设置参数
		DescribeLoadBalancerUDPListenerAttributeRequest request = new DescribeLoadBalancerUDPListenerAttributeRequest();
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
		DescribeLoadBalancerHTTPListenerAttributeRequest request = new DescribeLoadBalancerHTTPListenerAttributeRequest();
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
		DescribeLoadBalancerHTTPSListenerAttributeRequest request = new DescribeLoadBalancerHTTPSListenerAttributeRequest();
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

	public static DescribeMasterSlaveVServerGroupsResponse describeMasterSlaveVServerGroups(String region,
			String lbid) {
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

	public static DescribeVServerGroupAttributeResponse describeVServerGroupsAttr(String region, String groupId) {
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

	public static DescribeMasterSlaveVServerGroupAttributeResponse describeMasterSlaveVServerGroupAttr(String region,
			String groupId) {
		IAcsClient client = new SLBHelperAPIClient().getClient(region);
		DescribeMasterSlaveVServerGroupAttributeResponse response = null;
		// 设置参数
		DescribeMasterSlaveVServerGroupAttributeRequest request = new DescribeMasterSlaveVServerGroupAttributeRequest();
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

	public static DescribeListenerAccessControlAttributeResponse describeListenerAccessControlAttr(String lbid,
			Integer port) {
		IAcsClient client = new SLBHelperAPIClient().getDefaultClient();
		DescribeListenerAccessControlAttributeResponse response = null;

		// 设置参数
		DescribeListenerAccessControlAttributeRequest describeListenerAccessControlAttribute = new DescribeListenerAccessControlAttributeRequest();
		describeListenerAccessControlAttribute.setLoadBalancerId(lbid);
		describeListenerAccessControlAttribute.setListenerPort(port);

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
}
