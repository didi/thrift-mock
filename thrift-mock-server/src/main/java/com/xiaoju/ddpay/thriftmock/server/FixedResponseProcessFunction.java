package com.xiaoju.ddpay.thriftmock.server;

import org.apache.thrift.TBase;

public class FixedResponseProcessFunction extends DynamicResponseProcessFunction{


  public FixedResponseProcessFunction(String methodName, TBase result) {
    this(methodName, result, 0);
  }

  public FixedResponseProcessFunction(String methodName, TBase result, int delay) {
    super(methodName, result, tBase -> result, delay);
  }

}
