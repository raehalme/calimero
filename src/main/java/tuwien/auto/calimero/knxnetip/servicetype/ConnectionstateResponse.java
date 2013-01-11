/*
    Calimero - A library for KNX network access
    Copyright (C) 2005 Bernhard Erb
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

package tuwien.auto.calimero.knxnetip.servicetype;

import java.io.ByteArrayOutputStream;

import tuwien.auto.calimero.exception.KNXFormatException;
import tuwien.auto.calimero.exception.KNXIllegalArgumentException;

/**
 * Represents a KNXnet/IP connection state response.
 * <p>
 * Connection state responses are sent by a server in reply to a connection state request.
 * <p>
 * Objects of this type are immutable.
 * 
 * @author B. Malinowsky
 * @author Bernhard Erb
 * @see tuwien.auto.calimero.knxnetip.servicetype.ConnectionstateRequest
 */
public class ConnectionstateResponse extends ServiceType
{
	private final short channelid;
	private final short status;

	/**
	 * Creates a connection state response out of a byte array.
	 * <p>
	 * 
	 * @param data byte array containing a connection state response structure
	 * @param offset start offset of response in <code>data</code>
	 * @throws KNXFormatException if no connection state response was found or invalid
	 *         structure
	 */
	public ConnectionstateResponse(byte[] data, int offset) throws KNXFormatException
	{
		super(KNXnetIPHeader.CONNECTIONSTATE_RES);
		if (data.length - offset < 2)
			throw new KNXFormatException("buffer too short for response");
		channelid = (short) (data[offset] & 0xFF);
		status = (short) (data[offset + 1] & 0xFF);
	}

	/**
	 * Creates a new connection state request.
	 * <p>
	 * 
	 * @param channelID communication channel ID passed with the corresponding connection
	 *        state request, 0 &lt;= id &lt;= 255
	 * @param status status of the connection, 0 &lt;= status &lt;= 255
	 */
	public ConnectionstateResponse(short channelID, short status)
	{
		super(KNXnetIPHeader.CONNECTIONSTATE_RES);
		if (channelID < 0 || channelID > 0xFF)
			throw new KNXIllegalArgumentException("channel ID out of range [0..255]");
		if (status < 0 || status > 0xFF)
			throw new KNXIllegalArgumentException("status code out of range [0..255]");
		channelid = channelID;
		this.status = status;
	}

	/**
	 * Returns the communication channel ID used for the response.
	 * <p>
	 * 
	 * @return channel ID as unsigned byte
	 */
	public final short getChannelID()
	{
		return channelid;
	}

	/**
	 * Returns the status of the connection.
	 * <p>
	 * 
	 * @return status code as unsigned byte
	 */
	public final short getStatus()
	{
		return status;
	}

	/**
	 * Returns a textual representation of the status code.
	 * <p>
	 * 
	 * @return short description of status as string
	 */
	public String getStatusString()
	{
		switch (status) {
		case ErrorCodes.NO_ERROR:
			return "the connection state is normal";
		case ErrorCodes.CONNECTION_ID:
			return "server could not find active data connection with specified ID";
		case ErrorCodes.DATA_CONNECTION:
			return "server detected error concerning the data connection";
		case ErrorCodes.KNX_CONNECTION:
			return "server detected error concerning the KNX bus/subsystem connection";
		default:
			return "unknown status";
		}
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.knxnetip.servicetype.ServiceType#getStructLength()
	 */
	short getStructLength()
	{
		return 2;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.knxnetip.servicetype.ServiceType#toByteArray
	 *      (java.io.ByteArrayOutputStream)
	 */
	byte[] toByteArray(ByteArrayOutputStream os)
	{
		os.write(channelid);
		os.write(status);
		return os.toByteArray();
	}
}
