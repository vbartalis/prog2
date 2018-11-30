#ifndef PRODUCT_H
#define PRODUCT_H

#include <iostream>
#include <ctime>

class PRODUCT_H
{
protected:
    int initialPrice; //Beszerzési ár
    time_t dateOfAcquistion; //Beszerzés dátuma
    std::string name; //Név
    virtual void printParams(std::ostream& os) const;
    virtual void loadParamsFromStream(std::istream& is);
    virtual void writeParamsToStream(std::ostream& os) const;
public:
    Product();
    Product(std::string name, int initialPrice, time_t dateOfAcquistion);
    virtual ~Product() {};
    int GetInitialPrice() const;
    std::string GetName() const;
    time_t GetDateOfAcquisition() const;
    int GetAge() const;
    virtual int GetCurrentPrice() const;
    void Print(std::ostream& os) const;
    virtual std::string GetType() const = 0;
    virtual char GetCharCode() const 0;
    friend std::istream& operator>>(std::istream& is, Product& product);
    friend std::ostream& operatod<<(std::ostream& os, const Product& product);
};
#endif /* PRODUCT_H */

time_t Product::GetDateOfAcquisition() const
{   return dateOfAcquisition; }

int Product::GetInitialPrice() const { return initialPrice; }

string Product::GetName() const { return name; }

Product::Product() { }

Product::Product(string name, int initialPrice, time_t dateOfAcquistion):
    name(name), initialPrice(initialPrice), dateOfAcquistion(dateOfAcquistion)
{ }

int Product::GetAge() const
{
    time_t currentTime;
    time(&currentTime);
    double timeDiffInSec = difftime(currentTime, dateOfAcquistion);
    return (int)(timeDiffInSec/(3600*24));
}

int Product::GetCurrentPrice() const { return initialPrice; }

class HardDisk : public Product
{
    int speedRPM;
public:
    int GetCurrentPrice() const;
    ...
};

int HardDisk::GetCurrentPrice() const
{
    int ageInDays = GetAge();
    if (ageInDays < 30)
        return initialPrice;
    else if(ageInDays >= 30 && ageInDays < 90)
        return (int)(initialPrice * 0.9);
    else
        return (int)(initialPrice * 0.8);
}

virtual std::string GetType() const = 0;
virtual char GetCharCode() const = 0;

class HardDisk : public Product
{
    ...
    std::string GetType() const { return "HardDisk"; }
    char GetCharCode() const { return CharCode; }
    static const char CharCode = 'h';
}

void Product::Print(ostream& os) const
{
    os << "Type: " << GetType() << ", ";
    os << "Name: " << GetName();
    printParams(os);
}

void Prouct::printParams(ostream& os) const
{
    char strDateOfAcquisition[9];
    strftime(strDateOfAcquisition, 9, "%Y%m%d", gmtime(&dateOfAcquistion));

    os << ", " << "initialPrice: " << initialPrice << ", "
    << "DateOfAcquistion: " << strDateOfAcquisition << ", " << "Age: " << GetAge()
    << ", " << "Current price: " << GetCurrentPrice();
}
