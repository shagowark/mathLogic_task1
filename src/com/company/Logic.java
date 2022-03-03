package com.company;

import com.company.util.ArrayUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;


public class Logic {
    public static void getResult(String fileNameInput, String fileNameOutput) throws Exception {
        int[][] determinant = ArrayUtils.readIntArray2FromFile(fileNameInput);

        checkIfArrayIsNull(determinant);
        checkIfArrayIsCorrect(determinant);
        checkIfArrayIsSquare(determinant);

        int result = solveDeterminant(determinant);
        writeIntoFile(result, fileNameOutput);
    }

    public static int solveDeterminant(int[][] det){
        if (det.length == 1){
            return det[0][0];
        }
        if (det.length == 2){
            return det[0][0]*det[1][1] - det[0][1]*det[1][0];
        }
        if (det.length == 3){
            return det[0][0]*det[1][1]*det[2][2]+det[0][1]*det[1][2]*det[2][0]+det[1][0]*det[0][2]*det[2][1]
                    -det[0][2]*det[1][1]*det[2][0]-det[0][1]*det[1][0]*det[2][2]-det[0][0]*det[1][2]*det[2][1];
        }

        int sum = 0;
        for (int i = 0; i < det.length; i++){
            sum += Math.pow(-1, i+1+1) * det[i][0] * solveDeterminant(getMinor(det, i, 0));
        }

        return sum;
    }

    public static int[][] getMinor(int[][] matrix, int rowNumber, int columnNumber){
        int[][] minor = new int [matrix.length - 1][matrix.length - 1];
        int r = 0;
        for (int i = 0; i < matrix.length; i++){
            if (i == rowNumber){
                continue;
            }

            int c = 0;
            for (int j = 0; j < matrix.length; j++){
                if (j == columnNumber){
                    continue;
                }
                minor[r][c] = matrix[i][j];

                c++;
            }

            r++;
        }

        return minor;
    }

    public static void writeIntoFile(int result, String fileName) throws Exception{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(result + "\n");
        writer.flush();
    }

    public static void checkIfArrayIsNull(int [][] array) throws Exception{
        if (array == null){
            throw new Exception("Отсутствует input файл");
        }
    }

    public static void checkIfArrayIsCorrect (int [][] array) throws Exception {
        if (array.length < 1 || array[0].length < 1) {
            throw new Exception("Input файл пустой");
        }
    }

    public static void checkIfArrayIsSquare (int [][] array) throws Exception {
        for (int[] line : array) {
            if (line.length != array.length) {
                throw new Exception("Матрица должна быть квадратной");
            }
        }
    }


}
