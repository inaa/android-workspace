import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class Main {

	public static void main(String[] args) throws Exception {

		String uri = "http://92.37.33.212:8080/IITech/service/topnovice";
		URL url = new URL(uri);
		HttpUriRequest request = new HttpGet(uri);
		request.addHeader("Content-Type", "application/json");
		request.addHeader("Accept-Encoding", "gzip");
		
	    HttpClient httpClient = new DefaultHttpClient();
	    HttpResponse response = httpClient.execute(request);
	    InputStream is = response.getEntity().getContent();
	    //is = new GZIPInputStream(new BufferedInputStream(is));
	    
		System.out.println(readInputStreamAsString(is).length());
	    //System.out.println(IOUtils.toByteArray(is).length);
	    //System.out.println(IOUtils.toString(IOUtils.toByteArray(is)));
	   // String commpress = readInputStreamAsString(is);
		
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setRequestMethod("GET");
//		connection.setRequestProperty("Content-Type", "application/json");
//		connection.setRequestProperty("Accept-Encoding", "gzip");
//		InputStream is = connection.getInputStream();
////		String commpress = readInputStreamAsString(is);
////		System.out.println(commpress);
//		is = new GZIPInputStream(new BufferedInputStream(is));
//		System.out.println(readInputStreamAsString(is).length());
		
		
		
	}
//	
//	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
//
//	    HttpUriRequest request = new HttpGet(url);
//	    request.addHeader("Accept-Encoding", "gzip");
//	    HttpClient httpClient = new DefaultHttpClient();
//	    HttpResponse response = httpClient.execute(request);
//
//	    InputStream instream = response.getEntity().getContent();
//	    org.apache.http.Header contentEncoding = response.getFirstHeader("Content-Encoding");
//
//	    JSONObject json = null;
//	    if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
//	        BufferedReader rd = new BufferedReader(new InputStreamReader(new GZIPInputStream(instream)));
//	        String jsonText = readAll(rd);
//	          if (jsonText.contains("</div>")) {
//	              json = new JSONObject(jsonText.split("</div>")[1]);
//	          } else {
//	              json = new JSONObject(jsonText);
//	          }
//	    }
//	    return json;
//	} 

	public static String readInputStreamAsString(InputStream is) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				System.out.println(line);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        System.out.println("String length : " + str.length());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        String outStr = out.toString("UTF-8");
        System.out.println("Output String lenght : " + outStr.length());
        return outStr;
     }
    
    public static String decompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        System.out.println("Input String length : " + str.length());
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("UTF-8")));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
          outStr += line;
        }
        System.out.println("Output String lenght : " + outStr.length());
        return outStr;
     }
	
}
