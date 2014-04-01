package org.agileclick.ultramc;

import org.junit.Test;


public class StorageTest
	{
	private MemCachedClient m_client;

	@Test
	public void noTest()
		{
		//Just to make junit happy till this moves
		}

	//this is an integration test that should be ran with testng
	/*@Test( cleanupMethod = "stopClient" )
	public void startClient()
		{
		String memServer = System.getenv().get("memcached_server");
		if (memServer == null)
			memServer = "localhost";
		m_client = new MemCachedClient(new InetSocketAddress(memServer, 11211));
		m_client.setDefaultExpiry(10);
		m_client.setDefaultTimeout(1000);
		
		m_client.flushAll();
		}
		
	@Test
	public void stopClient()
		{
		m_client.close();
		}
		
	@Test (hardDependencyOn = { "startClient" } )
	public void testSetOperation()
		{
		assertEquals(
				StorageOperation.STORED, 
				m_client.createSet("key", "value").run().getResponse() );
				
		assertEquals(
				DeleteOperation.DELETED,
				m_client.createDelete("key").setReply(false).run().getResponse());
				
		}
		
	@Test (hardDependencyOn = { "testSetOperation" } )
	public void testGetOperation()
		{
		String value = (String)m_client.createGet("key").run().getValue();
		assertNull(value);
		
		assertEquals(
				StorageOperation.STORED, 
				m_client.createSet("key", "value").setReply(true).run().getResponse() );
		
		value = (String)m_client.createGet("key").run().getValue();
		assertEquals("value", value);
		}*/
	}
