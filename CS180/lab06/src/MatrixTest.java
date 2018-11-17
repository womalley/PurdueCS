/**
 * Created by bomal_000 on 2/13/2016.
 */

import static org.junit.Assert.*;
import org.junit.*;

public class MatrixTest {
    public Matrix t;

    public int[][] t1 = {{1, 0, 0},  //Checks for symmetric
                          {0, 1, 0},
                          {0, 0, 1 }};

    public int[][] t2 = {{0, 1, 0, 2},  //Checks non symmetric
                          {2, 0, 1, 0},
                          {0, 2, 0, 1},
                          {1, 0, 2, 0}};

    public int[][] t3 = {{1, 1, 1, 1},  //Not square test (ragged matrix)
                          {1, 1, 1, 1},
                          {1, 1, 1},
                          {1, 1, 1, 1}};

    @Before
    public void setUp() throws Exception {
        t = new Matrix();
    }

    //Symmetric Test Cases
    @Test (timeout = 100)
    public void isSymmetricT1() {
        boolean ret = t.isSymmetric(t1);
        boolean expected = (true);

        String message = ("isSymmetric(): Checks when it's symmetric");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isSymmetricT2() {
        boolean ret = t.isSymmetric(t2);
        boolean expected = (false);

        String message = ("isSymmetric(): Checks when it's not symmetric");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isSymmetricT3() {
        boolean ret = t.isSymmetric(t3);
        boolean expected = (true);

        String message = ("isSymmetric(): Checks when it's a ragged matrix");
        assertEquals(message, expected, ret);
    }

    //isDiagonal Test Cases
    @Test (timeout = 100)
    public void isDiagonalT1() {
        boolean ret = t.isDiagonal(t1);
        boolean expected = (true);

        String message = ("isDiagonal(): Checks when it's diagonal");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isDiagonalT2() {
        boolean ret = t.isDiagonal(t2);
        boolean expected = (false);

        String message = ("isDiagonal(): Checks when it's not diagonal");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isDiagonalT3() {
        boolean ret = t.isDiagonal(t3);
        boolean expected = (true);

        String message = ("isDiagonal(): Checks when it's a ragged matrix");
        assertEquals(message, expected, ret);
    }

    //isIdentity Test Cases
    @Test (timeout = 100)
    public void isIdentityT1() {
        boolean ret = t.isIdentity(t1);
        boolean expected = (true);

        String message = ("isIdentity(): Checks when it's identity");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isIdentityT2() {
        boolean ret = t.isIdentity(t2);
        boolean expected = (false);

        String message = ("isIdentity(): Checks when it's not identity");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isIdentityT3() {
        boolean ret = t.isIdentity(t3);
        boolean expected = (false);

        String message = ("isIdentity(): Checks when it's a ragged matrix");
        assertEquals(message, expected, ret);
    }

    //isUpperTriangular Test Cases
    @Test (timeout = 100)
    public void isUpperTriangularT1() {
        boolean ret = t.isUpperTriangular(t1);
        boolean expected = (true);

        String message = ("isUpperTriangular(): Checks when it's upper triangular matrix");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isUpperTriangularT2() {
        boolean ret = t.isUpperTriangular(t2);
        boolean expected = (false);

        String message = ("isUpperTriangular(): Checks when it's not upper triangular matrix");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isUpperTriangularT3() {
        boolean ret = t.isUpperTriangular(t3);
        boolean expected = (true);

        String message = ("isUpperTriangular(): Checks when it's a ragged matrix");
        assertEquals(message, expected, ret);
    }

    //isTridiagonal Test Cases
    @Test (timeout = 100)
    public void isTriDiagonalT1() {
        boolean ret = t.isTriDiagonal(t1);
        boolean expected = (true);

        String message = ("isUpperTriangular(): Checks when it's tridiagonal");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isTriDiagonalT2() {
        boolean ret = t.isTriDiagonal(t2);
        boolean expected = (false);

        String message = ("isUpperTriangular(): Checks when it's not tridiagonal");
        assertEquals(message, expected, ret);
    }
    @Test (timeout = 100)
    public void isTriDiagonalT3() {
        boolean ret = t.isTriDiagonal(t3);
        boolean expected = (true);

        String message = ("isUpperTriangular(): Checks when it's a ragged matrix ");
        assertEquals(message, expected, ret);
    }
}