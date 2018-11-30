#include <iostream>

using namespace std;

class Osztaly
{
public:
	Osztaly() //Alap konstruktor
	{
	}

	Osztaly (const Osztaly &t)
	{
		cout << "Másoló konstruktor.." << endl;
	}

	Osztaly & operator= (const Osztaly &t)
	{
		cout << "Másoló értékadás.." << endl;
	}

	Osztaly (Osztaly && t)
	{
		cout << "Mozgató konstruktor.." << endl;
	}

	Osztaly & operator= (Osztaly &&t)
	{
		cout << "Mozgató értékadás.." << endl;
	}
};

int main()
{
	Osztaly c1, c2, c4, c6;
	Osztaly c3 = c1; //vagy Osztaly c3(c1)
	c2 = c1;
	Osztaly c5 = move(c2);
	c6 = move(c5);
	return 0;
}
