package com.tlcsdm.eclipse.translation.tencent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TencentTranslateModel {

	private String result = null;
	private String from = null;
	private String to = null;
	private String dst = "";
	private String requestId = "";
	private int usedAmount = 0;

	public TencentTranslateModel() {
		super();
	}

	public TencentTranslateModel(String result) {
		super();
		this.result = result;
		init();
	}

	/**
	 * 获取译文
	 * 
	 * @return
	 */
	public String getDst() {
		return dst;
	}

	public String getText() {
		return result;
	}

	public void setText(String text) {
		this.result = text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public int getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(int usedAmount) {
		this.usedAmount = usedAmount;
	}

	/**
	 * 解析json
	 */
	public void init() {
		Gson gson = new Gson();
		JsonObject root = gson.fromJson(result, JsonObject.class);
		this.from = root.getAsJsonObject("Response").get("Source").getAsString();
		this.to = root.getAsJsonObject("Response").get("Target").getAsString();
		this.dst = root.getAsJsonObject("Response").get("TargetText").getAsString();
	}
}
