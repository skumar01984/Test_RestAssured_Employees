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
import static org.testng.Assert.assertEquals;

public class EmployeeAPI extends Config {

	Response response;
	String[] json = null;
	JsonPath jsonPath = null;
	List<Map<Object, Object>> employeeList = null;

	public EmployeeAPI() {
		response = given().contentType(ContentType.TEXT).get(Config.employeeURL);
		String text = response.then().statusCode(200).extract().body().asString();

		// String[] extractJson = text.replace("},", "}").replace("....",
		// "").split("<pre>");
		String[] extractJson = text.replace("....", "{}").split("<pre>");
		json = extractJson[1].split("</pre>");
		System.out.println("=================================== \n" + json[0]);

	}

	@Test(priority = 0)
	public void CheckTotalNumberOfEmployees() {
		try {
			jsonPath = new JsonPath(json[0]);

			// First Way : to get number of employee from list

			int numFromsResponse = jsonPath.getInt("data.id.size()");
			System.out.println("1 way using Json Parser: Total no. of employee : " + numFromsResponse);

			// Second Way : to get number of employee from list

			employeeList = jsonPath.getList("data");
			System.out.println("2 way using List: Total no. of employee : " + employeeList.size());

			assertEquals(employeeList.size(), 2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void getEmployeeID() {
		try {

			// To get the employee data id from list

			jsonPath = new JsonPath(json[0]);
			
			// 1. Way : To get the second employee id using json parser

			String id = jsonPath.getString("data.id[1]");

			// Second employee data is not present in the response text.
			// Extracted Json will return null

			if (id == null) {
				System.out.println("Second employee data is not present in the response text." + id);
			} else {
				System.out.println("Second employee data is present in the response text." + id);
			}

			System.out.println("=======================================================================");
			// assertEquals(id, null);

			// 2. Way using Has Map : To get the second employee id using Has Map
			if (employeeList.size() > 1) {

				/*
				 * employeeList.forEach(empData -> { System.out.println("Employee Data is : " +
				 * empData); });
				 */
				String empkey = null, empValue = null;
				for (int i = 0; i < employeeList.size(); i++) {

					Map<Object, Object> map = employeeList.get(i);
					if (map.containsKey("id")) {
						for (Map.Entry<Object, Object> entry : map.entrySet()) {
							empkey = entry.getKey().toString();
							empValue = entry.getValue().toString();
							// System.out.println("key: " + empkey + " value: " + empValue);

							if (empkey.equals("id")) {
								System.out.println(
										"First employee id is present in the JSON. as Key : " + empkey + " ==> " + empValue);
								System.out.println("=================================================================");
							}

						}

					} else {
						System.out.println("Second employee data is not present in the JSON. as Key : " + empkey
								+ " ==> " + empValue);
						System.out.println("=======================================================================");
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
