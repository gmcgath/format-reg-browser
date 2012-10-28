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

//import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;

/** Class to construct a query from the fields of a QueryWindow.
 *  It's a superclass for query builders for specific repositories.
 */
public abstract class QueryBuilder {

    static protected final String NL = System.getProperty("line.separator") ; 
    
    /** Values returned by checkFields */
    public enum QueryCheckCode {
        OK,
        NO_DATA,
        BAD_LIMIT,
        BAD_CHARS
    }
    
    /** Characters that would have to be treated specially in a regexp */
    private char[] BAD_CHARS = ".&[]?*{}|\\".toCharArray();
    
    /** The QueryWindow to which this is attached. */
    private QueryWindow theQueryWindow;
    
    /** The base URI of the SPARQL endpoint. */
    protected String baseURI;
    
    /** The name field from the query form */
    protected String name;
    
    /** The extension field from the query form */
    protected String extension;
    
    /** The MIME type field from the query form */
    protected String mimeType;
    
    /** The creator field from the query form */
    protected String creator;
    
    /** The maximum number of results requested. */
    protected int limit;
    
    /** Constructor, invoked by a QueryWindow */
    public QueryBuilder(QueryWindow win) {
        theQueryWindow = win;
        name = "";
        extension = "";
        mimeType = "";
        creator = "";
        limit = 20;     // default
    }
    
    /** Pull the fields out of the query window. */
    public void extractFields () {
        name = theQueryWindow.getName().trim();
        extension = theQueryWindow.getExtension().trim();
        mimeType = theQueryWindow.getMimeType().trim();
        creator = theQueryWindow.getCreator().trim();
        try {
            String limitTxt = theQueryWindow.getLimit().trim();
            if (limitTxt.length() > 0) {
                limit = Integer.parseInt (limitTxt);
            }
            // If there's nothing in the limit field it just stays at the default
        }
        catch (Exception e) {
            // parsing error, most likely
            limit = -1;
        }
    }
    
    /** Check if the fields are suitable for a query. There needs
     *  to be at least one non-blank one, and there can't be
     *  characters that would mess up a regular expression. Returns
     *  a QueryCheckCode. 
     */
    public QueryCheckCode checkFields () {
        if (name.isEmpty() &&
            extension.isEmpty() &&
            mimeType.isEmpty() &&
            creator.isEmpty()) {
            return QueryCheckCode.NO_DATA;
        }
        if (limit <= 0) {
            return QueryCheckCode.BAD_LIMIT;
        }
        if (hasForbiddenChars(name) ||
            hasForbiddenChars(extension) ||
            hasForbiddenChars(mimeType) ||
            hasForbiddenChars(creator)) {
            return QueryCheckCode.BAD_CHARS;
        }
        return QueryCheckCode.OK;
    }
    
    /** This function is only for setting up tests. */
    public void setFields(String ext, String mimeType, int limit) {
        this.extension = ext;
        this.mimeType = mimeType;
        this.limit = limit;
    }
    
    /** Build and issue the query, returning ... something.
     *  Building queries using the algebraic classses is supposedly easier ...
     *  or would be, if they were better documented.
     */
    public List<QuerySolution> doQuery() throws RegBrowserException {
        List<QuerySolution> results;
        
        /** Now dispatch the query */
        String queryString = buildQueryString ();
        QueryManager qm = new QueryManager (baseURI);
        results = qm.doQuery(queryString);
        return results;
    }
    
    /** Build the query string for the particular service. */
    protected abstract String buildQueryString ();
    
    /** Check if a string has characters that will mess up a regexp. 
     *  could quote them, but for now ban them. */
    private boolean hasForbiddenChars (String s) {
        for (char ch : BAD_CHARS) {
            if (s.indexOf(ch) >= 0) {
                return true;
            }
        }
        return false;
    }
}
