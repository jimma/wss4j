/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.wss4j.dom.message;

import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.derivedKey.ConversationConstants;
import org.apache.wss4j.dom.WSSConfig;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Document;

/**
 * Builder class to add a <code>wsc:SecurityContextToken</code> into the
 * <code>wsse:Security</code>
 */
public class WSSecSecurityContextToken {

    /**
     * The <code>wsc:SecurityContextToken</code> to be added to the
     * <code>wsse:SecurityHeader</code>
     */
    private SecurityContextToken sct;

    /**
     * The <code>wsu:Id</code> of the <code>wsc:SecurityContextToken</code> 
     */
    private String sctId;

    /**
     * The <code>wsc:Identifier</code> of the
     * <code>wsc:SecurityContextToken</code>
     */
    private String identifier;

    /**
     * The symmetric secret associated with the SecurityContextToken
     */
    private byte[] secret;
    
    private int wscVersion = ConversationConstants.DEFAULT_VERSION;
    private WSSConfig wssConfig;
    
    public WSSecSecurityContextToken() {
    }
    
    public WSSecSecurityContextToken(WSSConfig config) {
        wssConfig = config;
    }

    public void prepare(Document doc, Crypto crypto) throws WSSecurityException {

        if (sct == null) {
            if (identifier != null) {
                sct = new SecurityContextToken(wscVersion, doc, identifier);
            } else {
                sct = new SecurityContextToken(wscVersion, doc);
                identifier = sct.getIdentifier();
            }
        }

        // The wsu:Id of the wsc:SecurityContextToken
        if (sctId == null) {
            sctId = getWsConfig().getIdAllocator().createId("sctId-", sct);
        }
        sct.setID(sctId);
    }

    public void prependSCTElementToHeader(Document doc, WSSecHeader secHeader)
        throws WSSecurityException {
        WSSecurityUtil.prependChildElement(secHeader.getSecurityHeader(), sct.getElement());
    }

    /**
     * @return Returns the sct.
     */
    public SecurityContextToken getSct() {
        return sct;
    }

    /**
     * @param sct The sct to set.
     */
    public void setSct(SecurityContextToken sct) {
        this.sct = sct;
    }

    /**
     * @return Returns the ephemeralKey.
     */
    public byte[] getSecret() {
        return secret;
    }

    /**
     * @param ephemeralKey The ephemeralKey to set.
     */
    protected void setSecret(byte[] ephemeralKey) {
        secret = ephemeralKey;
    }

    /**
     * @return Returns the identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier The identifier to set.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return Returns the sctId.
     */
    public String getSctId() {
        if (sct != null) {
            return sct.getID();
        }
        return sctId;
    }

    /**
     * @param sctId The sctId to set.
     */
    public void setSctId(String sctId) {
        this.sctId = sctId;
    }

    /**
     * @param wscVersion The wscVersion to set.
     */
    public void setWscVersion(int wscVersion) {
        this.wscVersion = wscVersion;
    }
    
    private WSSConfig getWsConfig() {
        if (wssConfig == null) {
            wssConfig = WSSConfig.getNewInstance();
        }
        return wssConfig;
    }
}
