package ultramc;


public class KeyedOperation<T extends KeyedOperation> 
		extends Operation<T>
	{
	protected String m_key;
	protected String m_hashKey;
	
	public KeyedOperation(String key, MemCachedClient client)
		{
		super(client);
		
		m_key = key;
		m_hashKey = key;
		}
		
	@SuppressWarnings("unchecked")
	public T setKeyEncoder(KeyEncoder encoder)
		{
		m_keyEncoder = encoder;
		return ((T)this);
		}
		
	@SuppressWarnings("unchecked")
	public T setHashKey(String hashKey)
		{
		m_hashKey = hashKey;
		return ((T)this);
		}
	}
