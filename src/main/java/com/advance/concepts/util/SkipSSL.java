package com.advance.concepts.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import sun.misc.BASE64Encoder;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SkipSSL {
    public static void main(String[] args) throws Exception {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        URL url = new URL("https://wahbe.zendesk.com/api/v2/tickets/285392.json");
        URLConnection con = url.openConnection();

        String username = "varulgnanajothi@deloitte.com";
        String password = "Dec@122018";

        BASE64Encoder enc = new sun.misc.BASE64Encoder();
        String userpassword = username + ":" + password;
        String encodedAuthorization = enc.encode(userpassword.getBytes());
        con.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

        Reader reader = new InputStreamReader(con.getInputStream());

        String json = streamToString(con.getInputStream()); // input stream to string
        System.out.println("json:" + json);

        /*while (true) {
            int ch = reader.read();
            if (ch == -1) {
                break;
            }
            System.out.print("out:" + (char) ch);
        }

*/
    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }
   /* public static void mains(String[] args) throws JSONException {
        JSONObject jsonObj;
        jsonObj = new JSONObject(jsonString);
        String ticket = jsonObj.getString("ticket");
        System.out.println("ticket:"+ ticket);
        //get each field values
        JSONObject eachError = new JSONObject(ticket);
        System.out.println("subject:" +eachError.getString("subject"));
        System.out.println("description:" +eachError.getString("description"));
   }*/


}
