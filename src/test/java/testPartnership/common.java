package testPartnership;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class common {
	private static final  String aadhaarxml="<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><OfflinePaperlessKyc referenceId=\\\"533820250224175050317\\\"><UidData><Poi dob=\\\"03-09-1995\\\" e=\\\"80f57fa4868fa08e08670be735c1b4f9a0cac2ea89c2f68aae20df1d4d651694\\\" gender=\\\"F\\\" m=\\\"6f593e2e35a616a979ec38dfbd546a304e6715a691c177613a7fce5f58e05c60\\\" name=\\\"Nabanita Karmakar\\\"/><Poa careof=\\\"C/o Santosh Karmakar\\\" country=\\\"India\\\" dist=\\\"Paschim Bardhaman\\\" house=\\\"47\\\" landmark=\\\"Near Milani Club\\\" loc=\\\"B-Zone\\\" pc=\\\"713205\\\" po=\\\"Durgapur Steel Town East\\\" state=\\\"West Bengal\\\" street=\\\"Sovapur Avenue\\\" subdist=\\\"\\\" vtc=\\\"Durgapur (m Corp.)\\\"/><Pht>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</Pht></UidData><Signature xmlns=\\\"http://www.w3.org/2000/09/xmldsig#\\\"><SignedInfo><CanonicalizationMethod Algorithm=\\\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\\\"/><SignatureMethod Algorithm=\\\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\\\"/><Reference URI=\\\"\\\"><Transforms><Transform Algorithm=\\\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\\\"/></Transforms><DigestMethod Algorithm=\\\"http://www.w3.org/2001/04/xmlenc#sha256\\\"/><DigestValue>f6K+7TAQcnRYjGFDCJlH140UWaY1NhzC0VKBHH8s89M=</DigestValue></Reference></SignedInfo><SignatureValue>bIHZGipWEAYS5+gfIv/Fj1KsG1l3g4YY69N6f0obgui7nffgTReJlDi4qejBroEhnN5Se+Gd4YaZ&#13;\\ni23yLao9LwVhANLhZ95Inwcjz/wN9GrKSksTGX/t+Ps+AjUqJPTj56MNEPvTtuKPRc8fIAjDp66d&#13;\\nZ7vMxf2DFjNKlBp9X50Z4AzXrfzaBQ/u+4ieELqQyNJeZsiZU+e2DRbjEqtt+yCDYg3enBOYvWGO&#13;\\nDy/0DiuA1kme0syT7z0eMxEfP0NPOQNC/SXpYrFHg/dYWWkc/v+Fyo65t0RsyksYzzw5OZ7cwZJM&#13;\\nNG1bU+dMIND5L0KFhqHwTAti881ztzcTwQcwmw==</SignatureValue><KeyInfo><X509Data><X509SubjectName>CN=DS UNIQUE IDENTIFICATION AUTHORITY OF INDIA 05,2.5.4.51=#131b4141444841522048512042414e474c4120534148494220524f4144,STREET=BEHIND KALI MANDIR,ST=Delhi,2.5.4.17=#1306313130303031,O=UNIQUE IDENTIFICATION AUTHORITY OF INDIA,C=IN</X509SubjectName><X509Certificate>MIIHwjCCBqqgAwIBAgIEU5laMzANBgkqhkiG9w0BAQsFADCB/DELMAkGA1UEBhMCSU4xQTA/BgNV&#13;\\nBAoTOEd1amFyYXQgTmFybWFkYSBWYWxsZXkgRmVydGlsaXplcnMgYW5kIENoZW1pY2FscyBMaW1p&#13;\\ndGVkMR0wGwYDVQQLExRDZXJ0aWZ5aW5nIEF1dGhvcml0eTEPMA0GA1UEERMGMzgwMDU0MRAwDgYD&#13;\\nVQQIEwdHdWphcmF0MSYwJAYDVQQJEx1Cb2Rha2RldiwgUyBHIFJvYWQsIEFobWVkYWJhZDEcMBoG&#13;\\nA1UEMxMTMzAxLCBHTkZDIEluZm90b3dlcjEiMCAGA1UEAxMZKG4pQ29kZSBTb2x1dGlvbnMgQ0Eg&#13;\\nMjAxNDAeFw0yMTAyMjYxMTU0MjRaFw0yNDAyMjcwMDI3MTFaMIHdMQswCQYDVQQGEwJJTjExMC8G&#13;\\nA1UEChMoVU5JUVVFIElERU5USUZJQ0FUSU9OIEFVVEhPUklUWSBPRiBJTkRJQTEPMA0GA1UEERMG&#13;\\nMTEwMDAxMQ4wDAYDVQQIEwVEZWxoaTEbMBkGA1UECRMSQkVISU5EIEtBTEkgTUFORElSMSQwIgYD&#13;\\nVQQzExtBQURIQVIgSFEgQkFOR0xBIFNBSElCIFJPQUQxNzA1BgNVBAMTLkRTIFVOSVFVRSBJREVO&#13;\\nVElGSUNBVElPTiBBVVRIT1JJVFkgT0YgSU5ESUEgMDUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw&#13;\\nggEKAoIBAQCiciwOXy3lunB+2T8DbsKx8LlVkyOQ+swPC8vyDIChXAiLSIaGa3LrJasL9Vov4Gtp&#13;\\n7b1cyDt0x3CdshQebAfGi834WdPa9/P87SQdByBV3BVIhHS0XCyYL6lUqlKqb/+ySBhhxlCF2Etk&#13;\\nFY6fQ9nzXKabSM6TAFIhAqTK4JO//UdLCNMtHQQG9of35VvSJqI4S/WKQcOEw5dPHHxRFYGckm3j&#13;\\nrfPsu5kExIbx9dUwOXe+pjWENnMptcFor9yVEhcx9/SNQ6988x9pseO755Sdx6ixDAvd66ur3r6g&#13;\\ndqHPgWat8GqKQd7fFDv/g129K9W7C2HSRywjSm1EEbybU2CVAgMBAAGjggNnMIIDYzAOBgNVHQ8B&#13;\\nAf8EBAMCBsAwKgYDVR0lBCMwIQYIKwYBBQUHAwQGCisGAQQBgjcKAwwGCSqGSIb3LwEBBTCCAQIG&#13;\\nA1UdIASB+jCB9zCBhgYGYIJkZAICMHwwegYIKwYBBQUHAgIwbgxsQ2xhc3MgMiBjZXJ0aWZpY2F0&#13;\\nZXMgYXJlIHVzZWQgZm9yIGZvcm0gc2lnbmluZywgZm9ybSBhdXRoZW50aWNhdGlvbiBhbmQgc2ln&#13;\\nbmluZyBvdGhlciBsb3cgcmlzayB0cmFuc2FjdGlvbnMuMGwGBmCCZGQKATBiMGAGCCsGAQUFBwIC&#13;\\nMFQMUlRoaXMgY2VydGlmaWNhdGUgcHJvdmlkZXMgaGlnaGVyIGxldmVsIG9mIGFzc3VyYW5jZSBm&#13;\\nb3IgZG9jdW1lbnQgc2lnbmluZyBmdW5jdGlvbi4wDAYDVR0TAQH/BAIwADAjBgNVHREEHDAagRhy&#13;\\nYWh1bC5rdW1hckB1aWRhaS5uZXQuaW4wggFuBgNVHR8EggFlMIIBYTCCAR6gggEaoIIBFqSCARIw&#13;\\nggEOMQswCQYDVQQGEwJJTjFBMD8GA1UEChM4R3VqYXJhdCBOYXJtYWRhIFZhbGxleSBGZXJ0aWxp&#13;\\nemVycyBhbmQgQ2hlbWljYWxzIExpbWl0ZWQxHTAbBgNVBAsTFENlcnRpZnlpbmcgQXV0aG9yaXR5&#13;\\nMQ8wDQYDVQQREwYzODAwNTQxEDAOBgNVBAgTB0d1amFyYXQxJjAkBgNVBAkTHUJvZGFrZGV2LCBT&#13;\\nIEcgUm9hZCwgQWhtZWRhYmFkMRwwGgYDVQQzExMzMDEsIEdORkMgSW5mb3Rvd2VyMSIwIAYDVQQD&#13;\\nExkobilDb2RlIFNvbHV0aW9ucyBDQSAyMDE0MRAwDgYDVQQDEwdDUkw1Njk0MD2gO6A5hjdodHRw&#13;\\nczovL3d3dy5uY29kZXNvbHV0aW9ucy5jb20vcmVwb3NpdG9yeS9uY29kZWNhMTQuY3JsMCsGA1Ud&#13;\\nEAQkMCKADzIwMjEwMjI2MTE1NDI0WoEPMjAyNDAyMjcwMDI3MTFaMBMGA1UdIwQMMAqACE0HvvGe&#13;\\nnfu9MB0GA1UdDgQWBBTpS5Cfqf2zdwqjupLAqMwk/bqX9DAZBgkqhkiG9n0HQQAEDDAKGwRWOC4x&#13;\\nAwIDKDANBgkqhkiG9w0BAQsFAAOCAQEAbTlOC4sonzb44+u5+VZ3wGz3OFg0uJGsufbBu5efh7kO&#13;\\n2DlYnx7okdEfayQQs6AUzDvsH1yBSBjsaZo3fwBgQUIMaNKdKSrRI0eOTDqilizldHqj113f4eUz&#13;\\nU2j4okcNSF7TxQWMjxwyM86QsQ6vxZK7arhBhVjwp443+pxfSIdFUu428K6yH4JBGhZSzWuqD6GN&#13;\\nhOhDzS+sS23MkwHFq0GX4erhVfN/W7XLeSjzF4zmjg+O77vTySCNe2VRYDrfFS8EAOcO4q7szc7+&#13;\\n6xdg8RlgzoZHoRG/GqUp9inpJUn7OIzhHi2e8MllaMdtXo0nbr150tMe8ZSvY2fMiTCY1w==</X509Certificate></X509Data></KeyInfo></Signature></OfflinePaperlessKyc>";
    private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final String ALL_CHARS = ALPHABETS + NUMBERS + SYMBOLS;
private static final String nextdate="2025-05-05T00:00:00";
    private static final SecureRandom random = new SecureRandom();

    public static String generateUniqueString(int length) {
        if (length < 3) {
            throw new IllegalArgumentException("Length must be at least 3 to include all character types.");
        }

        StringBuilder sb = new StringBuilder(length + 4); // extra for "test"

        // Ensure at least one character from each category
        sb.append(randomChar(ALPHABETS));
        sb.append(randomChar(NUMBERS));
        sb.append(randomChar(SYMBOLS));

        // Fill remaining length with random characters from ALL_CHARS
        for (int i = 3; i < length; i++) {
            sb.append(randomChar(ALL_CHARS));
        }

        // Shuffle the characters so the first three are not predictable
        char[] chars = sb.toString().toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Swap chars[i] and chars[j]
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        // Append "test" at the end
        return new String(chars) + "test";
    }

    private static char randomChar(String source) {
        int index = random.nextInt(source.length());
        return source.charAt(index);
    }
//    //private static final String getNextDate(String currentDate, String pattern) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//        LocalDate date = LocalDate.parse(currentDate, formatter);
//        return date.plusDays(1).format(formatter);
//    }

   // static String nextDate = getNextDate("2025/04/24", "yyyy/mm/dd");
    static void sendSlackCIF(String response) {
		System.out.println("Inside sendSlackCIF");
		String webhookUrl = "https://hooks.slack.com/services/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";
		String payload = "{\"channel\": \"#notifications\", \"username\": \"Admin\", \"text\": \""+response+"\"}";

		// Create an HTTP client
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// Create a POST request
			HttpPost post = new HttpPost(webhookUrl);
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");

			// Set the request payload
			StringEntity entity = new StringEntity("payload=" + payload, StandardCharsets.UTF_8);
			post.setEntity(entity);
			// Execute the request
			CloseableHttpResponse r = httpClient.execute(post);

			// Print the response status
			//System.out.println("Response Code: " + r.getCode());

		} catch (Exception e) {
			System.out.println("error inside sendSlackCIF ", e)
			e.printStackTrace();
		}
	}
