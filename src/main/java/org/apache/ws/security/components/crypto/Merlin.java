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

package org.apache.ws.security.components.crypto;

import java.io.IOException;
import java.util.Properties;

/**
 * JDK1.4 based implementation of Crypto (uses keystore). <p/>
 * 
 * @author Davanum Srinivas (dims@yahoo.com).
 */
public class Merlin extends AbstractCrypto {

    /**
     * Constructor. <p/>
     * 
     * @param properties
     * @throws CredentialException
     * @throws IOException
     */
    public Merlin(Properties properties) throws CredentialException, IOException {
        super(properties);
    }

    public Merlin(Properties properties, ClassLoader loader)
        throws CredentialException, IOException {
        super(properties, loader);
    }

}