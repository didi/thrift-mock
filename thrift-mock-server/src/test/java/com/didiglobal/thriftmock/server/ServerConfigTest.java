package com.didiglobal.thriftmock.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.junit.Assert;
import org.junit.Test;

public class ServerConfigTest {

    @Test
    public void testConstructorWithPort() {
        ServerConfig config = new ServerConfig(8800);
        Assert.assertEquals(8800, config.getPort());
        Assert.assertNotNull(config.getTProtocolFactory());
        Assert.assertNotNull(config.getProcessMap());
        Assert.assertTrue(config.getProcessMap().isEmpty());
    }

    @Test
    public void testConstructorWithPortAndFactory() {
        TProtocolFactory factory = new TBinaryProtocol.Factory();
        ServerConfig config = new ServerConfig(8801, factory);
        Assert.assertEquals(8801, config.getPort());
        Assert.assertEquals(factory, config.getTProtocolFactory());
        Assert.assertEquals(2, config.getMinWorkerThread());
        Assert.assertEquals(2, config.getMaxWorkerThread());
    }

    @Test
    public void testConstructorWithAllArgs() {
        TProtocolFactory factory = new TBinaryProtocol.Factory();
        ServerConfig config = new ServerConfig(8802, factory, 4, 8);
        Assert.assertEquals(8802, config.getPort());
        Assert.assertEquals(factory, config.getTProtocolFactory());
        Assert.assertEquals(4, config.getMinWorkerThread());
        Assert.assertEquals(8, config.getMaxWorkerThread());
    }

    @Test
    public void testSetPort() {
        ServerConfig config = new ServerConfig(8800);
        ServerConfig returned = config.setPort(8803);
        Assert.assertEquals(8803, config.getPort());
        Assert.assertSame(config, returned);
    }

    @Test
    public void testSetMinWorkerThread() {
        ServerConfig config = new ServerConfig(8800);
        config.setMinWorkerThread(5);
        Assert.assertEquals(5, config.getMinWorkerThread());
    }

    @Test
    public void testSetMaxWorkerThread() {
        ServerConfig config = new ServerConfig(8800);
        config.setMaxWorkerThread(10);
        Assert.assertEquals(10, config.getMaxWorkerThread());
    }

    @Test
    public void testSettServerProtocolFactory() {
        ServerConfig config = new ServerConfig(8800);
        TProtocolFactory factory = new TCompactProtocol.Factory();
        ServerConfig returned = config.settServerProtocolFactory(factory);
        Assert.assertEquals(factory, config.getTProtocolFactory());
        Assert.assertSame(config, returned);
    }

    @Test
    public void testProcessMapIsWritable() {
        ServerConfig config = new ServerConfig(8800);
        Assert.assertNotNull(config.getProcessMap());
        Assert.assertEquals(0, config.getProcessMap().size());
    }
}
