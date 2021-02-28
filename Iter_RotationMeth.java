package com.lab4;
import java.lang.reflect.Array;
import java.util.ArrayList;
public class Main {
    public static class Pair<D, Y> {
        public D x;
        public Y y;
    }
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
    public static double ri(double aSimMatrix[][],double sobstvVect[],double lambda){
        double res=0;
        double bufMatrix[][]=new double[5][5];
        double bufVect[]=new double[5];
        multiMatrixTAndVector(aSimMatrix,sobstvVect,bufVect);
        for(int i=0;i<5;i++){
            bufVect[i]-=sobstvVect[i]*lambda;
        }
        System.out.println("r vect is:");
        for(int i=0;i<5;i++){
            System.out.printf("%6.4e ",bufVect[i]);
        }
        System.out.println();
        res=maxOfVect(bufVect);
        return  res;
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
    public static void multiMatrixTAndMatrix(double aMatrix[][],double aMultiMatrixx[][]) {
        double aMatrixT[][]=new double[5][5];
        double aMultiMatrix[][]=aMultiMatrixx;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                aMatrixT[j][i]=aMatrix[i][j];
            }
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
    public static Pair<Integer,Integer> findMaxCoordinates(double aMatrix[][],int n){
        double max=0;
        Pair<Integer,Integer> retCoordinates=new Pair<>();
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(Math.abs(aMatrix[i][j])>Math.abs(max)){
                    max=aMatrix[i][j];
                    retCoordinates.x=i;
                    retCoordinates.y=j;
                }
            }
        }
        return retCoordinates;
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
    public static void main(String[] args) {
        double aMatrix[][] = { {0.4974,0.0000,-0.1299,0.0914,0.1523},{-0.0305,0.3248,0,-0.0619,0.0203},{0.0102,-0.0914,0.5887,0.0112,0.0355},{0.0305,0,-0.0741,0.5887,0},{0.0203,-0.0305,0.1472,-0.0122,0.4263} };
        double aSimMatrix[][]=new double[5][5];
        double aSimMatrix2[][]=new double[5][5];
        ArrayList<double[][]> uMatrixs=new ArrayList<double[][]>();
        multiMatrixTAndMatrix(aMatrix,aSimMatrix);
        for(int i=0;i<5;i++){
            aSimMatrix2[i]=aSimMatrix[i].clone();
        }
        double e=0.00001;
        System.out.println("A^T*A matrix is:");
        printDualMatrix(aSimMatrix,5,5);
        boolean a=true;
        while(a){
            Pair<Integer,Integer> max=findMaxCoordinates(aSimMatrix,5);
            double angle=0.5*Math.atan((2* aSimMatrix[max.x][max.y])/(aSimMatrix[max.x][max.x]-aSimMatrix[max.y][max.y]));
            double sin=Math.sin(angle);
            double cos=Math.cos(angle);
            double uMatrix[][]=new double[5][5];
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    if(i==j){
                        uMatrix[i][j]=1;
                    }
                    else {
                        uMatrix[i][j] = 0;
                    }
                }
            }
            uMatrix[max.x][max.x]=cos;
            uMatrix[max.y][max.y]=cos;
            uMatrix[max.x][max.y]=-sin;
            uMatrix[max.y][max.x]=sin;
            uMatrixs.add(uMatrix);
            double uTMatrix[][]=new double[5][5];
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    uTMatrix[i][j]=uMatrix[j][i];
                }
            }
            double bufMatrix[][]=new double[5][5];
            multiMatrix(aSimMatrix,uMatrix,bufMatrix);
            multiMatrix(uTMatrix,bufMatrix,aSimMatrix);
            if(Math.abs(aSimMatrix[findMaxCoordinates(aSimMatrix,5).x][findMaxCoordinates(aSimMatrix,5).y])<e){
                a=false;
            }
        }
        System.out.println("Labdas are:");
        System.out.printf("%12.8f %12.8f %12.8f %12.8f %12.8f",aSimMatrix[0][0],aSimMatrix[1][1],aSimMatrix[2][2],aSimMatrix[3][3],aSimMatrix[4][4]);
        double bufMatrix[][]=new double[5][5];
        double bufMatrix2[][]=new double[5][5];
        for(int j=0;j<5;j++){
            bufMatrix[j]=uMatrixs.get(0)[j].clone();
        }
        for(int i=1;i< uMatrixs.size();i++ ){
            multiMatrix(bufMatrix,uMatrixs.get(i),bufMatrix2);
            for(int j=0;j<5;j++){
                bufMatrix[j]=bufMatrix2[j].clone();
            }
        }
        System.out.println("\n\nX matrix is:");
        printDualMatrix(bufMatrix2,5,5);
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n",aSimMatrix[0][0],fi(aSimMatrix[0][0]));
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n",aSimMatrix[1][1],fi(aSimMatrix[1][1]));
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n",aSimMatrix[2][2],fi(aSimMatrix[2][2]));
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n",aSimMatrix[3][3],fi(aSimMatrix[3][3]));
        System.out.printf("lambda= %11.4f, P(lambda)=%11.4e\n\n",aSimMatrix[4][4],fi(aSimMatrix[4][4]));
        for(int i=0;i<5;i++){
            double aSimMatrix3[][]=new double[5][5];
            for(int j=0;j<5;j++){
                aSimMatrix3[j]=aSimMatrix2[j].clone();
            }
            System.out.printf("Lambda is %8.4f\n",aSimMatrix[i][i]);
            System.out.println("xVect is:");
            double bufVect[]=new double[5];
            for(int j=0;j<5;j++){
                bufVect[j]=bufMatrix2[j][i];
            }
            printVect(bufVect,5);
            double buf=ri(aSimMatrix3,bufVect,aSimMatrix[i][i]);
            System.out.printf("max of ri is: %11.4e\n\n",buf);
        }
    }
}
