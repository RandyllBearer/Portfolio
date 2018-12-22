#include <stdio.h>

int main() {

	//PART 1 (SIZEOF)
	printf("int\t\t%d bytes\n", sizeof(int));
	printf("short\t\t%d bytes\n", sizeof(short));
	printf("long\t\t%d bytes\n", sizeof(long));
	printf("long long\t%d bytes\n", sizeof(long long));
	printf("unsigned int\t%d bytes\n", sizeof(unsigned int));
	printf("char\t\t%d bytes\n", sizeof(char));
	printf("float\t\t%d bytes\n", sizeof(float));
	printf("double\t\t%d bytes\n", sizeof(double));
	printf("long double\t%d bytes\n", sizeof(long double));
	
	//PART2 (WEIGHTS)
	double userWeight = 0;
	double mercuryWeight = 0;
	double venusWeight = 0;
	double marsWeight = 0;
	double jupiterWeight = 0;
	double saturnWeight = 0;
	double uranusWeight = 0;
	double neptuneWeight = 0;
	double plutoWeight = 0;
	
	printf("\nPlease enter the weight you'd like to convert: ");
	scanf("%lf", &userWeight);
	mercuryWeight = userWeight * .38;
	venusWeight = userWeight * .91;
	marsWeight = userWeight * .38;
	jupiterWeight = userWeight * 2.54;
	saturnWeight = userWeight * 1.08;
	uranusWeight = userWeight * .91;
	neptuneWeight = userWeight * 1.19;
	plutoWeight = userWeight * .06;

	printf("\nHere is your weight on other planets:\n");
	printf("\nMercury\t\t%lf", mercuryWeight);
	printf("\nVenus\t\t%lf", venusWeight);
	printf("\nMars\t\t%lf", marsWeight);
	printf("\nJupiter\t\t%lf", jupiterWeight);
	printf("\nSaturn\t\t%lf", saturnWeight);
	printf("\nUranus\t\t%lf", uranusWeight);
	printf("\nNeptune\t\t%lf", neptuneWeight);
	printf("\nPluto\t\t%lf", plutoWeight);
	printf("\n");



}
