package sg.edu.nus.iss.miniproject.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.miniproject.model.Meal;
import sg.edu.nus.iss.miniproject.model.Meals;
import sg.edu.nus.iss.miniproject.model.Recipe;
import sg.edu.nus.iss.miniproject.model.RecipeSteps;
import sg.edu.nus.iss.miniproject.repository.UserRepository;

@Service
public class RecipeService {

    @Autowired
    private UserRepository userRepository;

    private RestTemplate template = new RestTemplate();

    public ArrayList<RecipeSteps> getRecipes() throws IOException {
        ArrayList<RecipeSteps> listOfRecipes = new ArrayList<>();

        String url = "https://themealdb.com/api/json/v1/1/filter.php?i=";

        /*
         * final String uri = "www.themealdb.com/api/json/v1/1/filter.php";
         * 
         * String url = UriComponentsBuilder.fromUriString(uri).queryParam("i", "")
         * .toUriString();
         */

        String payload;

        RequestEntity<Void> req = RequestEntity.get(url).build();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray array = reader.readObject().getJsonArray("meals");

        for (JsonValue value : array) {
            JsonObject obj = value.asJsonObject();
            String meal = obj.getString("strMeal", "");
            String mealThumb = obj.getString("strMealThumb", "");
            String id = obj.getString("idMeal", "");

            RecipeSteps recipe = new RecipeSteps();
            recipe.setMeal(meal);
            recipe.setMealThumb(mealThumb);
            recipe.setId(id);
            listOfRecipes.add(recipe);
        }

        return listOfRecipes;
    }

    public List<Meal> searchForRecipes(String query) {
        
        String apiUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + query;
        ResponseEntity<Meals> response = template.getForEntity(apiUrl, Meals.class);

        Meals listOfMeals = response.getBody();
        List<Meal> mealList = listOfMeals.getMeals();

        return mealList;
    }

    public Meal searchForRecipeStepsById(String recipeId) {

        String apiUrl = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + recipeId;
        ResponseEntity<Meals> response = template.getForEntity(apiUrl, Meals.class);

        Meal foundMeal = response.getBody().getMeals().get(0);

        return foundMeal;
    }

    public Boolean saveFavorites(String user, List<Recipe> recipes) {
        List<String> idsToSave = new ArrayList<>();
	    for (Recipe recipe : recipes) {
		String id = recipe.getId();
		idsToSave.add(id);
	}

        Boolean isSavedSuccessfully = userRepository.saveFavourites(user, idsToSave);
        return isSavedSuccessfully;
    }

    public Recipe searchRecipestepsById(String idMeal) {

        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

        String payload;

        RequestEntity<Void> req = RequestEntity.get(url).build();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray array = reader.readObject().getJsonArray("meals");

        for (JsonValue value : array) {
            JsonObject obj = value.asJsonObject();
            String meal = obj.getString("strMeal", "");
            String mealThumb = obj.getString("strMealThumb", "");
            String id = obj.getString("idMeal", "");
            String cuisine = obj.getString("strArea", "");
            String recipesteps = obj.getString("strInstruction", "");
            String youtube = obj.getString("strYoutube", "");
            String ingredient = obj.getString("strIngredient", "");
            String measure = obj.getString("strMeasure", "");

            Recipe recipe = new Recipe();
            recipe.setMeal(meal);
            recipe.setMealThumb(mealThumb);
            recipe.setId(id);
            recipe.setCuisine(cuisine);
            recipe.setRecipesteps(recipesteps);
            recipe.setYoutube(youtube);
            recipe.setIngredient(ingredient);
            recipe.setMeasure(measure);
        }

        return userRepository.searchRecipestepsById(idMeal);
    }

    public Recipe searchRecipestepsByName(String strMeal) {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

        String payload;

        RequestEntity<Void> req = RequestEntity.get(url).build();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray array = reader.readObject().getJsonArray("meals");

        for (JsonValue value : array) {
            JsonObject obj = value.asJsonObject();
            String meal = obj.getString("strMeal", "");
            String mealThumb = obj.getString("strMealThumb", "");
            String id = obj.getString("idMeal", "");
            String cuisine = obj.getString("strArea", "");
            String recipesteps = obj.getString("strInstruction", "");
            String youtube = obj.getString("strYoutube", "");
            String ingredient = obj.getString("strIngredient", "");
            String measure = obj.getString("strMeasure", "");

            Recipe recipe = new Recipe();
            recipe.setMeal(meal);
            recipe.setMealThumb(mealThumb);
            recipe.setId(id);
            recipe.setCuisine(cuisine);
            recipe.setRecipesteps(recipesteps);
            recipe.setYoutube(youtube);
            recipe.setIngredient(ingredient);
            recipe.setMeasure(measure);
        }

        return userRepository.searchRecipestepsByName(strMeal);
    }

    public List<Recipe> getFavorites(String user) {
        List<String> idsRetrieved = userRepository.getFavourites(user);
        List<Recipe> recipesRetrieved = new ArrayList<Recipe>();
        for (String id: idsRetrieved) {
            Recipe newRecipe = searchRecipestepsById(id);
            recipesRetrieved.add(newRecipe);
        }
        return recipesRetrieved;
    }

    public Recipe getSearch() {
        return null;
    }

    public Recipe getRecipesteps() {
        return null;
        }
    }
