package com.bskyb.ovp.stratos.tdd.modules;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

public class SigGenModule extends AbstractModule {

  @Override
  protected void configure() {
      Names.bindProperties(binder(), System.getProperties());

      bind(Session.class).toProvider(SessionProvider.class);
  }
  public static class SessionProvider implements Provider<Session> {


    private Session session;

    @Inject
    @Named("cassandraHost")
    private String host;

    @Override
    public Session get() {
      if (session == null) {
        session = Cluster.builder()
                .addContactPoint(host)
                .withPort(9042)
                .withQueryOptions(new QueryOptions().setConsistencyLevel(ConsistencyLevel.ALL))
                .build().newSession();
      }

      return session;
    }
  }
}
