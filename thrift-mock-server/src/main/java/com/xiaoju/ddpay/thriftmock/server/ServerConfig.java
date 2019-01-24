package com.xiaoju.ddpay.thriftmock.server;

import com.google.common.collect.Maps;

import org.apache.thrift.ProcessFunction;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerConfig {
  private int port;
  private TProtocolFactory tServerProtocolFactory;
  private Map<String, ProcessFunction> processMap = Maps.newConcurrentMap();

  public static ServerConfig createServerConfig() {
    ServerConfig config = new ServerConfig();
    config.settServerProtocolFactory(new TBinaryProtocol.Factory());
    return config;
  }

  public static ServerConfig createServerConfig(int port) {
    ServerConfig config = new ServerConfig();
    config.settServerProtocolFactory(new TBinaryProtocol.Factory())
        .setPort(port);
    return config;
  }

  public int getPort() {
    return port;
  }

  public ServerConfig setPort(int port) {
    this.port = port;
    return this;
  }

  public TProtocolFactory gettServerProtocolFactory() {
    return tServerProtocolFactory;
  }

  public ServerConfig settServerProtocolFactory(TProtocolFactory tServerProtocolFactory) {
    this.tServerProtocolFactory = tServerProtocolFactory;
    return this;
  }

  public Map<String, ProcessFunction> getProcessMap() {
    return processMap;
  }

  public void setProcessMap(Map<String, ProcessFunction> processMap) {
    this.processMap = processMap;
  }
}
