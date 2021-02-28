package com.lab4;
public class Main {
    public static void  rMax(double aMatrix[][],double bVect[],double xVect[]){
        double bufVect[]=new double[5];
        double a=0;
        System.out.print("r vect is\n");
        for(int i=0;i<5;i++){
            bufVect[i]=aMatrix[i][0]*xVect[0]+aMatrix[i][1]*xVect[1]+aMatrix[i][2]*xVect[2]+aMatrix[i][3]*xVect[3]+aMatrix[i][4]*xVect[4]-bVect[i];
            if(Math.abs(bufVect[i])>Math.abs(a)){
                a=bufVect[i];
            }
            System.out.printf("%-11.4e ",bufVect[i]);
        }
        System.out.printf("\n\n||r||1= %11.4e",a);
        return;
    }
    public static void main(String[] args) {
        int n=0;
        double Amatrix[][] = { {0.4974,0.0000,0,0,0},{-0.0305,0.3248,0,0,0},{0,-0.0914,0.5887,0.0112,0},{0,0,-0.0741,0.5887,0},{0,0,0,-0.0122,0.4263} };
        double bVect[] = { 1.5875,-1.7590,1.4139,1.7702,-2.0767 };
        double Pcoef[]=new double[5];
        double Qcoef[]=new double[5];
        double Xvect[]=new double[5];
        Pcoef[0]=-Amatrix[0][1]/Amatrix[0][0];
        Qcoef[0]=bVect[0]/Amatrix[0][0];
        n+=2;
        for(int i=1;i<4;i++){
            Pcoef[i]=Amatrix[i][i+1]/(-Amatrix[i][i-1]*Pcoef[i-1]-Amatrix[i][i]);
            Qcoef[i]=(-bVect[i]+Amatrix[i][i-1]*Qcoef[i-1])/(-Amatrix[i][i-1]*Pcoef[i-1]-Amatrix[i][i]);
            n+=8;
        }
        Xvect[4]=(-bVect[4]+Amatrix[4][3]*Qcoef[3])/(-Amatrix[4][3]*Pcoef[3]-Amatrix[4][4]);
        n+=5;
        for(int i=3;i>=0;i--){
            Xvect[i]=Pcoef[i]*Xvect[i+1]+Qcoef[i];
            n+=2;
        }
        System.out.println("A matrix is:");
        for (int i=0;i<5;i++){
            for(int j=0;j<5;j++) {
                System.out.printf("%-7.4f", Amatrix[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println("\nB vect is:");
        for(int j=0;j<5;j++) {
            System.out.printf("%-7.4f", bVect[j]);
        }
        System.out.println("\n\nX vect is:");
        for (int i=0;i<5;i++){
            System.out.printf("%-11.4f ",Xvect[i]);
        }
        System.out.println("\n\nКоличество арифметических операций - "+n);
        rMax(Amatrix,bVect,Xvect);
    }
}
