package com.utilities;

//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.File;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.Properties;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

@SuppressWarnings("unused")
public class ExcelReader {

	public static Map<String, String> getRow(String file, String sheet, String testCase) throws FilloException {
		List<String> fields = new ArrayList<String>();
		Map<String, String> testData = new LinkedHashMap<String, String>();
		Fillo fillo = new Fillo();
		try {
			Connection connection = fillo.getConnection(file);
			String strQuery = "Select * from " + sheet;
			Recordset recordset = connection.executeQuery(strQuery);

			Recordset rste = recordset.where("TestCaseName='" + testCase + "'");
			int i = rste.getCount();

			if (i == 1) {
				fields = rste.getFieldNames();
				while (rste.next()) {
					for (String field : fields) {
						String fieldValue = rste.getField(field);
						testData.put(field, fieldValue);
					}
				}
			} else {
				System.out.println("Number of resultant row is greater than one.");
			}
			for (String k : testData.keySet()) {
				System.out.println("Field- '" + k + "', FieldValue- " + testData.get(k));
			}
			recordset.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testData;
	}

}
