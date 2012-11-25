package com.mcgath.regbrowser.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.logging.*;

import com.hp.hpl.jena.query.QuerySolution;
import com.mcgath.regbrowser.QueryBuilder;
import com.mcgath.regbrowser.QueryBuilderDbpedia;
import com.mcgath.regbrowser.QueryBuilderPronom;
import com.mcgath.regbrowser.QueryBuilderUDFR;
import com.mcgath.regbrowser.RegBrowser;
import com.mcgath.regbrowser.RegBrowserException;

public class Search extends HttpServlet {

    private static Logger logger;

    private final static String DBPEDIA = "DBPEDIA";
    private final static String UDFR = "UDFR";
    private final static String PRONOM = "PRONOM";
    
    public void init() {
        logger = Logger.getLogger ("com.mcgath.regbrowser");
        logger.setLevel(Level.FINE);
    }
    
    @Override
    public void doGet(HttpServletRequest request,
               HttpServletResponse response) {
        doRequest (request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request,
               HttpServletResponse response) {
        doRequest (request, response);
    }
    
    /* handle either GET or POST */
    private void doRequest(HttpServletRequest request,
            HttpServletResponse response)  {
        ServletContext ctx = this.getServletContext();
        String name = request.getParameter("name");
        String mimeType = request.getParameter("mime");
        String extension = request.getParameter ("ext");
        String creator = request.getParameter("creator");
        try {
            try {
                Map<String, List<QuerySolution>> results =
                        submitForm (name, mimeType, extension, creator);
                RequestDispatcher disp = ctx.getRequestDispatcher ("/result.jsp");
                request.setAttribute("results", results);
                disp.forward (request, response);
            }
            catch (RegBrowserException e) {
                RequestDispatcher errdisp = ctx.getRequestDispatcher ("/error.jsp");
                errdisp.forward (request, response);
            }
        }
        catch (IOException e) {
            logger.log (Level.INFO, "Exception in Search.java: " + e.getClass().getName() +
                    "\n" + e.getMessage());
        }
        catch (ServletException e) {
            logger.log (Level.INFO, "ServletException in Search.java: " + e.getMessage());
        }
    }
    
    
    private Map<String, List<QuerySolution>> submitForm (String name, 
            String mimeType, 
            String extension,
            String creator) throws RegBrowserException {
        boolean useDBPedia = RegBrowser.getUseDBPedia();
        boolean usePronom = RegBrowser.getUsePronom();
        boolean useUDFR = RegBrowser.getUseUDFR();
        ServletFieldSource sfs;
        
        Map<String, List<QuerySolution>> results = 
                new HashMap<String, List<QuerySolution>> ();
        List<QuerySolution> dbpResults = new ArrayList<QuerySolution> (1);
        List<QuerySolution> pronomResults = new ArrayList<QuerySolution> (1);
        List<QuerySolution> udfrResults = new ArrayList<QuerySolution> (1);

        sfs = new ServletFieldSource();
        sfs.setName (name);
        sfs.setMimeType (mimeType);
        sfs.setExtension (extension);
        sfs.setCreator (creator);
        sfs.setLimit ("20");

        if (useDBPedia) {
            QueryBuilder dbpqb = new QueryBuilderDbpedia(sfs);
            dbpqb.extractFields ();
            dbpResults = dbpqb.doQuery();
            results.put (DBPEDIA, dbpResults);
            logger.log(Level.FINE,"DBPedia Query got " + dbpResults.size() + " results");
        }

        if (useUDFR) {
            QueryBuilder uqb = new QueryBuilderUDFR(sfs);
            uqb.extractFields();
            udfrResults = uqb.doQuery();
            results.put (UDFR, udfrResults);
            logger.log(Level.FINE, "UDFR Query got " + udfrResults.size() + " results");
        }

        if (usePronom) {
            QueryBuilder pqb = new QueryBuilderPronom(sfs);
            pqb.extractFields();
            pronomResults = pqb.doQuery();
            results.put (PRONOM, pronomResults);
            logger.log(Level.FINE, "PRONOM Query got " + pronomResults.size() + " results");
            
        }
        return results;
    }

//    private void logAndReport (RegBrowserException e) {
//        Throwable cause = e.getCause();
//        String causeStr = "Unknown";
//        if (cause != null) {
//            causeStr = cause.getClass().getName();
//            String causeMsg = cause.getMessage();
//            if (causeMsg != null) {
//                causeStr += ": " + causeMsg;
//            }
//        }
//        logger.log(Level.INFO, "Problem querying database: " + 
//              e.getMessage() + 
//              "  Cause:" +
//              causeStr);
//    }

}
