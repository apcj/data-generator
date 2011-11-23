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
package org.neo4j.data.generator.domains.names;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Uses data from the US Census http://www.census.gov/genealogy/names/names_files.html
 */
public class NameGenerator
{
    private List<String> names;
    private List<Double> cumulativeProbabilities;

    public enum NameType {
        MaleFirstName("dist.male.first.txt"),
        FemaleFirstName("dist.male.first.txt"),
        LastName("dist.all.last.txt");

        String resourceFileName;

        NameType( String resourceFileName )
        {
            this.resourceFileName = resourceFileName;
        }
    }

    public NameGenerator(NameType nameType)
    {
        names = new ArrayList<String>();
        cumulativeProbabilities = new ArrayList<Double>();

        InputStream resource = getClass().getResourceAsStream( nameType.resourceFileName );
        BufferedReader reader = new BufferedReader( new InputStreamReader( resource ) );
        try
        {
            String line;
            while ( (line = reader.readLine()) != null ) {
                String[] tokens = line.split( " +" );
                names.add( tokens[0] );
                cumulativeProbabilities.add( Double.parseDouble( tokens[2] ) / 100 );
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    public String nextName()
    {
        double random = Math.random();
        int index = Math.abs( Collections.binarySearch( cumulativeProbabilities, random ) );
        if (index >= names.size())
        {
            index = 0;
        }
        return names.get( index );
    }
}
