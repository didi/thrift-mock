package com.didiglobal.thriftmock.server;



import com.google.common.base.Preconditions;

import org.apache.thrift.TBase;
import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class ThriftMockServer implements MockServer {
  private static final Logger LOG = LoggerFactory.getLogger(ThriftMockServer.class);

  private ServerConfig serverConfig;
  private TServer tServer;

  public ThriftMockServer(int port) {
    this.serverConfig = new ServerConfig(port);
  }

  public ThriftMockServer(ServerConfig config){
    this.serverConfig =  config;
    Preconditions.checkArgument(config.getMinWorkerThread() > 0);
    Preconditions.checkArgument(config.getMaxWorkerThread() > 0);
  }

  @Override
  public void start() {
    // Only support blocking now
    TServerTransport serverTransport;
    try {
      serverTransport = new TServerSocket(serverConfig.getPort());
    } catch (TTransportException e) {
      LOG.warn("init server transport failed", e);
      throw new IllegalArgumentException("port already in use");
    }
    TBaseProcessor tProcessor = new TMockProcessor(serverConfig.getProcessMap());
    TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverTransport);
    serverArgs.processor(tProcessor);
    serverArgs.protocolFactory(serverConfig.getTProtocolFactory());
    serverArgs.minWorkerThreads(serverConfig.getMinWorkerThread());
    serverArgs.maxWorkerThreads(serverConfig.getMaxWorkerThread());
    tServer = new TThreadPoolServer(serverArgs);
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
    setExpectReturn(new ProcessFunctionMock(methodName, result));
  }

  @Override
  public void setExpectReturn(String methodName, TBase result, int delay) {
    setExpectReturn(new ProcessFunctionMock(methodName, result, delay));
  }


  @Override
  public void setExpectReturn(String methodName, TBase args,
                              Function<TBase, TBase> mockResultFunction) {
    setExpectReturn(new ProcessFunctionMock(methodName, args, mockResultFunction));
  }

  public void setExpectReturn(ProcessFunctionMock processFunction) {
    serverConfig.getProcessMap().put(processFunction.getMethodName(), processFunction);
  }

}
