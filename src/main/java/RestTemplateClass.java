import model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestTemplateClass {
    private static final RestTemplate restTemplate = new RestTemplate();
   private static final String URL_API = "http://94.198.50.185:7081/api/users";
   private static String COOKIE;
   private static HttpHeaders headers = new HttpHeaders();
   private static User user;

    public static void main(String[] args) {
        getAllUsers();
        createUser();
        getAllUsersWithSession();
        updateUser();
        getAllUsersWithSession();
        deleteUser();
        getAllUsersWithSession();
    }

    public static void getAllUsers(){
        ResponseEntity<List<User>> response = restTemplate.exchange(URL_API,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
        });
        System.out.println(response);
        COOKIE = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        //System.out.println("Cookie: " + SESSION_ID);
    }

    public static void getAllUsersWithSession(){
       HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List<User>> response = restTemplate.exchange(URL_API,
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<User>>() {
                });
        System.out.println(response);
    }
    public static void createUser(){
        user = new User(3L, "James", "Brown", (byte) 25);
        headers.add(HttpHeaders.COOKIE, COOKIE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> postRequest = new HttpEntity<>(user, headers);
        restTemplate.exchange(URL_API, HttpMethod.POST, postRequest, String.class);
    }

    public static void updateUser(){
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> putRequest = new HttpEntity<>(user, headers);
        restTemplate.exchange(URL_API, HttpMethod.PUT, putRequest, String.class);
    }

    public static void deleteUser(){
        HttpEntity<User> deleteRequest = new HttpEntity<>(user, headers);
        restTemplate.exchange(URL_API + "/" + user.getId(), HttpMethod.DELETE, deleteRequest, Void.class);
    }


}
