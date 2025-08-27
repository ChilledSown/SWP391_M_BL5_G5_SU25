package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GeminiUtil {
    private static final String GEMINI_API_KEY = "AIzaSyA1xBlrucFYSI2K6WN6dUZL0ZsVFVmHCWc";
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent";
    
    public static String generateResponse(String prompt) {
        try {
            String jsonBody = "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"text\": \"" + escapeJson(prompt) + "\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
            
            String fullUrl = GEMINI_API_URL + "?key=" + GEMINI_API_KEY;
            
            // Create connection
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(30000); // 30 seconds
            connection.setReadTimeout(30000); // 30 seconds
            
            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = connection.getResponseCode();
            
            // Read response
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream(), 
                    StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine);
                }
            }
            
            String responseString = response.toString();
            
            if (responseCode >= 400) {
                return "Sorry, I'm having trouble connecting to the AI service right now. Please try again later.";
            }
            
            // Check if response is HTML (login page) instead of JSON
            if (responseString.trim().startsWith("<!doctype html>") || responseString.trim().startsWith("<html")) {
                return "Sorry, there's an issue with the AI service authentication. Please contact support.";
            }
            
            // Parse response to get text
            String responseText = parseGeminiResponse(responseString);
            if (responseText != null && !responseText.trim().isEmpty()) {
                return responseText;
            } else {
                return "Sorry, I received an empty response from the AI service. Please try again.";
            }
            
        } catch (Exception e) {
            return "Sorry, I encountered an error while processing your request. Please try again later.";
        }
    }
    
    private static String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    private static String parseGeminiResponse(String jsonResponse) {
        try {
            // Look for the text field in the response
            int textIndex = jsonResponse.indexOf("\"text\": \"");
            if (textIndex != -1) {
                int startIndex = textIndex + 9;
                // Find the closing quote, but handle escaped quotes properly
                int endIndex = findClosingQuote(jsonResponse, startIndex);
                if (endIndex != -1) {
                    String extractedText = jsonResponse.substring(startIndex, endIndex)
                                     .replace("\\n", "\n")
                                     .replace("\\\"", "\"")
                                     .replace("\\\\", "\\");
                    
                    // Clean up Unicode escapes and markdown
                    extractedText = cleanResponseText(extractedText);
                    
                    return extractedText;
                }
            }
            
            // If the above method fails, try alternative parsing
            int candidatesIndex = jsonResponse.indexOf("\"candidates\":");
            if (candidatesIndex != -1) {
                int contentIndex = jsonResponse.indexOf("\"content\":", candidatesIndex);
                if (contentIndex != -1) {
                    int partsIndex = jsonResponse.indexOf("\"parts\":", contentIndex);
                    if (partsIndex != -1) {
                        int textIndex2 = jsonResponse.indexOf("\"text\": \"", partsIndex);
                        if (textIndex2 != -1) {
                            int startIndex2 = textIndex2 + 9;
                            int endIndex2 = findClosingQuote(jsonResponse, startIndex2);
                            if (endIndex2 != -1) {
                                String extractedText2 = jsonResponse.substring(startIndex2, endIndex2)
                                                 .replace("\\n", "\n")
                                                 .replace("\\\"", "\"")
                                                 .replace("\\\\", "\\");
                                
                                // Clean up Unicode escapes and markdown
                                extractedText2 = cleanResponseText(extractedText2);
                                
                                return extractedText2;
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            // Silent fail
        }
        return null;
    }
    
    private static int findClosingQuote(String json, int startIndex) {
        boolean escaped = false;
        for (int i = startIndex; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (c == '"') {
                return i;
            }
        }
        return -1;
    }
    

    
    private static String cleanResponseText(String text) {
        if (text == null) return "";
        
        // Replace Unicode escapes
        text = text.replace("\\u003c", "<")
                  .replace("\\u003e", ">")
                  .replace("\\u003d", "=")
                  .replace("\\u0026", "&")
                  .replace("\\u0027", "'")
                  .replace("\\u0022", "\"")
                  .replace("\\u0020", " ")
                  .replace("\\u0021", "!")
                  .replace("\\u0023", "#")
                  .replace("\\u0024", "$")
                  .replace("\\u0025", "%")
                  .replace("\\u0028", "(")
                  .replace("\\u0029", ")")
                  .replace("\\u002a", "*")
                  .replace("\\u002b", "+")
                  .replace("\\u002c", ",")
                  .replace("\\u002d", "-")
                  .replace("\\u002e", ".")
                  .replace("\\u002f", "/")
                  .replace("\\u003a", ":")
                  .replace("\\u003b", ";")
                  .replace("\\u003f", "?")
                  .replace("\\u0040", "@")
                  .replace("\\u005b", "[")
                  .replace("\\u005c", "\\")
                  .replace("\\u005d", "]")
                  .replace("\\u005e", "^")
                  .replace("\\u005f", "_")
                  .replace("\\u0060", "`")
                  .replace("\\u007b", "{")
                  .replace("\\u007c", "|")
                  .replace("\\u007d", "}")
                  .replace("\\u007e", "~");
        
        // Remove markdown formatting
        text = text.replace("**", "")  // Remove bold
                  .replace("*", "")    // Remove italic
                  .replace("### ", "") // Remove heading markers
                  .replace("## ", "")
                  .replace("# ", "")
                  .replace("---", "")  // Remove horizontal rules
                  .replace("```", "")  // Remove code blocks
                  .replace("`", "");   // Remove inline code
        
        // Clean up extra whitespace
        text = text.replaceAll("\\s+", " ").trim();
        
        return text;
    }
    

}
