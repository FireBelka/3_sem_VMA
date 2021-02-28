package com.lab4;
import java.util.Arrays;

public class Main {
    public static double findMax(double[] array) {
        Arrays.sort(array);
        double max = array[array.length - 1];
        return max;
    }
    public static boolean checkVectLessThatValue(double mas1[],double mas2[],double value){
        double bufVect[]=new double[5];
        for(int i=0;i<5;i++){
            bufVect[i]=Math.abs(mas1[i]-mas2[i]);
        }
        double bufVal=findMax(bufVect);
        if(bufVal<=value){
            return true;
        }
        else{
            return false;
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
    public static void multiMatrixTAndVector(double aMatrix[][],double vect[],double multiVector[]) {
        double aMatrixT[][]=new double[5][5];
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                aMatrixT[j][i]=aMatrix[i][j];
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
    public static void rMax(double aMatrix[][],double bVect[],double xVect[]){
        double bufVect[]=new double[5];
        double a=-1;
        System.out.println("r vect is:");
        for(int i=0;i<5;i++){
            bufVect[i]=aMatrix[i][0]*xVect[0]+aMatrix[i][1]*xVect[1]+aMatrix[i][2]*xVect[2]+aMatrix[i][3]*xVect[3]+aMatrix[i][4]*xVect[4]-bVect[i];
            if(bufVect[i]>a){
                a=bufVect[i];
            }
            System.out.printf("%11.4e ",bufVect[i]);
        }
        System.out.printf("\n\n||r||1= %11.4e",a);
        return;
    }
    public static void main(String[] args) {
        double aMatrixBuf[][] = { {0.4974,0.0000,-0.1299,0.0914,0.1523},{-0.0305,0.3248,0,-0.0619,0.0203},{0.0102,-0.0914,0.5887,0.0112,0.0355},{0.0305,0,-0.0741,0.5887,0},{0.0203,-0.0305,0.1472,-0.0122,0.4263} };
        double aMatrix[][]=new double[5][5];
        double bVectBuf[] = { 1.5875,-1.7590,1.4139,1.7702,-2.0767 };
        double bVect[]=new double[5];
        double xVector[]= { 1.5875,-1.7590,1.4139,1.7702,-2.0767 };
        double xVectorBuf[]= { 1.5875,-1.7590,1.4139,1.7702,-2.0767 };
        multiMatrixTAndMatrix(aMatrixBuf,aMatrix);
        multiMatrixTAndVector(aMatrixBuf,bVectBuf,bVect);
        boolean a=true;
        double w=1.114;
        int iter=0;
        int countOfCalk=0;
        while(a){
            iter++;
            xVectorBuf[0]=xVector[0];
            xVectorBuf[1]=xVector[1];
            xVectorBuf[2]=xVector[2];
            xVectorBuf[3]=xVector[3];
            xVectorBuf[4]=xVector[4];
            xVector[0]=(1-w)*xVectorBuf[0]+w*bVect[0]/aMatrix[0][0]-w*xVector[1]*aMatrix[0][1]/aMatrix[0][0]-w*xVector[2]*aMatrix[0][2]/aMatrix[0][0]-w*xVector[3]*aMatrix[0][3]/aMatrix[0][0]-w*xVector[4]*aMatrix[0][4]/aMatrix[0][0];
            xVector[1]=(1-w)*xVectorBuf[1]+w*bVect[1]/aMatrix[1][1]-w*xVector[0]*aMatrix[1][0]/aMatrix[1][1]-w*xVector[2]*aMatrix[1][2]/aMatrix[1][1]-w*xVector[3]*aMatrix[1][3]/aMatrix[1][1]-w*xVector[4]*aMatrix[1][4]/aMatrix[1][1];
            xVector[2]=(1-w)*xVectorBuf[2]+w*bVect[2]/aMatrix[2][2]-w*xVector[0]*aMatrix[2][0]/aMatrix[2][2]-w*xVector[1]*aMatrix[2][1]/aMatrix[2][2]-w*xVector[3]*aMatrix[2][3]/aMatrix[2][2]-w*xVector[4]*aMatrix[2][4]/aMatrix[2][2];
            xVector[3]=(1-w)*xVectorBuf[3]+w*bVect[3]/aMatrix[3][3]-w*xVector[0]*aMatrix[3][0]/aMatrix[3][3]-w*xVector[1]*aMatrix[3][1]/aMatrix[3][3]-w*xVector[2]*aMatrix[3][2]/aMatrix[3][3]-w*xVector[4]*aMatrix[3][4]/aMatrix[3][3];
            xVector[4]=(1-w)*xVectorBuf[4]+w*bVect[4]/aMatrix[4][4]-w*xVector[0]*aMatrix[4][0]/aMatrix[4][4]-w*xVector[1]*aMatrix[4][1]/aMatrix[4][4]-w*xVector[2]*aMatrix[4][2]/aMatrix[4][4]-w*xVector[3]*aMatrix[4][3]/aMatrix[4][4];
            countOfCalk+=(19*5);
            a=!checkVectLessThatValue(xVector,xVectorBuf,0.00001);
        }
        System.out.println("A matrix is: ");
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                System.out.printf("%-11.4f ",aMatrixBuf[i][j]);
            }
            System.out.println();
        }
        System.out.print("\nb vect= ");
        for(int j=0;j<5;j++){
            System.out.printf("%-11.4f ",bVectBuf[j]);
        }

        System.out.println("\n\nAT*A matrix is: ");
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                System.out.printf("%-11.4f ",aMatrix[i][j]);
            }
            System.out.println();
        }
        System.out.print("\nAT*b vect= ");
        for(int j=0;j<5;j++){
            System.out.printf("%-11.4f ",bVect[j]);
        }
        System.out.print("\n\nx vext= ");
        for(int i=0;i<5;i++){
            System.out.printf("%-11.4f ",xVector[i]);
        }
        System.out.println("\n\nИтерация #"+iter);
        System.out.println("\nКоличество операций = "+countOfCalk);
        rMax(aMatrix,bVect,xVector);
    }
}
