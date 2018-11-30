#include <stdio.h>
#include <math.h>
#include <time.h>
/*
 * pi_bbp_bench.c
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 * A PiBBP.java-ból kivettük az "objektumorientáltságot", így kaptuk
 * a PiBBPBench osztályt, amit pedig átírtuk C nyelvre.
 *
 */

/*
 * 16^n mod k
 * [BBP ALGORITMUS] David H. Bailey: The
 * BBP Algorithm for Pi. alapján.
 */
long
n16modk (int n, int k)
{
  long r = 1;

  int t = 1;
  while (t <= n)
    t *= 2;

  for (;;)
    {

      if (n >= t)
	{
	  r = (16 * r) % k;
	  n = n - t;
	}

      t = t / 2;

      if (t < 1)
	break;

      r = (r * r) % k;

    }

  return r;
}

/* {16^d Sj}
 * [BBP ALGORITMUS] David H. Bailey: The
 * BBP Algorithm for Pi. alapján.
 */
double
d16Sj (int d, int j)
{

  double d16Sj = 0.0;
  int k;

  for (k = 0; k <= d; ++k)
    d16Sj += (double) n16modk (d - k, 8 * k + j) / (double) (8 * k + j);

  /*
     for(k=d+1; k<=2*d; ++k)
     d16Sj += pow(16.0, d-k) / (double)(8*k + j);
   */

  return d16Sj - floor (d16Sj);
}

/*
 * {16^d Pi} = {4*{16^d S1} - 2*{16^d S4} - {16^d S5} - {16^d S6}}
 * [BBP ALGORITMUS] David H. Bailey: The
 * BBP Algorithm for Pi. alapján.
 */
int main ()
{

  double d16Pi = 0.0;

  double d16S1t = 0.0;
  double d16S4t = 0.0;
  double d16S5t = 0.0;
  double d16S6t = 0.0;

  int jegy;
  int d;

  clock_t delta = clock ();

  for (d = 100000000; d < 100000001; ++d)
    {

      d16Pi = 0.0;

      d16S1t = d16Sj (d, 1);
      d16S4t = d16Sj (d, 4);
      d16S5t = d16Sj (d, 5);
      d16S6t = d16Sj (d, 6);

      d16Pi = 4.0 * d16S1t - 2.0 * d16S4t - d16S5t - d16S6t;

      d16Pi = d16Pi - floor (d16Pi);

      jegy = (int) floor (16.0 * d16Pi);

    }

  printf ("%d\n", jegy);
  delta = clock () - delta;
  printf ("%f\n", (double) delta / CLOCKS_PER_SEC);
  return 0;
}
