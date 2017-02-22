package com.bskyb.ovp.stratos.tdd;

import com.bskyb.ovp.stratos.tdd.client.HelloClient;
import com.bskyb.ovp.stratos.tdd.client.RequestContext;
import com.bskyb.ovp.stratos.tdd.data.CustomSigDao;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;


public class HelloStepDefs {

  private final HelloClient helloClient;
  private final RequestContext requestContext;
  private final CustomSigDao customSigDao;

  @Inject
  public HelloStepDefs(HelloClient helloClient, RequestContext requestContext, CustomSigDao customSigDao) {
    this.helloClient = helloClient;
    this.requestContext = requestContext;
    this.customSigDao = customSigDao;
  }


  @When("^I call the hello end point$")
  public void I_call_the_hello_end_point() throws Throwable {
    helloClient.getHello();
  }

  @Then("^'([^']+)' is returned$")
  public void _Hello_World_is_returned(String expected) throws Throwable {
    assertEquals(expected, requestContext.getLastResponse().parseAsString());
  }

  @Given("^there is a custom sig$")
  public void thereIsACustomSig() {
    customSigDao.setCustomSig("jason", "TTFN");
  }

}
