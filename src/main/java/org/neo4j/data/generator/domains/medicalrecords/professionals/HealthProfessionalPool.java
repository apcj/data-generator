/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
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
 */
package org.neo4j.data.generator.domains.medicalrecords.professionals;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.data.generator.domains.gender.Gender;
import org.neo4j.data.generator.domains.gender.GenderGenerator;
import org.neo4j.data.generator.domains.names.FullName;
import org.neo4j.data.generator.domains.names.FullNameGenerator;

/**
 * Pool size based on http://www.rsm.ac.uk/media/downloads/j06-06moredrs.pdf
 *
 * 2.3 doctors per 1000 population in 1993.
 * Assume no significant change since then.
 * 62,300,000 UK population in 2010 http://www.ons.gov.uk/ons/rel/pop-estimate/population-estimates-for-uk--england-and-wales--scotland-and-northern-ireland/mid-2010-population-estimates/annual-mid-year-population-estimates--2010.pdf
 * 2.3 * 62,300 = 143290
 *
 */
public class HealthProfessionalPool
{
    public static final int POOL_SIZE = 143290;
    private List<HealthProfessional> professionals = new ArrayList<HealthProfessional>();

    public HealthProfessionalPool( int size )
    {
        GenderGenerator genderGenerator = new GenderGenerator();
        FullNameGenerator nameGenerator = new FullNameGenerator();

        for ( int i = 0; i < size; i++ )
        {
            HealthProfessionalId id = new HealthProfessionalId( i );
            Gender gender = genderGenerator.nextGender();
            FullName name = nameGenerator.nextFullName( gender );
            professionals.add( new HealthProfessional( id, gender, name ) );
        }
    }

    public HealthProfessional nextProfessional()
    {
        return professionals.get( (int) (professionals.size() * Math.random()) );
    }

    public List<HealthProfessional> allProfessionals()
    {
        return professionals;
    }
}
