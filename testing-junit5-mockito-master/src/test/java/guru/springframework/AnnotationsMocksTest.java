package guru.springframework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

public class AnnotationsMocksTest {

    @Mock
    Map<String, Object> mapMock;

    @BeforeEach
    void setUp() {
//        Argument must not be a null
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMock() {
        mapMock.put("Keyvalue", "I'm an object bro");
    }
}
