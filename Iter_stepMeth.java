package com.lab4;

public class Main {
    public static double fi(double lambda){
        double f=Math.pow(lambda,5)+1.32080000000000020000000000000000d*Math.pow(lambda,4)-0.64693325000000020000000000000000d*Math.pow(lambda,3)+0.14443810359200002000000000000000d*Math.pow(lambda,2)-0.01444789978142068500000000000000d*lambda+0.00052187183228398020000000000000d;
        return f;
    }
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
    public static void printDualMatrix(double matrix[][],int n,int m){
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.printf("%10.4f ",matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void printVect(double matrix[],int n){
        for(int i=0;i<n;i++){
            System.out.printf("%6.4f ",matrix[i]);
        }
        System.out.println();
    }
    public static void multiMatrix(double bMatrix[][],double aMatrix[][],double aMultiMatrixx[][]) {
        double aMatrixT[][]=new double[5][5];
        double aMultiMatrix[][]=aMultiMatrixx;
        for(int i=0;i<5;i++){
            aMatrixT[i]=bMatrix[i].clone();
        }
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                double a=0;
                for(int g=0;g<5;g++){
                    a+=aMatrixT[i][g]*aMatrix[g][j];
                }
                aMultiMatrix[i][j]=a;
            }
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
        printVect(bufVect,5);
        res=maxOfVect(bufVect);
        return  res;
    }
    public static void main(String[] args) {
        double[][] aMatrix = {{    0.2498,    -0.0115 ,   -0.0579,     0.0652 ,    0.0842 },
                {-0.0115,     0.1148,    -0.0583,    -0.0208,    -0.0097 },
                {-0.0579,    -0.0583,     0.3906,    -0.0507,     0.0639},
                {0.0652,    -0.0208,    -0.0507,     0.3590,     0.0079 },
                {0.0842,    -0.0097,     0.0639,     0.0079,     0.2066 }};
        System.out.print("Исходая матрица А^T*A:\n");
        printDualMatrix(aMatrix,5,5);
        double xVect1[]={1,1,1,1,1};
        double xVect2[]={1,1,1,1,1};
        double e=0.00001;
        boolean a=true;
        double lambda1=0,lambda2=0;
        while (a){
            multiMatrixTAndVector(aMatrix,xVect1,xVect2);
            lambda2=xVect2[0]/xVect1[0];
            xVect1=xVect2.clone();
            if(Math.abs(lambda1-lambda2)<=e){
                a=false;
            }
            lambda1=lambda2;
        }

        System.out.printf("Max lambda is %6.4f\n\n",lambda2);
        double buf=xVect2[0];
        for(int i=0;i<xVect2.length;i++){
            xVect2[i]/=buf;
        }
        System.out.println("Sobstv vect of max lambda is:");
        printVect(xVect2,5);
        System.out.printf("\nlambda= %11.4f, P(lambda)=%11.4e\n",lambda2,fi(lambda2));
        System.out.printf("\nLambda is %8.4f\n",lambda2);
        System.out.println("xVect is:");
        double bufVect[]=new double[5];
        printVect(xVect2,5);
        double buf2=ri(aMatrix,xVect2,lambda2);
        System.out.printf("max of ri is: %11.4e\n\n",buf2);
    }
}
