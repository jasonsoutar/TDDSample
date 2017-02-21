package com.bskyb.ovp.stratos.tdd.client;

import com.google.api.client.http.HttpResponse;
import cucumber.runtime.java.guice.ScenarioScoped;

@ScenarioScoped
public class RequestContext {
  private HttpResponse lastResponse;

  public HttpResponse getLastResponse() {
    return lastResponse;
  }

  public RequestContext setLastResponse(HttpResponse lastResponse) {
    this.lastResponse = lastResponse;
    return this;
  }
}
