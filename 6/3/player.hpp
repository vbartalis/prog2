/**
 * @brief FUTURE DEBRECEN, UDProg1/DeacH Lightsaber
 *
 * @file player.cpp
 * @author  Norbert Bátfai <nbatfai@gmail.com>
 * @version f9f2ls.0.0.2
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @section DESCRIPTION
 * 
 */

#include <random>
#include <iostream>
#include <map>

#include <boost/date_time/gregorian/gregorian.hpp>

using P_nact_per_day = std::map< int, std::map< std::string, double > >;

enum class SimulMode {identical, normal};

class Player
{

        std::default_random_engine &rnd;
        std::uniform_real_distribution<double> udist;
        std::string name;
        P_nact_per_day nact_per_day;
	SimulMode mode;

public:

        Player ( std::default_random_engine &rnd, std::string name, P_nact_per_day nact_per_day, 
		 SimulMode mode = SimulMode::identical) : rnd ( rnd ), name ( name ), 
        nact_per_day ( nact_per_day ), mode ( mode ) {

                udist = std::uniform_real_distribution<double> {0, 1.0};

        }

        void devel ( int day, boost::gregorian::date n ) {

                devel ( day, n, std::cout );
        }

        void devel ( int day, boost::gregorian::date n, std::ostream &os ) {

	  switch(mode)
	  {
	    case SimulMode::identical:	  
                for ( auto & act : nact_per_day[day] ) {

                        if ( udist ( rnd ) < act.second )
                                os << "\"" << name << "\",\"./" << act.first << "\",\"" <<  boost::gregorian::to_iso_extended_string ( n ) << " 21:45:41\""<< std::endl;

                }
                break;
                
                
	  }

        }
};
