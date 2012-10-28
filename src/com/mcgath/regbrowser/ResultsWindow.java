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

import java.awt.Dimension;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import com.hp.hpl.jena.query.QuerySolution;

/** Window for displaying query results. A new one is generated for each query,
 *  replacing the old one. */
public class ResultsWindow extends InfoWindow {

    private final static boolean SAVE_IMPLEMENTED = false;
    
    private static final long serialVersionUID = 1L;
    
    private DefaultMutableTreeNode rootNode;
    private JTree tree;
    private boolean rawMode;

    public ResultsWindow (QueryWindow qwin) 
    {
        // Give the window a temporary title.  The title should probably
        // be changed to the top-level directory, or else should be
        // given a sequential number for each new window.
        super ("Results");
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        setSize (350, 600);
        
        rawMode = qwin.isRawDataRequested();
        rootNode = new DefaultMutableTreeNode("Formats");
        TreeModel treeModel = new DefaultTreeModel (rootNode);
        tree = new JTree ();
        tree.setModel (treeModel);
        tree.setShowsRootHandles (true);
        
        TreeCellRenderer rend = tree.getCellRenderer ();
        if (rend instanceof DefaultTreeCellRenderer) {
            // it should be
            DefaultTreeCellRenderer trend =
                (DefaultTreeCellRenderer) rend;
            trend.setOpenIcon (null);
            trend.setClosedIcon (null);
            trend.setLeafIcon (null);
        }
        JScrollPane scrollPane = new JScrollPane (tree);
        getContentPane ().add (scrollPane, "Center");

        // Add a small panel at the bottom, since on some OS's there
        // may be stuff near the bottom of a window which will conflict
        // with the scroll bar.
        JPanel panel = new JPanel ();
        panel.setMinimumSize (new Dimension (8, 8));
        getContentPane ().add (panel, "South");
        setVisible(true);
    }

    /** Display the results of the query. */
    public void populate (String source, List<QuerySolution> results) {
        RepTreeNode node = new RepTreeNode (source, results, rawMode);
        rootNode.add (node);
//        tree.expandRow (0);
    }
    
    /** Expands the tree appropriately when everything is built. */
    public void expandRows ()
    {
        tree.expandRow (0);
    }
    
    /** Invoked when the "Close" menu item is selected.
     *  Overrides the parent class's method to delete
     *  the window rather than hiding it. */
    @Override
    protected void closeFromMenu ()
    {
        super.closeFromMenu ();
        dispose ();
    }
    
    /**
     * Saves the information to a file specified by the user. 
     * Well, actually, it doesn't but it could.
     */
    @Override
    protected void saveFromMenu ()
    {
        PrintWriter wtr = doSaveDialog ();
        if (wtr == null) {
            return;
        }
    }
    
    
    /** This is a stub which could save the search results but doesn't
     *  do anything as yet.
     */
    protected PrintWriter doSaveDialog ()
    {
        if (SAVE_IMPLEMENTED) {
            JFileChooser saver = new JFileChooser ();
            // On Mac OS, make packages and .apps opaque.
            QueryWindow.makeChooserOpaque (saver);
            
            File lastDir = RegBrowser.getSaveDirectory ();
            if (lastDir != null) {
                saver.setCurrentDirectory(lastDir);
            }
            
            // Create a custom panel so we can set options.
            JPanel accessory = new JPanel ();
            accessory.setPreferredSize(new Dimension (180, 120));
        }
        //TODO actually save something
        return null;   
    }

}
