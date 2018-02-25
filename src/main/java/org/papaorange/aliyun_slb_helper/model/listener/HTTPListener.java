package org.papaorange.aliyun_slb_helper.model.listener;

import java.util.List;
import com.aliyuncs.slb.model.v20140515.DescribeRulesResponse.Rule;

public class HTTPListener extends Listener {

	private String cookie;
	private Integer cookieTimeout;
	private String stickySessionType;
	private String gzip;
	private String xForwardedFor_SLBIP;
	private String xForwardedFor_SLBID;
	private String xForwardedFor_proto;
	private String xForwardedFor;
	private List<Rule> rules;

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public Integer getCookieTimeout() {
		return cookieTimeout;
	}

	public void setCookieTimeout(Integer cookieTimeout) {
		this.cookieTimeout = cookieTimeout;
	}

	public String getGzip() {
		return gzip;
	}

	public void setGzip(String gzip) {
		this.gzip = gzip;
	}

	public String getxForwardedFor_SLBIP() {
		return xForwardedFor_SLBIP;
	}

	public void setxForwardedFor_SLBIP(String xForwardedFor_SLBIP) {
		this.xForwardedFor_SLBIP = xForwardedFor_SLBIP;
	}

	public String getxForwardedFor_proto() {
		return xForwardedFor_proto;
	}

	public void setxForwardedFor_proto(String xForwardedFor_proto) {
		this.xForwardedFor_proto = xForwardedFor_proto;
	}

	public String getxForwardedFor() {
		return xForwardedFor;
	}

	public void setxForwardedFor(String xForwardedFor) {
		this.xForwardedFor = xForwardedFor;
	}

	public String getxForwardedFor_SLBID() {
		return xForwardedFor_SLBID;
	}

	public void setxForwardedFor_SLBID(String xForwardedFor_SLBID) {
		this.xForwardedFor_SLBID = xForwardedFor_SLBID;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public String getStickySessionType() {
		return stickySessionType;
	}

	public void setStickySessionType(String stickySessionType) {
		this.stickySessionType = stickySessionType;
	}

}
