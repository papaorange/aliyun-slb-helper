package org.papaorange.aliyun_slb_helper.clone;

import java.io.IOException;
import java.util.List;

import org.papaorange.aliyun_slb_helper.api.SLBHelperAPI;

public class App {
	public static void main(String[] args) {
		List<String> regions = SLBHelperAPI.describeRegions();

		regions.forEach(region -> {
			System.out.println("Region:" + region);

			SLBHelperAPI.describeLoadBalancers(region).forEach(lb -> {
				try {
					Exporter.exportByLoadBalancerId(region,
							lb.getLoadBalancerId(), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		});
	}
}
