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
package org.neo4j.data.generator.domains.medicalrecords;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.neo4j.data.generator.domains.names.NameGenerator;

public class NameGeneratorTest
{
    @Test
    public void shouldChooseNamesFromTheDistributionFiles()
    {
        verifyGeneratesEnoughDifferentNames( NameGenerator.NameType.MaleFirstName );
        verifyGeneratesEnoughDifferentNames( NameGenerator.NameType.FemaleFirstName );
        verifyGeneratesEnoughDifferentNames( NameGenerator.NameType.LastName );
    }

    private void verifyGeneratesEnoughDifferentNames( NameGenerator.NameType nameType )
    {
        NameGenerator nameGenerator = new NameGenerator( nameType );

        Set<String> uniqueNames = new HashSet<String>();
        for (int i = 0; i < 100; i++) {
            uniqueNames.add( nameGenerator.nextName() );
        }

        assertThat( uniqueNames.size(), greaterThan( 50 ) );
    }
}
