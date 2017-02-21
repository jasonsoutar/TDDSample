package com.bskyb.ovp.stratos.tdd.guice;

import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.swing.text.html.HTMLDocument;

public class GuiceInjector implements InjectorSource {


  @Override
  public Injector getInjector() {
    return Guice.createInjector(
      CucumberModules.SCENARIO,
      new HelloServiceModule()
    );
  }
}