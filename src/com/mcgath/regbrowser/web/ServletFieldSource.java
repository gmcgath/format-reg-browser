package com.mcgath.regbrowser.web;

import com.mcgath.regbrowser.FieldSource;

/** Accumulate the results of a web form here */
public class ServletFieldSource implements FieldSource {

    private String extension;
    private String mimeType;
    private String name;
    private String creator;
    private String limit;
    private boolean rawDataRequested;
    
    @Override
    public String getExtension() {
        return extension;
    }
    
    public void setExtension(String ext) {
        extension = ext;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mime) {
        mimeType = mime;
    }


    @Override
    public String getName() {
        return name;
    }
    
    public void setName(String nam) {
        name = nam;
    }

    @Override
    public String getCreator() {
        return creator;
    }
    
    public void setCreator (String c) {
        creator = c;
    }

    @Override
    public String getLimit() {
        return limit;
    }
    
    public void setLimit (String lim) {
        limit = lim;
    }

    @Override
    public boolean isRawDataRequested() {
        return rawDataRequested;
    }

    public void setRawDataRequested(boolean b) {
        rawDataRequested = b;
    }
}
