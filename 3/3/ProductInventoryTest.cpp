#include <fstream>
#include "ProductInventory.h"
#include "ProductFactory.h"
#include "ComputerProductFactory.h"
#include "Display.h"
#include "HardDisk.h"

using namespace std;

void ReadInvFromFileTest(ProductInventory& inv);
void WriteInvToFileTest(ProductInventory& inv);

int main(int argc, char* argv[])
{
    try
    {
        ProductFactory::Init(new ComputerProductFactory());

        // Teszt1: készlet létrehozása és kiírása a kimenetre.
        cout << "Test1: creating inventory and printing it to the screen." << endl;
        time_t currentTime;
        time(&currentTime);
        ProductInventory inv1;
        inv1.AddProduct(new Display("TFT1", 30000, currentTime, 13, 12));
        inv1.AddProduct(new HardDisk("WD", 25000, currentTime, 7500));
        inv1.PrintProducts(cout);
        //várunk billentyűleütésre
        cout << "Press any key to continue...";
        cin.get();
        cout << endl;

        cout << "Test2: loading inventory from a file (computerproducts.txt), printint it, and then writing it to a file (computerproducts_out.txt)." << endl;
        ProductInventory inv2;
        ReadInvFromFileTest(inv2);
        WriteInvToFileTest(inv2);
        cout << endl;
        cout << "Done.";
        //várunk billentyűleütésre
        cin.get();

        return 0;
    }
    catch(const std::exception& e)
    {
        cerr << "There was an error: " << endl;
        cerr << e.what() << endl;
    }
    catch(...)
    {
        cout << "Unexpected error occured." << endl;
    }
    void ReadInvFromFileTest(ProductInventory& inv)
    {
        ifstream fs("../TestData/computerproducts.txt");
        if (!fs)
        {
            cerr << "Error pening file." << endl;
            return;
        }
        inv.ReadInventory(fs);
        cout << "The content of computerproducts.txt is:" << endl;
        inv.PrintProducts(cout);
        cout << endl;
    }

    void WriteInvToFileTest(ProductInventory& inv)
    {
        ofstream fs("../TestData/computerproducts_out.txt");
        if (!fs)
        {
            cerr << "Error opening file." << endl;
            return;
        }
        inv.WriteInventory(fs);
        cout << "The content of the inventory has been written to computerproducts_out.txt." << endl;
    }
}
