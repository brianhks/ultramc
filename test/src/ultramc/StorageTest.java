package ultramc;

import org.depunit.annotations.*;
import org.depunit.RunContext;
import static org.junit.Assert.*;
import java.net.InetSocketAddress;

public class StorageTest
	{
	private MemCachedClient m_client;
	
	@Test( cleanupMethod = "stopClient" )
	public void startClient()
		{
		m_client = new MemCachedClient(new InetSocketAddress("localhost", 11211));
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
		}
	}
