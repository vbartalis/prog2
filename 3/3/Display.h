class Display : public Product
{
    int inchWidth;
    int inchHeight;

protected:
    void printParams(std::ostream& os) const;
    ...
}
