package com.didiglobal.thriftmock.server;

import org.apache.thrift.AsyncProcessFunction;
import org.apache.thrift.TBase;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class AsyncThriftMockServer implements MockServer {
  private static final Logger LOG = LoggerFactory.getLogger(AsyncThriftMockServer.class);

  private Config<AsyncProcessFunction> serverConfig;
  private TServer tServer;

  public AsyncThriftMockServer(int port) {
    this.serverConfig = new AsyncServerConfig(port);
  }

  public AsyncThriftMockServer(AsyncServerConfig config){
    this.serverConfig =  config;
  }

  @Override
  public void start() {
    TNonblockingServerSocket serverTransport;
    try {
      serverTransport = new TNonblockingServerSocket(serverConfig.getPort());
    } catch (TTransportException e) {
      LOG.warn("init server transport failed", e);
      throw new IllegalArgumentException("port already in use");
    }
    TProcessor tProcessor = new AsyncTMockProcessor(serverConfig.getProcessMap());
    TNonblockingServer.Args serverArgs = new TNonblockingServer.Args(serverTransport);
    serverArgs.processor(tProcessor);
    serverArgs.protocolFactory(serverConfig.getTProtocolFactory());
    tServer = new TNonblockingServer(serverArgs);
    tServer.serve();
  }

  @Override
  public void stop() {
    if (tServer != null) {
      tServer.stop();
      LOG.info("thrift mock server stopped!");
    }
  }

  @Override
  public void setExpectReturn(String methodName, TBase result) {
    setExpectReturn(methodName, new AsyncProcessFunctionMock(methodName, result));
  }

  @Override
  public void setExpectReturn(String methodName, TBase result, int delay) {
    setExpectReturn(methodName, new AsyncProcessFunctionMock(methodName, result, delay));
  }

  public void setExpectReturn(String methodName, AsyncProcessFunction processFunction) {
    serverConfig.getProcessMap().put(methodName, processFunction);
  }

  @Override
  public void setExpectReturn(String methodName, TBase args,
                              Function<TBase, TBase> mockResultFunction) {
    serverConfig.getProcessMap().put(methodName, new AsyncProcessFunctionMock(methodName, args, mockResultFunction, 0));
  }
}
