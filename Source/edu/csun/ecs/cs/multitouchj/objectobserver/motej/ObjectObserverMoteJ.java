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

import java.util.Map;

import motej.Mote;
import motej.MoteFinder;
import motej.event.IrCameraEvent;
import motej.event.IrCameraListener;
import motej.request.ReportModeRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.csun.ecs.cs.multitouchj.objectobserver.ObjectObserver;

/**
 * @author Atsuya Takagi
 *
 * $Id: ObjectObserverMoteJ.java 69 2009-02-13 10:35:01Z Atsuya Takagi $
 */
public class ObjectObserverMoteJ extends ObjectObserver implements IrCameraListener {
    public enum Parameter {
        InverseX,
        InverseY
    }
    private static final int IR_CAMERA_MAXIMUM_SLOTS = 4;
    private static final int IR_CAMERA_WIDTH = 1024;
    private static final int IR_CAMERA_HEIGHT = 768;
    private static Log log = LogFactory.getLog(ObjectObserverMoteJ.class);
    private Mote mote;
    private MoteFinderHandler moteFinderHandler;
    private boolean inverseX;
    private boolean inverseY;
    
    
    public ObjectObserverMoteJ() {
        super();
        
        moteFinderHandler = new MoteFinderHandler();
    }
    
    /* (non-Javadoc)
     * @see motej.event.IrCameraListener#irImageChanged(motej.event.IrCameraEvent)
     */
    public void irImageChanged(IrCameraEvent event) {
        /*
        for(int i = 0; i < IR_CAMERA_MAXIMUM_SLOTS; i++) {
            IrPoint irPoint = event.getIrPoint(i);
            if(irPoint != null) {
                objectTouched(
                    i,
                    (float)irPoint.getX(),
                    (float)(IR_CAMERA_HEIGHT - irPoint.getY()),
                    irPoint.getSize()
                );
            }
        }
        */
         // bottom left is (0, 0), so convert it to make (0, 0) to be top left.
        objectTouched(
            event.getSlot(),
            (inverseX) ? (IR_CAMERA_WIDTH - event.getX()) : event.getX(),
            (inverseY) ? (IR_CAMERA_HEIGHT - event.getY()) : event.getY(),
            event.getSize()
        );
    }

    /* (non-Javadoc)
     * @see edu.csun.ecs.cs.multitouchj.ObjectObserver#initialize(java.util.Map)
     */
    @Override
    public void initialize(Map<String, String> parameters) throws Exception {
        inverseX = parameters.containsKey(Parameter.InverseX.toString());
        log.info("InverseX: "+inverseX);
        
        inverseY = parameters.containsKey(Parameter.InverseY.toString());
        log.info("InverseY: "+inverseY);
    }

    /* (non-Javadoc)
     * @see edu.csun.ecs.cs.multitouchj.ObjectObserver#start()
     */
    @Override
    public void start() throws Exception {
        if(!isStarted()) {
            log.info("Turn on Wiimote now.");
            
            try {
                mote = moteFinderHandler.findMote();
                mote.addIrCameraListener(this);
                mote.enableIrCamera();
                mote.setReportMode(ReportModeRequest.DATA_REPORT_0x36);
            } catch(Exception exception) {
                log.error("Failed to detect Wiimote.", exception);
                throw new Exception("Failed to detect Wiimote.");
            }
            
            setStarted(true);
        }
    }

    /* (non-Javadoc)
     * @see edu.csun.ecs.cs.multitouchj.ObjectObserver#stop()
     */
    @Override
    public void stop() throws Exception {
        if(isStarted()) {
            mote.setReportMode(ReportModeRequest.DATA_REPORT_0x30);
            mote.disableIrCamera();
            mote.removeIrCameraListener(this);
            mote.disconnect();
            
            setStarted(false);
        }
    }
}
