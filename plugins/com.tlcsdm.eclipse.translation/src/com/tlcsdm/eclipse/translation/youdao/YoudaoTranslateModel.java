package com.tlcsdm.eclipse.translation.youdao;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class YoudaoTranslateModel {

	private List<String> translation = new ArrayList<String>();
	private Basic basic = new Basic();
	private Web web = new Web();

	public void initModel(String text) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();

		Map<String, Object> map = gson.fromJson(text, type);
		// System.out.println(map);
		if ("0".equals(map.get("errorCode").toString())) {
			Type type1 = new TypeToken<List<String>>() {
			}.getType();
			translation = gson.fromJson(map.get("translation").toString(), type1);
			if (map.get("basic") != null) {
				Map<String, Object> basicMap = gson.fromJson(map.get("basic").toString(), type);
				if (basicMap.get("phonetic") != null)
					basic.phonetic = basicMap.get("phonetic").toString();
				if (basicMap.get("uk-phonetic") != null)
					basic.uk_phonetic = basicMap.get("uk-phonetic").toString();
				if (basicMap.get("us-phonetic") != null)
					basic.us_phonetic = basicMap.get("us-phonetic").toString();
				basic.explains = gson.fromJson(basicMap.get("explains").toString(), type1);
				basic.value = true;
			}
			if (map.get("web") != null) {
				Type type2 = new TypeToken<List<HashMap<String, Object>>>() {
				}.getType();
				web.webList = gson.fromJson(map.get("web").toString(), type2);
				web.value = true;
			}
		}
	}

	public String printResult() {
		StringBuffer sb = new StringBuffer();
		for (Object o : translation) {
			sb.append(o).append("\r");
		}
		if (basic.value) {
			sb.append("有道词典-基本词典：").append("\r").append("发音：").append(basic.phonetic).append("\r").append("英式发音：")
					.append(basic.uk_phonetic).append("\r").append("美式发音：").append(basic.us_phonetic).append("\r");
			for (String str : basic.explains) {
				sb.append(str).append("\r");
			}
		}
		if (web.value) {
			sb.append("有道词典-网络释义").append("\r");
			for (int i = 0; i < web.webList.size(); i++) {
				Map<String, Object> m = web.webList.get(i);
				Object obj = m.get("value");
				sb.append(m.get("key")).append("    ").append(obj).append("\n");
			}
		}
		return sb.toString();
	}

	class Basic {
		public boolean value = false;
		public String us_phonetic = "";
		public String phonetic = "";
		public String uk_phonetic = "";
		public List<String> explains = new ArrayList<String>();
	}

	class Web {
		public boolean value = false;
		public List<HashMap<String, Object>> webList = new ArrayList<HashMap<String, Object>>();
	}

}
