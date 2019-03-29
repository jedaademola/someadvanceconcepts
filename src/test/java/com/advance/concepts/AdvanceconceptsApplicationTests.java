package com.advance.concepts;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.ssl.TrustStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;

import static org.apache.http.conn.ssl.SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvanceconceptsApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenException()
            throws GeneralSecurityException {
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        DefaultHttpClient httpClient
                = (DefaultHttpClient) requestFactory.getHttpClient();
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLSocketFactory sf = new SSLSocketFactory(
                (SSLContext) acceptingTrustStrategy, ALLOW_ALL_HOSTNAME_VERIFIER);
        httpClient.getConnectionManager().getSchemeRegistry()
                .register(new Scheme("https", 8443, sf));

        String urlOverHttps
                = "https://localhost:8443/spring-security-rest-basic-auth/api/bars/1";
        ResponseEntity<String> response = new RestTemplate(requestFactory).
                exchange(urlOverHttps, HttpMethod.GET, null, String.class);
        //assertThat(response.getStatusCode().value(), equals(200));
        assertFalse(response.getStatusCode().value() == 200);
    }
}
