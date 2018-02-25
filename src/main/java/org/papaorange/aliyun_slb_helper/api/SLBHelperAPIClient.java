package org.papaorange.aliyun_slb_helper.api;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class SLBHelperAPIClient {

	private IAcsClient client = null;

	public IAcsClient getClient(String region) {
		DefaultProfile profile = DefaultProfile.getProfile(region,
				AKConfig.getInstance().getAccessKeyId(),
				AKConfig.getInstance().getAccessKeySecret());
		client = new DefaultAcsClient(profile);
		return client;
	}

	public IAcsClient getDefaultClient() {
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
				AKConfig.getInstance().getAccessKeyId(),
				AKConfig.getInstance().getAccessKeySecret());
		client = new DefaultAcsClient(profile);
		return client;
	}
}
