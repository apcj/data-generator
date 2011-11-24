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
package org.neo4j.data.generator.domains.medicalrecords.locations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Data from http://www.nhs.uk/servicedirectories/pages/acutetrustlisting.aspx
 */
public class HealthLocationPool
{
    private List<HealthLocation> locations = new ArrayList<HealthLocation>();

    public HealthLocationPool()
    {
        InputStream resource = getClass().getResourceAsStream( "uk-acute-trusts.txt" );
        BufferedReader reader = new BufferedReader( new InputStreamReader( resource ) );
        try
        {
            String line;
            while ( (line = reader.readLine()) != null )
            {
                locations.add( new HealthLocation( line ) );
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    public HealthLocation nextLocation()
    {
        return locations.get( (int) (Math.random() * locations.size()) );
    }

    public List<HealthLocation> allLocations()
    {
        return locations;
    }
}
