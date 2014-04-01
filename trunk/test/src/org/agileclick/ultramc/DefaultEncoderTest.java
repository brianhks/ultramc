package org.agileclick.ultramc;

import org.junit.Test;
import org.agileclick.ultramc.buffer.*;
import java.nio.ByteBuffer;
import java.util.*;

public class DefaultEncoderTest
	{
	@Test
	public void firstEncoderTest()
		{
		BufferPool pool = new BufferPool(1024);
		DefaultValueTranscoder encoder = new DefaultValueTranscoder(pool);
		
		BufferSet bs = pool.createBufferSet();
		encoder.encodeValue("String to encode", new ByteBufferOutputStream(bs));
		List<ByteBuffer> bufList = bs.getBuffers();
		
		//System.out.println(bufList.size());
		//System.out.println(bufList.get(0).limit());
		}
	}
