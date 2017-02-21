package com.bskyb.ovp.stratos.tdd.guice;

import com.bskyb.ovp.stratos.tdd.client.HelloClient;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.inject.AbstractModule;

import javax.inject.Provider;


public class HelloServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(HttpRequestFactory.class).toProvider(HttpRequestFactoryProvider.class);
  }

  public static class HttpRequestFactoryProvider implements Provider<HttpRequestFactory> {

    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final JsonFactory JSON_FACTORY = new JacksonFactory();
    public HttpRequestFactory get() {


      return HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {

        public void initialize(HttpRequest request) {
          request.setParser(new JsonObjectParser(JSON_FACTORY));
        }
      });
    }
  }
}
