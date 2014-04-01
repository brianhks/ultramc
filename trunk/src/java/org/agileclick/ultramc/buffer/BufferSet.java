package org.agileclick.ultramc.buffer;

import java.nio.ByteBuffer;
import java.util.*;

public class BufferSet
	{
	private ByteBuffer m_currentBuffer; //This is the last buffer returned from allocateBuffer
	private BufferPool m_pool;
	private ArrayList<ByteBuffer> m_buffers; //Buffers obtained from pool
	//Need to store buffers
	
	/*package*/ BufferSet(BufferPool pool)
		{
		m_pool = pool;
		m_buffers = new ArrayList<ByteBuffer>();
		m_currentBuffer = null;
		}
		
	public ByteBuffer[] allocateBuffers(int size)
		{
		int requiredBuffers = ((size / m_pool.getBufferSize()) + 1);
		ByteBuffer[] ret = new ByteBuffer[requiredBuffers];
		
		for (int I = 0; I < requiredBuffers; I++)
			{
			ret[I] = m_pool.getBufferFromPool();
			}
			
		ret[requiredBuffers -1].limit(size - (m_pool.getBufferSize() * (requiredBuffers-1)));
		return (ret);
		}
		
	public ByteBuffer allocateBuffer()
		{
		//ByteBuffer buf = m_ref.getBuffer();
		ByteBuffer buf = m_pool.getBufferFromPool();
		m_buffers.add(buf);
		m_currentBuffer = buf;
		return (buf);
		}
		
	public ByteBuffer getCurrentBuffer()
		{
		if (m_currentBuffer == null)
			allocateBuffer();
			
		return (m_currentBuffer);
		}
		
	public void freeBuffers()
		{
		for (ByteBuffer b : m_buffers)
			m_pool.returnBuffer(b);
			
		m_buffers.clear();
		}
		
	/**
		Flips all allocated buffers
	*/
	public void flipBuffers()
		{
		Iterator<ByteBuffer> it = m_buffers.iterator();
		
		while (it.hasNext())
			it.next().flip();
		}
		
	/**
	Returns previously allocated buffers
	*/
	public List<ByteBuffer> getBuffers()
		{
		//return (m_buffers.toArray(new ByteBuffer[0]));
		return (m_buffers);
		}
	}
