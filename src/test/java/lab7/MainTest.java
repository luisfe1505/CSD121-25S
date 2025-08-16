package lab7;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testGetQuickRecipesReturnsEmptyListIfNoData() {
        var recipes = lab7.Main.getQuickRecipes(List::of);
        assertEquals(0, recipes.size());
    }

    @Test
    void testGetQuickRecipesReturnsEmptyListIfNoQuickRecipes() {
        var recipes = lab7.Main.getQuickRecipes(() -> List.of(
                new lab7.Recipe(0, "", "", "", 4, 10, 10, 16),
                new lab7.Recipe(1, "", "", "", 4, 10, 10, 20),
                new lab7.Recipe(2, "", "", "", 4, 10, 10, 200)
        ));
        assertEquals(0, recipes.size());
    }

    @Test
    void testGetQuickRecipesReturnsAllRecipesIfAllQuick() {

        var recipes = lab7.Main.getQuickRecipes(() -> List.of(
                new lab7.Recipe(0, "", "", "", 4, 10, 10, 15),
                new lab7.Recipe(1, "", "", "", 4, 10, 10, 1),
                new lab7.Recipe(2, "", "", "", 4, 10, 10, 10)
        ));

        assertEquals(3, recipes.size());
    }

    @Test
    void testGetQuickRecipesWorksOnTypicalData() {

        var recipes = lab7.Main.getQuickRecipes(() -> List.of(
                new lab7.Recipe(0, "", "", "", 4, 10, 10, 10),
                new lab7.Recipe(1, "", "", "", 4, 10, 10, 15),
                new lab7.Recipe(2, "", "", "", 4, 10, 10, 16),
                new lab7.Recipe(3, "", "", "", 4, 10, 10, 20),
                new lab7.Recipe(4, "", "", "", 4, 10, 10, 2343)
        ));

        assertEquals(2, recipes.size());

        // Verify that the two recipes we expected are in fact in the list
        assertEquals(0, recipes.get(0).id());
        assertEquals(1, recipes.get(1).id());
    }

    // TODO: test the searchRecipes method


    @Test
    void testSearchRecipesFindsByName() {
        lab7.DataService ds = () -> List.of(
                new lab7.Recipe(0, "Chicken Soup", "Tasty soup", "", 2, 5, 10, 20),
                new lab7.Recipe(1, "Beef Stew", "Hearty meal", "", 3, 10, 20, 45)
        );

        var results = lab7.Main.searchRecipes("chicken", ds);
        assertEquals(1, results.size());
        assertEquals("Chicken Soup", results.get(0).name());
    }

    @Test
    void testSearchRecipesFindsByDescription() {
        lab7.DataService ds = () -> List.of(
                new lab7.Recipe(0, "Pasta", "Chicken Alfredo", "", 2, 5, 10, 25),
                new lab7.Recipe(1, "Toast", "Just bread", "", 1, 0, 2, 5)
        );

        var results = lab7.Main.searchRecipes("chicken", ds);
        assertEquals(1, results.size());
        assertEquals("Pasta", results.get(0).name());
    }

    @Test
    void testSearchRecipesNoResults() {
        lab7.DataService ds = () -> List.of(
                new lab7.Recipe(0, "Pasta", "Cheese", "", 2, 5, 10, 15),
                new lab7.Recipe(1, "Toast", "Butter", "", 1, 0, 2, 5)
        );

        var results = lab7.Main.searchRecipes("chicken", ds);
        assertTrue(results.isEmpty());
    }

    @Test
    void testSearchRecipesHandlesError() {
        lab7.DataService ds = () -> { throw new RuntimeException("boom"); };

        var results = lab7.Main.searchRecipes("anything", ds);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void testSearchRecipesBlankOrNullTerm() {
        lab7.DataService ds = () -> List.of(
                new lab7.Recipe(0, "Chicken Soup", "Good", "", 2, 5, 10, 20)
        );

        assertTrue(lab7.Main.searchRecipes("", ds).isEmpty());
        assertTrue(lab7.Main.searchRecipes("   ", ds).isEmpty());
        assertTrue(lab7.Main.searchRecipes(null, ds).isEmpty());
    }
}
