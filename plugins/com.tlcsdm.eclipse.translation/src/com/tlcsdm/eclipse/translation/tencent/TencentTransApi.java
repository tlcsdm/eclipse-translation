package com.tlcsdm.eclipse.translation.tencent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tlcsdm.eclipse.translation.Activator;
import com.google.gson.Gson;

public class TencentTransApi {

	private static final String SERVICE = "tmt";
	private static final String REGION = "ap-beijing";
	private static final String VERSION = "2018-03-21";
	private static final int PROJECTID = 1341946;
	private static final String ACTION = "TextTranslate";
	private static final String TOKEN = "";

	private String securityId;
	private String securityKey;

	public TencentTransApi(String securityId, String securityKey) {
		this.securityId = securityId;
		this.securityKey = securityKey;
	}

	public String getTransResult(String query, String from, String to) {
		String body = "{}";
		Map<String, Object> params = buildParams(query, from, to);
		Gson gson = new Gson();
		body = gson.toJson(params);
		String result = "";
		try {
			result = doRequest(this.securityId, this.securityKey, SERVICE, VERSION, ACTION, body, REGION, TOKEN);
		} catch (Exception e) {
			Activator.getDefault().getLog()
					.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
		return result;
	}

	private Map<String, Object> buildParams(String query, String from, String to) {
		Map<String, Object> params = new HashMap<>();
		params.put("SourceText", query);
		params.put("Source", from);
		params.put("Target", to);
		params.put("ProjectId", PROJECTID);
		return params;
	}

	public String doRequest(String secretId, String secretKey, String service, String version, String action,
			String body, String region, String token)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, InterruptedException {

		HttpRequest request = buildRequest(secretId, secretKey, service, version, action, body, region, token);

		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}

	public HttpRequest buildRequest(String secretId, String secretKey, String service, String version, String action,
			String body, String region, String token) throws NoSuchAlgorithmException, InvalidKeyException {
		String host = "tmt.tencentcloudapi.com";
		String endpoint = "https://" + host;
		String contentType = "application/json; charset=utf-8";
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String auth = getAuth(secretId, secretKey, host, contentType, timestamp, body);

		return HttpRequest.newBuilder().uri(URI.create(endpoint)).header("X-TC-Timestamp", timestamp)
				.header("X-TC-Version", version).header("X-TC-Action", action).header("X-TC-Region", region)
				.header("X-TC-Token", token).header("X-TC-RequestClient", "SDK_JAVA_BAREBONE")
				.header("Authorization", auth).header("Content-Type", contentType)
				.POST(HttpRequest.BodyPublishers.ofString(body)).build();
	}

	private String getAuth(String secretId, String secretKey, String host, String contentType, String timestamp,
			String body) throws NoSuchAlgorithmException, InvalidKeyException {
		String canonicalUri = "/";
		String canonicalQueryString = "";
		String canonicalHeaders = "content-type:" + contentType + "\nhost:" + host + "\n";
		String signedHeaders = "content-type;host";

		String hashedRequestPayload = sha256Hex(body.getBytes(StandardCharsets.UTF_8));
		String canonicalRequest = "POST\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n"
				+ signedHeaders + "\n" + hashedRequestPayload;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String date = sdf.format(new Date(Long.parseLong(timestamp + "000")));
		String service = host.split("\\.")[0];
		String credentialScope = date + "/" + service + "/tc3_request";

		String hashedCanonicalRequest = sha256Hex(canonicalRequest.getBytes(StandardCharsets.UTF_8));
		String stringToSign = "TC3-HMAC-SHA256\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;

		byte[] secretDate = hmac256(("TC3" + secretKey).getBytes(StandardCharsets.UTF_8), date);
		byte[] secretService = hmac256(secretDate, service);
		byte[] secretSigning = hmac256(secretService, "tc3_request");
		String signature = printHexBinary(hmac256(secretSigning, stringToSign)).toLowerCase();

		return "TC3-HMAC-SHA256 Credential=" + secretId + "/" + credentialScope + ", SignedHeaders=" + signedHeaders
				+ ", Signature=" + signature;
	}

	public String sha256Hex(byte[] b) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] d = md.digest(b);
		return printHexBinary(d).toLowerCase();
	}

	private final char[] hexCode = "0123456789ABCDEF".toCharArray();

	public String printHexBinary(byte[] data) {
		StringBuilder r = new StringBuilder(data.length * 2);
		for (byte b : data) {
			r.append(hexCode[(b >> 4) & 0xF]);
			r.append(hexCode[(b & 0xF)]);
		}
		return r.toString();
	}

	public byte[] hmac256(byte[] key, String msg) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
		mac.init(secretKeySpec);
		return mac.doFinal(msg.getBytes(StandardCharsets.UTF_8));
	}
}
