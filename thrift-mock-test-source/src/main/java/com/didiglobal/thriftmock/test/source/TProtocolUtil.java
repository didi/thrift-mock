package com.didiglobal.thriftmock.test.source;


import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class TProtocolUtil {

  private TProtocolUtil(){}


  public static TProtocol initTProtocol(String host, int port) throws TTransportException {
    return initTProtocol(host, port, null);
  }

  public static TProtocol initTProtocol(String host, int port,
                                        Integer timeout) throws TTransportException {
    TTransport transport;
    if (timeout == null || timeout <= 0){
      transport = new TSocket(host, port);
    } else {
      transport = new TSocket(host, port, timeout);
    }
    transport.open();
    return new TBinaryProtocol(transport);
  }


}
