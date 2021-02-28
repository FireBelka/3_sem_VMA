package com.lab4;
import java.util.ArrayList;
public class Main {
    public static double fi(double lambda){
        double f=Math.pow(lambda,5)+1651*Math.pow(lambda,4)/1250-2587733*Math.pow(lambda,3)/4000000+18054762949f*Math.pow(lambda,2)/125000000000f-144478997814207f*lambda/10000000000000000f+26093591614199079f/50000000000000000000f;
        return f;
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
    public static void multiMatrixAndVector(double aMatrix[][],double vect[],double multiVector[]) {
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
    public static double[] gaussMethod(double ainMatrix[][],double binVect[]){
        double xVect[]=new double[5];
        double aMatrix[][]=new double[5][5];
        for(int i=0;i<5;i++){
            aMatrix[i]=ainMatrix[i].clone();
        }

        double bVect[]=binVect.clone();
        for(int i=0;i<5;i++){
            double leadElem=aMatrix[i][i];
            double buf;
            for(int j=0;j<5;j++){
                buf=aMatrix[0][j];
                aMatrix[0][j]=aMatrix[i][j];
                aMatrix[i][j]=buf;
            }
            buf=bVect[0];
            bVect[0]=bVect[i];
            bVect[i]=buf;
            for(int j=0;j<5;j++){
                aMatrix[0][j]/=leadElem;
            }
            bVect[0]/=leadElem;

            for(int j=1;j<5;j++) {
                double del=aMatrix[j][0];
                for (int g = 0; g < 5; g++) {
                    aMatrix[j][g] -= aMatrix[0][g] * del;
                }
                bVect[j] -= bVect[0] * del;
            }
        }
        xVect[4]=bVect[0];
        xVect[0]=bVect[1];
        xVect[1]=bVect[2];
        xVect[2]=bVect[3];
        xVect[3]=bVect[4];
        return xVect;
    }
    public static void main(String[] args) {
        double aMatrix[][] = { {0.4974,0.0000,-0.1299,0.0914,0.1523},{-0.0305,0.3248,0,-0.0619,0.0203},{0.0102,-0.0914,0.5887,0.0112,0.0355},{0.0305,0,-0.0741,0.5887,0},{0.0203,-0.0305,0.1472,-0.0122,0.4263} };
        double aSimMatrix[][]=new double[5][5];
        multiMatrixTAndMatrix(aMatrix,aSimMatrix);
        double yMatrix[][]=new double[6][5];
        yMatrix[5][0]=1;
        yMatrix[5][1]=0;
        yMatrix[5][2]=0;
        yMatrix[5][3]=0;
        yMatrix[5][4]=0;
        for(int i=4;i>=0;i--){
            multiMatrixAndVector(aSimMatrix, yMatrix[i+1], yMatrix[i]);
        }
        double yVect[]=yMatrix[0].clone();
        double bufMatrix[][]=new double[5][5];
        for(int i=0;i<5;i++){
            for(int j=1;j<6;j++) {
                bufMatrix[i][j-1] = yMatrix[j][i];
            }
        }
       // double qVect[]=GaussMethodClass.gaussMethod(bufMatrix,yVect);
        double qVect[]=new double[5];
        qVect[0]=(double)((double)1651/(double)1250);
        qVect[1]=(double)((double)2587733/(double)4000000);
        qVect[2]=(double)((double)18054762949f/(double)125000000000f);
        qVect[3]=(double)((double)144478997814207f/(double)10000000000000000f);
        qVect[4]=(double)((double)26093591614199079f/(double)50000000000000000000f);
        System.out.printf("λ^5+%6.4e*λ^4-%6.4e*λ^3+%6.4e*λ^2+%6.4e*λ+%6.4e",qVect[0],qVect[1],qVect[2],qVect[3],qVect[4]);
        ArrayList<Double> lambdas=new ArrayList<Double>();
        for(int i=-10000001;i<10000001;i++){
            if(Math.abs(fi((i*0.0000001)))<=0.00001)
            {
                lambdas.add((i*0.0000001));
            }
        }
        for(int i=0;i< lambdas.size();i++){
            System.out.printf("\n%6.4f ",lambdas.get(i));
        }
    }
}
