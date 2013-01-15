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
 * Translator for KNX DPTs with main number 12, type <b>4 byte unsigned value</b>.
 * <p>
 * The KNX data type width is 4 bytes.<br>
 * The default return value after creation is 0.
 * <p>
 * In value methods expecting string items, the item might be formatted using decimal,
 * hexadecimal, and octal numbers, distinguished by using these prefixes:
 * <dd>no prefix for decimal numeral
 * <dd><code>0x</code>, <code>0X</code> or <code>#</code> for hexadecimal
 * <dd><code>0</code> for octal numeral
 * 
 * @author Victor Belov (openhab.org)
 */
public class DPTXlator4ByteFloat extends DPTXlator
{
    /**
     * DPT ID 14.056, Power; values from <b>0</b> to <b>4294967295</b> power
     * in Watts.
     * <p>
     */
    public static final DPT DPT_VALUE_4_POWER =
            new DPT("14.056", "Power", "0", "4294967295", "W");

    /**
     * DPT ID 14.019, Electric Current; values from <b>0</b> to <b>4294967295</b> current
     * in Ampers.
     * <p>
     */
    public static final DPT DPT_VALUE_4_ELECTRIC_CURRENT =
            new DPT("14.019", "Electric Current", "0", "4294967295", "A");

    /**
     * DPT ID 14.027, Electric Potential; values from <b>0</b> to <b>4294967295</b> potential
     * in Volts.
     * <p>
     */
    public static final DPT DPT_VALUE_4_ELECTRIC_POTENTIAL =
            new DPT("14.027", "Electric Potential", "0", "4294967295", "V");

    /**
     * DPT ID 14.033, Frequency; values from <b>0</b> to <b>4294967295</b> frequency
     * in Hz.
     * <p>
     */
    public static final DPT DPT_VALUE_4_FREQUENCY =
            new DPT("14.033", "Frequency", "0", "4294967295", "Hz");


	private static final Map types;

	static {
		types = new HashMap();
		types.put(DPT_VALUE_4_POWER.getID(), DPT_VALUE_4_POWER);
		types.put(DPT_VALUE_4_ELECTRIC_CURRENT.getID(), DPT_VALUE_4_ELECTRIC_CURRENT);
		types.put(DPT_VALUE_4_ELECTRIC_POTENTIAL.getID(), DPT_VALUE_4_ELECTRIC_POTENTIAL);
		types.put(DPT_VALUE_4_FREQUENCY.getID(), DPT_VALUE_4_FREQUENCY);
	}

	/**
	 * Creates a translator for the given datapoint type.
	 * <p>
	 * 
	 * @param dpt the requested datapoint type
	 * @throws KNXFormatException on not supported or not available DPT
	 */
	public DPTXlator4ByteFloat(DPT dpt) throws KNXFormatException
	{
		this(dpt.getID());
	}

	/**
	 * Creates a translator for the given datapoint type ID.
	 * <p>
	 * 
	 * @param dptID available implemented datapoint type ID
	 * @throws KNXFormatException on wrong formatted or not expected (available)
	 *         <code>dptID</code>
	 */
	public DPTXlator4ByteFloat(String dptID) throws KNXFormatException
	{
		super(4);
		setTypeID(types, dptID);
		data = new short[4];
	}

	/**
	 * Sets the value of the first translation item.
	 * <p>
	 * 
	 * @param value unsigned value, 0 &lt;= value &lt;= 0xFFFFFFFF
	 * @throws KNXFormatException on input value out of range for DPT
	 * @see #getType()
	 */
	public final void setValue(long value) throws KNXFormatException
	{
		data = toDPT(value, new short[4], 0);
	}

	/**
	 * Returns the first translation item as unsigned 32 Bit value.
	 * <p>
	 * 
	 * @return unsigned 32 Bit value using type long
	 * @see #getType()
	 */
/*	public final long getValueUnsigned()
	{
		return fromDPT(0);
	}*/
	
	public final Float getValueFloat()
	{
		return fromDPT(0);
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.dptxlator.DPTXlator#getValue()
	 */
	public String getValue()
	{
		return makeString(0);
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.dptxlator.DPTXlator#getAllValues()
	 */
	public String[] getAllValues()
	{
		final String[] s = new String[data.length / 4];
		for (int i = 0; i < s.length; ++i)
			s[i] = makeString(i);
		return s;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.dptxlator.DPTXlator#setData(byte[], int)
	 */
	public void setData(byte[] data, int offset)
	{
		if (offset < 0 || offset > data.length)
			throw new KNXIllegalArgumentException("illegal offset " + offset);
		final int size = (data.length - offset) & ~0x03;
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
	public byte[] getData(byte[] dst, int offset)
	{
		final int end = Math.min(data.length, dst.length - offset) & ~0x03;
		for (int i = 0; i < end; ++i)
			dst[offset + i] = (byte) data[i];
		return dst;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.dptxlator.DPTXlator#getSubTypes()
	 */
	public final Map getSubTypes()
	{
		return types;
	}

	/**
	 * @return the subtypes of the 4-byte unsigned translator type
	 * @see DPTXlator#getSubTypesStatic()
	 */
	protected static Map getSubTypesStatic()
	{
		return types;
	}

	private Float fromDPT(int index)
	{
		// This is quite easy
		final int i = 4 * index;
		// Take 4 bytes as a Long
		Long interim = (long) data[i] << 24 | data[i + 1] << 16 | data[i + 2] << 8 | data[i + 3];
		// Then use Java constructor to get Float from bits
		return Float.intBitsToFloat(interim.intValue());
	}

	private String makeString(int index)
	{
		return appendUnit(Float.toString(fromDPT(index)));
	}

	protected void toDPT(String value, short[] dst, int index) throws KNXFormatException
	{
		try {
			// Doing back trick here. Make a 4 octet Long from bits of Float.
			Long result = Long.valueOf(Float.floatToIntBits(Float.parseFloat(removeUnit(value))));
			toDPT(Long.decode(removeUnit(value)).longValue(), dst, index);
		}
		catch (final NumberFormatException e) {
			throw logThrow(LogLevel.WARN, "wrong value format " + value, null, value);
		}
	}

	private short[] toDPT(long value, short[] dst, int index) throws KNXFormatException
	{
		if (value < 0 || value > 0xFFFFFFFFL)
			throw logThrow(LogLevel.WARN, "translation error for " + value,
				"input value out of range", Long.toString(value));
		final int i = 4 * index;
		dst[i] = (short) ((value >> 24) & 0xFF);
		dst[i + 1] = (short) ((value >> 16) & 0xFF);
		dst[i + 2] = (short) ((value >> 8) & 0xFF);
		dst[i + 3] = (short) (value & 0xFF);
		return dst;
	}
}
