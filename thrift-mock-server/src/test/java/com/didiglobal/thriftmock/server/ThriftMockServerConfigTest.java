package com.didiglobal.thriftmock.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.junit.Test;

public class ThriftMockServerConfigTest {

    @Test
    public void testStopWithoutStart_doesNotThrow() {
        ThriftMockServer server = new ThriftMockServer(8810);
        server.stop(); // tServer is null, must not throw
    }

    @Test
    public void testAsyncStopWithoutStart_doesNotThrow() {
        AsyncThriftMockServer server = new AsyncThriftMockServer(new AsyncServerConfig(8811));
        server.stop(); // tServer is null, must not throw
    }

    @Test
    public void testThriftMockServerWithValidCustomConfig() {
        ServerConfig config = new ServerConfig(8812, new TBinaryProtocol.Factory(), 1, 4);
        ThriftMockServer server = new ThriftMockServer(config);
        server.stop(); // must not throw
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThriftMockServerWithZeroMinWorkerThread_throws() {
        ServerConfig config = new ServerConfig(8813, new TBinaryProtocol.Factory(), 0, 2);
        new ThriftMockServer(config);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThriftMockServerWithZeroMaxWorkerThread_throws() {
        ServerConfig config = new ServerConfig(8814, new TBinaryProtocol.Factory(), 1, 0);
        new ThriftMockServer(config);
    }

    @Test
    public void testAsyncSetExpectReturnWithDelay() {
        AsyncThriftMockServer server = new AsyncThriftMockServer(new AsyncServerConfig(8815));
        // Exercises the (String, TBase, int) overload and AsyncProcessFunctionMock(String, TBase, int) constructor
        server.setExpectReturn("sayHello", new com.didiglobal.thriftmock.test.source.Response(200, "test"), 50);
        server.stop();
    }

    @Test
    public void testAsyncConstructorWithIntPort() {
        // Exercises the AsyncThriftMockServer(int port) constructor
        AsyncThriftMockServer server = new AsyncThriftMockServer(8816);
        server.stop();
    }

    @Test
    public void testAsyncSetExpectReturnWithDynamicFunction() {
        AsyncThriftMockServer server = new AsyncThriftMockServer(new AsyncServerConfig(8817));
        // Exercises the (String, TBase, Function) overload
        server.setExpectReturn(
            "sayHello",
            new com.didiglobal.thriftmock.test.source.HelloService.sayHello_args(),
            args -> new com.didiglobal.thriftmock.test.source.Response(200, "dynamic"));
        server.stop();
    }
}