//    static void sendSlackFile() {
//    	String token = "xoxb-your-slack-bot-token"; // Replace with your Slack Bot Token
//        String channel = "notifications"; // Replace with your channel ID
//        File file = new File("/Partnership/test-output/emailable-report.html"); // Path to your HTML file
//
//        Response response = RestAssured
//            .given()
//            .header("Authorization", "Bearer " + token)
//            .multiPart("file", file)
//            .multiPart("channels", channel)
//            .multiPart("filename", "file.html")
//            .multiPart("title", "My HTML File")
//            .post("https://slack.com/api/files.upload");
//
//        System.out.println(response.asString());						
//    }
    //customer onboarding
    public static String customeronboarding(String partnercodeUC, String UCCustomerid ) {
    	String onbarding="{\r\n"
    			+ "        \"data\" : {\r\n"
    			+ "            \"identifiers\" : {\r\n"
    			+ "                \"customer_id\" : \""+UCCustomerid+"\",\r\n"
    			+ "                \"mobile\" : \"8767678767\"\r\n"
    			+ "            },\r\n"
    			+ "            \"customer_data\" : {\r\n"
    			+ "                \"customer_id\" : \""+UCCustomerid+"\",\r\n"
    			+ "                \"name\" : \"Veer Rai\",\r\n"
    			+ "                \"dob\" : \"1970-01-12\",\r\n"
    			+ "                \"gender\" : \"M\",\r\n"
    			+ "                \"prefix\" : \"MR\",\r\n"
    			+ "                \"primary_mobile\" : {\r\n"
    			+ "                    \"detail\" : \"7479512195\"\r\n"
    			+ "                },\r\n"
    			+ "                \"primary_email\" : {\r\n"
    			+ "                    \"detail\" : \"sdd1v32@gmail.com\"\r\n"
    			+ "                },\r\n"
    			+ "                \"primary_mailing_address\" : {\r\n"
    			+ "                    \"address_line\" : \"S O Dineshbhai Sector 28 K 1 802 1\",\r\n"
    			+ "                    \"landmark\" : \"\",\r\n"
    			+ "                    \"pincode\" : \"382028\",\r\n"
    			+ "                    \"city\" : \"Gandhinagar\",\r\n"
    			+ "                    \"state\" : \"Gujarat\",\r\n"
    			+ "                    \"country\" : \"IN\",\r\n"
    			+ "                    \"full_address\" : \"S O Dineshbhai Sector 28 K 1 802 1,, 382028, Gandhinagar, Gujarat\",\r\n"
    			+ "                    \"ownership\" : \"Owned\"\r\n"
    			+ "                },\r\n"
    			+ "                \"office_address\" : {\r\n"
    			+ "                    \"address1\" : \"S O Dineshbhai Sector 28 K 1 802 1\",\r\n"
    			+ "                    \"address2\" : \"\",\r\n"
    			+ "                    \"city\" : \"Gandhi Nagar\",\r\n"
    			+ "                    \"state\" : \"Gujarat\",\r\n"
    			+ "                    \"pincode\" : \"382028\"\r\n"
    			+ "                },\r\n"
    			+ "                \"business_type\" : 8\r\n"
    			+ "            },\r\n"
    			+ "            \"bankdetails\" : {\r\n"
    			+ "                \"account_name\" : \"Veer Rai\",\r\n"
    			+ "                \"account_number\" : \"50122318964581\",\r\n"
    			+ "                \"account_type\" : \"Savings Account\",\r\n"
    			+ "                \"ifsc\" : \"ICIC0000816\",\r\n"
    			+ "                \"micr\" : \"\"\r\n"
    			+ "            },\r\n"
    			+ "            \"aadhar\" : {\r\n"
    			+ "                \"id\" : \"z20384z2p203z2e4y2w2t2r2\",\r\n"
    			+ "                \"front\" : {\r\n"
    			+ "                    \"url\" : \"\"\r\n"
    			+ "                }\r\n"
    			+ "            },\r\n"
    			+ "            \"pan\" : {\r\n"
    			+ "                \"id\" : \"NGZPS9243H\",\r\n"
    			+ "                \"detail\" : {\r\n"
    			+ "                    \"url\" : \"\"\r\n"
    			+ "                }\r\n"
    			+ "            },\r\n"
    			+ "            \"borrower_photo\" : {\r\n"
    			+ "                \"id\" : \"\",\r\n"
    			+ "                \"detail\" : {\r\n"
    			+ "                    \"url\" : \"\"\r\n"
    			+ "                }\r\n"
    			+ "            }\r\n"
    			+ "        },\r\n"
    			+ "        \"event\" : \"customer_onboarding\",\r\n"
    			+ "        \"partnerCode\" : \""+partnercodeUC+"\"\r\n"
    			+ "    }";
		return onbarding;
    }

    //customer onboarding
    public static String finsallcustomeronboarding(String partnercodeUC, String UCCustomerid ) {
    	String onbarding="{\r\n"
    			+ "    \"data\": {\r\n"
    			+ "        \"identifiers\": {\r\n"
    			+ "            \"customer_id\": \""+UCCustomerid+"\",\r\n"
    			+ "            \"mobile\": \"8530501968\"\r\n"
    			+ "        },\r\n"
    			+ "        \"customer_data\": {\r\n"
    			+ "            \"name\": \"Veer Anil Kambale\",\r\n"
    			+ "            \"dob\": \"1995-08-15\",\r\n"
    			+ "            \"gender\": \"M\",\r\n"
    			+ "            \"prefix\": \"MR\",\r\n"
    			+ "            \"primary_mobile\": {\r\n"
    			+ "                \"detail\": \"8530501968\"\r\n"
    			+ "            },\r\n"
    			+ "            \"primary_email\": {\r\n"
    			+ "                \"detail\": \"veerkamble11@gmail.com\"\r\n"
    			+ "            },\r\n"
    			+ "            \"primary_mailing_address\": {\r\n"
    			+ "                \"address_line\": \"Juna Bail Bazar\",\r\n"
    			+ "                \"pincode\": \"414403\",\r\n"
    			+ "                \"city\": \"Rashin\",\r\n"
    			+ "                \"state\": \"Maharashtra\",\r\n"
    			+ "                \"country\": \"India\",\r\n"
    			+ "                \"full_address\": \"  juna bail bazar ward 6, Rashin,Karjat, Rashin,Ahmadnagar,Maharashtra, India,414403 \"\r\n"
    			+ "            },\r\n"
    			+ "            \"business_type\": 8\r\n"
    			+ "        },\r\n"
    			+ "        \"bankdetails\": {\r\n"
    			+ "            \"account_name\": \"VEER ANIL KAMBALE\",\r\n"
    			+ "            \"account_number\": \"364202010125840\",\r\n"
    			+ "            \"account_type\": \"SAVINGS\",\r\n"
    			+ "            \"ifsc\": \"UBIN0536423\"\r\n"
    			+ "        },\r\n"
    			+ "        \"pan\": {\r\n"
    			+ "            \"id\": \"JRMPK7838G\"\r\n"
    			+ "        },\r\n"
    		    + "        \"xmlData\": {\r\n"
    		    + "          \"aadharXML\": \""+aadhaarxml+"\"\r\n"
    		    + "        }\r\n"
    			+ "    },\r\n"
    			+ "    \"event\": \"customer_onboarding\",\r\n"
    			+ "    \"partnerCode\": \""+partnercodeUC+"\"\r\n"
    			+ "}";
		return onbarding;
    }

    //doc upload
    public static String docupload(String cif,String base64, String partnercodeUC) {
    	String docuploadrequest="{\r\n"
    			+ "        \"data\": {\r\n"
    			+ "            \"cif\": \""+cif+"\",\r\n"
    			+ "            \"pan\": {\r\n"
    			+ "                \"id\": \"JRMPK7838G\",\r\n"
    			+ "                \"detail\": {\"BASE64STRING\": \""+base64+"\"}\r\n"
    			+ "            },\r\n"
    			+ "            \"aadhar\": {\r\n"
    			+ "                \"id\": \"XXXXXXXX9432\",\r\n"
    			+ "                \"front\":{\"BASE64STRING\":\""+base64+"\"},\r\n"
    			+ "                \"back\": {\"BASE64STRING\":\""+base64+"\"}\r\n"
    			+ "            },\r\n"
    			+ "            \"borrower_photo\": {\r\n"
    			+ "                \"detail\": {\"BASE64STRING\":\""+base64+"\"}\r\n"
    			+ "            },\r\n"
    			+ "            \"bureau_report\": {\r\n"
    			+ "                \"detail\": {\"BASE64STRING\":\""+base64+"\"}\r\n"
    			+ "            }\r\n"
    			+ "        },\r\n"
    			+ "        \"event\": \"customer_onboarding\",\r\n"
    			+ "        \"partnerCode\": \""+partnercodeUC+"\",\r\n"
    			+ "        \"NiyoLeadID\": \"29073ca9-0981-4ea4-814d-8c46005b6058\"\r\n"
    			+ "    }";
		return docuploadrequest;
    }
    
    //create loan
    public static String createloan(String UCapplicantid, String UCCustomerid, String partnercodeUC) {
    	String createloan="{\r\n"
    			+ "        \"data\" : {\r\n"
    			+ "            \"loan_data\" : {\r\n"
    			+ "                \"loan_amount\" : 30000,\r\n"
    			+ "                \"tenure\" : 5,\r\n"
    			+ "                \"roi\" : 16,\r\n"
    			+ "                \"processing_fee\" : \"\",\r\n"
    			+ "                \"other_fee\" : \"\",\r\n"
    			+ "                \"nextRepayDate\" : \"\",\r\n"
    			+ "                \"InterestFrequency\" : \"\"\r\n"
    			+ "            },\r\n"
    			+ "            \"loan_account\" : \""+UCapplicantid+"\",\r\n"
    			+ "            \"identifiers\" : {\r\n"
    			+ "                \"application_id\" : \""+UCapplicantid+"\",\r\n"
    			+ "                \"customer_id\" : \""+UCCustomerid+"\"\r\n"
    			+ "            },\r\n"
    			+ "            \"extended_field\" : {}\r\n"
    			+ "        },\r\n"
    			+ "        \"event\" : \"create_loan\",\r\n"
    			+ "        \"partnerCode\": \""+partnercodeUC+"\"\r\n"
    			+ "    } ";
		return createloan;
    }
    
    //create loan
    public static String finsallcreateloan(String UCapplicantid, String UCCustomerid, String partnercodeUC) {
    	String createloan="{\r\n"
    			+ "        \"data\": {\r\n"
    			+ "            \"identifiers\": {\r\n"
    			+ "                \"customer_id\": \""+UCCustomerid+"\",\r\n"
    			+ "                \"application_id\": \""+UCapplicantid+"\",\r\n"
    			+ "                \"mobile\": \"7096651503\"\r\n"
    			+ "            },\r\n"
    			+ "            \"bankdetails\": {\r\n"
    			+ "                \"account_name\": \"ABHICL  Online Collection\",\r\n"
    			+ "                \"account_number\": \"00600310040545\",\r\n"
    			+ "                \"account_type\": \"Current\",\r\n"
    			+ "                \"ifsc\": \"HDFC0000060\"\r\n"
    			+ "            },\r\n"
    			+ "            \"loan_data\": {\r\n"
    			+ "                \"loan_amount\": 38803,\r\n"
    			+ "                \"tenure\": \"9\",\r\n"
    			+ "                \"roi\": \"21.13\",\r\n"
    			+ "                \"processing_fee\": \"0\",\r\n"
    			+ "                \"nextRepayDate\": \""+nextdate+"\",\r\n"
    			+ "                \"InterestFrequency\": \"Monthly\",\r\n"
    			+ "                \"down_payment\": \"5820.0\",\r\n"
    			+ "                \"loan_date\": \"08-04-2025\"\r\n"
    			+ "            },\r\n"
    			+ "            \"extended_field\": {\r\n"
    			+ "                \"NTCFINSALL\": \"ETC\",\r\n"
    			+ "                \"CREDITPOLICY\": \"P1\",\r\n"
    			+ "                \"INSURANCETYPE\": \"Health Multi-year\",\r\n"
    			+ "                \"INSUCOMPNAME\": \"Aditya Birla\",\r\n"
    			+ "                \"POLICYTENURE\": 2,\r\n"
    			+ "                \"POLICYREFNO\": \"PROP-976817248459368\",\r\n"
    			+ "                \"CUSTSEGMENT\": \"Self Employed\",\r\n"
    			+ "                \"LOCATIONS\": \"Vatva\",\r\n"
    			+ "                \"PREMAMT\": 38803,\r\n"
    			+ "                \"CUSTMARAMT\": 5820,\r\n"
    			+ "                \"BUREAUSCO\": 755,\r\n"
    			+ "                \"DPDINLST3MNT\": 0,\r\n"
    			+ "                \"PLUS30INLST12\": 0,\r\n"
    			+ "                \"PLUS60INLST24\": 0,\r\n"
    			+ "                \"WRITTENOFF\": 0,\r\n"
    			+ "                \"WILFULDEFAULTER\": 0,\r\n"
    			+ "                \"INCOMEAMTFIN\": 94591,\r\n"
    			+ "                \"EMIO\": 24693,\r\n"
    			+ "                \"INCOELIGIBILITY\": 1,\r\n"
    			+ "                \"LOANELIGIBILITY\": 32983,\r\n"
    			+ "                \"CASHFREEID\": \"17049866\",\r\n"
    			+ "                \"PROCFEEGSTINC\": \"873.00\"\r\n"
    			+ "            }\r\n"
    			+ "        },\r\n"
    			+ "        \"event\": \"create_loan\",\r\n"
    			+ "        \"partnerCode\": \""+partnercodeUC+"\"\r\n"
    			+ "    }\r\n"
    			+ "\r\n"
    			+ " ";
		return createloan;
    }
