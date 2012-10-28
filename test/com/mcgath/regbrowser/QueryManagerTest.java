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

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import java.util.Iterator;
import java.util.List;

public class QueryManagerTest {

    static private final String NL = System.getProperty("line.separator") ; 
    
//    @Test
//    public void testSimpleQuery () throws Exception {
//        QueryManager qm = new QueryManager (Services.DBPEDIA_BASE_URI);
//        Query q = qm.makeSimpleQuery("foo", "bar");
//        assertNotNull (q);
//    }
    
    @Test
    public void testQueryFromString() throws Exception {
        // This is from the example Ex2.java
        // First part or the query string 
        String prolog = Services.DBPEDIA_PREFIX_DBPEDIA2;
        
        // Query string.
        String queryString = prolog + NL +
            "SELECT ?format ?abstract WHERE { " + NL +
            "?format dbpedia2:extension \".pdf\"@en ." + NL +
            "}"  + NL +
            "LIMIT 20" ; 
        QueryManager qm = new QueryManager (Services.DBPEDIA_BASE_URI);
        List<QuerySolution> results = qm.doQuery(queryString);
        assertNotNull(results);
        assertTrue (results.size() > 0);
    }

    @Test
    public void testQueryFromString2() throws Exception {
        // This is from the example Ex2.java
        // First part or the query string 
        String prolog = Services.DC_PREFIX_DCTERMS + NL + Services.DBPEDIA_PREFIX_CATEGORY;
        
        // Query string.
        String queryString = prolog + NL +
            "SELECT ?x WHERE {" +
            "?x dcterms:subject category:Actors_from_New_York" + NL +
            "}" + NL +
            "LIMIT 20" ; 
        QueryManager qm = new QueryManager (Services.DBPEDIA_BASE_URI);
//        Query q = qm.makeQueryFromString(queryString);
//        assertNotNull(q);
        List<QuerySolution> results = qm.doQuery(queryString);
        assertNotNull(results);
        assertTrue (results.size() > 0);
        
        for (QuerySolution sol : results) {
            Iterator<String> vars = sol.varNames();
            while (vars.hasNext()) {
                String var = vars.next();
                RDFNode node = sol.get(var);
                assertNotNull(node);
            }
        }
    }

    @Test
    public void testSolutionsToText() throws Exception {
        // This is from the example Ex2.java
        // First part or the query string 
        String prolog = Services.DBPEDIA_PREFIX_DBPEDIA2;
        
        // Query string.
        String queryString = prolog + NL +
            "SELECT ?format ?abstract WHERE { " + NL +
            "?format dbpedia2:extension \".pdf\"@en ." + NL +
            "}"  + NL +
            "LIMIT 20" ; 
        QueryManager qm = new QueryManager (Services.DBPEDIA_BASE_URI);
        List<QuerySolution> results = qm.doQuery(queryString);
        List<List<String>> textResults = QueryManager.solutionsToText(results, false);
        assertTrue(textResults.size() > 0);
        for (List<String> textResult : textResults) {
            assertTrue (textResult.size() > 0);
        }
    }

}
