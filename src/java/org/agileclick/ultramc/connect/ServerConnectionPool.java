package org.agileclick.ultramc.connect;

import java.util.*;
import java.nio.channels.SocketChannel;
import java.io.IOException;
import java.net.SocketAddress;

public class ServerConnectionPool
	{
	private Stack<ServerConnection> m_connectionPool;
	private SocketAddress m_sockAddress;
	private int m_maxPoolSize;
	private int m_minPoolSize;
	private boolean m_connectFailure;
	
	public ServerConnectionPool(SocketAddress sockAddr /*, Some error handler*/)
		{
		m_sockAddress = sockAddr;
		m_connectionPool = new Stack<ServerConnection>();
		m_maxPoolSize = 100;
		m_minPoolSize = 5;
		}
		
	public synchronized ServerConnection getServerConnection()
		{
		ServerConnection ret = null;
		
		//TODO: need to make a way to get out of this state
		if (m_connectFailure)
			return (null);
			
		if (m_connectionPool.isEmpty())
			{
			try
				{
				//System.out.println("NEW SOCKET");
				SocketChannel channel = SocketChannel.open(m_sockAddress);
				
				ret = new ServerConnection(channel, this);
				}
			catch (IOException ioe)
				{
				m_connectFailure = true;
				//Fling the exception to handler
				ioe.printStackTrace();
				}
			}
		else
			{
			//System.out.println("Reuse socket");
			ret = m_connectionPool.pop();
			}
			
		return (ret);
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
		
	public void close()
		{
		while (!m_connectionPool.isEmpty())
			{
			m_connectionPool.pop().closeConnection();
			}
		}
		
	public void returnConnection(ServerConnection sc)
		{
		if (m_connectionPool.size() < m_maxPoolSize)
			m_connectionPool.push(sc);
		else
			sc.closeConnection();
		}
	}
