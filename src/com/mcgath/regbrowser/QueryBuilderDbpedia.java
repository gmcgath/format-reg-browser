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

import org.apache.log4j.Logger;

/** QueryBuilder subclass for DBPedia. */
public class QueryBuilderDbpedia extends QueryBuilder {

    private Logger logger;

    public QueryBuilderDbpedia(FieldSource fs) {
        super(fs);
        baseURI = Services.DBPEDIA_BASE_URI;
        logger = Logger.getLogger ("com.mcgath.regbrowser");
    }

    /** Constructs the SPARQL query for DBPedia. */
    @Override
    protected String buildQueryString () {
        String prolog = Services.DBPEDIA_PREFIX_DBPEDIA2 + NL +
                "SELECT ?Format ?Name ?MimeType ?Extension ?Typecode " +
                "  ?Owner ?ExtendedFrom ?ExtendedTo " +
                "WHERE { " + NL;
        String postlog = "} LIMIT " + Integer.toString(limit);
        StringBuffer triples = new StringBuffer ();

        // Triples for returning variables
        triples.append("?Format dbpedia2:name ?Name ." + NL);
        triples.append("?Format dbpedia2:mime ?MimeType ." + NL);
        triples.append("?Format dbpedia2:extension ?Extension ." + NL);
        triples.append("OPTIONAL {?Format dbpedia2:typecode ?Typecode } ." + NL);
        triples.append("OPTIONAL {?Format dbpedia2:owner ?Owner } ." + NL);
        triples.append("OPTIONAL {?Format dbpedia2:extended_from ?ExtendedFrom } ." + NL);
        triples.append("OPTIONAL {?Format dbpedia2:extended_to ?ExtendedTo } ." + NL);
        
        // Filters
        StringBuffer filters = new StringBuffer ();
        if (name.length() > 0) {
            filters.append("FILTER regex(?Name, \".*" + name + ".*\") ." + NL);
        }
        if (extension.length() > 0) {
            filters.append("FILTER regex(?Extension, \".*" + extension + ".*\") ." + NL);
        }
        if (mimeType.length() > 0) {
            filters.append("FILTER regex(?MimeType, \".*" + mimeType + ".*\") ." + NL);
        }
        if (creator.length() > 0) {
            filters.append("FILTER regex(?Owner, \".*" + creator + ".*\") ." + NL);
        }
        String retval = prolog + triples.toString() + filters.toString() + postlog;
        logger.debug ("Query: " + retval);
        return retval;
    }

}
