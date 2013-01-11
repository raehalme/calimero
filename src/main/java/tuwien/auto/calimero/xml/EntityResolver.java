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

package tuwien.auto.calimero.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

/**
 * Used to resolve entities to be read with a {@link XMLReader} or written with a
 * {@link XMLWriter}.
 * <p>
 * An EntityResolver is used by the {@link XMLFactory} to resolve the specified
 * identifiers in the creator methods of the factory.
 * 
 * @author B. Malinowsky
 */
public interface EntityResolver
{
	/**
	 * Resolves the system identifier for a XML resource and wraps it into an input
	 * stream.
	 * <p>
	 * 
	 * @param systemID location identifier of the XML resource
	 * @return the input stream for the resource
	 * @throws KNXMLException if resolving failed
	 */
	InputStream resolveInput(String systemID) throws KNXMLException;

	/**
	 * Resolves the system identifier for a XML resource and wraps it into an output
	 * stream.
	 * <p>
	 * 
	 * @param systemID location identifier of the XML resource
	 * @return the output stream for the resource
	 * @throws KNXMLException if resolving failed
	 */
	OutputStream resolveOutput(String systemID) throws KNXMLException;

	/**
	 * Creates an Reader using the supplied input stream.
	 * <p>
	 * This method creates an appropriate reader based on the character encoding of the
	 * resource specified by the input stream and the XML declaration pseudo attribute, if
	 * available.<br>
	 * It should provide a default reader if a selection of the source encoding can't be
	 * done reliably.
	 * 
	 * @param is input stream to use as input for the reader to create
	 * @return the Reader for the input stream
	 * @throws KNXMLException on I/O error reading the input stream
	 */
	Reader getInputReader(InputStream is) throws KNXMLException;
}
