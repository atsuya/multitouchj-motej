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
package edu.csun.ecs.cs.multitouchj.objectobserver.motej.test;

import java.util.ArrayList;
import java.util.List;

import motej.Mote;
import motej.MoteFinder;
import motej.event.IrCameraEvent;
import motej.event.IrCameraListener;
import motej.request.ReportModeRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Atsuya Takagi
 *
 * $Id: MoteJTest.java 65 2009-02-13 01:54:38Z Atsuya Takagi $
 */
public class MoteJTest {
    private static Log log = LogFactory.getLog(MoteJTest.class);
    private static List<Mote> motes = new ArrayList<Mote>();
    
    public static void main(String[] args) throws Exception {
        /*
        try {
            log.info("test");
            
            IrCameraListener listener = new IrCameraListener() {
                public void irImageChanged(IrCameraEvent evt) {
                    System.out.println(evt.getSlot()+": "+evt.getX()+" "+evt.getY()+" "+evt.getSize());
                }
            };

            log.info("looking...");
            Mote mote = MoteFinder.getMoteFinder().findMote();
            mote.setReportMode(ReportModeRequest.DATA_REPORT_0x36);
            log.info("found one");
            mote.rumble(1000);
            mote.addIrCameraListener(listener);
            mote.enableIrCamera();
    
            Thread.sleep(60000);
    
            mote.setReportMode(ReportModeRequest.DATA_REPORT_0x30);
            mote.disableIrCamera();
            mote.disconnect();
        } catch(Exception exception) {
          exception.printStackTrace();  
        }
        */
    }
}
