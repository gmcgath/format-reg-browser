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

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.QuerySolution;

/** This is the main form window. It has only one instance, which lasts
 *  as long as the application. */
public class QueryWindow extends JFrame implements FieldSource {
    
    private final static int NUM_GRID_ROWS = 6;
    /**
     * So Eclipse won't annoy us
     */
    private static final long serialVersionUID = 1L;

    private Logger logger;
    
    private final static Dimension windowDim = new Dimension(600, 400);
    
    private ActionListener submitListener;   // for the Submit button
    
    private JTextField nameField;
    private JTextField extensionField;
    private JTextField mimeTypeField;
    private JTextField creatorField;
    private JTextField limitField;
    private JCheckBox rawDataBox;
    
    private ResultsWindow resultsWindow;
    
    /** Constructor. Sets up the window. */
    public QueryWindow () {
        super("Registry Browser");
        logger = Logger.getLogger ("com.mcgath.regbrowser");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        Container rootPane = getContentPane ();
        rootPane.setBackground (new Color (220, 220, 220));
        rootPane.setLayout (new GridLayout (NUM_GRID_ROWS, 1)); 
        rootPane.setMinimumSize (windowDim);
        rootPane.setMaximumSize (windowDim);
        rootPane.setPreferredSize (windowDim);
        createActionListeners ();
        
        // Now add the content
        addFormFields (rootPane);
        addControls (rootPane);
        pack();
        setVisible(true);
    }
    
    /* A piece of creating the window. */
    private void addFormFields (Container parent) {
        nameField = addFormField ("Name: ", parent);
        extensionField = addFormField ("Extension: ", parent);
        mimeTypeField = addFormField ("Mime type: ", parent);
        creatorField = addFormField("Creator: ", parent);
        limitField = addFormField("Maximum responses: ", parent);
        limitField.setText("20");
    }
    
    /* A piece of addFormFields for text boxes. */
    private JTextField addFormField(String label, Container parent) {
        JPanel rowPanel = new JPanel();
        parent.add(rowPanel);
        rowPanel.add(new JLabel(label));
        JTextField fld = new JTextField ("", 20);
        rowPanel.add(fld);
        return fld;
    }
    
    /* A piece of addFormFields for checkboxes. */
    
    /* Call this last when creating the window. */
    private void addControls (Container parent) {
        JPanel subPanel = new JPanel ();
        parent.add(subPanel);
        rawDataBox = new JCheckBox ("Raw data");
        subPanel.add(rawDataBox);
        JButton subButton = new JButton ("Submit");
        subPanel.add(subButton);
        subButton.addActionListener (submitListener);
    }
    
    /** Return the contents of the extension field. */
    public String getExtension () {
        String val = extensionField.getText();
        return val;
    }

    /** Return the contents of the MIME type field. */
    public String getMimeType () {
        String val = mimeTypeField.getText();
        return val;
    }
    
    /** Return the contents of the name field. */
    public String getName () {
        String val = nameField.getText();
        return val;
    }
    
    /** Return the contents of the creator field. */
    public String getCreator () {
        String val = creatorField.getText();
        return val;
    }
    
    /** Return the contents of the limit field. Doesn't parse or validate it. */
    public String getLimit () {
        String val = limitField.getText ();
        return val;
    }
    
    /** Return the state of the "raw data" checkbox. */
    public boolean isRawDataRequested () {
        return rawDataBox.isSelected ();
    }
    
    /**
     *  Makes a JFileChooser dialog treat packages and applications
     *  as opaque entities.  Has no effect on other platforms.
     */
    public static void makeChooserOpaque (JFileChooser chooser)
    {
        // Apple TN 2042 LIES; we need to set both properties.
        chooser.putClientProperty
            ("JFileChooser.appBundleIsTraversable", "never");
        chooser.putClientProperty
            ("JFileChooser.packageIsTraversable", "never");
    }

