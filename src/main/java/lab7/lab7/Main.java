package lab7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Get all recipes that can be made in 15 minutes or less
     * @param dataService a service that provides access to recipe data
     * @return a list of quick recipes
     */
    public static List<lab7.Recipe> getQuickRecipes(lab7.DataService dataService) {
        try {
            var recipes = dataService.getRecipes();
            return recipes.stream().filter( r -> r.totalTime() <= 15 ).toList();
        } catch(Exception e ) {
            logger.error("Error while getting quick recipes: " + e.getMessage());
            logger.debug("Stack trace: " + Arrays.toString(e.getStackTrace()));
            return List.of();
        }
    }

    // TODO: implement the searchRecipes method
    public static List<lab7.Recipe> searchRecipes(String term, lab7.DataService dataService) {
        if (dataService == null) {
            logger.error("DataService was null");
            return List.of();
        }
        if (term == null || term.isBlank()) {
            return List.of();
        }
        var needle = term.toLowerCase();

        try {
            var recipes = dataService.getRecipes();
            if (recipes == null) return List.of();

            return recipes.stream()
                    .filter(r -> {
                        if (r == null) return false;
                        var name = r.name() == null ? "" : r.name().toLowerCase();
                        var desc = r.description() == null ? "" : r.description().toLowerCase();
                        return name.contains(needle) || desc.contains(needle);
                    })
                    .toList();

        } catch (Exception e) {
            logger.error("Error while searching recipes: " + e.getMessage());
            logger.debug("Stack trace: " + Arrays.toString(e.getStackTrace()));
            return List.of();
        }
    }

    public static void main(String[] args) {

        var quickRecipes = getQuickRecipes(new lab7.SqliteDataService());
        System.out.println("Quick Recipes:");
        quickRecipes.forEach(System.out::println);

        // TODO: use your searchRecipes method with a SqliteDataService object

        var searchResults = searchRecipes("chicken", new lab7.SqliteDataService());
        System.out.println("\nSearch Results for 'chicken':");
        searchResults.forEach(System.out::println);
    }
}

