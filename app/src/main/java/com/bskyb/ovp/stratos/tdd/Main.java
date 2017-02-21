package com.bskyb.ovp.stratos.tdd;

import com.bskyb.ovp.stratos.tdd.config.SigGenConfig;
import com.bskyb.ovp.stratos.tdd.modules.SigGenModule;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ratpack.guice.Guice;
import ratpack.handling.RequestLogger;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;
import ratpack.server.ServerConfigBuilder;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Main {

  public static void main(String[] args) throws Exception {
    ServerConfigBuilder serverConfig = ServerConfig
      .builder()
      .development(false)
      .port(8080)
      .props(Main.class.getResource("/config/default.properties"))
      .sysProps("config.")
      .require("/siggen", SigGenConfig.class);

    RatpackServer.start(server -> server
        .serverConfig(serverConfig)
        .registry(Guice.registry(b -> b
            .module(SigGenModule.class)
            .add(createDefaultObjectMapper())
        ))
        .handlers(chain -> chain
            .all(RequestLogger.ncsa())
            .get("hello", ctx -> ctx.getResponse().send("Hello World"))
        )
    );
  }




  private static ObjectMapper createDefaultObjectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss\'Z\'");
    df.setTimeZone(TimeZone.getTimeZone("UTC"));
    objectMapper.setDateFormat(df);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    objectMapper.registerModule(new Jdk8Module());
    objectMapper.registerModule(new JavaTimeModule());
    JsonFactory factory = objectMapper.getFactory();
    factory.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
    factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
    return objectMapper;
  }
}
