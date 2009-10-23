package ultramc;

import java.util.*;
import java.nio.ByteBuffer;
import java.io.IOException;
import ultramc.connect.ServerConnection;
import ultramc.buffer.*;

public class DeleteOperation extends Operation<DeleteOperation>
	{
	public static final String DELETED = "DELETED";
	public static final String NOT_FOUND = "NOT_FOUND";
	
	private String m_key;
	private String m_response;
	private boolean m_reply;
	private int m_time;
	
	/*package*/ DeleteOperation(String key, MemCachedClient client)
		{
		super(client);
		m_response = NOT_CALLED;
		m_key = key;
		m_time = 0;
		m_reply = client.getDefaultReply();
		}
		
		
	public DeleteOperation setReply(boolean reply)
		{
		m_reply = reply;
		return (this);
		}
		
	public String getResponse()
		{
		return (m_response);
		}
		
	public DeleteOperation setTime(int time)
		{
		m_time = time;
		return (this);
		}
		
	public DeleteOperation run()
		{
		String resp = NOT_FOUND;
		String key = m_keyEncoder.encodeKey(m_key);
		int hash = hashKey(key);
		
		ServerConnection serverConnection = m_client.getServerConnection(hash);
		if (serverConnection == null)
			{
			m_response = ERROR;
			return (this);
			}
		
		StringBuilder command = new StringBuilder();
		command.append("delete ");
		command.append(key).append(" ");
		command.append(m_time).append(" ");
		if (!m_reply)
			command.append("noreply");
		command.append("\r\n");
		
		ByteBuffer[] sendBuffers = new ByteBuffer[1];
		sendBuffers[0] = UTF8.encode(command.toString());
		
		int bytesToWrite = sendBuffers[0].limit();
		
		BufferSet bs = m_client.getBufferSet();
		
		try
			{
			writeToChannel(serverConnection.getChannel(), sendBuffers, bytesToWrite);
			
			if (m_reply)
				{
				readResponse(serverConnection, bs, m_timeout, END_OF_LINE);
			
				String line = readLine(new ByteBufferInputStream(bs));
						
				if (line != null)
					resp = line;
				}
			else
				resp = DELETED;
			
			serverConnection.recycleConnection();
			}
		catch (IOException ioe)
			{
			ioe.printStackTrace();
			serverConnection.closeConnection();
			}
			
		bs.freeBuffers();
		m_response = resp;
		return (this);
		}
		
	public DeleteOperation runAsnyc()
		{
		
		return (this);
		}
	}
