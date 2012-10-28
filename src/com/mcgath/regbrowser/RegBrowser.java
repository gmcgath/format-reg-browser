/** Format Registry Browser
 * 
 * Copyright (c) 2012, Gary McGath
 * All rights reserved.
 * 
 * The developer of this software may be available for enhancements or
 * related development work. See http://www.garymcgath.com for current status.
 * 
 * Licensed under the BSD license:
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this 
 *  list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice, 
 *  this list of conditions and the following disclaimer in the documentation 
 *  and/or other materials provided with the distribution.
 *  
 *  Neither the name of Gary McGath nor the names of contributors 
 *  may be used to endorse or promote products derived from this software 
 *  without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mcgath.regbrowser;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** The main class for the registry browser application.
 */
public class RegBrowser {

    private static Logger logger;
    
    /** Log level. Set to suit. */
    private final static Level logLevel = Level.WARN;
    
    private static File saveDir;     // remember last save operation location

    /* Preferences */
    private static boolean useDBPedia = true;
    private static boolean usePronom = true;
    private static boolean useP2 = false;    // Preserv2 seems to be non-responsive
    private static boolean useUDFR = true;


    /**
     * Main entry for the application. Uses com.mcgath.regbrowser.config.properties
     * to determine which registries to query. There are no command line arguments.
     */
    public static void main(String[] args) {
        new QueryWindow();
        initLogging ();
        configure ();
    }
    
    /** Configure from config.properties */
    protected static void configure () {
        try {
            ResourceBundle resources = 
                    ResourceBundle.getBundle ("com.mcgath.regbrowser.config");
            try {
                useDBPedia = resourceAllowed (resources, "useDBPedia");
            } catch (Exception e) {}
            try {
                useP2 = resourceAllowed (resources, "useP2");
            } catch (Exception e) {}
            try {
                useUDFR = resourceAllowed (resources, "useUDFR");
            } catch (Exception e) {}
            try {
                usePronom = resourceAllowed (resources, "usePronom");
            } catch (Exception e) {}
            String level = resources.getString ("logLevel");
            if (level != null && level.length() > 0) {
                logger.setLevel (Level.toLevel (level, Level.WARN));
            }
        }
        catch (Exception e) {
            // If no config.proprties, just use defaults
        }

    }

    /** Init logging.  */
    private static void initLogging () {
        logger = Logger.getLogger ("com.mcgath.regbrowser");
        BasicConfigurator.configure();
        logger.setLevel(logLevel);
    }
    
    /** This returns true if the property value starts with "Y" or "y", and false if
     *  it starts with "N" or "n". Anything else throws an exception. */
    private static boolean resourceAllowed (ResourceBundle resources, String key) 
             throws RegBrowserException {
        try {
            String val = resources.getString (key).toLowerCase();
            if (val.startsWith ("n")) {
                return false;
            }
            if (val.startsWith ("y")) {
                return true;
            }
        }
        catch (Exception e) {
        }
        throw new RegBrowserException ("Invalid property");
    }
    
    /** Returns true if the config says to use DBPedia */
    public static boolean getUseDBPedia () {
        return useDBPedia;
    }
    
    public static void setUseDBPedia (boolean b) {
        useDBPedia = b;
    }
    
    /** Returns true if the config says to use Pronom */
    public static boolean getUsePronom () {
        return usePronom;
    }
    
    public static void setUsePronom (boolean b) {
        usePronom = b;
    }

    /** Returns true if the config says to use Preserv2 */
    public static boolean getUseP2 () {
        return useP2;
    }

    public static void setUseP2 (boolean b) {
        useP2 = b;
    }
    
    /** Returns true if the config says to use UDFR */
    public static boolean getUseUDFR () {
        return useUDFR;
    }
    
    public static void setUseUDFR (boolean b) {
        useUDFR = b;
    }
    
    /** Set a directory for saving search results. Has no function as yet. */
    public static void setSaveDirectory(File f) {
        //TODO implement saving results
        saveDir = f;
    }
    
    /** Return a directory for saving search results. Has no function as yet. */
    public static File getSaveDirectory () {
        //TODO implement saving results
        return saveDir;
    }
}
