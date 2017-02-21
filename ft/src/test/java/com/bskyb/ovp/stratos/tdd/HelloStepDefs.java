package com.bskyb.ovp.stratos.tdd;

import com.bskyb.ovp.stratos.tdd.client.HelloClient;
import com.bskyb.ovp.stratos.tdd.client.RequestContext;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;


public class HelloStepDefs {

  private final HelloClient helloClient;
  private final RequestContext requestContext;

  @Inject
  public HelloStepDefs(HelloClient helloClient, RequestContext requestContext) {
    this.helloClient = helloClient;
    this.requestContext = requestContext;
  }


  @When("^I call the hello end point$")
  public void I_call_the_hello_end_point() throws Throwable {
    helloClient.getHello();
  }

  @Then("^'([^']+)' is returned$")
  public void _Hello_World_is_returned(String expected) throws Throwable {
    assertEquals(expected, requestContext.getLastResponse().parseAsString());
  }
}
