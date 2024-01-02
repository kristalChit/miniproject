package sg.edu.nus.iss.miniproject.model;

import java.util.HashMap;
import java.util.Map;

import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSteps {
    private String id;
    private String meal;
    private String category;
    private String cuisine;
    private String instructions;
    private String mealThumb;
    private String youtube;
    private Map<String, String> ingredients;
    /*
     * private List<String> ingredient;
     * private String measure;
     */

    public static RecipeSteps create(JsonObject jo) {

        RecipeSteps recipe = new RecipeSteps();
        recipe.setId(jo.getString("idMeal"));
        recipe.setMeal(jo.getString("strMeal"));
        recipe.setCategory(jo.getString("strCategory"));
        recipe.setCuisine(jo.getString("strArea"));
        recipe.setInstructions(jo.getString("strInstructions"));
        recipe.setMealThumb(jo.getString("strMealThumb"));
        recipe.setYoutube(jo.getString("strYoutube"));

        Map<String, String> newIngredients = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            String ingredient = jo.getString("strIngredient" + i);
            String measure = jo.getString("strMeasure" + i);
            newIngredients.put(ingredient, measure);
        }

        recipe.setIngredients(newIngredients);
        return recipe;
    }
}