package ultramc.buffer;

import java.io.OutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class ByteBufferOutputStream extends OutputStream
	{
	private BufferSet m_bufferSet;
	private ByteBuffer m_curBuffer;
	//private int m_writeCount;
	
	public ByteBufferOutputStream(BufferSet bufferSet)
		{
		m_bufferSet = bufferSet;
		m_curBuffer = m_bufferSet.allocateBuffer();
		}
		
		
	public BufferSet getBufferSet()
		{
		//need to flip the buffers
		Iterator<ByteBuffer> it = m_bufferSet.getBuffers().iterator();
		
		while (it.hasNext())
			it.next().flip();
		
		return (m_bufferSet);
		}
		
	/* public int getWriteCount()
		{
		return (m_writeCount);
		} */
	
	//TODO implement the other write methods
		
	public void write(int b)
			throws IOException
		{
		//m_writeCount ++;
		
		if (m_curBuffer.position() == m_curBuffer.limit())
			{
			m_curBuffer = m_bufferSet.allocateBuffer();
			}
		
		m_curBuffer.put((byte)b);
			
		//System.out.print(b+" ");
		}
		
	}
