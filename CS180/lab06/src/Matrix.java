/**
 * Created by bomal_000 on 2/12/2016.
 */
public class Matrix {
//    int i;
//    int k;

    public boolean isSymmetric(int[][] matrix) {
        int i; //for the for loop
        int k; //for the second for loop

        for (i = 0; i < matrix.length; i++) {
            for (k = 0; k < matrix[i].length; k++) {
                if (matrix[i][k] != matrix[k][i]) {
                    return (false);
                }
            }
        }
        return (true);
    }
    public boolean isDiagonal(int[][] matrix) {
        int i, k;
        for (i = 0; i < matrix.length; i++) {
            for (k = 0; k < matrix[i].length; k++) {
                if (matrix[i][k] == 0 && i == k) {
                    return (false);
                }
            }
        }
        return (true);
    }
    public boolean isIdentity(int[][] matrix) {
        int i, k;
        for (i = 0; i < matrix.length; i++) {
            for (k = 0; k < matrix[i].length; k++) {
                if (matrix[i][k] == 1 && i == k) {
                    //Will return true
            }
            else if (matrix[i][k] == 0 && i != k) {
                //Will also return true
            } else {
                return (false);
            }
        }
    }

    return (true);

    }
    public boolean isUpperTriangular(int[][] matrix) {
        int i, k;
        for (i = 0; i < matrix.length; i++) {
            for (k = 0; k < matrix[i].length; k++) {
                if (matrix[i][k] == 0 && i == k) {
                    return (false);
                }
            }
        }
        return (true);
    }
    public boolean isTriDiagonal(int[][] matrix) {
        int i, k;
        for (i = 0; i < matrix.length; i++) {
            for (k = 0; k < matrix[i].length; k++) {
                if (i == k && i - 1 == k && i == k - 1) {
                    return (false);
                }
            }
        }
        return (true);
    }
}
