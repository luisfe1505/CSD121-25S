import lab4.game.Col;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class coltest {
    @Test
    public void valid() {
    assertEquals(Col.from("1"), Col.Left);
    assertEquals(Col.from("l"), Col.Left);
    assertEquals(Col.from("2"), Col.Middle);
    assertEquals(Col.from("m"), Col.Middle);
    assertEquals(Col.from("c"), Col.Middle);
    assertEquals(Col.from("3"), Col.Right);
    assertEquals(Col.from("r"), Col.Right);
    }
    @Test
    public void caseSensitive() {
        assertEquals(Col.from("L"), Col.Left);
        assertEquals(Col.from("M"), Col.Middle);
        assertEquals(Col.from("C"), Col.Middle);
        assertEquals(Col.from("R"), Col.Right);
    }
    @Test
    public void invalid() {
        assertThrows(IllegalArgumentException.class, () -> Col.from(""));
        assertThrows(IllegalArgumentException.class, () -> Col.from("a"));
        assertThrows(IllegalArgumentException.class, () -> Col.from("7"));
        assertThrows(IllegalArgumentException.class, () -> Col.from("x"));
        assertThrows(IllegalArgumentException.class, () -> Col.from("z"));
        assertThrows(IllegalArgumentException.class, () -> Col.from("5"));

    }
}
