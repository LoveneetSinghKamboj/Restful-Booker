package org.NAGP.Assignment.Rest.Utilities.apitest.restassuredFuntions;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.NAGP.Assignment.Rest.Utilities.apitest.actions.Helper;
import org.NAGP.Assignment.Rest.Utilities.apitest.actions.HttpOperation;
import org.NAGP.Assignment.Rest.Utilities.apitest.actions.ValidatorOperation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;


public class API {

	RequestSpecification reqSpec;
	HttpOperation method;
	String url;
	Response resp;

	public void init() {

	}

	public void initBase(String baseConst) {
		Helper getHelp = new Helper();
		getHelp.set_path("src/test/resources/properties/execution.properties");
		try {
			RestAssured.baseURI = getHelp.loadProperties(baseConst);
			reqSpec = given();
		} catch (Exception e) {
			e.printStackTrace();
		}
		reqSpec = RestAssured.given().log().all();
	}


	public void setHeader(String head, String val) {
		reqSpec.header(head, val);
	}


	public void setBody(Object object) {
		reqSpec.body(object);
	}


	public ValidatableResponse methodCall(String endPoint, HttpOperation method) {
		try {
			this.url = endPoint;
			this.method = method;
			if (method.toString().equalsIgnoreCase("get")) {
				resp = reqSpec.get(url);
				return resp.then();
			} else if (method.toString().equalsIgnoreCase("post")) {
				resp = reqSpec.post(url);
				return resp.then();
			} else if (method.toString().equalsIgnoreCase("patch")) {
				resp = reqSpec.patch(url);
				return resp.then();
			} else if (method.toString().equalsIgnoreCase("put")) {
				resp = reqSpec.put(url);
				return resp.then();
			} else if (method.toString().equalsIgnoreCase("delete")) {
				resp = reqSpec.delete(url);
				return resp.then();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public API assertIt(String key, Object val, ValidatorOperation operation) {

		switch (operation.toString()) {
			case "EQUALS":
				resp.then().body(key, equalTo(val));
				break;

			case "KEY_PRESENTS":
				resp.then().body(key, hasKey(key));
				break;

			case "HAS_ALL":
				break;

			case "NOT_EQUALS":
				resp.then().body(key, not(equalTo(val)));
				break;

			case "EMPTY":
				resp.then().body(key, empty());
				break;

			case "NOT_EMPTY":
				resp.then().body(key, not(emptyArray()));
				break;

			case "NOT_NULL":
				resp.then().body(key, notNullValue());
				break;

			case "HAS_STRING":
				resp.then().body(key, containsString((String)val));
				break;

			case "SIZE":
				resp.then().body(key, hasSize((int)val));
				break;
		}

		return this;
	}


}
