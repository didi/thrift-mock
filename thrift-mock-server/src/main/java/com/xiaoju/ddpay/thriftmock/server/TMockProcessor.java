package com.xiaoju.ddpay.thriftmock.server;

import org.apache.thrift.ProcessFunction;
import org.apache.thrift.TBaseProcessor;

import java.util.Map;

public class TMockProcessor extends TBaseProcessor {

  public TMockProcessor(Map<String, ProcessFunction> processFunctionMap) {
    super(new MockIface() {}, processFunctionMap);
  }
}
