package ultramc;

import javax.crypto.*;
import java.security.*;
import java.util.*;
import java.nio.charset.Charset;

public class ConsistentHash
	{
	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	public static class WeightedKey
		{
		private double m_weight;
		private String m_key;
		
		public WeightedKey(String key, double weight)
			{
			m_key = key;
			m_weight = weight;
			}
			
		public String getKey() { return (m_key); }
		public double getWeight() { return (m_weight); }
		}
		
	/**
		Provides a sin wave mapping of entries
	*/
	private static class EntryMapper
		{
		private int m_count;  //The number of values to map
		private int m_size;   //The size off the array to map them into
		
		public EntryMapper(int count, int size)
			{
			m_count = count;
			m_size = size;
			}
			
		public int mapEntry(int index)
			{
			return ((int)(((Math.cos(((Math.PI * (double)index) / (double)m_count ) + Math.PI) + 1.0) / 2.0 ) * (double)m_size));
			}
		}
	
	private int[] m_lookupTable;
	private List<WeightedKey> m_keys;
	private MessageDigest m_md5;
	private int m_hashCount;      //Number of primary entries to place in the lookup table
	
	public ConsistentHash(int tableSize, int hashCount, String[] keys)
		{
		List<WeightedKey> wkeys = new ArrayList<WeightedKey>();
		for (String s : keys)
			wkeys.add(new WeightedKey(s, 1.0));
			
		init(tableSize, hashCount, wkeys);
		}
	
	public ConsistentHash(int tableSize, int hashCount, List<WeightedKey> keys)
		{
		init(tableSize, hashCount, keys);
		}
		
	private void init(int tableSize, int hashCount, List<WeightedKey> keys)
		{
		m_hashCount = hashCount;
		m_lookupTable = new int[tableSize];
		m_keys = new ArrayList<WeightedKey>();
		m_keys.addAll(keys);
		try
			{
			m_md5 = MessageDigest.getInstance("MD5");
			}
		catch (java.security.NoSuchAlgorithmException nsae)
			{
			nsae.printStackTrace();
			}
		
		reHash();
		}
		
		
	//---------------------------------------------------------------------------
	public String lookupKey(String lookupString)
		{
		long value = hash(lookupString);
		
		return (m_keys.get(m_lookupTable[(int)(value % m_lookupTable.length)]).getKey());
		}
		
	//---------------------------------------------------------------------------
	/*package*/ int[] getLookupTable() { return (m_lookupTable); }
	
	//---------------------------------------------------------------------------
	private long hash(String str)
		{
		byte[] hash;
		long tmp = 0L;
		long value = 0L;

		m_md5.update(UTF8.encode(str));
		hash = m_md5.digest();
		
		//We use the first 7 bytes to create the value for our lookup table
		tmp = hash[0] & 0xff;
		value |= tmp << (6*8);
		tmp = hash[1] & 0xff;
		value |= tmp << (5*8);
		tmp = hash[2] & 0xff;
		value |= tmp << (4*8);
		tmp = hash[3] & 0xff;
		value |= tmp << (3*8);
		tmp = hash[4] & 0xff;
		value |= tmp << (2*8);
		tmp = hash[5] & 0xff;
		value |= tmp << (1*8);
		tmp = hash[6] & 0xff;
		value |= tmp; //	 << (0*8);

		return (value);
		}
	
	//---------------------------------------------------------------------------
	private Set<Integer> createHashSet(String key, int count)
		{
		Set<Integer> ret = new HashSet<Integer>();
		
		for (int I = 0; I < count; I++)
			{
			long value = hash(key + I);
			
			int hashVal = (int)(value % m_lookupTable.length);
			//System.out.println(hashVal);
			ret.add(hashVal);
			}
			
		//System.out.println(ret.size());
		return (ret);
		
		/* Set<Integer> ret = new HashSet<Integer>();
		//Hash of key detirmins where the oscilation starts
		long hash = hash(key);
		//System.out.println("hash "+hash);
		int start = (int)(hash % m_lookupTable.length);
		
		int oscillations = (int)(hash & 0x1F) +1;
		System.out.println("Oscil "+oscillations);
		
		int oscilLength = m_lookupTable.length / oscillations;
		int oscilCount = count / oscillations;
		EntryMapper mapper = new EntryMapper(oscilCount ,oscilLength);
		
		
		int curPos = start;
		
		for (int oscNum = 0; oscNum < oscillations; oscNum++)
			{
			for (int I = 0; I < oscilCount; I++)
				{
				int entry = curPos + mapper.mapEntry(I);
				if (entry > m_lookupTable.length)
					entry = entry - m_lookupTable.length;
					
				//System.out.println("Entry = "+entry);
				ret.add(entry);
				}
				
			curPos += oscilLength;
			}
			
		System.out.println("Set size "+ret.size());
		return (ret); */
		}
		
	//---------------------------------------------------------------------------
	private void reHash()
		{
		Map<Integer, Integer> primaryEntryMap = new HashMap<Integer, Integer>();
		
		for (int I = 0; I < m_keys.size(); I++)
			{
			//figure the weighted number of hash numbers to create
			int count = (int)((double)m_hashCount * m_keys.get(I).getWeight());
			
			Set<Integer> hashSet = createHashSet(m_keys.get(I).getKey(), count);
			
			for (Integer i : hashSet)
				{
				if (primaryEntryMap.put(i, I) != null)
					primaryEntryMap.put(i, -1);  //Any colision is set to -1 and will be removed
				}
			}
			
		//index in lookupTable cannot be zero (it has no negative for a shadow value)
		//So all values will be off by one from the key entry
		int lastValue = -1;
		for (int I = 0; I < m_lookupTable.length; I++)
			{
			Integer value = primaryEntryMap.get(I);
			if ((value == null)||(value == -1))
				{
				//if ((value != null) && (value == -1)) System.out.println("colision");
				m_lookupTable[I] = lastValue;
				}
			else
				{
				m_lookupTable[I] = value;
				lastValue = value; //set shadow value
				}
			}
			
		//set the first non set values to the shadow value fo the last primary so we get the loop around
		for (int I = 0; (I < m_lookupTable.length) && (m_lookupTable[I] == -1); I++)
			m_lookupTable[I] = lastValue;
		}
	}
