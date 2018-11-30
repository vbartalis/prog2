
#include <cxxabi.h>
#include <iostream>
#include <sys/stat.h>
#include <sys/shm.h>
#include <unistd.h>
#include <vector>

class Arena {
	
	char* p;
	int s;

public:
	char *q;
	int nnn {0};
	Arena(char* p, int s):p(p),s(s){
		q = p+100;
	}
};

template<typename T>
struct CustomAlloc {

	
	using size_type = size_t;
	using value_type = T;
	using pointer = T*;
	using const_pointer = const T*;
	using reference = T&;
	using const_reference = const T&;
	using difference_type = ptrdiff_t;


	Arena& arena;
	CustomAlloc (Arena& arena):arena(arena){}
	int nn {0};

	pointer allocate (size_type n){
		++arena.nnn;
		++nn;
		int s;
		char* p=abi::__cxa_demangle(typeid (T).name(), 0, 0, &s);
		std::cout << "Allocating "
				  << n << " objects of "
				  << n*sizeof(T)
				  << " bytes"
				  << typeid (T).name() << "=" << p
				  << " hivasok szama " << nn
				  << " hivasok szama " << arena.nnn
				  << std::endl;

		free(p);

		char *q = arena.q;
		std::cout << "q " << static_cast <const void *> ( q) << std::endl;

		arena.q += n*sizeof(T);
		return reinterpret_cast<T*>(q);
	}
	void deallocate (pointer p, size_type n){

	}
};



int main(int argc, char* argv[]){
	int osztott_memoria;
	char *osztott_memoria_terulet;
	if((osztott_memoria = shmget(ftok(".",44), 10*1024*1024, IPC_CREAT | S_IRUSR | S_IWUSR)) == -1){
		perror("shmget");
		exit(EXIT_FAILURE);
	}

	if((osztott_memoria_terulet = (char *) shmat (osztott_memoria, NULL, 0)) < 0){
		perror("shmat");
		exit(EXIT_FAILURE);
	}

	Arena arena {osztott_memoria_terulet, 10*1024*1024};

	CustomAlloc<int> allocobj {arena};

	int gyermekem_pid;
	using TP = std::vector< int, CustomAlloc<int> >;

	if((gyermekem_pid = fork()) == 0){
		TP* v = new (osztott_memoria_terulet) TP {allocobj};

		v->push_back(42);
		v->push_back(43);
		v->push_back(44);

		std::cout << "v " << static_cast <const void *> (v) << std::endl;
		std::cout << "o " << static_cast <const void *> (osztott_memoria_terulet) << std::endl;

		if(shmdt(osztott_memoria_terulet) == -1){
			perror("shmdt");
			exit (EXIT_FAILURE);
		}
		exit(EXIT_SUCCESS);
		
	}else if(gyermekem_pid > 0){
			sleep (5);
			TP *vp = (TP*) osztott_memoria_terulet;

			std::cout << "0 " << static_cast <const void *> (vp) << std::endl;
			std::cout << (*vp) [0] << std::endl;
			std::cout << (*vp) [1] << std::endl;
			std::cout << (*vp) [2] << std::endl;

			for(int i:*vp){
				std::cout << i << std::endl;
			}

			if(shmdt(osztott_memoria_terulet) == -1){
				perror("shmdt");
				exit(EXIT_FAILURE);
			}
	}

	return 0;
}