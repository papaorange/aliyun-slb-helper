package org.papaorange.aliyun_slb_helper.clone;

import org.papaorange.aliyun_slb_helper.api.SLBHelperAPIClient;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerRequest;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerResponse;

public class App {
	public static void main(String[] args) {
		// List<String> regions = SLBHelperAPI.describeRegions();
		//
		// regions.forEach(region -> {
		// System.out.println("Region:" + region);
		//
		// SLBHelperAPI.describeLoadBalancers(region).forEach(lb -> {
		// try {
		// Exporter.exportByLoadBalancerId(region,
		// lb.getLoadBalancerId(), true);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// });
		// });

		IAcsClient client = new SLBHelperAPIClient().getDefaultClient();

		// 设置参数
		CreateLoadBalancerRequest request = new CreateLoadBalancerRequest();
		request.setLoadBalancerSpec("slb.s1.small");;
		// 发起请求
		try {

			CreateLoadBalancerResponse response = client
					.getAcsResponse(request);
			System.out.println(response.getLoadBalancerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
