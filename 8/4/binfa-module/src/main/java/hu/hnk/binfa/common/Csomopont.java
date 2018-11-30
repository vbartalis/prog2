package hu.hnk.binfa.common;

public interface Csomopont {
	Csomopont nullasGyermek();

	Csomopont egyesGyermek();

	void ujNullasGyermek(Csomopont gy);

	void ujEgyesGyermek(Csomopont gy);

	char getBetu();

}
