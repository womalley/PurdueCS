/**
 * CS180 - Lab 03 - StringManipulator
 * The program takes a name and creates a username
 * @author William O'Malley, womalley@purdue.edu
 *
 * Created by bomal_000 on 1/26/2016.
 */

import static org.junit.Assert.*;
import org.junit.*;

//JUnit test case class that tests the functionality of methods
//in the StringManipulator class.

public class StringManipulatorTest {
    private StringManipulator sm;

    @Before
    public void setUp() throws Exception {
        sm = new StringManipulator();
    }
}

//Test the functionality of makeUserName.
//Don't check for correct case.

@Test(timeout = 100)
public void testMakeUserNameBasic() {
    String ret = sm.makeUserName("john doe");
}