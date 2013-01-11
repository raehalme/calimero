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

import java.util.HashMap;
import java.util.Map;

import tuwien.auto.calimero.exception.KNXFormatException;
import tuwien.auto.calimero.exception.KNXIllegalArgumentException;
import tuwien.auto.calimero.log.LogLevel;


/**
 * Translator for KNX DPTs with main number 17, type <b>Scene Number</b>.
 * <p>
 * The KNX data type width is 1 byte.<br>
 * The default return value after creation is 1.<br>
 * 
 * @author Thomas.Eichstaedt-Engelen (openHAB.org)
 */
public class DPTXlatorScene extends DPTXlator {

	/**
	 * DPT ID 17.001, Scene Control; values from <b>1</b> to <b>64</b>.
	 * <p>
	 */
	public static final DPT DPT_SCENE_NUMBER = new DPT("17.001", "Scene Number", "1", "64", "");

	private static final Map<String, DPT> types;

	static {
		types = new HashMap<String, DPT>();
		types.put(DPT_SCENE_NUMBER.getID(), DPT_SCENE_NUMBER);
	}

	/**
	 * Creates a translator for the given datapoint type.
	 * <p>
	 * 
	 * @param dpt
	 *            the requested datapoint type
	 * @throws KNXFormatException
	 *             on not supported or not available DPT
	 */
	public DPTXlatorScene(DPT dpt) throws KNXFormatException {
		super(1);
	}

	/**
	 * Creates a translator for the given datapoint type ID.
	 * <p>
	 * 
	 * @param dptID
	 *            available implemented datapoint type ID
	 * @throws KNXFormatException
	 *             on wrong formatted or not expected (available)
	 *             <code>dptID</code>
	 */
	public DPTXlatorScene(String dptID) throws KNXFormatException {
		super(1);
		setSubType(dptID);
	}

	/**
	 * Sets a new subtype to use for translating items.
	 * <p>
	 * The translator is reset into default state, all currently contained items
	 * are removed (default value is set).
	 * 
	 * @param dptID
	 *            new subtype ID to set
	 * @throws KNXFormatException
	 *             on wrong formatted or not expected (available)
	 *             <code>dptID</code>
	 */
	private void setSubType(String dptID) throws KNXFormatException {
		setTypeID(types, dptID);
		data = new short[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tuwien.auto.calimero.dptxlator.DPTXlator#getValue()
	 */
	public String getValue() {
		return makeString(0);
	}

	/**
	 * Sets one new translation item from a scaled unsigned value, replacing any
	 * old items.
	 * <p>
	 * The scale of the input value is according to the current DPT.
	 * 
	 * @param scaled
	 *            scaled unsigned value, the dimension is determined by the set
	 *            DPT, 0 &lt;= scaled value &lt;= defined maximum of DPT
	 * @throws KNXFormatException
	 *             on wrong scaled value, if input doesn't conform to subtype
	 *             dimension
	 * @see #getType()
	 */
	public final void setValue(int scaled) throws KNXFormatException {
		data = new short[] { toDPT(scaled) };
	}
	
	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.dptxlator.DPTXlator#getSubTypes()
	 */
	@SuppressWarnings("rawtypes")
	public final Map getSubTypes() {
		return types;
	}	
	
	private short fromDPT(short data) {
		return (short) (data + 1);
	}

	protected void toDPT(String value, short[] dst, int index) throws KNXFormatException {
		try {
			dst[index] = toDPT(Short.decode(value).shortValue());
		} catch (final NumberFormatException e) {
			throw logThrow(LogLevel.WARN, "wrong value format " + value, null, value);
		}
	}

	private short toDPT(int value) throws KNXFormatException {
		int adjustedValue = value -1;
		if (adjustedValue < 1 || adjustedValue > 64) {
			throw new KNXFormatException("new value is out of range (1-64)");
		}
		return (short) adjustedValue;
	}

	private String makeString(int index) {
		return Short.toString(fromDPT(data[index]));
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.dptxlator.DPTXlator#getAllValues()
	 */
	public String[] getAllValues() {
		final String[] s = new String[data.length];
		for (int i = 0; i < data.length; ++i)
			s[i] = makeString(i);
		return s;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.dptxlator.DPTXlator#setData(byte[], int)
	 */
	public void setData(byte[] data, int offset) {
		if (offset < 0 || offset > data.length)
			throw new KNXIllegalArgumentException("illegal offset " + offset);
		final int size = data.length - offset;
		if (size == 0)
			throw new KNXIllegalArgumentException("data length " + size
					+ " < KNX data type width " + Math.max(1, getTypeSize()));
		this.data = new short[size];
		for (int i = 0; i < size; ++i)
			this.data[i] = ubyte(data[offset + i]);
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.dptxlator.DPTXlator#getData(byte[], int)
	 */
	public byte[] getData(byte[] dst, int offset) {
		final int end = Math.min(data.length, dst.length - offset);
		for (int i = 0; i < end; ++i)
			dst[offset + i] = (byte) data[i];
		return dst;
	}
	
}
