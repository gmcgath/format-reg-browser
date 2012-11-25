package com.mcgath.regbrowser.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.hp.hpl.jena.query.QuerySolution;
import com.mcgath.regbrowser.QueryManager;

/** This gets one result from the results object and turns it into
 *  HTML. */
public class ResultsTag extends BodyTagSupport {

    protected int index;
    protected String repository;
    private List<List<String>> solutionStrings;
    private List<String> solutionTitles;
    
    private boolean raw = false;
    
    @Override
    public int doStartTag() throws JspException {
        index = 0;
        pageContext.setAttribute ("representation", null);
        ServletRequest req = pageContext.getRequest ();
        @SuppressWarnings("unchecked")
        Map<String, List<QuerySolution>> results = 
                (Map<String, List<QuerySolution>>) req.getAttribute("results");
        List<QuerySolution> resList = results.get(repository);
        if (resList == null) {
            List<String> sol = new ArrayList<String> ();
            sol.add ("No results");
            solutionStrings = new ArrayList<List<String>>();
            solutionStrings.add(sol);
        }
        else {
            solutionStrings = QueryManager.solutionsToText(resList, raw);
            solutionTitles = QueryManager.solutionsToTitles(resList);
        }
        return EVAL_BODY_INCLUDE;
    }
    
    /** Attribute setter for "repository" */
    public void setRepository (String rep) {
        repository = rep;
    }
    
    /* This will turn out all the successive results for a single
     * repository */
    @Override
    public int doAfterBody() throws JspException {
        //ServletRequest req = pageContext.getRequest ();
//        Map<String, List<QuerySolution>> results = 
//                (Map<String, List<QuerySolution>>) req.getAttribute("results");
//        List<QuerySolution> resList = results.get(repository);
        if (index >= solutionStrings.size()) {
            return SKIP_BODY;
        }
        pageContext.setAttribute ("representation", 
                toRep (solutionStrings.get(index), solutionTitles.get(index)));
        index++;
        return EVAL_BODY_AGAIN;
    }
    
    /* Turn a result into an index */
    private String toRep (List<String> sol, String title) {
        StringBuffer valBuf = new StringBuffer ();
        valBuf.append("<h3>" + title + "</h3>\n");
        for (String item : sol) {
            valBuf.append (item + "<br>\n");
        }
        return valBuf.toString();
    }
    
    
    /* Show no results */
    private String noResultsRep () {
        return "<div><p>No results</p></div>";
    }
}
