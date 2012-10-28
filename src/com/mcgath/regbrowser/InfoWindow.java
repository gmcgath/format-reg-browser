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

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/** Superclass for information windows, even though there may be only one.
 *  Heavily lifted from JHOVE. */
public abstract class InfoWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private JMenuItem saveItem;
    private JMenuItem closeItem;
    
    private ActionListener closeListener;
    private ActionListener saveListener;

    /** Constructor with window title. Sets up a File menu. */
    public InfoWindow(String title) 
            throws HeadlessException {
        super(title);
        createActionListeners();
    
        JMenuBar menuBar = new JMenuBar ();
        JMenu fileMenu = new JMenu ("File");
        menuBar.add (fileMenu);
        saveItem = new JMenuItem ("Save as...");
        fileMenu.add (saveItem);
        saveItem.addActionListener(saveListener);
        
        closeItem = new JMenuItem ("Close");
        fileMenu.add (closeItem);
        // Make mnemonic control-W, command-W, or whatever-W
        closeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        closeItem.addActionListener (closeListener);
        
        setDefaultCloseOperation (JFrame.HIDE_ON_CLOSE);
        setJMenuBar (menuBar);
        //_dateFmt = new SimpleDateFormat ("yyyy-MM-dd");
    }
    
    
    /** Break out the ActionListeners to avoid clutter */
    private void createActionListeners () {
        closeListener = 
            new ActionListener () {
                public void actionPerformed (ActionEvent e) 
                {
                    closeFromMenu ();
                }
        };
        saveListener = 
            new ActionListener () {
                public void actionPerformed (ActionEvent e) 
                {
                    saveFromMenu ();
                }
        };
    }

    /**
     *  Handler for the "Close" menu item.
     *  Simply hides the window without deleting it.
     */
    protected void closeFromMenu ()
    {
        setVisible (false);
    }

    /**
     *  Handler for the "Save" menu item.
     *  A stub.
     */
    protected void saveFromMenu ()
    {
        // TODO stub
    }

}
