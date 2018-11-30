void Display::printParams(std::ostream& os) const
{
    Product::printParams(os);
    os << ", " << "InchWidth: " << inchWidth;
    os << ", " << "inchHeight: " << inchHeight;
}

void Product::writeParamsToStream(std::ostream& os) const
{
    char strDateOfAcquisition[9];
    tm* t = localtime(&dateOfAcquistion);
    strftime(strDateOfAcquisition, 9, "%Y%m%d", t);
    os << ' ' << name << ' ' << initialPrice << ' ' << strDateOfAcquisition;
}

void Display::writeParamsToStream(std::ostream& os) const
{
    Product::writeParamsToStream(os);
    os << ' ' << inchWidth << ' ' << inchHeight;
}

void Display::loadParamsFromStream(std::istream& is)
{
    Product::loadParamsFromStream(is);
    is >> inchWidth >> inchHeight;
}
