//	g++ jdk.cpp -o jdk -lboost_system -lboost_filesystem
//	./jdk /home/zenol/Desktop/prog2misc/5het/1/src


#include "boost/filesystem/operations.hpp" // includes boost/filesystem/path.hpp
#include "boost/filesystem/fstream.hpp"    // ditto
#include "boost/filesystem.hpp"
#include <iostream>                        // for std::cout

using namespace boost::filesystem;
using namespace std;


void dirBejar(path p){
	try{
		if (exists(p)){
			if (is_regular_file(p)){
				if (p.has_extension()){
					if (p.extension().string() == ".java"){
						cout << p.filename() << endl;
					}
				}
			}
			else if (is_directory(p)){
				for(directory_entry& x: directory_iterator(p)){
					dirBejar(x.path());
				}
			}
		}
		else{
			cout << " does not exist" << endl;
		}
	}

	catch (const filesystem_error& ex)
  {
    cout << ex.what() << endl;
  }
}





int main(int argc, char* argv[])
{
  path p (argv[1]);   // p reads clearer than argv[1] in the following code
	dirBejar(p);

  return 0;
}
