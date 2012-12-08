package ultramc.buffer;

import org.depunit.annotations.*;
import org.depunit.RunContext;
import static org.junit.Assert.*;

public class BufferTest
	{
	private BufferPool m_pool;
	
	@Test
	public void createBufferPool()
		{
		m_pool = new BufferPool(1024);
		
		BufferSet bs = m_pool.createBufferSet();
		
		for (int I = 0; I < 10; I++)
			bs.allocateBuffer();
			
		bs.freeBuffers();
		
		for (int I = 0; I < 10; I++)
			bs.allocateBuffer();
			
		bs.freeBuffers();
		
		assertEquals(10, m_pool.getPoolSize());
		}
	}
