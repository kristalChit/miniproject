package sg.edu.nus.iss.miniproject.model;

import java.util.List;

public class Meals {

    private List<Meal> meals;

    public Meals() {
    }

    public Meals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Meals [meals=" + meals + "]";
    }

}
