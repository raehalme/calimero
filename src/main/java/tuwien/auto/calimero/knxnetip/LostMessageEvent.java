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

package tuwien.auto.calimero.knxnetip;

import java.util.EventObject;

/**
 * Event informing about an overflow with loss of messages in KNXnet/IP routing mode.
 * <p>
 * It contains lost message and device state information on overflow of the LAN-to-KNX
 * queue in the KNXnet/IP router, which leads to loss of received KNXnet/IP messages. This
 * event is multicasted by the KNXnet/IP router every time an increment in lost messages
 * occurs. In the router, the number of lost messages is maintained in the
 * PID_QUEUE_OVERFLOW_TO_KNX property value.<br>
 * The lost message value states the total of lost messages in the router.
 * 
 * @author B. Malinowsky
 * @see KNXnetIPRouter
 */
public class LostMessageEvent extends EventObject
{
	private static final long serialVersionUID = 1L;

	private final short state;
	private final int lost;

	/**
	 * Creates a new lost message event.
	 * <p>
	 * 
	 * @param source the {@link KNXnetIPRouter} on which the event occurred
	 * @param deviceState KNXnet/IP router device state, 0 &lt;= state &lt;= 255
	 * @param lostMessages number of lost messages, 0 &lt;= number &lt;= 0xFFFF
	 */
	public LostMessageEvent(Object source, short deviceState, int lostMessages)
	{
		super(source);
		state = deviceState;
		lost = lostMessages;
	}

	/**
	 * Returns the total of lost messages maintained by the KNXnet/IP router.
	 * <p>
	 * 
	 * @return number of lost messages as unsigned short
	 */
	public final int getLostMessages()
	{
		return lost;
	}

	/**
	 * Returns the router device state.
	 * <p>
	 * The device state is a bit field with 8 Bits and conforms to the
	 * PID_KNXNETIP_DEVICE_STATE property value.
	 * 
	 * @return device state as unsigned byte
	 */
	public final short getDeviceState()
	{
		return state;
	}

	/**
	 * Returns whether the KNX network cannot be accessed, causing the message loss.
	 * <p>
	 * The KNX fault mode is part of the device state.
	 * 
	 * @return <code>true</code> on KNX access fault, <code>false</code> otherwise
	 * @see #getDeviceState()
	 */
	public final boolean isKNXFault()
	{
		return (state & 0x01) != 0;
	}
}
