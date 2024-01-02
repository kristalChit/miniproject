package sg.edu.nus.iss.miniproject.model;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Email(message = "Invalid Email Format")
    @Size(max = 30, message = "Email length exceeded 30 characters")
    @NotBlank(message = "Email is a mandatory field")
    private String email;

    @NotEmpty(message = "Password is a mandatory field")
    @Size(min = 3, max = 20, message = "Password must be between 3 to 20 characters")
    private String password;

    private List<String> favourites;

    public static User create(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static User create(JsonObject jo) {
        User user = new User();
        user.setEmail(jo.getString("email"));
        user.setPassword(jo.getString("password"));

        return user;
    }

   public JsonObject toJson() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String favorite : favourites) {
            arrayBuilder.add(favorite);
        }

        return Json.createObjectBuilder()
                .add("email", email)
                .add("password", password)
                .add("favourites", arrayBuilder)
                .build();
    }

    public List<String> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<String> favourites) {
        this.favourites = favourites;
    }
}
