package com.didiglobal.thriftmock.server;

import com.google.common.collect.Maps;

import org.apache.thrift.AsyncProcessFunction;
import org.apache.thrift.ProcessFunction;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import java.util.Map;

public class AsyncServerConfig implements Config<AsyncProcessFunction>{
  private int port;
  private TProtocolFactory tProtocolFactory;
  private Map<String, AsyncProcessFunction> processMap;

  public AsyncServerConfig(int port) {
    this(port, new TBinaryProtocol.Factory());
  }

  public AsyncServerConfig(int port, TProtocolFactory tProtocolFactory) {
    this.port = port;
    this.tProtocolFactory = tProtocolFactory;
    this.processMap = Maps.newConcurrentMap();
  }

  @Override
  public int getPort() {
    return port;
  }

  @Override
  public Map<String, AsyncProcessFunction> getProcessMap() {
    return processMap;
  }

  @Override
  public TProtocolFactory getTProtocolFactory() {
    return tProtocolFactory;
  }

  public AsyncServerConfig setPort(int port) {
    this.port = port;
    return this;
  }

  public AsyncServerConfig settServerProtocolFactory(TProtocolFactory tServerProtocolFactory) {
    this.tProtocolFactory = tServerProtocolFactory;
    return this;
  }

}
