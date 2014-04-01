package org.agileclick.ultramc;

import java.io.*;

public interface ValueTranscoder
	{
	public void encodeValue(Object value, OutputStream os);
	public <T> T decodeValue(InputStream is, int size, int flags);
	}
