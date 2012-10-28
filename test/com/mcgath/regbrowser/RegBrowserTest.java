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

import static org.junit.Assert.*;

import org.junit.Test;

public class RegBrowserTest {


    @Test
    public void testConfig () {
        RegBrowser.configure ();
        
        boolean useDBPedia = RegBrowser.getUseDBPedia();
        boolean useP2 = RegBrowser.getUseP2();
        boolean useUDFR = RegBrowser.getUseUDFR();

        // Change them and see if calling configure again set them back.
        RegBrowser.setUseDBPedia(!useDBPedia);
        RegBrowser.setUseP2(!useP2);
        RegBrowser.setUseUDFR(!useUDFR);
        
        // Sanity check on setter
        assertFalse (useUDFR == RegBrowser.getUseUDFR());
        
        RegBrowser.configure ();
        /* These results assume that the config file agrees with the defaults.
         * If not, the test will fail. Of course, if configure does nothing at
         * all, the test will succeed.
         */ 
        assertTrue (RegBrowser.getUseDBPedia() == useDBPedia);
        assertTrue (RegBrowser.getUseP2() == useP2);
        assertTrue (RegBrowser.getUseUDFR() == useUDFR);
    }
}
