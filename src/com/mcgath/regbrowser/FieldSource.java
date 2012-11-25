package com.mcgath.regbrowser;

public interface FieldSource {

    /** Return the contents of the extension field. */
    public String getExtension ();

    /** Return the contents of the MIME type field. */
    public String getMimeType ();
    
    /** Return the contents of the name field. */
    public String getName ();  
    
    /** Return the contents of the creator field. */
    public String getCreator ();

    /** Return the contents of the limit field. Doesn't parse or validate it. */
    public String getLimit ();
    
    /** Return the state of the "raw data" checkbox. */
    public boolean isRawDataRequested ();
}
