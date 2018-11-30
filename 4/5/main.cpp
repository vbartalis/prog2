//g++ mlp.hpp main.cpp -o main -lpng -std=c++11

#include <iostream>
#include "mlp.hpp"
#include <png++/png.hpp>

int main(int argc, char *argv[]){
png::image <png::rgb_pixel> png_image ( argv[1] );

int size = png_image.get_width() *png_image.get_height();

Perceptron* pR = new Perceptron(3, size, 256, 256);
Perceptron* pB = new Perceptron(3, size, 256, size);
Perceptron* pG = new Perceptron(3, size, 256, 64);
double* image_red = new double[size];
double* image_blue = new double[size];
double* image_green = new double[size];
for(int i {0}; i<png_image.get_width(); ++i)
	for(int j {0}; j<png_image.get_height();++j){
		image_red[i*png_image.get_width()+j]=png_image[i][j].red;
		image_blue[i*png_image.get_width()+j]=png_image[i][j].blue;
		image_green[i*png_image.get_width()+j]=png_image[i][j].green;
	}

//double value=( *pR ) ( image_red );



*image_red =( *pR) ( image_red );
*image_blue =( *pB) ( image_blue );
*image_green =( *pG) ( image_green );

	//////////////////////////////////////

/*
	int szelesseg = 600, magassag = 600, iteraciosHatar = 1000;
   	png::image <png::rgb_pixel> kep (szelesseg, magassag);
	for (int j=0; j<magassag; ++j) {
        	//sor = j;
        	for (int k=0; k<szelesseg; ++k) {

            		kep.set_pixel(k, j, png::rgb_pixel(255, 255, 255));
        	}
	}
	*/
for(int i {0}; i<png_image.get_width(); ++i)
	for(int j {0}; j<png_image.get_height();++j) {
		png_image[i][j].red=image_red[i*png_image.get_width()+j];
		png_image[i][j].blue=image_blue[i*png_image.get_width()+j];
		png_image[i][j].green=image_green[i*png_image.get_width()+j];
	}
    	png_image.write ("output.png");
    	std::cout  << " kép mentve" << std::endl;


//std::cout<< value << std::endl;
delete pR;
delete pB;
delete pG;
delete [] image_red;
delete [] image_blue;
delete [] image_green;
}
