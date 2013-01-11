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

import java.io.Writer;
import java.util.List;

/**
 * <p>
 * XML writer implementations don't need to be thread safe.
 * 
 * @author B. Malinowsky
 */
public interface XMLWriter
{
	/**
	 * Sets the output destination for this XML writer.
	 * <p>
	 * If this XML writer was already closed, setting a new output has no effect.
	 * 
	 * @param output a writer for output
	 * @param close <code>true</code> if the specified writer should be closed on
	 *        {@link #close()}, <code>false</code> to leave it open
	 */
	void setOutput(Writer output, boolean close);

	/**
	 * Writes the XML declaration, specifying standalone document declaration and encoding
	 * declaration.
	 * <p>
	 * The version information is always <code>1.0</code>.<br>
	 * Note that an arbitrary encoding might be specified in the declaration. It is not
	 * necessarily checked by the XML writer whether the actual output meets that
	 * encoding. The user is responsible to specify the encoding matching the output
	 * writer.
	 * 
	 * @param standalone <code>true</code> if there are no external markup declarations,
	 *        <code>false</code> to indicates that there are or may be such external
	 *        markup declarations
	 * @param encoding character encoding (for example "UTF-8"), case-insensitive, (IANA
	 *        registered name)
	 * @throws KNXMLException on output error
	 */
	void writeDeclaration(boolean standalone, String encoding) throws KNXMLException;

	/**
	 * Writes a new element to the current position in a document.
	 * <p>
	 * Predefined entities in <code>text</code> are replaced with references before
	 * write.
	 * 
	 * @param name element name, the element's type
	 * @param att attribute specifications for this element, empty list or
	 *        <code>null</code> for no attributes
	 * @param text text to write, the character data, <code>null</code> for no data
	 * @throws KNXMLException on output error
	 */
	void writeElement(String name, List att, String text) throws KNXMLException;

	/**
	 * Writes an empty element tag to the current position in a document.
	 * <p>
	 * Note that an empty element tag has no content and {@link #endElement()} does not
	 * recognize such elements.
	 * 
	 * @param name element name, the element's type
	 * @param att attribute specifications for this element, empty list or
	 *        <code>null</code> for no attributes
	 * @throws KNXMLException on output error
	 */
	void writeEmptyElement(String name, List att) throws KNXMLException;

	/**
	 * Writes a comment to the current position in a document.
	 * <p>
	 * The text of the comment is wrapped with "&lt;!--" and "--&gt;" by this method. The
	 * string "--" (double-hyphen) must not occur within comments.
	 * 
	 * @param comment text of the comment
	 * @throws KNXMLException on output error
	 */
	void writeComment(String comment) throws KNXMLException;

	/**
	 * Writes character data to the current position in a document.
	 * <p>
	 * Character data can be put into CDATA sections.<br>
	 * CDATA sections begin with the string "&lt;![CDATA[" and end with the string
	 * "]]&gt;". A CDATA section is used to escape text with characters which would be
	 * recognized as markup otherwise.<br>
	 * Predefined entities in <code>text</code> are replaced before write, iff the
	 * character data to write is no CDATA section. Data in CDATA sections is not
	 * modified.
	 * 
	 * @param text text to write, the character data
	 * @param isCDATASection <code>true</code> to write data into CDATA section,
	 *        <code>false</code> to write default character data
	 * @throws KNXMLException on output error
	 */
	void writeCharData(String text, boolean isCDATASection) throws KNXMLException;

	/**
	 * Closes the current element.
	 * <p>
	 * 
	 * @throws KNXMLException on output error
	 */
	void endElement() throws KNXMLException;

	/**
	 * Closes all open elements and flushes buffered data to output.
	 * <p>
	 * 
	 * @throws KNXMLException on output error
	 */
	void endAllElements() throws KNXMLException;

	/**
	 * Closes this XML writer.
	 * <p>
	 * If this XML writer is already closed, no action is performed. All open elements are
	 * closed. If for an output source was specified to get closed on invocation of this
	 * method, it is closed (calling {@link Writer#close()} before return.
	 * 
	 * @throws KNXMLException
	 * @see XMLWriter#endAllElements()
	 */
	void close() throws KNXMLException;
}
