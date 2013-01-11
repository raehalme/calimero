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

package tuwien.auto.calimero.dptxlator;

import junit.framework.Assert;
import tuwien.auto.calimero.dptxlator.DPT;
import tuwien.auto.calimero.dptxlator.DPTXlator;
import tuwien.auto.calimero.dptxlator.TranslatorTypes;
import tuwien.auto.calimero.exception.KNXException;

/**
 * @author B. Malinowsky
 */
public final class Helper
{
	private Helper()
	{}

	/**
	 * Assert similar for String array.
	 * <p>
	 * 
	 * @param expected expected result
	 * @param actual actual result
	 */
	public static void assertSimilar(String[] expected, String[] actual)
	{
		Assert.assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; i++)
			assertSimilar(expected[i], actual[i]);
	}

	/**
	 * Assert similar for two strings.
	 * <p>
	 * Case insensitive check whether <code>expected</code> is contained in
	 * <code>actual</code>.
	 * 
	 * @param expected expected result
	 * @param actual actual result
	 */
	public static void assertSimilar(String expected, String actual)
	{
		Assert.assertTrue("expected: " + expected + ", actual: " + actual, actual
			.toLowerCase().indexOf(expected.toLowerCase()) > -1);
	}

	/**
	 * Creates DPT translator for given dpts and sets the dpts lower and upper value in
	 * the translator.
	 * <p>
	 * 
	 * @param dpts dpts to check in translator
	 * @param testSimilarity <code>true</code> to check if getValue() of translator
	 *        returns the expected exact value set before
	 */
	public static void checkDPTs(DPT[] dpts, boolean testSimilarity)
	{
		try {
			for (int i = 0; i < dpts.length; i++) {
				final DPTXlator t = TranslatorTypes.createTranslator(0, dpts[i].getID());
				t.setValue(dpts[i].getLowerValue());
				if (testSimilarity)
					assertSimilar(dpts[i].getLowerValue(), t.getValue());
				t.setValue(dpts[i].getUpperValue());
				if (testSimilarity)
					assertSimilar(dpts[i].getUpperValue(), t.getValue());
			}
		}
		catch (final KNXException e) {
			Assert.fail(e.getMessage());
		}
	}
}
