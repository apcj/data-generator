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
package org.neo4j.data.generator.mapping.medicalrecords.locations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.data.generator.domains.medicalrecords.locations.HealthLocation;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

public class HealthLocationRepository
{
    private GraphDatabaseService database;
    private Map<String, Node> nodes = new HashMap<String, Node>();

    public HealthLocationRepository( GraphDatabaseService database )
    {
        this.database = database;
    }

    public void store( List<HealthLocation> locations )
    {
        Transaction transaction = database.beginTx();
        try
        {
            for ( HealthLocation location : locations )
            {
                Node node = database.createNode();
                nodes.put( location.getName(), node );
                node.setProperty( "name", location.getName() );
            }
            transaction.success();
        }
        finally
        {
            transaction.finish();
        }
    }

    public Node findFor( HealthLocation healthLocation )
    {
        return nodes.get( healthLocation.getName() );
    }
}
