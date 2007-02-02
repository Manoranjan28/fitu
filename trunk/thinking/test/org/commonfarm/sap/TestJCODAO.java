package org.commonfarm.sap;

import java.util.ArrayList;
import java.util.List;

import org.commonfarm.dao.Test;
import org.commonfarm.sap.JcoSource;
import org.commonfarm.sap.Parameter;
import org.commonfarm.sap.SapJCOCallback;
import org.commonfarm.sap.SapJCOSupport;
import org.commonfarm.sap.SapJCOTemplate;

import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

public class TestJCODAO extends SapJCOSupport {
	public List getObjects(String funName, Parameter paraObj) {
		JcoSource jcoSource = new JcoSource("SAPJCO", "10.20.2.56", "ivoeif", "ivoeif", "EN", "00", "800");
		SapJCOTemplate sapJCO = new SapJCOTemplate(jcoSource);
        Object object = sapJCO.execute(funName, paraObj, new SapJCOCallback() {
        	public Object doInJCO(Client client, Function function, Object paraObj) {
        		ParameterList paraList = function.getImportParameterList();
        		paraList.setValue("20070202", "TODATE");
        		paraList.setValue("073000", "TOTIME");
        		paraList.setValue("20070201", "FROMDATE");
        		paraList.setValue("073000", "FROMTIME");
        		client.execute(function);
        		Table table = function.getTableParameterList().getTable("I_CELL_OUTPUT");
        		
        		List tests = new ArrayList();
        		while(table.nextRow()) {
        			//String matnr = table.getString("MATNR");
        			//Integer quanty = new Double(table.getDouble("QUANTY")).intValue();
        			Test test = new Test();
        			//whIn.setMatnr(matnr);
        			//test.setQty(quanty);
        			tests.add(test);
        		}
        		return tests;
        	}
        });
		return (List) object;
	}
	
	public void saveObject(Object model) {
		
	}
	public static void main(String[] args) {
		TestJCODAO testDAO = new TestJCODAO();
		Parameter paraObj = new Parameter();
		testDAO.getObjects("ZCELLOUTPUT", paraObj);
	}
}
