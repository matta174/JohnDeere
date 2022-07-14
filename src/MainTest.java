import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void main() {
    }

    @Test
    public void testGetTopWords() throws IOException {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("the",3);
        testMap.put("hello",5);
        testMap.put("cancel",4);
        testMap.put("hike",10);
        testMap.put("bannana",3);
        testMap.put("cabinet",7);
        var testResult = Main.getTopWords(testMap, 3);
        assertEquals(3,testResult.size());
        assertEquals("hike",testResult.stream().findFirst().get().getKey());
        assertEquals(10, testResult.stream().findFirst().get().getValue());
    }

    @Test
    public void testFilterString() throws IOException {
        String[] testString = Main.filterString();
        assertEquals(215864,testString.length);
    }

    @Test
    public void testGetWordCountMap(){
        String[] words = {"hi","hi","test","lots","of","Words","to","the","the"};
        var testMap = Main.getWordCountMap(words);

        assertEquals(4,testMap.size());
        assertEquals(2,testMap.get("hi"));
        assertFalse(testMap.containsKey("of"));
        assertFalse(testMap.containsKey("the"));
    }
}