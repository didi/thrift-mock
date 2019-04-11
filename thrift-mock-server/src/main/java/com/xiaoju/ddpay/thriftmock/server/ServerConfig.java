package com.xiaoju.ddpay.thriftmock.server;

import com.google.common.collect.Maps;

import org.apache.thrift.ProcessFunction;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerConfig {
  private static final int DEFAULT_THREAD_SIZE = 2;
  private int port;
  private int minWorkerThread;
  private int maxWorkerThread;
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
    config.setMinWorkerThread(DEFAULT_THREAD_SIZE);
    config.setMaxWorkerThread(DEFAULT_THREAD_SIZE);
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

  public int getMinWorkerThread() {
    return minWorkerThread;
  }

  public void setMinWorkerThread(int minWorkerThread) {
    this.minWorkerThread = minWorkerThread;
  }

  public int getMaxWorkerThread() {
    return maxWorkerThread;
  }

  public void setMaxWorkerThread(int maxWorkerThread) {
    this.maxWorkerThread = maxWorkerThread;
  }
}
