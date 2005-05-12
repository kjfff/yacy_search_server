// Log_p.java 
// -----------------------
// part of the AnomicHTTPD caching proxy
// (C) by Michael Peter Christen; mc@anomic.de
// first published on http://www.anomic.de
// Frankfurt, Germany, 2004
//
// This File is contributed by Alexander Schier
// last major change: 14.12.2004
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Using this software in any meaning (reading, learning, copying, compiling,
// running) means that you agree that the Author(s) is (are) not responsible
// for cost, loss of data or any harm that may be caused directly or indirectly
// by usage of this softare or this documentation. The usage of this software
// is on your own risk. The installation and usage (starting/running) of this
// software may allow other people or application to access your computer and
// any attached devices and is highly dependent on the configuration of the
// software which must be done by the user of the software; the author(s) is
// (are) also not responsible for proper configuration and usage of the
// software, even if provoked by documentation provided together with
// the software.
//
// Any changes to this file according to the GPL as documented in the file
// gpl.txt aside this file in the shipment you received can be done to the
// lines that follows this copyright notice here, but changes must not be
// done inside the copyright notive above. A re-distribution must contain
// the intact and unchanged copyright notice.
// Contributions and changes to the program code must be marked as such.


// You must compile this file with
// javac -classpath .:../Classes Message.java
// if the shell's current path is HTROOT

import de.anomic.http.httpHeader;
import de.anomic.plasma.plasmaSwitchboard;
import de.anomic.server.serverLog;
import de.anomic.server.serverObjects;
import de.anomic.server.serverSwitch;

public class Log_p {


    public static serverObjects respond(httpHeader header, serverObjects post, serverSwitch env) {
	plasmaSwitchboard switchboard = (plasmaSwitchboard) env;
	serverObjects prop = new serverObjects();
	String log = "";
	boolean reversed = false;
	int lines = 50;

	Object logLines[] = serverLog.getLastLog().toArray();
	
	if(post != null){
	    if(post.containsKey("mode") && ((String)post.get("mode")).equals("reversed")){
		reversed=true;
	    }
	    if(post.containsKey("lines")){
		lines = (int)Integer.parseInt((String)post.get("lines"));
	    }
	}

	if(!reversed){
	    //Iterator it = serverLog.getLastLog().iterator();
	    //while(it.hasNext()){
	    //log += it.next() + "\n";
	    //}

		int startLine=logLines.length-lines;
		if(startLine<0){
			startLine=0;
		}

	    //either all Entries(<lines) or "lines" entries
	    for(int i=startLine;i<logLines.length;i++){
		log += (String)logLines[i] + "\n";
	    }
	}else{
	    for(int i=(logLines.length-1);i>=(logLines.length-1)-lines;i--){
		if(i>=0){
		    log += (String)logLines[i] + "\n";
		}
	    }
	}
	prop.put("log", log);

	// return rewrite properties
	return prop;
    }

}
