/**
 * Copyright 2009 Atsuya Takagi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package edu.csun.ecs.cs.multitouchj.objectobserver.motej;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import motej.Mote;
import motej.MoteFinder;
import motej.MoteFinderListener;

/**
 * @author Atsuya Takagi
 *
 * $Id: MoteFinderHandler.java 78 2009-03-01 23:35:48Z Atsuya Takagi $
 */
public class MoteFinderHandler implements MoteFinderListener {
    private static Log log = LogFactory.getLog(MoteFinderHandler.class);
    private static final long MESSAGE_DELAY = 10000;
    private Mote mote;
    
    
    public MoteFinderHandler() {
        mote = null;
    }
    
    /* (non-Javadoc)
     * @see motej.MoteFinderListener#moteFound(motej.Mote)
     */
    public void moteFound(Mote mote) {
        this.mote = mote;
    }
    
    public Mote findMote() {
        MoteFinder moteFinder = MoteFinder.getMoteFinder();
        moteFinder.addMoteFinderListener(this);
        moteFinder.startDiscovery();
        
        long lastTimeMessaged = 0;
        while(mote == null) {
            long currentTime = new Date().getTime();
            if((currentTime - lastTimeMessaged) >= MESSAGE_DELAY) {
                log.info("Waiting to detect Wiimote...");
                lastTimeMessaged = currentTime;
            }
            
            try {
                Thread.sleep(100);
            } catch(Exception exception) {}
        }
        moteFinder.stopDiscovery();
        
        return mote;
    }
}
