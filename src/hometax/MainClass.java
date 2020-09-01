package hometax;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainClass {

	public static void main(String[] args) {
		String clientId = ""; // 사업자 등록 번호

		String apiURL = "https://teht.hometax.go.kr/wqAction.do?actionId=ATTABZAA001R08&screenId=UTEABAAA13&popupYn=false&realScreenId="; // json
		
		String request = "<map id=\"ATTABZAA001R08\"><pubcUserNo/><mobYn>N</mobYn><inqrTrgtClCd>1</inqrTrgtClCd><txprDscmNo>" + 
						clientId + "</txprDscmNo><dongCode>20</dongCode><psbSearch>Y</psbSearch><map id=\"userReqInfoVO\"/></map>";
		try {
			String responseBody = post(apiURL, request);
			String ret = xmlParse(responseBody);
			System.out.println(ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static String post(String apiUrl, String request) throws Exception {
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);
		connection.setDoOutput(true);
		connection.setUseCaches(true);
		connection.setRequestMethod("POST");
		// Set Headers
		connection.setRequestProperty("Accept", "application/xml");
		connection.setRequestProperty("Content-Type", "application/xml");
		// Write XML
		OutputStream outputStream = connection.getOutputStream();
		byte[] b = request.getBytes("UTF-8");
		outputStream.write(b);
		outputStream.flush();
		outputStream.close();
		// Read XML
		InputStream inputStream = connection.getInputStream();
		byte[] res = new byte[2048];
		int i = 0;
		StringBuilder response = new StringBuilder();
		while ((i = inputStream.read(res)) != -1) {
			response.append(new String(res, 0, i));
		}
		inputStream.close();
		return response.toString();
	}
	
	private static String xmlParse(String resp) throws Exception {
		String start = "<smpcBmanTrtCntn>";
		String end = "</smpcBmanTrtCntn>";
		int sidx = resp.indexOf(start);
		int eidx = resp.indexOf(end);
		String ret = resp.substring(start.length() + sidx, eidx);
		
        return ret;
	}

}
