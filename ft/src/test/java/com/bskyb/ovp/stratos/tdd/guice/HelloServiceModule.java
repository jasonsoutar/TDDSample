package com.bskyb.ovp.stratos.tdd.guice;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;


public class HelloServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    Names.bindProperties(binder(), System.getProperties());

    bind(HttpRequestFactory.class).toProvider(HttpRequestFactoryProvider.class);
    bind(Session.class).toProvider(SessionProvider.class);
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
        createSchema(session);
      }

      return session;
    }

    private void createSchema(Session session) {
      session.execute("CREATE KEYSPACE IF NOT EXISTS siggen WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1}");
      session.execute(SchemaBuilder.createTable("siggen", "customsig")
              .ifNotExists()
              .addPartitionKey("name", DataType.text())
              .addColumn("customsig", DataType.text()));

    }
  }
}
