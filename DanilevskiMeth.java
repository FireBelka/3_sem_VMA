package com.lab4;
import java.util.ArrayList;
import static java.lang.Math.*;
public class Main {
    public static double maxOfVect(double vect[]){
        double max=0;
        for(int i=0;i<vect.length;i++){
            if(Math.abs(vect[i])>Math.abs(max)){
                max=vect[i];
            }
        }
        return max;
    }
    public static void multiMatrixTAndVector(double aMatrix[][],double vect[],double multiVector[]) {
        double aMatrixT[][]=new double[5][5];
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                aMatrixT[i][j]=aMatrix[i][j];
            }
        }
        double mas[]=new double[5];
        for(int i=0;i<5;i++){
            double a=0;
            for(int j=0;j<5;j++){
                a+=aMatrixT[i][j]*vect[j];
            }
            multiVector[i]=a;
        }
    }
    public static double ri(double aSimMatrix[][],double sobstvVect[],double lambda){
        double res=0;
        double bufMatrix[][]=new double[5][5];
        double bufVect[]=new double[5];
        multiMatrixTAndVector(aSimMatrix,sobstvVect,bufVect);
        for(int i=0;i<5;i++){
            bufVect[i]-=sobstvVect[i]*lambda;
        }
        System.out.println("r vect is:");
        output_vec(bufVect);
        res=maxOfVect(bufVect);
        return  res;
    }
    public static double fi(double lambda){
        double f=Math.pow(lambda,5)+1.32080000000000020000000000000000d*Math.pow(lambda,4)-0.64693325000000020000000000000000d*Math.pow(lambda,3)+0.14443810359200002000000000000000d*Math.pow(lambda,2)-0.01444789978142068500000000000000d*lambda+0.00052187183228398020000000000000d;
        return f;
    }
    public static double[][] copying_mat(double[][] A) {
        double[][] copy_mat = new double[A.length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                copy_mat[i][j] = A[i][j];
            }
        }
        return copy_mat;
    }
    public static double[][] init_E(int n) {
        double[][] mat = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i == j)
                    mat[i][j] = 1;
                else
                    mat[i][j] = 0;
            }
        return mat;
    }
    public static void output_mat(double[][] mat) {
        int n = mat.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%1.7f\t\t", mat[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
    public static double[] multiplication_mat_vector(double[][] A, double[] b) {
        int n = A.length;
        double[] vec = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                vec[i] += A[i][j] * b[j];
            }
        }
        return vec;
    }
    public static double[][] multiplication_mat(double[][] A, double[][] E) {
        int n = A.length;
        double[][] c = new double[n][n];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                for (int k = 0; k < n; ++k)
                    c[i][j] += A[i][k] * E[k][j];
        return c;
    }
    public static ArrayList<Double> odds = new ArrayList<Double>();
    public static double[][] B_matrix;
    public static double[][] mat_Frobenius(double[][] A) {
        int n = A.length;
        B_matrix = init_E(n);
        double[][] D = copying_mat(A);
        double[][] C = new double[n][n];
        double[][] B = init_E(n);
        double[][] B_reverse = init_E(n);
        for (int k = n - 1; k > 0; k--) {
            if (D[k][k - 1] == 0) { // проверка на нулевой элемент
                for (int i = 0; i < k - 1; i++) {
                    if (D[k][i] != 0) // если в к-ой строке есть ненулевой элемент
                    {
                        double tmp = 0;
                        for (int j = 0; j < n; j++) {   // меняем столбцы
                            tmp = D[j][i];
                            D[j][i] = D[j][k - 1];
                            D[j][k - 1] = tmp;
                        }
                        for (int j = 0; j < n; j++) {     // меняем строки
                            tmp = D[k - 1][j];
                            D[k - 1][j] = D[i][j];
                            D[i][j] = tmp;
                        }
                        break;
                    } else { // если все элементы = 0
                        double[][] M = new double[k][k];
                        double[][] F = new double[k][k];
                        for (int ii = 0; ii < k; ii++) {
                            for (int jj = 0; jj < k; jj++) {
                                M[ii][jj] = D[ii][jj];
                            }
                        }
                        for (int ii = 0; ii < k; ii++) {
                            for (int jj = 0; jj < k; jj++) {
                                F[ii][jj] = D[k + ii][k + jj];
                            }
                        }
                        M = mat_Frobenius(M);
                        odds.clear();
                        for (int ii = 0; ii < M.length; ii++) {
                            odds.add(Double.parseDouble(String.valueOf(M[0][ii])));
                        }
                        for (int ii = 0; ii < F.length; ii++) {
                            odds.add(Double.parseDouble(String.valueOf(F[0][ii])));
                        }
                        return M;
                    }
                }
            }
            for (int j = 0; j < n; j++) { // находим матрицу B и обратную ей
                B_reverse[k - 1][j] = D[k][j];
                if (j != k - 1)
                    B[k - 1][j] = -D[k][j] / D[k][k - 1];
                else
                    B[k - 1][k - 1] = 1 / D[k][k - 1];
            }
            for (int i = 0; i < n; i++) { // находим матрицу С = A * B
                C[i][k - 1] = D[i][k - 1] * B[k - 1][k - 1];
                for (int j = 0; j < n; j++) {
                    if (j != k - 1)
                        C[i][j] = D[i][j] + D[i][k - 1] * B[k - 1][j];
                }
            }
            double tmp = 0; // находим новую матрицу D
            for (int j = 0; j < n; j++) {
                for (int kk = 0; kk < n; kk++) {
                    tmp += D[k][kk] * C[kk][j];
                }
                D[k - 1][j] = tmp;
                tmp = 0;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != k - 1)
                        D[i][j] = C[i][j];
                }
            }
            multiplication_mat(B_matrix, B);
        }
        for (int ii = 0; ii < D.length; ii++) {
            odds.add(Double.parseDouble(String.valueOf(D[0][ii])));
        }
        return D;
    }
    public static void output_vec(double[] vec) {
        for (int i = 0; i < vec.length; i++)
            System.out.printf("%6.4f ",vec[i]);
        System.out.println();
    }
    public static void main(String[] args) {
        double[][] A = {{    0.2498,    -0.0115 ,   -0.0579,     0.0652 ,    0.0842 },
                {-0.0115,     0.1148,    -0.0583,    -0.0208,    -0.0097 },
                {-0.0579,    -0.0583,     0.3906,    -0.0507,     0.0639},
                {0.0652,    -0.0208,    -0.0507,     0.3590,     0.0079 },
                {0.0842,    -0.0097,     0.0639,     0.0079,     0.2066 }};
        System.out.print("Исходая матрица А^T*A:\n");
        output_mat(A);
        double[][] F = mat_Frobenius(A);
        System.out.print("Матрица Фробениуса:\n");
        output_mat(F);
        System.out.print("характеристический многочлен:\n");
        System.out.printf("λ^(5) - 1.3208 * λ^(4) + 0.6469 * λ^(3) - 0.1444 * λ^(2) + 0.0144 * λ^(1) - 0.0005\n\n");
        ArrayList<Double> x = new ArrayList<Double>();
        int k = 0;
        boolean book = true;
        for (double i = -1.00001; i < 1; i = i + 0.00001) {
            if (abs(pow(i, 5) - (F[0][0] * pow(i, 4)) - (F[0][1] * pow(i, 3)) - (F[0][2] * pow(i, 2)) -
                    (F[0][3] * pow(i, 1)) - F[0][4]) < 0.0000001) {
                if (k == 0) {
                    x.add(i);
                    k++;

                } else {
                    for (int j = 0; j < k; j++) {
                        if (abs(i -x.get(j)) < 0.01) {
                            book = false;
                        }
                    }
                    if (book) {
                        x.add(i);
                        k++;
                    }
                    book = true;
                }
            }
        }
        System.out.print("Действительные корни характеристического многочлена:\n");
        System.out.printf("%6.4f %6.4f %6.4f %6.4f %6.4f\n",x.get(0),x.get(1),x.get(2),x.get(3),x.get(4));
        double[] y0= {pow(x.get(0), 4), pow(x.get(0), 3), pow(x.get(0), 2), x.get(0), 1};
        double[] y1 = {pow(x.get(1), 4), pow(x.get(1), 3), pow(x.get(1), 2), x.get(1), 1};
        double[] y2 = {pow(x.get(2), 4), pow(x.get(2), 3), pow(x.get(2), 2), x.get(2), 1};
        double[] y3 = {pow(x.get(3), 4), pow(x.get(3), 3), pow(x.get(3), 2), x.get(3), 1};
        double[] y4 = {pow(x.get(4), 4), pow(x.get(4), 3), pow(x.get(4), 2), x.get(4), 1};
        System.out.println("\nСобственный вектор значения lambda1 :");
        double[] x0 = multiplication_mat_vector(B_matrix, y0);
        output_vec(x0);
        System.out.println("\nСобственный вектор значения lambda2 :");
        double[] x1 = multiplication_mat_vector(B_matrix, y1);
        output_vec(x1);
        System.out.println("\nСобственный вектор значения lambda3 :");
        double[] x2 = multiplication_mat_vector(B_matrix, y2);
        output_vec(x2);
        System.out.println("\nСобственный вектор значения lambda4 :");
        double[] x3 = multiplication_mat_vector(B_matrix, y3);
        output_vec(x3);
        System.out.println("\nСобственный вектор значения lambda5 :");
        double[] x4 = multiplication_mat_vector(B_matrix, y4);
        output_vec(x4);
        System.out.println();
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n",x.get(0),fi(x.get(0)));
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n",x.get(1),fi(x.get(1)));
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n",x.get(2),fi(x.get(2)));
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n",x.get(3),fi(x.get(3)));
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n\n",x.get(4),fi(x.get(4)));
            System.out.printf("Lambda is %8.4f\n",x.get(0));
            System.out.println("xVect is:");
            output_vec(x0);
            double buf=ri(A,x0,x.get(0));
            System.out.printf("max of ri is: %11.4e\n\n",buf);

        System.out.printf("Lambda is %8.4f\n",x.get(1));
        System.out.println("xVect is:");
        output_vec(x1);
        buf=ri(A,x1,x.get(1));
        System.out.printf("max of ri is: %11.4e\n\n",buf);

        System.out.printf("Lambda is %8.4f\n",x.get(2));
        System.out.println("xVect is:");
        output_vec(x2);
        buf=ri(A,x2,x.get(2));
        System.out.printf("max of ri is: %11.4e\n\n",buf);

        System.out.printf("Lambda is %8.4f\n",x.get(3));
        System.out.println("xVect is:");
        output_vec(x3);
        buf=ri(A,x3,x.get(3));
        System.out.printf("max of ri is: %11.4e\n\n",buf);

        System.out.printf("Lambda is %8.4f\n",x.get(4));
        System.out.println("xVect is:");
        output_vec(x4);
        buf=ri(A,x4,x.get(4));
        System.out.printf("max of ri is: %11.4e\n\n",buf);
    }
}
