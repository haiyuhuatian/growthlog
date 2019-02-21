
package cn.com.czrz.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WeixinUtil
{
    protected static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
            .create();

    public static Map https(String requestUrl, String requestInfo)
            throws Exception
    {
        StringBuilder sb = new StringBuilder();
        URL u = new URL(requestUrl);
        HttpURLConnection http = (HttpURLConnection) u.openConnection();
        http.setDoOutput(true);
        http.setDoInput(true);
        http.setUseCaches(false);
        http.setRequestMethod("GET");
        if (requestInfo != null)
        {
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Content-length",
                    Integer.toString(requestInfo.length()));
            OutputStream outputStream = http.getOutputStream();
            outputStream.write(requestInfo.getBytes("UTF-8"));
            outputStream.close();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                http.getInputStream(), "UTF-8"));
        String line = null;
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        br.close();
        String json = sb.toString();
        return gson.fromJson(json, Map.class);
    }

}
