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

package tuwien.auto.calimero.xml.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tuwien.auto.calimero.xml.Attribute;
import tuwien.auto.calimero.xml.Element;

/**
 * Default implementation of XML element.
 * <p>
 * 
 * @author B. Malinowsky
 */
public class DefaultElement implements Element
{
	private final String type;
	private final List attributes;
	private String content;
	private volatile boolean emptyTag;

	/**
	 * Creates a new element <code>name</code>.
	 * <p>
	 * 
	 * @param name name of element, the element's type
	 */
	public DefaultElement(String name)
	{
		type = name;
		attributes = new ArrayList();
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.xml.Element#getName()
	 */
	public final String getName()
	{
		return type;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.xml.Element#addAttribute
	 * (tuwien.auto.calimero.xml.Attribute)
	 */
	public final void addAttribute(Attribute a)
	{
		synchronized (attributes) {
			attributes.add(a);
		}
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.xml.Element#getAttribute(java.lang.String)
	 */
	public final String getAttribute(String name)
	{
		synchronized (attributes) {
			for (final Iterator i = attributes.iterator(); i.hasNext();) {
				final Attribute a = (Attribute) i.next();
				if (a.getName().equals(name))
					return a.getValue();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.xml.Element#hasAttribute(java.lang.String)
	 */
	public boolean hasAttribute(String name)
	{
		return getAttribute(name) != null;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.xml.Element#setCharacterData(java.lang.String)
	 */
	public final void setCharacterData(String data)
	{
		content = data;
		if (content != null)
			emptyTag = false;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.xml.Element#getCharacterData()
	 */
	public final String getCharacterData()
	{
		return content;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.xml.Element#setEmptyElementTag(boolean)
	 */
	public final void setEmptyElementTag(boolean empty)
	{
		emptyTag = empty;
		if (empty)
			content = null;
	}

	/* (non-Javadoc)
	 * @see tuwien.auto.calimero.xml.Element#isEmptyElementTag()
	 */
	public final boolean isEmptyElementTag()
	{
		return emptyTag;
	}
}
