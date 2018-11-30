//  g++ perceptron.cpp -o perceptron -lpng -std=c++11
//  ./perceptron Galaxy2.png
#include <iostream>
#include <cstdarg>
#include <map>
#include <iterator>
#include <cmath>
#include <random>
#include <limits>
#include <png++/png.hpp>
#include <string>


int count=0;


class Perceptron
{
public:
  Perceptron ( png::image <png::rgb_pixel> img, int nof, ... )
  {
    n_layers = nof;

    units = new double*[n_layers];
    n_units = new int[n_layers];

    va_list vap;

    va_start ( vap, nof );

    for ( int i {0}; i < n_layers; ++i )
      {
        n_units[i] = va_arg ( vap, int );

        if ( i ) {
          units[i] = new double [n_units[i]];
        }

      }

    va_end ( vap );
    weights = new double**[n_layers-1];

    std::random_device init;
    std::default_random_engine gen {init() };
    std::uniform_real_distribution<double> dist ( -1.0, 1.0 );

//    /*
int size = img.get_width() *img.get_height();
  double* image_red = new double[size];
  double* image_blue = new double[size];
  double* image_green = new double[size];
  for(int i {0}; i<img.get_width(); ++i)
    for(int j {0}; j<img.get_height();++j){
      image_red[i*img.get_width()+j]=img[i][j].red;
      image_blue[i*img.get_width()+j]=img[i][j].blue;
  		image_green[i*img.get_width()+j]=img[i][j].green;
    }
//    */
    for ( int i {1}; i < n_layers; ++i )
      {
        weights[i-1] = new double *[n_units[i]];

        for ( int j {0}; j < n_units[i]; ++j )
          {
            weights[i-1][j] = new double [n_units[i-1]];

            for ( int k {0}; k < n_units[i-1]; ++k )
              {
                weights[i-1][j][k] = dist ( gen );


              }
          }
      }
//    /*
      *image_red =( *this) ( image_red );
      *image_blue =( *this) ( image_blue );
      *image_green =( *this) ( image_green );

      for(int i {0}; i<img.get_width(); ++i)
      	for(int j {0}; j<img.get_height();++j) {
      	//	img[i][j].red=image_red[i*img.get_width()+j];
      		img[i][j].blue=image_blue[i*img.get_width()+j];
      		img[i][j].green=image_green[i*img.get_width()+j];
      	}

        img.write ("percOut.png"); //mentjük a képet
        std::cout  << " img mentve percOut.png" << std::endl;
//      */
  }

  double sigmoid ( double x )
  {

    return 1.0/ ( 1.0 + exp ( -x ) );
	return x;
  }

  double operator() ( double image [] )
  {
    units[0] = image;

    for ( int i {1}; i < n_layers; ++i )
      {
        for ( int j {0}; j < n_units[i]; ++j )
          {
            units[i][j] = 0.0;

            for ( int k {0}; k < n_units[i-1]; ++k )
              {
                units[i][j] += weights[i-1][j][k] * units[i-1][k];
              }

            units[i][j] = sigmoid( units[i][j] );

          }
      }
	for(int i=0; i<n_units[n_layers-1]; i++){
		image[i]=image[i]*units[n_layers-1][i];
//    std::cout << image[i] << "  " << units[n_layers-1][i] << " || ";
	}
//    return sigmoid( units[n_layers - 1][0] );
/**/    return *image;

  }

  void learning ( double image [], double q, double prev_q )
  {
    double y[1] {q};

    learning ( image, y );
  }

  void learning ( double image [], double y[] )
  {
    ( *this ) ( image );

    units[0] = image;

    double ** backs = new double*[n_layers-1];

    for ( int i {0}; i < n_layers-1; ++i )
      {
        backs[i] = new double [n_units[i+1]];
      }

    int i {n_layers-1};

    for ( int j {0}; j < n_units[i]; ++j )
      {
        backs[i-1][j] = sigmoid ( units[i][j] ) * ( 1.0-sigmoid ( units[i][j] ) ) * ( y[j] - units[i][j] );

        for ( int k {0}; k < n_units[i-1]; ++k )
          {
            weights[i-1][j][k] += ( 0.2* backs[i-1][j] *units[i-1][k] );
          }

      }

    for ( int i {n_layers-2}; i >0 ; --i )
      {
        for ( int j {0}; j < n_units[i]; ++j )
          {

            double sum = 0.0;

            for ( int l {0}; l < n_units[i+1]; ++l )
              {
                sum += 0.19*weights[i][l][j]*backs[i][l];
              }

            backs[i-1][j] = sigmoid ( units[i][j] ) * ( 1.0-sigmoid ( units[i][j] ) ) * sum;

            for ( int k {0}; k < n_units[i-1]; ++k )
              {
                weights[i-1][j][k] += ( 0.19* backs[i-1][j] *units[i-1][k] );
              }
          }
      }

    for ( int i {0}; i < n_layers-1; ++i )
      {
        delete [] backs[i];
      }

    delete [] backs;

  }

  ~Perceptron()
  {
    for ( int i {1}; i < n_layers; ++i )
      {
        for ( int j {0}; j < n_units[i]; ++j )
          {
            delete [] weights[i-1][j];
          }

        delete [] weights[i-1];
      }

    delete [] weights;

    for ( int i {0}; i < n_layers; ++i )
      {
        if ( i )
          delete [] units[i];
      }

    delete [] units;
    delete [] n_units;


  }

private:
  Perceptron ( const Perceptron & );
  Perceptron & operator= ( const Perceptron & );

  int n_layers;
  int* n_units;
  double **units;
  double ***weights;

};


int main(int argc, char *argv[]){
png::image <png::rgb_pixel> png_image ( argv[1] );

int size = png_image.get_width() *png_image.get_height();

Perceptron* p = new Perceptron(png_image, 3, size, 256, size);


delete p;


}
