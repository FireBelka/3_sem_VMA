#define _CRT_SECURE_NO_WARNINGS
#include <iomanip>
#include<iostream>
#include<vector>
#include <cmath>

using namespace std;
double firstNormMatrix(double mas[5][5]) {
	double r = -1;
	for (int i = 0; i < 5; i++) {
		double buf = 0;
		for (int j = 0; j < 5; j++) {
			buf += abs(mas[j][i]);
		}
		if (buf > r) {
			r = buf;
		}
	}
	return r;
}
pair<int, int> findMax(double** matrix, int n, int m) {
	double max = -1;
	pair<int, int> coordinates = { 0,0 };
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			if (fabs(matrix[i][j]) > max) {
				max = fabs(matrix[i][j]);
				coordinates = { i,j };
			}
		}
	}
	return coordinates;
}
pair<int, int> findMax2(double matrix[5][5], int n, int m) {
	double max = -1;
	pair<int, int> coordinates = { 0,0 };
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			if (fabs(matrix[i][j]) > max) {
				max = fabs(matrix[i][j]);
				coordinates = { i,j };
			}
		}
	}
	return coordinates;
}
double masToInt(double* mas, int n) {
	double out = 0;
	for (int i = 0; i < n; i++) {
		out *= 10;
		out += mas[i];
	}
	return out;
}
void sortToEMatrix(double** aMatrix, double** aRevMatrix, int n, int m, double* bVect) {
	for (int i = 0; i < n - 1; i++) {
		if (masToInt(aMatrix[i], m) < masToInt(aMatrix[i + 1], m)) {
			swap(aMatrix[i], aMatrix[i + 1]);
			swap(aRevMatrix[i], aRevMatrix[i + 1]);
			swap(bVect[i], bVect[i + 1]);
			i--;
		}
	}
}
void printDualMatrix(double** matrix, int n, int m) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			cout << setw(10) <<fixed<< setprecision(4) << matrix[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl;
	return;
}
void toUpperTriangleMatrix(double** aMatrix, int n, int m, double* bVect) {
	double aMatrixBuf[5][5];
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			aMatrixBuf[i][j] = aMatrix[i][j];
		}
	}
	
	for (int i = 0; i < n; i++) {
		pair<int, int> coordinates = findMax2(aMatrixBuf, n, m);
		for (int i = 0; i < 5; i++) {
			aMatrixBuf[i][coordinates.second] = 0;
			aMatrixBuf[coordinates.first][i] = 0;
		}
	
		double elem = aMatrix[coordinates.first][coordinates.second];
		for (int j = 0; j < m; j++) {
			aMatrix[coordinates.first][j] /= elem;
		}
		bVect[coordinates.first] /= elem;		
	}
	
	//for (int i = 0; i < 5; i++) {
	//	for (int j = 0; j < 5; j++) {
	//		if (aMatrix[i][j] == 1 && (i != j)) {
	//			swap(aMatrix[i], aMatrix[j]);
	//			swap(bVect[i], bVect[j]);
	//			i = 0;
	//			j = 0;
	//		}
	//	}
	//}
	//printDualMatrix(aMatrix, n, m);//в этом моменте 1 по диагонали


	for (int i = 0; i < 5; i++) {

		printDualMatrix(aMatrix, n, m);
		printDualMatrix(&bVect, 1, 5);
		double elem = aMatrix[i][i];
		for (int g = i; g < 5; g++) {
			aMatrix[i][g] /= elem;
		}
		bVect[i] /= elem;
	
		for (int j = i+1; j < 5; j++) {
			double elem2 = -aMatrix[j][i];
			for (int g = i; g < 5; g++) {
				aMatrix[j][g] += (aMatrix[i][g] * elem2);
			}
			bVect[j] += (bVect[i] * elem2);

		}
			
	}
	printDualMatrix(aMatrix, n, m);
	printDualMatrix(&bVect, 1, 5);
	//получаем ответ
	double xVect[5] = { 1,1,1,1,1 };
	xVect[4] = bVect[4];
	xVect[3] = bVect[3] - xVect[4] * aMatrix[3][4];
	xVect[2] = bVect[2] - xVect[4] * aMatrix[2][4] - xVect[3] * aMatrix[2][3];
	xVect[1] = bVect[1] - xVect[4] * aMatrix[1][4] - xVect[3] * aMatrix[1][3] - xVect[2] * aMatrix[1][2];
	xVect[0] = bVect[0] - xVect[4] * aMatrix[0][4] - xVect[3] * aMatrix[0][3] - xVect[2] * aMatrix[0][2]- xVect[1]* aMatrix[0][1];

	for (int i = 0; i < 5; i++) {
		bVect[i] = xVect[i];
	}
	return ;
}
double* toDlMatrix(double aMatrix[5][5], double** aRevMatrix, int n, int m, double bVect[5]) {
	int swapMas[5];
	pair<int, int> swapPlace[5];
	double* leadElements = new double[5];
	for (int i = 0; i < n; i++) {
		pair<int, int> coordinates = findMax2(aMatrix, n, m);
		swapPlace[i] = coordinates;
		swapMas[i] = coordinates.first;
		double elem = aMatrix[coordinates.first][coordinates.second];
		leadElements[i] = elem;
		for (int j = 0; j < m; j++) {
			aMatrix[coordinates.first][j] /= elem;
			aRevMatrix[coordinates.first][j] /= elem;
		}
		bVect[coordinates.first] /= elem;		
		swap(aMatrix[0], aMatrix[coordinates.first]);
		swap(aRevMatrix[0], aRevMatrix[coordinates.first]);
		swap(bVect[0], bVect[coordinates.first]);

		for (int j = 1; j < n; j++) {
			double elem2 = -aMatrix[j][coordinates.second];
			for (int g = 0; g < m; g++) {
				aMatrix[j][g] += (aMatrix[0][g] * elem2);
				aRevMatrix[j][g] += (aRevMatrix[0][g] * elem2);
			}
			bVect[j] += (bVect[0] * elem2);
		}		
		aMatrix[0][coordinates.second] = 0;
	}
	for (int i = n - 1; i >= 0; i--) {
		swap(aMatrix[0], aMatrix[swapMas[i]]);
		swap(aRevMatrix[0], aRevMatrix[swapMas[i]]);
		swap(bVect[0], bVect[swapMas[i]]);
		aMatrix[swapPlace[i].first][swapPlace[i].second] = 1;
	}
	/*sortToEMatrix(aMatrix, aRevMatrix, 5, 5, bVect);*/
	return leadElements;
}
double* discrep(double aMatrix[5][5], int n, int m, double* xVect, double yVect[5]) {//невязка r=Ax-y
	double* axVect = new double[n];
	for (int i = 0; i < n; i++) {
		axVect[i] = 0;
	}
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			axVect[i] += (aMatrix[i][j] * xVect[j]);
		}
	}
	for (int i = 0; i < m; i++) {
		axVect[i] -= yVect[i];
	}
	return axVect;
}
double Rcalk(double aMatrix[5][5], double** aRevMatrix) {
	double r=-1;
	double mas[5][5];
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			mas[i][j] = 0;
		}
	}
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			for (int g = 0; g < 5; g++) {
				mas[i][j] += aMatrix[i][g] * aRevMatrix[g][j];
			}		
		}
	}
	for (int i = 0; i < 5; i++) {
		mas[i][i] -= 1;
	}
	
	for (int i = 0; i < 5; i++) {
		double buf = 0;
		for (int j = 0; j < 5; j++) {
			buf += abs(mas[j][i]);
		}
		if (buf > r) {
			r = buf;
		}
	}
	cout << "R matrix = A*(A-1)-E matrix: " << endl;
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			cout << setw(15)  << setprecision(4)<<mas[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl;

	cout << "||R||1:" << endl;
	//cout << findMax2(mas, 5, 5).first<<" "<< findMax2(mas, 5, 5).second;
	return r;
}
int main(void) {
	setlocale(LC_ALL, "Rus");
	double aMatrixBuf[5][5] = { {0.4974,0.0000,-0.1299,0.0914,0.1523},{-0.0305,0.3248,0,-0.0619,0.0203},{0.0102,-0.0914,0.5887,0.0112,0.0355},{0.0305,0,-0.0741,0.5887,0},{0.0203,-0.0305,0.1472,-0.0122,0.4263} };
	double aMatrixBuf2[5][5] = { {0.4974,0.0000,-0.1299,0.0914,0.1523},{-0.0305,0.3248,0,-0.0619,0.0203},{0.0102,-0.0914,0.5887,0.0112,0.0355},{0.0305,0,-0.0741,0.5887,0},{0.0203,-0.0305,0.1472,-0.0122,0.4263} };
	double aRevMatrixBuf[5][5] = { {1,0,0,0,0},{0,1,0,0,0},{0,0,1,0,0},{0,0,0,1,0},{0,0,0,0,1} };
	double bVectBuf[5] = { 1.5875,-1.7590,1.4139,1.7702,-2.0767 };
	double bVectBuf2[5] = { 1.5875,-1.7590,1.4139,1.7702,-2.0767 };
	double** aMatrix = new double* [5];
	double** aRevMatrix = new double* [5];
	double* bVect = new double[5];
	double xVect[5];
	for (int i = 0; i < 5; i++) {
		bVect[i] = bVectBuf[i];
		aMatrix[i] = new double[5];
		aRevMatrix[i] = new double[5];
		for (int j = 0; j < 5; j++) {
			aMatrix[i][j] = aMatrixBuf[i][j];
			aRevMatrix[i][j] = aRevMatrixBuf[i][j];
		}
	}
	cout << "Приведение к верхнетреугольной матрице" << endl << endl;
	double conditioningNumber = aMatrix[findMax(aMatrix, 5, 5).first][findMax(aMatrix, 5, 5).second];//число обусловленностей ||a||
	toUpperTriangleMatrix(aMatrix, 5, 5, bVect);
	double* leadElements = toDlMatrix(aMatrixBuf2, aRevMatrix, 5, 5, bVectBuf2); //решение + возврат ведущих эл-тов
	int countOfOperations = 6 + 12 * 4 + 5 + 10 * 3 + 4 + 8 * 2 + 3 + 6 * 1 + 2; 
	countOfOperations += (2 + 4 + 6 + 8);
	double determinator = leadElements[0]; //определитель исх матрицы
	for (int i = 1; i < 5; i++) {
		determinator *= leadElements[i];
	}
	conditioningNumber *= aRevMatrix[findMax(aRevMatrix, 5, 5).first][findMax(aRevMatrix, 5, 5).second];//||A||*||a-1||
	 //в вект b находится решение
	double* rVect = discrep(aMatrixBuf, 5, 5, bVect, bVectBuf);
	//-------------------------------------------------------
	//cout << endl << endl << endl << endl;
	cout << "Results:" << endl;
	cout << "A matrix:" << endl;
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			cout << setw(10) << fixed << setprecision(4) << aMatrixBuf[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl;

	cout << "X vector:" << endl;
	printDualMatrix(&bVect, 1, 5);

	cout << "B vector:" << endl;
	for (int i = 0; i < 5; i++) {
		cout << setw(10) << fixed << setprecision(4) << bVectBuf[i] << " ";
	}
	cout << endl << endl;

	cout << "A-1 matrix:" << endl;
	printDualMatrix(aRevMatrix, 5, 5);

	cout << "Determinator of A matrix:" << endl;
	cout << fixed << setprecision(4) << determinator << endl << endl;

	cout << "r=Ax-B vector:" << endl;
	cout.unsetf(ios::fixed);
	for (int i = 0; i < 5; i++) {
		cout << rVect[i] << " ";
	}
	cout << endl << endl;

	cout << "||r||1:" << endl;
	cout.setf(ios::scientific);
	cout  << rVect[findMax(&rVect, 1, 5).second];
	cout << endl<<endl;

	//cout << "||R||1:" << endl;
	cout  << Rcalk(aMatrixBuf,aRevMatrix);
	cout << endl;

	cout.unsetf(ios::scientific);
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++) {
			aRevMatrixBuf[i][j] = aRevMatrix[i][j];
		}
	}
	cout << endl<<"V=||A||*||A^(-1)||=" << (firstNormMatrix(aMatrixBuf) * firstNormMatrix(aRevMatrixBuf));
	cout << endl << "Количество операций при нахождении х - "<<countOfOperations;
	return 0;
}
