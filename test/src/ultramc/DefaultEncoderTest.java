package ultramc;

import ultramc.buffer.*;
import org.depunit.annotations.*;
import org.depunit.RunContext;
import java.nio.ByteBuffer;
import static org.junit.Assert.*;
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
