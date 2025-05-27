package testPartnership;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Production {
	String partnercodeUC = "UC";
	String statuscode;
	String lrn;
	String Result;
	String message;
	String response;

	@Test(groups = "Getmaster")
	public void Getmasterdata() {

		RestAssured.baseURI = "https://soa.niyogin.in";
		try {
			// create customer
			response = given().log().all().header("Content-type", "application/json")
					.header("Authorization", "Basic QXBxcXg6QjFRLVZaVmkt").when().get("/gates/1.0/sweeps/getMasterData")
					.then().log().all().assertThat().statusCode(200).time(lessThan(100L))
					.body("message", equalTo("Master data from LOS"))
					// store response in string
					.extract().response().asString();
			// step3: covert json response into string and access id from response

			JsonPath jp = new JsonPath(response);

			statuscode = jp.getString("statusCode");
			message = jp.getString("message");
			System.out.println("Response code"+statuscode);
			if(statuscode.contains("200")) {
				System.out.println("PASS");
			}
			else {
				common.sendSlackCIF(Result= "GetMasterData API Production Result:-->\t"+"statuscode=" + statuscode + "\n" + "message" + message);
			}
		} catch (AssertionError e) {
			String errorMsg = e.getMessage();
			if (errorMsg != null && errorMsg.contains("Expected response time was not a value less than")) {
				System.out.println("Response time assertion FAILED!");
				System.out.println(errorMsg);
				common.sendSlackCIF("🚨 *API Alert*: " + errorMsg);
				
			} else {
				System.out.println("Some other assertion FAILED: " + errorMsg);
			}
		}
	}
}
