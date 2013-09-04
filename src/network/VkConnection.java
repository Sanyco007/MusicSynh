package network;

public class VkConnection {

    private String accessToken;
    private String request = "https://api.vk.com/method/%s?%s&access_token=%s";

    public VkConnection(String token) {
        accessToken = token;
    }

    public void sendApiRequest() {

    }

}
