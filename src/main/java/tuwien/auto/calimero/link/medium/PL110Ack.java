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

package tuwien.auto.calimero.link.medium;

import tuwien.auto.calimero.exception.KNXFormatException;

/**
 * Raw acknowledge frame on PL110 communication medium.
 * <p>
 * 
 * @author B. Malinowsky
 */
public class PL110Ack extends RawAckBase
{
	/**
	 * Creates a new PL110 acknowledge frame out of a byte array.
	 * <p>
	 * 
	 * @param data byte array containing the acknowledge frame
	 * @param offset start offset of frame structure in <code>data</code>, offset &gt;=
	 *        0
	 * @throws KNXFormatException if no valid acknowledge frame was found
	 */
	public PL110Ack(byte[] data, int offset) throws KNXFormatException
	{
		final int ctrl = data[offset] & 0xff;
		if (ctrl == ACK)
			ack = ACK;
		else if (ctrl == NAK)
			ack = NAK;
		else if ((ctrl & 0xD3) == 0x90)
			// filter L-Data.req ID
			throw new KNXFormatException("no PL110 ACK frame, L-Data.req control field");
		else
			// everything else is interpreted as NAK
			ack = NAK;
	}
}
