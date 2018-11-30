/**
 * @brief FUTURE DEBRECEN, UDProg1/DeacH Lightsaber
 *
 * @file fenykard.cpp
 * @author  Norbert Bátfai <nbatfai@gmail.com>
 * @version f9ls.0.0.4
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
 * $ g++ fenykard.cpp -o fenykard -lboost_system -lboost_filesystem -lboost_program_options -std=c++14
 * $ ./fenykard
 * UDPROG:
 * $ ./fenykard --root=City/Debrecen/Oktatás/Informatika/Programozás/DEIK/Prog1/Példák/Előadás\;City/Debrecen/Szórakozás/Könyv/Ismeretterjesztő/Informatika\;City/Debrecen/Oktatás/Informatika/Programozás/Tankönyv\ olvasás --db=db-2018-03-10.csv
 * DEAC:
 * $ ./fenykard --root=City/Debrecen/Sport/Esport/DEAC-Hackers\;City/Debrecen/Szórakozás/Játék/Elektronikus --db=db-2018-03-10.csv
 */

#include <iostream>
#include <string>
#include <map>
#include <iomanip>
#include <fstream>

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

void print_acts(std::map <std::string, int> acts)
{
    std::cout << "Total score: " << sum_points(acts) << std::endl;

    for (auto & a : acts)
        std::cout << std::setw(4) << a.second << "    " << a.first << std::endl;

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

        }

    } else if (is_directory(path))
        for (boost::filesystem::directory_entry & entry : boost::filesystem::directory_iterator(path))
            read_acts(entry.path(), acts);

}

std::map <std::string, int> acts_map(std::string folder_names)
{
    std::map <std::string, int> acts;
    boost::char_separator<char> delim {";"};
    boost::tokenizer<boost::char_separator<char>> tokens {folder_names, delim};

    for (const auto & node : tokens) {

        boost::filesystem::path root(node);
        read_acts(root, acts);

    }

    return acts;

}

std::map <std::string, int> trad_map(std::string db_name, std::map <std::string, int> &act_points)
{

    std::map <std::string, int> rank;

    std::ifstream db(db_name);

    std::string line;
    while (std::getline(db, line)) {

        boost::tokenizer<boost::escaped_list_separator<char>> tokens {line};

        auto iter = tokens.begin();
        std::string name = *iter;
        std::string act = *++iter;
        act = act.substr(2);

        rank[name] += act_points[act];

    }

    return rank;
}

std::vector<std::pair<std::string, int>> sort_map(std::map <std::string, int> &rank)
{
    std::vector<std::pair<std::string, int>> ordered;

    for (auto & i : rank) {
        if (i.second) {
            std::pair<std::string, int> p {i.first, i.second};
            ordered.push_back(p);
        }
    }

    std::sort(
        std::begin(ordered), std::end(ordered),
    [ = ](auto && p1, auto && p2) {
        return p1.first < p2.first;
    }
    );

    return ordered;
}

int main(int argc, char *argv[])
{

    std::string defops = "City/Debrecen/Oktatás/Informatika/Programozás/DEIK/Prog1/Példák/Előadás;City/Debrecen/Szórakozás/Könyv/Ismeretterjesztő/Informatika;City/Debrecen/Oktatás/Informatika/Programozás/Tankönyv\ olvasás";

    boost::program_options::options_description desc("Options");
    desc.add_options()
    ("version", "print version number")
    ("help", "print help message")
    ("name", boost::program_options::value< std::string >(), "no default")
    ("root", boost::program_options::value< std::string > ()->default_value(defops),
     " the name of a subdirectory of the FUTURE activity tree to be processed by this program. The default value is City/Debrecen/Oktatás/Informatika/Programozás/DEIK/Prog1/Példák/Előadás")
    ("db", boost::program_options::value< std::string > (),
     " the name of a  database dump CSV file. There is no default value for this option.")
    ;

    boost::program_options::variables_map vm;
    boost::program_options::store(boost::program_options::parse_command_line(argc, argv, desc), vm);
    boost::program_options::notify(vm);

    if (vm.count("version")) {
        std::cout << "FUTURE DEBRECEN, UDProg1/DeacH Lightsaber, f9ls.0.0.4" << std::endl
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

	std::string name;
	if (vm.count("name"))
        name.assign(vm["name"].as < std::string > ());

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
    std::cout << "FUTURE DEBRECEN, UDProg1/DeacH Lightsaber to support an alternative grading scale" << std::endl
    << "Copyright (C) 2018 Norbert Bátfai" << std::endl
    << "License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>" << std::endl
    << "This is free software: you are free to change and redistribute it." << std::endl
    << "There is NO WARRANTY, to the extent permitted by law." << std::endl;

    std::map <std::string, int> act_points = acts_map(subtree);

    print_acts(act_points);

    if (vm.count("db")) {

        std::map <std::string, int> rank = trad_map(vm["db"].as < std::string > (), act_points);
        // See also https://www.twitch.tv/videos/232384581 that introduces the difference between the traditional grading scale and an alternative one

        std::vector<std::pair<std::string, int>> res = sort_map(rank);

        for (auto & i : res)

		//if (name==i.first)

            std::cout << i.first << " "  << i.second << std::endl;

        

       	

    }
}

