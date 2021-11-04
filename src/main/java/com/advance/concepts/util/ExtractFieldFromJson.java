package com.advance.concepts.util;

import com.advance.concepts.config.RestTemplateConfig;
import com.advance.concepts.config.SSLCertificateValidation;
import com.advance.concepts.model.Result;
import org.apache.commons.codec.binary.Base64;
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

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

@Component
public class ExtractFieldFromJson {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractFieldFromJson.class);

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

    public Result extractJson2(String id) {
        Result result = null;
        JSONObject jsonObj;
        try {
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

            URL url = null;

            url = new URL("https://url.com/" + id + ".json");

            URLConnection con = url.openConnection();

            String username = "myusername";
            String password = "Dec@122018";

            BASE64Encoder enc = new sun.misc.BASE64Encoder();
            String userpassword = username + ":" + password;
            String encodedAuthorization = enc.encode(userpassword.getBytes());
            con.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

            String json = streamToString(con.getInputStream()); // input stream to string
            System.out.println("json:" + json);

            jsonObj = new JSONObject(json);

            String ticket = jsonObj.getString("ticket");
            System.out.println("ticket:" + ticket);
            //get each field values
            JSONObject fields = new JSONObject(ticket);
            System.out.println("subject:" + fields.getString("subject"));
            System.out.println("description:" + fields.getString("description"));

            result = new Result();

            result.setDesc(fields.getString("description"));
            result.setSubject(fields.getString("subject"));

        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            LOGGER.error(e.getMessage());
        }

        return result;
    }


}
