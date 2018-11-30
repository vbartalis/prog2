/**
 * @brief FUTURE DEBRECEN, Prog1 Lightsaber
 *
 * @file fenykard.cpp
 * @author  Norbert Bátfai <nbatfai@gmail.com>
 * @version f8ls.0.0.2
 *
 * @section LICENSE
 *
 * Copyright (C) 2018 Norbert Bátfai, batfai.norbert@inf.unideb.hu, PhD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @section DESCRIPTION
 * This program supports an alternative grading scale for the
 * course "High level programming language I".
 *
 * $ g++ fenykard.cpp -o fenykard -lboost_system -lboost_filesystem -lboost_program_options -std=c++11
 * $ ./fenykard
 * 
 */

#include <iostream>
#include <string>
#include <map>
#include <iomanip>

#include <boost/filesystem.hpp>
#include <boost/filesystem/fstream.hpp>
#include <boost/program_options.hpp>
#include <boost/tokenizer.hpp>

int get_points(boost::filesystem::path file)
{
    int points {0};

    boost::filesystem::ifstream ifs {file};

    std::string line;

    while (std::getline(ifs, line)) {
        int propeof = line.find_last_not_of("0123456789 \t");
        std::string val = line.substr(propeof + 1);

        points += std::stoi(val);
    }

    return points;
}

int sum_points(std::map<std::string, int> &acts)
{
    int sum = std::accumulate(acts.begin(), acts.end(), 0,
    [](int total, std::pair<std::string, int> pair) {
        return total + pair.second;
    });

    return sum;
}

void read_acts(boost::filesystem::path path, std::map <std::string, int> &acts)
{

    if (is_regular_file(path)) {

        std::string ext(".props");
        if (!ext.compare(boost::filesystem::extension(path))) {

            std::string actpropspath = path.string();
            std::size_t end = actpropspath.find_last_of("/");
            std::string act = actpropspath.substr(0, end);

            acts[act] = get_points(path);

            std::cout << std::setw(4) << acts[act] << "    " << act << std::endl;
        }


    } else if (is_directory(path))
        for (boost::filesystem::directory_entry & entry : boost::filesystem::directory_iterator(path))
            read_acts(entry.path(), acts);

}

int main(int argc, char *argv[])
{

    std::string defops = "City/Debrecen/Oktatás/Informatika/Programozás/DEIK/Prog1/Példák/Előadás;City/Debrecen/Szórakozás/Könyv/Ismeretterjesztő/Informatika;City/Debrecen/Oktatás/Informatika/Programozás/Tankönyv\ olvasás";

    boost::program_options::options_description desc("Options");
    desc.add_options()
    ("version", "print version number")
    ("help", "print help message")
    ("root", boost::program_options::value< std::string > ()->default_value(defops),
     " the name of a subdirectory of the FUTURE activity tree to be processed by this program. The default value is City/Debrecen/Oktatás/Informatika/Programozás/DEIK/Prog1/Példák/Előadás")
    ;

    boost::program_options::variables_map vm;
    boost::program_options::store(boost::program_options::parse_command_line(argc, argv, desc), vm);
    boost::program_options::notify(vm);

    if (vm.count("version")) {
        std::cout << "FUTURE DEBRECEN, Prog1 Lightsaber, f8ls.0.0.2" << std::endl
                  << "Copyright (C) 2018 Norbert Bátfai\n" << std::endl
                  << "License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>" << std::endl
                  << "This is free software: you are free to change and redistribute it." << std::endl
                  << "There is NO WARRANTY, to the extent permitted by law." << std::endl;
        return 0;
    }

    if (vm.count("help")) {
        std::cout << "This program supports an alternative grading scale for the course \"High level programming language I\"." << std::endl;
        std::cout << desc << std::endl;
        std::cout << "Please report bugs to: nbatfai@gmail.com" << std::endl;
        return 0;
    }

    std::string subtree;
    if (vm.count("root"))
        subtree.assign(vm["root"].as < std::string > ());

    // If you use this sample you should add your copyright information here too:
    /*
    std::cout << "This Prog1 Lightsaber has been modified by <Your Name>" << std::endl
    << "Copyright (C) 2018 Norbert Bátfai" << std::endl
    << "License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>" << std::endl
    */

    // Do not remove this copyright notice!
    std::cout << "FUTURE DEBRECEN, Prog1 Lightsaber to support an alternative grading scale" << std::endl
              << "Copyright (C) 2018 Norbert Bátfai" << std::endl
              << "License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>" << std::endl
              << "This is free software: you are free to change and redistribute it." << std::endl
              << "There is NO WARRANTY, to the extent permitted by law." << std::endl;

    int sum {0};
    boost::char_separator<char> delim {";"};
    boost::tokenizer<boost::char_separator<char>> tokens {subtree, delim};

    for (const auto & node : tokens) {

        std::cout << node << std::endl;

        boost::filesystem::path root(node);
        std::map <std::string, int> acts;
        read_acts(root, acts);

        sum += sum_points(acts);
    }

    std::cout << sum << std::endl;
}
