package testPartnership.id;

import java.security.SecureRandom;

public class common {

    private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final String ALL_CHARS = ALPHABETS + NUMBERS + SYMBOLS;

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
    			+ "            }\r\n"
    			+ "        },\r\n"
    			+ "        \"event\": \"customer_onboarding\",\r\n"
    			+ "        \"partnerCode\": \""+partnercodeUC+"\",\r\n"
    			+ "        \"NiyoLeadID\": \"29073ca9-0981-4ea4-814d-8c46005b6058\"\r\n"
    			+ "    }";
		return docuploadrequest;
    }
    
    //create loan

}
