package RestAssured_Employee.RestAssured_Employees.Action;


import org.json.simple.JSONObject;
//import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import RestAssured_Employee.RestAssured_Employees.util.Config;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import static io.restassured.RestAssured.*;

public class EmployeeAPI extends Config{
	
	Response response;
	String[] text2 = null;
	JsonPath jsonPath = null;
	
	public EmployeeAPI() {
		response=given().contentType(ContentType.TEXT).get(Config.employeeURL);
		String text = response.
				then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		
		String[] text1 = text.replace("},", "}")
				.replace("....", "")
				.split("<pre>");
		text2 = text1[1].split("</pre>");
		System.out.println("================================================================== \n"+text2[0]);
		
	
	}
	
	@Test(priority=0)
	public void getEmployeeID() {
		try {
			
			// To get the data list id
			System.out.println(text2[0]);
			jsonPath = new JsonPath(text2[0]);
		    String id = jsonPath.getString("data.id[0]"); // To get employee id from list
		    //String data=jsonPath.getString("data");
		    System.out.println("Employaee id is : "+id);
		
	}catch(Exception e) {
		e.printStackTrace();
		}
	}
		
	
	@Test(priority=1)
	public void CheckTotalSizeOfEmployee() {
		try {
		jsonPath = new JsonPath(text2[0]);	
		
		// First Way to get size of list
		int numFromsResponse = jsonPath.getInt("data.id.size()");
		System.out.println("Employee size of list 1.way : "+ numFromsResponse);
		
		// Second Way to get size of list
		List<Map<Object, Object>> employeeList = jsonPath.getList("data");
	    System.out.println("Employee size of list 1.way : " + employeeList.size());
		
		
	}catch(Exception e) {
		e.printStackTrace();
		}
	}
	
	
	
}
