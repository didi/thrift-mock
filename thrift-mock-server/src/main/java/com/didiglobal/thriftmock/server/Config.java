package com.didiglobal.thriftmock.server;


import org.apache.thrift.protocol.TProtocolFactory;

import java.util.Map;

public interface Config<P> {

  int getPort();
  TProtocolFactory getTProtocolFactory();
  Map<String, P> getProcessMap();

}
