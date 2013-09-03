package auth;

public class AuthProfile {

    private static String appId = "3858796";
    private static String scope = "friends,audio";
    private static String redirectUri = "https://oauth.vk.com/blank.html";
    private static String display = "popup";
    private static String apiVersion = "5.0";
    private static String responseType = "token";

    private static String patternString = "https://oauth.vk.com/authorize?" +
            "client_id=%s&" +
            "scope=%s&" +
            "redirect_uri=%s&" +
            "display=%s&" +
            "v=%s&" +
            "response_type=%s";

    public static String getAuthLink() {
        return String.format(patternString, appId, scope, redirectUri, display, apiVersion, responseType);
    }

}
