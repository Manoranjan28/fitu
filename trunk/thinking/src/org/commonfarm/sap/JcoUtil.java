package org.commonfarm.sap;

import org.commonfarm.util.StringUtil;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;

public class JcoUtil {

	public static void release(Client client, JcoSource source) {
		client.disconnect();
	}
	
	public static Client getClient(JcoSource jcoSource) {
		if (!StringUtil.notEmpty(jcoSource.getEncode())) {
			jcoSource.setEncode("EN");
		}
		Client client = JCO.createClient(jcoSource.getCatalog(), 
				jcoSource.getUsername(), jcoSource.getPassword(), 
				jcoSource.getEncode(), jcoSource.getHost(), jcoSource.getPort());
		client.connect();
		return client;
	}
	public static void main(String[] args) {
		
	}
}
