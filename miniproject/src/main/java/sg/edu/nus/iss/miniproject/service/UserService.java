package sg.edu.nus.iss.miniproject.service;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.miniproject.model.User;
import sg.edu.nus.iss.miniproject.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public boolean existingUser(String email) {
        System.out.println(email);
        return userRepository.userExists(email);
    }

    public User checkUser(String email) {
        Optional<String> opt = userRepository.checkUser(email);
        if (opt.isEmpty()) {
            return null;
        }

        String value = opt.get();

        StringReader stringReader = new StringReader(value);
        JsonReader jsonReader = Json.createReader(stringReader);

        JsonObject jObject = jsonReader.readObject();
        return User.create(jObject);
    }

    public boolean saveUser(User user) {
        userRepository.saveUser(user);
        return true;
    }
}

 /* public List<String> favorites(String email, List<String> s) {
        return userRepository.favourites(email, s);
    }

    public void saveUser(User user) {
    }
 */