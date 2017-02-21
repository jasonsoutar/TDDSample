package com.bskyb.ovp.stratos.tdd;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
  glue = {"com.bskyb.ovp.stratos.tdd"},
  plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
  tags = {"~@wip"},
  strict = true)
public class RunCukesIT {

}
