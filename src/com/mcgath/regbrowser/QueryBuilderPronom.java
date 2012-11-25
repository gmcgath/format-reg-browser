package com.mcgath.regbrowser;

import org.apache.log4j.Logger;

public class QueryBuilderPronom extends QueryBuilder {

    private Logger logger;

    /** Constructor from QueryWindow */
    public QueryBuilderPronom(FieldSource fs) {
        super(fs);
        baseURI = Services.PRONOM_BASE_URI;
        logger = Logger.getLogger ("com.mcgath.regbrowser");
    }



    /** Constructs the SPARQL query string for Preserv2. Doesn't
     *  really do much of anything right now. */
    @Override
    protected String buildQueryString() {
        //TODO Get this usefully working if P2 can be accessed.
        String prolog = Services.PRONOM_PREFIX + NL +
                Services.RDF_PREFIX + NL +
                Services.RDFS_PREFIX + NL +
                "SELECT DISTINCT ?Name ?PUID ?Extension ?MimeType ?Version ?ByteOrder " +
                "?DevelopedBy ?SupportedBy ?Description ?Released ?Withdrawn " +
                "?Lossy " +
                "WHERE { " + NL;
        String postlog = "} LIMIT " + Integer.toString(limit) + NL + "OFFSET 0";
        StringBuffer triples = new StringBuffer ();
        triples.append("?Format rdfs:label ?Name  ." + NL);
        triples.append("?Format pronom:PUID ?PUID ." + NL);
        triples.append("?Format pronom:extension ?Extension ." + NL);
        triples.append("?Format rdf:type pronom:file-format ." + NL);
        triples.append("OPTIONAL {?Format pronom:version ?Version } . " + NL);
        triples.append("OPTIONAL {?Format pronom:developedBy ?DevelopedBy } . " + NL);
        triples.append("OPTIONAL {?Format pronom:supportedBy ?SupportedBy } . " + NL);
        triples.append("OPTIONAL {?Format pronom:description ?Description } . " + NL);
        triples.append("OPTIONAL {?Format pronom:releaseDate ?Released } . " + NL);
        triples.append("OPTIONAL {?Format pronom:withdrawnDate ?Withdrawn } . " + NL);
        triples.append("OPTIONAL {?Format pronom:lossiness ?Lossy } . " + NL);
        triples.append("OPTIONAL {?Format pronom:byteOrder ?ByteOrder } . " + NL);
        triples.append("OPTIONAL {?Format pronom:MIMETYPE ?MimeType } . " + NL);
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
            filters.append("FILTER regex(?DevelopedBy, \".*" + mimeType + ".*\", \"i\") ." + NL);
        }
        String retval = prolog + triples.toString() + filters.toString() + postlog;
        logger.debug ("Query: " + retval);
        
//        retval = Services.RDF_PREFIX + NL + Services.PRONOM_PREFIX + NL +
//                "select distinct ?s ?p ?o WHERE { ?s ?p ?o .  " +
//                "FILTER regex(?o, \".*ian.*\", \"i\") ." +
//                "} LIMIT 20";
        return retval;
    }


}
