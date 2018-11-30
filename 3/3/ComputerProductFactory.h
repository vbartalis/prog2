#include "ProductFactory.h"

class ComputerProductFactory: public ProductFactory
{
public:
    Product* CreateProduct(char typeCode) const;
}
