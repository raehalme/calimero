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

package tuwien.auto.calimero;

import java.util.EventObject;

import tuwien.auto.calimero.link.event.LinkListener;

/**
 * Informs about the closing of a previously established communication with the KNX
 * network.
 * <p>
 * In general, the source of the event is the connection object or network link to be
 * closed.
 * 
 * @author B. Malinowsky
 * @see LinkListener
 * @see KNXListener
 */
public class CloseEvent extends EventObject
{
	private static final long serialVersionUID = 1L;

	private final boolean user;
	private final String msg;

	/**
	 * Creates a new close event object.
	 * <p>
	 * 
	 * @param source the communication object to be closed
	 * @param userRequest <code>true</code> if the closing was requested by the user of
	 *        the object, <code>false</code> otherwise (for example, a close initiated by
	 *        a remote server)
	 * @param reason brief description of the reason leading to the close event
	 */
	public CloseEvent(Object source, boolean userRequest, String reason)
	{
		super(source);
		user = userRequest;
		msg = reason;
	}

	/**
	 * Returns whether the close event was initiated by the user of the communication
	 * object.
	 * <p>
	 * 
	 * @return <code>true</code> if close is user requested, <code>false</code>
	 *         otherwise
	 */
	public final boolean isUserRequest()
	{
		return user;
	}

	/**
	 * Returns a brief textual description of the closing reason.
	 * <p>
	 * 
	 * @return reason as string
	 */
	public final String getReason()
	{
		return msg;
	}
}
