// ez a T az LSP-ben
class Madar {
public:
     virtual void repul() {};
};

// ez a két osztály alkotja a "P programot" az LPS-ben
class Program {
public:
     void fgv ( Madar &madar ) {
          madar.repul();
     }
};

// itt jönnek az LSP-s S osztályok
class Sas : public Madar
{};

class Pingvin : public Madar // ezt úgy is lehet/kell olvasni, hogy a pingvin tud repülni
{};

int main ( int argc, char **argv )
{
     Program program;
     Madar madar;
     program.fgv ( madar );

     Sas sas;
     program.fgv ( sas );

     Pingvin pingvin;
     program.fgv ( pingvin ); // sérül az LSP, mert a P::fgv röptetné a Pingvint, ami ugye lehetetlen.

}


