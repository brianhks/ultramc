package ultramc;

import org.depunit.annotations.*;
import org.depunit.RunContext;
import static org.junit.Assert.*;

public class HashTest
	{
	private ConsistentHash m_conHash;
	
	@Test
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
		
		int zeros = 0;
		int ones = 0;
		int twos = 0;
		int threes = 0;
		int fours = 0;
		for (int I : table)
			{
			//System.out.println(I);
			if (I == 0) zeros ++;
			else if (I == 1) ones ++;
			else if (I == 2) twos ++;
			else if (I == 3) threes ++;
			else if (I == 4) fours ++;
			}
			
		System.out.println("0 = "+zeros);
		System.out.println("1 = "+ones);
		System.out.println("2 = "+twos);
		System.out.println("3 = "+threes);
		System.out.println("4 = "+fours);
		}
	}

