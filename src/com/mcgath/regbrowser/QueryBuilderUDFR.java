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

/* This class handles queries for UDFR, the Universal Digital Format Registry.
 * Peter May's work in exploring SPARQL queries for UDFR was extremely 
 * valuable in developing this class.
 */
public class QueryBuilderUDFR extends QueryBuilder {

    private Logger logger;
    
    public QueryBuilderUDFR(FieldSource fs) {
        super(fs);
        baseURI = Services.UDFR_BASE_URI; 
        logger = Logger.getLogger ("com.mcgath.regbrowser");
    }


    /** Constructs the SPARQL query string for UDFR. "Extended from"
     *  and "Extended by" have no clear equivalent in UDFR, so they're
     *  ignored. */
    @Override
    protected String buildQueryString () {
        String prolog = Services.RDFS_PREFIX + NL +
                Services.UDFR_PREFIX_UDFRS + NL + 
                Services.RDF_PREFIX + NL +
                Services.DC_PREFIX_DCTERMS + NL +
                " SELECT DISTINCT ?Name ?MimeType ?Extension " +
                "?Description ?Version ?ReleaseDate " +
                "?Creator ?ByteOrder ?RelatedFormat" +
                NL +
                "WHERE { " + NL;
        String postlog = "} LIMIT " + Integer.toString(limit);
        StringBuffer triples = new StringBuffer ();

        // Triples for returning variables
        triples.append("?format rdfs:label ?Name ." + NL);
        triples.append("?format udfrs:mimeType ?mimeT ." + NL);
        triples.append("?mimeT  rdf:type udfrs:MIME; " +
                          "rdfs:label ?MimeType ." + NL);
        triples.append("?format udfrs:signature ?extensionT ." + NL);
        triples.append("?extensionT  rdf:type udfrs:ExternalSignature; " +
                          "rdfs:label ?Extension ." + NL);
        triples.append("OPTIONAL {?format dcterms:Description ?Description } ." + NL);
        triples.append("OPTIONAL {?format udfrs:version ?Version } ." + NL);
        triples.append("OPTIONAL {?format udfrs:releaseDate ?ReleaseDate } ." + NL);
        triples.append("OPTIONAL {?format udfrs:creator ?Creator } ." + NL);
        triples.append("OPTIONAL {?format udfrs:byteOrderType ?byteOrd } ." + NL);
        triples.append("OPTIONAL {?byteOrd rdfs:label ?ByteOrder } ." + NL);
        triples.append("OPTIONAL {?format udfrs:relatedFormat ?RelatedFormat } ." + NL);
        
        // Filters
        StringBuffer filters = new StringBuffer ();
        if (name.length() > 0) {
            filters.append("FILTER regex(?Name, \".*" + name + ".*\", \"i\") ." + NL);
        }
        if (extension.length() > 0) {
            filters.append("FILTER regex(?Extension, \".*" + extension + ".*\", \"i\") ." + NL);
        }
        if (mimeType.length() > 0) {
            filters.append("FILTER regex(?MimeType, \".*" + mimeType + ".*\", \"i\") ." + NL);
        }
        if (creator.length() > 0) {
            filters.append("FILTER regex(?Creator, \".*" + creator + ".*\", \"i\") ." + NL);
        }

        String retval = prolog + triples.toString() + filters.toString() + postlog;
        logger.debug ("Query: " + retval);

            //***DEBUG*****
        //retval = Services.RDF_PREFIX + NL + "select distinct ?s ?p ?o where {  ?s ?p ?o .  " + NL +
        //        "FILTER regex (?o, \".*dian.*\") . } limit 100 ";
        //logger.debug ("Fake query: " + retval);
        return retval;
    }


}
