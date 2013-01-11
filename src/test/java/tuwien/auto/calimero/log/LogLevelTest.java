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

package tuwien.auto.calimero.log;

import junit.framework.TestCase;
import tuwien.auto.calimero.log.LogLevel;

/**
 * @author B. Malinowsky
 */
public class LogLevelTest extends TestCase
{

	/**
	 * Test method for {@link tuwien.auto.calimero.log.LogLevel#equals(java.lang.Object)}.
	 */
	public void testEqualsObject()
	{
		assertTrue(LogLevel.ALL.equals(LogLevel.ALL));
		assertTrue(LogLevel.INFO.equals(LogLevel.INFO));
		assertFalse(LogLevel.TRACE.equals(LogLevel.OFF));
		assertFalse(LogLevel.WARN.equals(LogLevel.INFO));
		assertFalse(LogLevel.FATAL.equals(new Object()));
		assertFalse(LogLevel.FATAL.equals(null));
	}

	/**
	 * Test method for {@link tuwien.auto.calimero.log.LogLevel#higher
	 * (tuwien.auto.calimero.log.LogLevel)}.
	 */
	public void testHigher()
	{
		assertTrue(LogLevel.ALL.higher(LogLevel.TRACE));
		assertTrue(LogLevel.TRACE.higher(LogLevel.INFO));
		assertTrue(LogLevel.INFO.higher(LogLevel.WARN));
		assertTrue(LogLevel.WARN.higher(LogLevel.ERROR));
		assertTrue(LogLevel.ERROR.higher(LogLevel.FATAL));
		assertTrue(LogLevel.FATAL.higher(LogLevel.OFF));
		assertFalse(LogLevel.OFF.higher(LogLevel.OFF));
	}

}
