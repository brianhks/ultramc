package org.agileclick.ultramc.buffer;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BufferTest
	{
	private BufferPool m_pool;

	@Test
	public void testCreateBufferPool()
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
