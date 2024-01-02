package sg.edu.nus.iss.miniproject.model;

import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String id;
    private String meal;
    private String mealThumb;

    public static Recipe create(JsonObject jo) {

        Recipe recipe = new Recipe();
        recipe.setId(jo.getString("idMeal"));
        recipe.setMeal(jo.getString("strMeal"));
        recipe.setMealThumb(jo.getString("strMealThumb"));

        return recipe;
    }

    public void setCuisine(String cuisine) {
    }

    public void setRecipesteps(String recipesteps) {
    }

    public void setYoutube(String youtube) {
    }

    public void setIngredient(String ingredient) {
    }

    public void setMeasure(String measure) {
    }

    public static void add(Recipe recipe) {
    }
}

