package org.agileclick.ultramc.buffer;

import java.nio.ByteBuffer;
import java.util.*;

public class BufferPool
	{
	private int m_bufferSize;
	private int m_maxPoolSize;
	private int m_minPoolSize;
	private Stack<ByteBuffer> m_bufferPool;
	
	
	
	public BufferPool(int bufferSize)
		{
		m_bufferSize = bufferSize;
		m_bufferPool = new Stack<ByteBuffer>();
		m_maxPoolSize = 1000;
		m_minPoolSize = 100;
		}
		
	/*package*/ int getBufferSize() { return (m_bufferSize); }
		
	//---------------------------------------------------------------------------
	public BufferSet createBufferSet()
		{
		BufferSet bs = new BufferSet(this);
		//BufferSetReference bsr = new BufferSetReference(bs, m_refQueue);
		//m_refSet.put(bsr, "");
		return (bs);
		}
		
	public void setMaxPoolSize(int size)
		{
		m_maxPoolSize = size;
		}
		
	public void setMinPoolSize(int size)
		{
		m_minPoolSize = size;
		}
		
	public int getMinPoolSize() { return (m_minPoolSize); }
		
	//---------------------------------------------------------------------------
	/**
		This must be synchronized because if we check and then another thread pops
		the last buffer the pop will throw an exception.
		
		returning buffers does not need to be synchronized because the underlying
		stack is synchronized.
	*/
	/*package*/ synchronized ByteBuffer getBufferFromPool()
		{
		ByteBuffer ret;
		
		if (m_bufferPool.isEmpty())
			ret = ByteBuffer.allocateDirect(m_bufferSize);
		else
			ret = m_bufferPool.pop();
			
		return (ret);
		}
		
	//---------------------------------------------------------------------------
	public void close()
		{
		while (!m_bufferPool.isEmpty())
			m_bufferPool.pop();
		}
		
	//---------------------------------------------------------------------------
	public void returnBuffer(ByteBuffer buf)
		{
		if (m_bufferPool.size() < m_maxPoolSize)
			{
			buf.clear();
			m_bufferPool.push(buf);
			}
		}
		
	//---------------------------------------------------------------------------
	public int getPoolSize()
		{
		return (m_bufferPool.size());
		}
		
	}
