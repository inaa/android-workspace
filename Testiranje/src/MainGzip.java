import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class MainGzip {

    public static void main(String[] args) throws ClientProtocolException, IOException {
        String file = "test.txt";
        String gzipFile = "test.txt.gz";
        String newFile = "nekaj.txt";
        
        String uri = "http://92.37.33.212:8080/IITech/service/topnovice";
		URL url = new URL(uri);
		HttpUriRequest request = new HttpGet(uri);
		request.addHeader("Content-Type", "application/json");
		request.addHeader("Accept-Encoding", "gzip");
		
	    HttpClient httpClient = new DefaultHttpClient();
	    HttpResponse response = httpClient.execute(request);
	    InputStream is = response.getEntity().getContent();
        
     //   compressGzipFile(file, gzipFile);
        
      decompressGzipFile(is, newFile);
               
    }

    private static void decompressGzipFile(InputStream is, String newFile) {
        try {
            //FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(is);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private static void compressGzipFile(String file, String gzipFile) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}