package com.tlcsdm.eclipse.translation.baidu;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BaiduTranslateModel {

	private String result = null;
	private String from = null;
	private String to = null;
	private String src = "";
	private String dst = "";

	public BaiduTranslateModel() {
		super();
	}

	public BaiduTranslateModel(String result) {
		super();
		this.result = result;
		init();
	}

	/**
	 * 获取原文
	 * 
	 * @return
	 */
	public String getSrc() {
		return src;
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

	public void setSrc(String src) {
		this.src = src;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	/**
	 * 解析json
	 */
	public void init() {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> map = gson.fromJson(result, type);
		this.from = map.get("from").toString();
		this.to = map.get("to").toString();

		@SuppressWarnings("unchecked")
		List<Map<String, String>> transResult = (List<Map<String, String>>) map.get("trans_result");
		StringBuilder srcBuilder = new StringBuilder();
		StringBuilder dstBuilder = new StringBuilder();
		for (Map<String, String> item : transResult) {
			srcBuilder.append(item.get("src")).append("\r");
			dstBuilder.append(item.get("dst")).append("\r");
		}
		this.src = srcBuilder.toString();
		this.dst = dstBuilder.toString();
	}

}
