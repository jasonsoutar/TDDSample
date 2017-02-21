package com.bskyb.ovp.stratos.tdd.client;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;

import javax.inject.Inject;

import java.io.IOException;

import static java.lang.String.*;

public class HelloClient {

  private final HttpRequestFactory requestFactory;
  private final RequestContext requestContext;

  @Inject
  public HelloClient(HttpRequestFactory requestFactory, RequestContext requestContext) {
    this.requestFactory = requestFactory;
    this.requestContext = requestContext;
  }

  public void getHello() throws IOException {
    requestContext.setLastResponse(requestFactory.buildGetRequest(new GenericUrl(format("%s://%s:%s/%s","http", "localhost", 8080, "hello"))).execute());
  }

}
