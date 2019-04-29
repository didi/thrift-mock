package com.didiglobal.thriftmock.server;

import com.google.common.collect.Maps;

import org.apache.thrift.ProcessFunction;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import java.util.Map;

public class ServerConfig implements Config<ProcessFunction>{
  private static final int DEFAULT_THREAD_SIZE = 2;
  private int port;
  private int minWorkerThread;
  private int maxWorkerThread;
  private TProtocolFactory tProtocolFactory;
  private Map<String, ProcessFunction> processMap = Maps.newConcurrentMap();

  public ServerConfig(int port) {
    this(port, new TBinaryProtocol.Factory());
  }

  public ServerConfig(int port, TProtocolFactory tProtocolFactory) {
    this(port, tProtocolFactory, DEFAULT_THREAD_SIZE, DEFAULT_THREAD_SIZE);

  }

  public ServerConfig(int port, TProtocolFactory tProtocolFactory,
                      int minWorkerThread, int maxWorkerThread) {
    this.port = port;
    this.tProtocolFactory = tProtocolFactory;
    this.minWorkerThread = minWorkerThread;
    this.maxWorkerThread = maxWorkerThread;
  }

  @Override
  public int getPort() {
    return port;
  }

  @Override
  public Map<String, ProcessFunction> getProcessMap() {
    return processMap;
  }

  @Override
  public TProtocolFactory getTProtocolFactory() {
    return tProtocolFactory;
  }

  public ServerConfig setPort(int port) {
    this.port = port;
    return this;
  }

  public ServerConfig settServerProtocolFactory(TProtocolFactory tServerProtocolFactory) {
    this.tProtocolFactory = tServerProtocolFactory;
    return this;
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
