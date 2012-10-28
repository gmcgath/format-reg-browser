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

/** This class provides static definitions for the SPARQL services used by
 *  the application. */
public class Services {
    
    /** The SPARQL endpoint for DBPedia. */
    public final static String DBPEDIA_BASE_URI = "http://dbpedia.org/sparql";

    /** The SPARQL endpoint for UDFR. */
    public final static String UDFR_BASE_URI = "http://udfr.org/ontowiki/sparql/";

    /** The SPARQL endpoint for Preserv2. */
    public final static String P2_BASE_URI = "http://p2-registry.ecs.soton.ac.uk/SPARQL/";
    
    /** The SPARQL endpoint for Pronom. */
    public final static String PRONOM_BASE_URI = "http://test.linkeddatapronom.nationalarchives.gov.uk/sparql/endpoint.php";

    /** Prefix declaration for DBPedia ontology */
    public final static String DBPEDIA_PREFIX_DBO = 
            "PREFIX dbo: <http://dbpedia.org/ontology/>";

    /** Prefix declaration for DBPedia properties */
    public final static String DBPEDIA_PREFIX_DBPEDIA2 = 
            "PREFIX dbpedia2: <http://dbpedia.org/property/>";

    /** Prefix declaration for DBPedia categories */
    public final static String DBPEDIA_PREFIX_CATEGORY =
            "PREFIX category: <http://dbpedia.org/resource/Category:>";

    /** Prefix declaration for Dublin Core terms */
    public final static String DC_PREFIX_DCTERMS =
            "PREFIX dcterms: <http://purl.org/dc/terms/>";
    
    /** Prefix declaration for PRONOM */
    public final static String PRONOM_PREFIX =
//            "PREFIX pronom: <http://pronom.nationalarchives.gov.uk/#>";
            "PREFIX pronom: <http://reference.data.gov.uk/technical-registry/>";
    
    /** Prefix declaration for RDF Schema */
    public final static String RDFS_PREFIX =
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
    
    /** Prefix declaration for RDF syntax */
    public final static String RDF_PREFIX =
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";

    /** Prefix declaration for UDFR */
    public final static String UDFR_PREFIX_UDFRS =
            "PREFIX udfrs: <http://udfr.org/onto#>";
}
