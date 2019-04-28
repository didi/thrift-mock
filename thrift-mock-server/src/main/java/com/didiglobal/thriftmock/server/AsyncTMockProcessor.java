package com.didiglobal.thriftmock.server;

import org.apache.thrift.AsyncProcessFunction;
import org.apache.thrift.ProcessFunction;
import org.apache.thrift.TBaseAsyncProcessor;
import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TProcessor;

import java.util.Map;

public class AsyncTMockProcessor extends TBaseAsyncProcessor{

  public AsyncTMockProcessor(Map<String, AsyncProcessFunction> processFunctionMap) {
    super(new MockIface() {}, processFunctionMap);
  }
}
