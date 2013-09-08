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

    static String getAuthLink() {
        return String.format(patternString, appId, scope, redirectUri, display, apiVersion, responseType);
    }

    static String accessToken;
    static String userId;
    static long expireOver;

    public static void initAuth(String token, String id, long expire) {
        accessToken = token;
        userId = id;
        expireOver = expire;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getUserId() {
        return userId;
    }

    public static long getExpireOver() {
        return expireOver;
    }


}