    /** Break out the ActionListeners to avoid clutter */
    private void createActionListeners () {
        submitListener = 
            new ActionListener () {
                public void actionPerformed (ActionEvent e) 
                {
                    submitForm ();
                }
        };
    }
    
    
    private void submitForm () {
        boolean useDBPedia = RegBrowser.getUseDBPedia();
        boolean useP2 = RegBrowser.getUseP2();
        boolean usePronom = RegBrowser.getUsePronom();
        boolean useUDFR = RegBrowser.getUseUDFR();
        QueryBuilder qb;
        List<QuerySolution> dbpResults = new ArrayList<QuerySolution> (1);
        List<QuerySolution> p2Results = new ArrayList<QuerySolution> (1);
        List<QuerySolution> pronomResults = new ArrayList<QuerySolution> (1);
        List<QuerySolution> udfrResults = new ArrayList<QuerySolution> (1);
        try {
            if (useDBPedia) {
                qb = new QueryBuilderDbpedia(this);
                qb.extractFields();
                if (qb.checkFields() != QueryBuilder.QueryCheckCode.OK) {
                    reportError ("Can't submit form for DBPedia", "Something is wrong with the form");
                    return;
                }
                dbpResults = qb.doQuery();
                logger.debug("DBPedia Query got " + dbpResults.size() + " results");
            }
        }
        catch (RegBrowserException e) {
            logAndReport(e);
        }
        try {
            if (useP2) {
                qb = new QueryBuilderP2(this);
                qb.extractFields();
                if (qb.checkFields() != QueryBuilder.QueryCheckCode.OK) {
                    reportError ("Can't submit form for P2", "Something is wrong with the form");
                    return;
                }
                p2Results = qb.doQuery();
                logger.debug("P2 Query got " + dbpResults.size() + " results");
            }
        }
        catch (RegBrowserException e) {
            logAndReport(e);
        }

        try {
            if (useUDFR) {
                qb = new QueryBuilderUDFR(this);
                qb.extractFields();
                if (qb.checkFields() != QueryBuilder.QueryCheckCode.OK) {
                    reportError ("Can't submit form for UDFR", "Something is wrong with the form");
                    return;
                }
                udfrResults = qb.doQuery();
                logger.debug("UDFR Query got " + udfrResults.size() + " results");
            }
        }
        catch (RegBrowserException e) {
            logAndReport(e);
        }

        try {
            if (usePronom) {
                qb = new QueryBuilderPronom(this);
                qb.extractFields();
                if (qb.checkFields() != QueryBuilder.QueryCheckCode.OK) {
                    reportError ("Can't submit form for PRONOM", "Something is wrong with the form");
                    return;
                }
                pronomResults = qb.doQuery();
                logger.debug("PRONOM Query got " + pronomResults.size() + " results");
            }
        }
        catch (RegBrowserException e) {
            logAndReport(e);
        }

        makeResultsWindow ();
        if (useDBPedia) {
            resultsWindow.populate ("DBPedia results", dbpResults);
        }
        if (usePronom) {
            resultsWindow.populate ("PRONOM results", pronomResults);
        }
        if (useP2) {
            resultsWindow.populate ("P2 results", p2Results);
        }
        if (useUDFR) {
            resultsWindow.populate ("UDFR results", udfrResults);
        }
        resultsWindow.expandRows();
    }
    
    private void logAndReport (RegBrowserException e) {
        Throwable cause = e.getCause();
        String causeStr = "Unknown";
        if (cause != null) {
            causeStr = cause.getClass().getName();
            String causeMsg = cause.getMessage();
            if (causeMsg != null) {
                causeStr += ": " + causeMsg;
            }
        }
        logger.error("Problem querying database: " + 
              e.getMessage() + 
              "  Cause:" +
              causeStr);
        reportError ("Error querying database", e.getMessage());
    }
    
    /* Create the results window if necessary. */
    private void makeResultsWindow () {
        if (resultsWindow != null) {
            resultsWindow.dispose();
        }
        resultsWindow = new ResultsWindow(this);
        resultsWindow.setVisible(true);
    }
    
    private void reportError (String title, String msg)
    {
        final String syncStr = "XX7799";
        synchronized (syncStr) {
            JOptionPane.showMessageDialog (this,
                msg, title, JOptionPane.ERROR_MESSAGE); 
        }
    }
}
