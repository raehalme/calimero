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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;
import tuwien.auto.calimero.log.ErrorHandler;
import tuwien.auto.calimero.log.LogLevel;
import tuwien.auto.calimero.log.LogStreamWriter;
import tuwien.auto.calimero.log.LogWriter;

/**
 * @author B. Malinowsky
 */
public class LogStreamWriterTest extends TestCase
{
	private static final String file = "./src/test/stream.log";
	private static final String file2 = "./src/test/stream2.log";
	
	private LogWriter w, w2;
	private final String logService = "my LogService";

	private OutputStream stream;

	private class MyErrorHandler extends ErrorHandler
	{
		public synchronized void error(LogWriter source, String msg, Exception e)
		{
			super.error(source, msg, e);
		}
	}

	/**
	 * @param name name for test case
	 */
	public LogStreamWriterTest(String name)
	{
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		w = new LogStreamWriter(LogLevel.ALL, stream = new FileOutputStream(file), true);
		// no autoflush
		w2 = new LogStreamWriter(LogLevel.WARN, new FileOutputStream(file2), false);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		w.close();
		w = null;
		w2.close();
		w2 = null;
	}

	/**
	 * Test method for
	 * {@link tuwien.auto.calimero.log.LogStreamWriter#write(String, LogLevel, String)}.
	 * 
	 * @throws IOException
	 */
	public void testWriteLevelString() throws IOException
	{
		w.setLogLevel(LogLevel.INFO);
		w.write(logService, LogLevel.INFO, "info msg");
		String[] buf = readLines(file);
		assertEquals(1, buf.length);
		w.write(logService, LogLevel.TRACE, "this string should not appear here");
		buf = readLines(file);
		assertEquals(1, buf.length);
		w.setLogLevel(LogLevel.OFF);
		w.write(logService, LogLevel.OFF, "this is a OFF msg");
		buf = readLines(file);
		assertEquals(1, buf.length);
		w.write(logService, LogLevel.ERROR, "error: this is a ERROR msg at loglevel OFF");
		buf = readLines(file);
		assertEquals(1, buf.length);

		w2.write(logService, LogLevel.INFO, "this string should not appear here");
		w2.write(logService, LogLevel.WARN, "this string is not autoflushed");
		buf = readLines(file2);
		assertEquals(0, buf.length);
		w2.close();
		buf = readLines(file2);
		assertEquals(1, buf.length);

		// write a bunch of logs out to force a flush due to full buffer
		w2 = new LogStreamWriter(LogLevel.WARN, new FileOutputStream(file2), false);
		for (int i = 0; i < 1000; ++i)
			w2.write(logService, LogLevel.ERROR,
				"this is a standard log message with error information");
		buf = readLines(file2);
		assertTrue(buf.length > 0);
		System.out
			.println("LogStreamWriter with no autoflush, buffer full, lines written to disk: "
				+ buf.length);
	}

	/**
	 * Test method for
	 * {@link tuwien.auto.calimero.log.LogStreamWriter#write
	 * (String, LogLevel, String, Throwable)}.
	 * 
	 * @throws IOException
	 */
	public void testWriteLevelStringThrowable() throws IOException
	{
		w.write(logService, LogLevel.INFO, "info msg", new Exception("exception string"));
		final String[] buf = readLines(file);
		assertEquals(1, buf.length);
	}

	/**
	 * Test method for {@link tuwien.auto.calimero.log.LogStreamWriter#close()}.
	 * 
	 * @throws IOException
	 */
	public void testClose() throws IOException
	{
		w.close();
		w.close();
		w.write(logService, LogLevel.FATAL, "error: stream should be closed");
		final String[] buf = readLines(file);
		assertEquals(0, buf.length);
	}

	public void testErrorHandler() throws IOException
	{
		System.out.println("*** error output on next lines is intentional ***");
		try {
			Thread.sleep(100);
		}
		catch (final InterruptedException e) {}
		LogWriter.setErrorHandler(new MyErrorHandler());
		stream.close();
		w.write(logService, LogLevel.FATAL, "error: stream should be closed");
		w.write(logService, LogLevel.FATAL, "error: stream should be closed");
		w.write(logService, LogLevel.FATAL, "error: stream should be closed");
	}

	/**
	 * @throws IOException
	 */
	private String[] readLines(String file) throws IOException
	{
		final BufferedReader r = new BufferedReader(new FileReader(new File(file)));
		String s = null;
		final List v = new Vector();
		try {
			while ((s = r.readLine()) != null)
				v.add(s);
		}
		catch (final IOException e) {
			fail("reading back log file failed");
		}
		r.close();
		return (String[]) v.toArray(new String[v.size()]);
	}

}
