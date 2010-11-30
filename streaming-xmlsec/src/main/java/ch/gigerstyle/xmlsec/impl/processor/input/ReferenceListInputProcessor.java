package ch.gigerstyle.xmlsec.impl.processor.input;

import ch.gigerstyle.xmlsec.ext.*;
import org.w3._2001._04.xmlenc_.ReferenceList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * User: giger
 * Date: May 13, 2010
 * Time: 5:55:30 PM
 * Copyright 2010 Marc Giger gigerstyle@gmx.ch
 * <p/>
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2, or (at your option) any
 * later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
public class ReferenceListInputProcessor extends AbstractInputProcessor {

    private ReferenceList currentReferenceList;

    public ReferenceListInputProcessor(SecurityProperties securityProperties, StartElement startElement) {
        super(securityProperties);
        currentReferenceList = new ReferenceList(startElement);
    }

    @Override
    public XMLEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        XMLEvent xmlEvent = inputProcessorChain.processHeaderEvent();

        boolean isFinishedcurrentReferenceList = false;

        if (currentReferenceList != null) {
            try {
                isFinishedcurrentReferenceList = currentReferenceList.parseXMLEvent(xmlEvent);
                if (isFinishedcurrentReferenceList) {
                    currentReferenceList.validate();
                }
            } catch (ParseException e) {
                throw new XMLSecurityException(e);
            }
        }

        if (currentReferenceList != null && isFinishedcurrentReferenceList) {
            try {
                inputProcessorChain.addProcessor(new DecryptInputProcessor(currentReferenceList, getSecurityProperties()));
            } finally {
                inputProcessorChain.removeProcessor(this);
                currentReferenceList = null;
                isFinishedcurrentReferenceList = false;
            }
        }
        return xmlEvent;
    }

    @Override
    public XMLEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        //this method should not be called (processor will be removed after processing header
        return null;
    }
}