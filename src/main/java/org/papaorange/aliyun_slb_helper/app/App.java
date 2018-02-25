package org.papaorange.aliyun_slb_helper.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.papaorange.aliyun_slb_helper.api.SLBHelperAPI;

public class App {
	public static void main(String[] args) {

		// SLBHelperAPI.describeLoadBalancers("ap-southeast-5").forEach(x->System.out.println(x.getLoadBalancerId()));
		// System.out.println(SLBHelperAPI.describeLoadBalancer("ap-southeast-5",
		// "lb-k1atqv2dbillciwm3v620").getLoadBalancerId());
		// SLBHelperAPI.describeLoadBalancer("ap-southeast-5",
		// "lb-k1a8q8ujg4zjktktedqvk");

		// System.setProperty("log4j2.debug", "true");

		List<String> regions = SLBHelperAPI.describeRegions();

		regions.forEach(region -> {
			System.out.println("Region:" + region);

			SLBHelperAPI.describeLoadBalancers(region).forEach(lb -> {

				// System.err.println("current lbid:"+lb.getLoadBalancerId());
				// "ap-southeast-5","ap-northeast-1","cn-huhehaote","ap-southeast-2","me-east-1","cn-zhangjiakou"
				List<String> badRegions = Arrays.asList("ap-northeast-1",
						"cn-huhehaote", "me-east-1");
				if (!badRegions.contains(lb.getRegionIdAlias())) {
					try {
						Exporter.exportByLoadBalancerId(region,
								lb.getLoadBalancerId(), true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});

		});

	}
}
