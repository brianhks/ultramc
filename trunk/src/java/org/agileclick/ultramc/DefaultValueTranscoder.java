package org.agileclick.ultramc;

import org.agileclick.ultramc.buffer.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DefaultValueTranscoder implements ValueTranscoder
	{
	private BufferPool m_pool;
	
	public DefaultValueTranscoder(BufferPool pool)
		{
		m_pool = pool;
		}
		
	public void encodeValue(Object value, OutputStream os)
		{
		try
			{
			ObjectOutputStream oos = new ObjectOutputStream(os);
		
			oos.writeObject(value);
			}
		catch (IOException ioe)
			{
			ioe.printStackTrace();
			}
			
		}
		
	@SuppressWarnings("unchecked")
	public <T> T decodeValue(InputStream is, int size, int flags)
		{
		T ret = null;
		
		try
			{
			ObjectInputStream ois = new ObjectInputStream(is);
			
			ret = (T)ois.readObject();
			}
		catch (ClassCastException cce)
			{
			cce.printStackTrace();
			}
		catch (IOException ioe)
			{
			ioe.printStackTrace();
			}
		catch (ClassNotFoundException cnfe)
			{
			cnfe.printStackTrace();
			}
			
		return (ret);
		}
	}
