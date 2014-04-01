package org.agileclick.ultramc;

public class SetOperation extends StorageOperation<SetOperation>
	{
	public static final String OPERATION = "set";
	
	/*package*/ SetOperation(String key, Object value, MemCachedClient mcClient)
		{
		super(key, value, mcClient);
		}
		
	protected String getOperation() { return (OPERATION); } 
	}
