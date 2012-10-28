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

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.hp.hpl.jena.query.QuerySolution;

/**
 *  Subclass of DefaultMutableTreeNode that simply adds a
 *  method for constructing the tree.  
 */
public class RepTreeNode extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 1L;

    
    /**
     *  Constructor.
     *  
     *  @param title  The window title
     *  @param data   A List of QuerySolution objects to be displayed
     */
    public RepTreeNode (String title, List<QuerySolution> data, boolean rawMode) 
    {
        super (title);
        List<List<String>> textData = QueryManager.solutionsToText(data, rawMode);
        for (List<String> solStrs : textData) {
            add (resultStringsToNode (solStrs));
        }
    }
    
    /**
     *  Constructs a DefaultMutableTreeNode representing a QuerySolution.
     *  Doesn't add it to anything.
     */
    private DefaultMutableTreeNode resultStringsToNode (List<String> strs)    {
        DefaultMutableTreeNode val =
                new DefaultMutableTreeNode ("Format", true);
        for (String str : strs) {
            val.add(new DefaultMutableTreeNode(str, true));
        }
        return val;
        
    }
    
}