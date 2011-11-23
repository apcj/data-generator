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
package org.neo4j.data.generator;

import static org.junit.Assert.assertThat;
import static org.neo4j.data.generator.GeneratorTest.Countable.nodes;
import static org.neo4j.data.generator.GeneratorTest.Countable.relationships;

import java.util.Iterator;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;
import org.neo4j.helpers.collection.PrefetchingIterator;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.test.TargetDirectory;

public class GeneratorTest
{
    @Test
    public void shouldGenerateSomeData()
    {
        String storeDir = TargetDirectory.forTest( GeneratorTest.class ).graphDbDir( true ).getPath();

        GraphDatabaseService database = new EmbeddedGraphDatabase( storeDir );
        new Generator().generateInto( database );
        database.shutdown();

        GraphDatabaseService readDatabase = new EmbeddedGraphDatabase( storeDir );
        assertThat( readDatabase, containsAtLeast( 100, nodes ) );
        assertThat( readDatabase, containsAtLeast( 100, relationships ) );
    }

    private Matcher<GraphDatabaseService> containsAtLeast( final int expectedCount, final Countable countable )
    {
        return new BaseMatcher<GraphDatabaseService>()
        {
            @Override
            public boolean matches( Object o )
            {
                GraphDatabaseService database = (GraphDatabaseService) o;
                int counter = 0;
                Iterator<?> iterator = countable.iterable( database ).iterator();
                while ( iterator.hasNext() && counter < expectedCount )
                {
                    iterator.next();
                    counter++;
                }
                return counter >= expectedCount;
            }

            @Override
            public void describeTo( Description description )
            {
                description.appendText( String.format( "%s with at least %d %s",
                        GraphDatabaseService.class.getSimpleName(), expectedCount, countable ) );
            }
        };
    }

    enum Countable
    {
        nodes
                {
                    Iterable<?> iterable( GraphDatabaseService database )
                    {
                        return database.getAllNodes();
                    }
                },
        relationships
                {
                    Iterable<?> iterable( final GraphDatabaseService database )
                    {
                        return new Iterable<Relationship>()
                        {
                            @Override
                            public Iterator<Relationship> iterator()
                            {
                                return new PrefetchingIterator<Relationship>()
                                {
                                    long relationshipId = 0;

                                    @Override
                                    protected Relationship fetchNextOrNull()
                                    {
                                        try
                                        {
                                            return database.getRelationshipById( relationshipId++ );
                                        }
                                        catch ( NotFoundException e )
                                        {
                                            return null;
                                        }
                                    }
                                };
                            }
                        };
                    }
                };

        abstract Iterable<?> iterable( GraphDatabaseService database );
    }
}
