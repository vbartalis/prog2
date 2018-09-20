// ez a T az LSP-ben
class Madar {
//public:
//  void repul(){};
};

// ez a két osztály alkotja a "P programot" az LPS-ben
class Program {
public:
     void fgv ( Madar &madar ) {
          // madar.repul(); a madár már nem tud repülni
          // s hiába lesz a leszármazott típusoknak
          // repül metódusa, azt a Madar& madar-ra úgysem lehet hívni
     }
};

// itt jönnek az LSP-s S osztályok
class RepuloMadar : public Madar {
public:
     virtual void repul() {};
};

class Sas : public RepuloMadar
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
     program.fgv ( pingvin );

}


