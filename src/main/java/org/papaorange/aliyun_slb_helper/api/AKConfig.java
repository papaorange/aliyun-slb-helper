package org.papaorange.aliyun_slb_helper.api;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class AKConfig {

	// Access Key ID Access Key Secret
	private String			accessKeyId;
	private String			accessKeySecret;

	private static AKConfig	_instance;

	static {
		Gson gson = new Gson();
		FileInputStream configIn = null;
		try {
			configIn = new FileInputStream("ak.json");
			_instance = gson.fromJson(IOUtils.toString(configIn), AKConfig.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(configIn);
		}
	}

	public static AKConfig getInstance() {
		return _instance;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

}

// 配置文件 ak.json ：
// {
// "accessKeyId": "xxxxx",
// "accessKeySecret": "xxxxx"
// }
