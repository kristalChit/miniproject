package sg.edu.nus.iss.miniproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.nus.iss.miniproject.model.Recipe;
import sg.edu.nus.iss.miniproject.model.RecipeSteps;
import sg.edu.nus.iss.miniproject.model.User;
import sg.edu.nus.iss.miniproject.repository.UserRepository;
import sg.edu.nus.iss.miniproject.service.RecipeService;
import sg.edu.nus.iss.miniproject.service.UserService;

@Controller
@RequestMapping
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        return "login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody MultiValueMap<String, String> form, Model model) {
        String email = form.getFirst("email").toLowerCase();
        String password = form.getFirst("password");

        if (!userService.existingUser(email)) {
            return "register";
        }

        User user = userService.checkUser(email);

        if (password.equals(user.getPassword())) {
            model.addAttribute("email", email);
            return "home";
        } else {
            return "register";
        }
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistration(@RequestBody MultiValueMap<String, String> form,
            Model model) {

        String email = form.getFirst("email").toLowerCase();
        String password = form.getFirst("password");

        System.out.println("email >>>>>>>>>>>>>>>>>>>" + email);
        System.out.println("password >>>>>>>>>>>>>>>>>>>" + password);

        if (!userService.existingUser(email)) {
            User user = User.create(email, password);
            userService.saveUser(user);

            model.addAttribute("username", email);
            return "success";
        } else {
            return "register";
        }
    }

    @GetMapping("/recipes")
    public String getRecipes(@RequestParam("email") String userEmail, Model model) throws IOException {
        ArrayList<RecipeSteps> recipes = recipeService.getRecipes();
        model.addAttribute("email", recipes);
        return "recipes";
    }

    @GetMapping("/search")
    public String getSearch(Model model)
            throws IOException {

        // Recipe recipe = recipeService.getSearch();
        // model.addAttribute("recipe", recipe);

        // System.out.println(search);
        return "search";
    }

    @PostMapping("/search")
    public String getSearchResults(@RequestParam("query") String query, Model model) {
        model.addAttribute("recipeList", recipeService.searchForRecipes(query));
        return "searchResults";
    }

    @GetMapping("/recipesteps")
    public String getRecipeSteps(@RequestParam("email") String userEmail, Model model) {

        Recipe recipe = recipeService.getRecipesteps();

        model.addAttribute("email", userEmail);
        model.addAttribute("recipe", recipe);

        // System.out.println(recipesteps);
        return "recipesteps";
    }
    
    @PostMapping("/favourites")
    public String saveFavourites(@RequestParam("favourites") List<String>
    favourites,
    
    @RequestParam("email") String userEmail,
    Model model) {
        User user = userService.checkUser(userEmail);
        if (user != null) {
            user.setFavourites(favourites);
            userService.saveUser(user);
            model.addAttribute("favorites", user.getFavourites());
            return "favorites";
        } else {
            return "noFavoritesAvailable";
        }
    }

    @GetMapping("/recipe/{recipeId}")
    public String getRecipeByID(@PathVariable("recipeId") String recipeId, Model model) {

        // take recipeId from URL and call another API
        // return recipe steps to user
        // maybe also return ingredients needed
        // add to model
        // show on HTML page

        // TODO: 
        // show apppropriate message when ID is not found

        model.addAttribute("recipeInfo", recipeService.searchForRecipeStepsById(recipeId));
        return "recipe";
    }
}
