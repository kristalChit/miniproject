package sg.edu.nus.iss.miniproject.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.miniproject.model.Recipe;
import sg.edu.nus.iss.miniproject.model.User;

@Repository
public class UserRepository {

    
    @Autowired@Qualifier("redis")
    private RedisTemplate<String, String> redisTemplate;

    public Optional<String> checkUser(String email) {
        if (redisTemplate.hasKey(email)) {
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            String result = valueOps.get(email);
            return Optional.of(result);
        }
        return Optional.empty();
    }

    public boolean userExists(String email) {
        System.out.println( redisTemplate.hasKey(email));
        return redisTemplate.hasKey(email);
    }

    public void saveUser(User user) {
        String email = user.getEmail();
        String password = user.getPassword(); 
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

        JsonObject userJson = Json.createObjectBuilder()
                .add("email", email)
                .add("password", password)
                .build();

        valueOps.set(email, userJson.toString());
    }

   public Boolean saveFavourites(String email, List<String> idsToSave) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String userFavorites = valueOps.get(email);

        if (userFavorites != null) {
            // Append the new recipe IDs to the existing favorites
            userFavorites += "," + String.join(",", idsToSave);
        } else {
            // Create a new list with the first favorite recipe IDs
            userFavorites = String.join(",", idsToSave);
        }

        // Save the updated favorites back to Redis
        valueOps.set(email, userFavorites);

        return true;
    }

    public List<String> getFavourites(String email) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String userFavorites = valueOps.get(email);

        if (userFavorites != null) {
            // Split the comma-separated IDs into a list
            return Arrays.asList(userFavorites.split(","));
        } else {
            return Collections.emptyList();
        }
    }

    public Recipe searchRecipestepsById(String idMeal) {
        // Implement logic to retrieve a recipe by its ID
        // This could involve querying an external API or your database
        return null; // Placeholder
    }

    public Recipe searchRecipestepsByName(String strMeal) {
        // Implement logic to retrieve recipes based on the search string
        // This could involve querying an external API or your database
        return null; // Placeholder
    } 
}
