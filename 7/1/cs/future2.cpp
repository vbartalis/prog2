/*

$ g++ future2.cpp -o future2 -lboost_system
$ ./future2

 */

/**
 * @brief Gyors protók a FUTURE játékélmény felderítéséhez, kialakításához.
 *
 * @file future2.cpp
 * @author  Norbert Bátfai <nbatfai@gmail.com>
 * @version 0.0.1
 *
 * @section LICENSE
 *
 * Copyright (C) 2017 Norbert Bátfai, batfai.norbert@inf.unideb.hu
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
 * FUTURE
 *
 */

#include <iostream>
#include <ctime>
#include <boost/date_time/gregorian/gregorian.hpp>
#include <boost/filesystem.hpp>
#include <boost/filesystem/fstream.hpp>
#include <numeric>

struct tm *next_calendar_day(struct tm *date)
{

    struct tm *real_date;

    ++date->tm_mday;

    time_t realepoch = std::mktime(date);
    real_date = std::gmtime(&realepoch);
    *date = *real_date;

    return date;
}

int
main()
{

    struct tm date;
    struct tm *tmpdate;

    boost::gregorian::date greta_birthday(2008, boost::gregorian::Mar, 11);

    time_t now = std::time(NULL);
    tmpdate = std::gmtime(&now);
    date = *tmpdate;

    do {

        //std::cout << std::asctime(&date) << std::endl;

        boost::gregorian::date n = boost::gregorian::date_from_tm(date);
        if (n.month() == greta_birthday.month()
                && n.day() == greta_birthday.day()) {
            std::cout << "Happy birthday, Gréta!" << std::endl;
            std::cout << std::asctime(&date) << std::endl;

        }

    } while (next_calendar_day(&date)->tm_year < 3000 - 1900);


}
