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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;


import org.apache.log4j.Logger;

/**
 *  This class sends queries to a SPARQL server and processes the results.
 */
public class QueryManager {

    private String serviceURI;
    
    private static Logger logger = Logger.getLogger("com.mcgath.regbrowser");

    
    /** Constructor with URI. */
    public QueryManager (String uri)  {
        serviceURI = uri;
    }
    
    

    /** Issue a query to the server */
    public List<QuerySolution> doQuery (String queryStr) throws RegBrowserException {
     // Execute the query and obtain results
        QueryExecution qe = null;
        List<QuerySolution> sols = new ArrayList<QuerySolution> ();
        try {
            qe = QueryExecutionFactory.sparqlService(serviceURI, queryStr);
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution result = results.next();
                sols.add(result);
            }
            return sols;
        }
        catch (Exception e) {
            throw new RegBrowserException ("Problem submitting query to " + serviceURI, e);
        }
        finally {
            if (qe != null) {
                qe.close();
            }
        }
    }

    /** Convert a list of solutions to a list of lists */
    public static List<List<String>> solutionsToText
                 (List<QuerySolution> sols, boolean raw) {
        List<List<String>> val = new ArrayList<List<String>> (sols.size());
        for (QuerySolution sol : sols) {
            val.add(solutionToText(sol, raw));
        }
        return val;
    }

    /** Extract a title from a list of lists */
    public static List<String> solutionsToTitles (List<QuerySolution> sols) {
        List<String> titles = new ArrayList<String> (sols.size());
        for (QuerySolution sol : sols) {
            RDFNode title = sol.get("Name");
            if (title != null) {
                String titleStr = title.toString();
                // Do some prettification: Chop off "@en" attribute.
                if (titleStr.endsWith("@en")) {
                    titleStr = titleStr.substring(0, titleStr.length() - 3);
                }
                titles.add ( titleStr);
            }
            else titles.add ( "(No title)");
        }
        return titles;
    }
    
    /* Convert a solution to text of the form "Label: value". */
    private static List<String> solutionToText (QuerySolution sol, boolean raw) {
        //TODO the label is just the variable name. Could be prettier.
        List<String> formattedPairs = new ArrayList<String>();
        Iterator<String> varIter = sol.varNames();
        while (varIter.hasNext()) {
            String var = varIter.next ();
            String nodeVal = sol.get(var).toString();
            if (!raw) {
                // Do some prettification: Chop off "@en" attribute.
                if (nodeVal.endsWith("@en")) {
                    nodeVal = nodeVal.substring(0, nodeVal.length() - 3);
                }
                // If the result is an HTTP URI, give just the last component
                if (nodeVal.startsWith ("http://")) {
                    int lastSlash = nodeVal.lastIndexOf("/");
                    if (lastSlash > 6 && lastSlash < nodeVal.length() - 1) {
                        nodeVal = nodeVal.substring (lastSlash + 1);
                    }
                }
                // If the result has a double-caret, chop it off and
                // everything that follows.
                int n = nodeVal.indexOf("^^");
                if (n > 0) {
                    nodeVal = nodeVal.substring (0,n);
                }
            }
            formattedPairs.add(var + ": " + nodeVal);
        }
        return formattedPairs;
    }
    
}
