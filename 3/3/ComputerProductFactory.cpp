Product* ComputerProductFactory::CreateProduct(char typeCode) const
{
    switch (typeCode)
    {
        case 'd':
            return new Display();
        case 'h':
            return new HardDisk();
        case 'c':
            return new ComputerConfiguration();
    }
    return NULL;
}
