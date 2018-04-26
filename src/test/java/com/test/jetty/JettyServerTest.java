package com.test.jetty;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class JettyServerTest {
    private static JettyServer jetty = new JettyServer();

    @BeforeClass
    public static void setUp() throws Exception {
        jetty.start();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        jetty.stop();
    }

    @Test
    public void testJetty() throws IOException {
        String url = "http://localhost:8090/heavy/async";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode())
                .isEqualTo(200);
        String responseContent = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        assertThat(responseContent).isEqualTo(
                "This is some heavy resource that will be served in an async way");
    }
}