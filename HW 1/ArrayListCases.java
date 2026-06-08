import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ArrayListCases {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        Object[] backing = list.getBackingArray();
        assertEquals(ArrayList.INITIAL_CAPACITY, backing.length);
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetInvalidIndex() {
        list.addToBack("0"); // 0
        list.addToBack("1"); // 0, 1
        list.addToBack("2"); // 0, 1, 2

        list.get(3);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex() {
        list.addToBack("0"); // 0
        list.addToBack("1"); // 0, 1
        list.addToBack("2"); // 0, 1, 2

        list.get(-1);
    }
    
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetNothing() {
        list.get(0);
    }
}