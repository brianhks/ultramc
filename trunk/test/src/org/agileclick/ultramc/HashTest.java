package org.agileclick.ultramc;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class HashTest
	{
	private static class Counter
		{
		private int m_count;
		public void Counter()
			{
			m_count = 1;
			}
			
		public void incr() { m_count ++; }
		public int getCount() { return (m_count); }
		}
		
	private ConsistentHash m_conHash;
	
	@Before
	public void createConsistentHash()
		{
		String[] keys = {
			"192.168.1.21:11211",
			"192.168.1.21:11212",
			"192.168.1.20:11211",
			"192.168.1.20:11212",
			"192.168.1.20:11213"
			};
			
		m_conHash = new ConsistentHash(1000, 700, keys);
		}
		
	@Test
	public void testConsistentHash()
		{
		int[] table = m_conHash.getLookupTable();
		
		Map<Integer, Counter> values = new HashMap<Integer, Counter>();
		for (int I : table)
			{
			Counter count = values.get(I);
			if (count == null)
				values.put(I, new Counter());
			else
				count.incr();
			}
			
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		int sum = 0;
		Iterator<Counter> it = values.values().iterator();
		while (it.hasNext())
			{
			Counter counter = it.next();
			System.out.println(counter.getCount());
			sum += counter.getCount();
			if (counter.getCount() < min)
				min = counter.getCount();
				
			if (counter.getCount() > max)
				max = counter.getCount();
			}
			
		double avg = (double)sum / (double)values.size();
		System.out.println("Max dif % = "+ ((double)(max - min) / avg));
		}
	}

