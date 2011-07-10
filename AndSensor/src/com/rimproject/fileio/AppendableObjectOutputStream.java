package com.rimproject.fileio;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/*
 * thanks to http://stackoverflow.com/questions/1194656/appending-to-an-objectoutputstream
 */
public class AppendableObjectOutputStream extends ObjectOutputStream
{
	    public AppendableObjectOutputStream(OutputStream out) throws IOException 
	  	  {
		    super(out);
		   
		  }

		  @Override
		  protected void writeStreamHeader() throws IOException 
		  {
			  
		  }

}


