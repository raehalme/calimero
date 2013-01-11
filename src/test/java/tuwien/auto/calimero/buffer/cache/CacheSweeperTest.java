/*
    Calimero - A library for KNX network access
    Copyright (C) 2006-2008 B. Malinowsky

    This program is free software; you can redistribute it and/or 
    modify it under the terms of the GNU General Public License 
    as published by the Free Software Foundation; either version 2 
    of the License, or at your option any later version. 
 
    This program is distributed in the hope that it will be useful, 
    but WITHOUT ANY WARRANTY; without even the implied warranty of 
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
    GNU General Public License for more details. 
 
    You should have received a copy of the GNU General Public License 
    along with this program; if not, write to the Free Software 
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. 
 
    Linking this library statically or dynamically with other modules is 
    making a combined work based on this library. Thus, the terms and 
    conditions of the GNU General Public License cover the whole 
    combination. 
 
    As a special exception, the copyright holders of this library give you 
    permission to link this library with independent modules to produce an 
    executable, regardless of the license terms of these independent 
    modules, and to copy and distribute the resulting executable under terms 
    of your choice, provided that you also meet, for each linked independent 
    module, the terms and conditions of the license of that module. An 
    independent module is a module which is not derived from or based on 
    this library. If you modify this library, you may extend this exception 
    to your version of the library, but you are not obligated to do so. If 
    you do not wish to do so, delete this exception statement from your 
    version. 
*/

package tuwien.auto.calimero.buffer.cache;

import junit.framework.TestCase;
import tuwien.auto.calimero.buffer.cache.Cache;
import tuwien.auto.calimero.buffer.cache.CacheObject;
import tuwien.auto.calimero.buffer.cache.CacheSweeper;
import tuwien.auto.calimero.exception.KNXIllegalArgumentException;

/**
 * @author B. Malinowsky
 */
public class CacheSweeperTest extends TestCase
{

	class TestCache implements Cache
	{
		public void clear()
		{}

		public CacheObject get(Object key)
		{
			return null;
		}

		public void put(CacheObject obj)
		{}

		public void remove(Object key)
		{}

		public synchronized void removeExpired()
		{
			this.notifyAll();
		}

		public Statistic statistic()
		{
			return null;
		}
	}

	Cache test;
	CacheSweeper sweeper;

	/**
	 * @param name name of test case
	 */
	public CacheSweeperTest(String name)
	{
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		test = new TestCache();
		sweeper = new CacheSweeper(test, 4);
		sweeper.start();
		Thread.sleep(10);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		if (sweeper != null)
			sweeper.stopSweeper();
	}

	/**
	 * Test method for
	 * {@link tuwien.auto.calimero.buffer.cache.CacheSweeper#setSweepInterval(int)}.
	 */
	public void testSetSweepInterval()
	{
		boolean zero = false;
		try {
			sweeper.setSweepInterval(0);
		}
		catch (final KNXIllegalArgumentException e) {
			zero = true;
		}
		assertTrue(zero);

		zero = false;
		try {
			sweeper.setSweepInterval(-3);
		}
		catch (final KNXIllegalArgumentException e) {
			zero = true;
		}
		assertTrue(zero);

		long now = System.currentTimeMillis();
		synchronized (test) {
			sweeper.setSweepInterval(1);
			try {
				test.wait();
			}
			catch (final InterruptedException e) {}
		}
		long after = System.currentTimeMillis();
		assertTrue(after - now < 1050);
		assertTrue(after - now >= 900);

		now = System.currentTimeMillis();
		synchronized (test) {
			sweeper.setSweepInterval(2);
			try {
				test.wait();
			}
			catch (final InterruptedException e) {}
		}
		after = System.currentTimeMillis();
		assertTrue("it was " + String.valueOf(after - now), after - now >= 2000);
		assertTrue("it was " + String.valueOf(after - now), after - now < 2050);
	}

	/**
	 * Test method for
	 * {@link tuwien.auto.calimero.buffer.cache.CacheSweeper#stopSweeper()}.
	 */
	public void testStopSweeper()
	{
		final long now = System.currentTimeMillis();
		sweeper.stopSweeper();
		try {
			sweeper.join();
		}
		catch (final InterruptedException e) {}
		final long after = System.currentTimeMillis();
		assertTrue(after - now < 50);
	}

}
