package utils;

public class PayloadUtils {
    public static String getPetPayload(int petId,String petName,String petStatus){
        String payload="{\n" +
                "  \"id\": "+petId+",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \""+petName+"\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \""+petStatus+"\"\n" +
                "}";
        return payload;

    }
}