//    
 
    //loan doc upload
    public static String loandocupload(String cif,String base64, String partnercodeUC) {
    	String docuploadrequest="{\r\n"
    			+ "        \"data\": {\r\n"
    			+ "            \"cif\": \""+cif+"\",\r\n"
    			+ "            \"loan_agreement\": {\r\n"
    			+ "                \"id\": \"JRMPK7838G\",\r\n"
    			+ "                \"detail\": {\"BASE64STRING\": \""+base64+"\"}\r\n"
    			+ "            },\r\n"
    			+ "            \"KFS\": {\r\n"
    			+ "                \"detail\": {\"BASE64STRING\":\""+base64+"\"}\r\n"
    			+ "            }\r\n"
    			+ "        },\r\n"
    			+ "        \"event\": \"customer_onboarding\",\r\n"
    			+ "        \"partnerCode\": \""+partnercodeUC+"\",\r\n"
    			+ "        \"NiyoLeadID\": \"29073ca9-0981-4ea4-814d-8c46005b6058\"\r\n"
    			+ "    }";
		return docuploadrequest;
    }
    
    //Status
    public static String status(String lrn, String partnercodeUC) {
    	String statusrequest="{\r\n"
    			+ "    \"event\": \"status\",\r\n"
    			+ "    \"partnerCode\": \""+partnercodeUC+"\",\r\n"
    			+ "    \"data\": {\r\n"
    			+ "        \"identifiers\": {\r\n"
    			+ "            \"lrn\": \""+lrn+"\"\r\n"
    			+ "        }\r\n"
    			+ "    }\r\n"
    			+ "}";
		return statusrequest;
    }
    //Repayment
    public static String repayment() {
    	String repaymentrequest="";
		return repaymentrequest;
    }
    
    //earlysettlementresponse
    public static String earlysettlement() {
    	String earlysettlement="";
		return earlysettlement;
    }
    
    //status
    public static String statusf() {
    	String Status="";
		return Status;
    }
}
