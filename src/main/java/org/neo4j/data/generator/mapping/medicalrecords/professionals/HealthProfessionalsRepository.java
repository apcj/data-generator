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
package org.neo4j.data.generator.mapping.medicalrecords.professionals;

import java.util.List;

import org.neo4j.data.generator.domains.medicalrecords.professionals.HealthProfessional;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

public class HealthProfessionalsRepository
{
    private GraphDatabaseService database;

    public HealthProfessionalsRepository( GraphDatabaseService database )
    {
        this.database = database;
    }

    public void store(List<HealthProfessional> professionals)
    {
        Transaction transaction = database.beginTx();
        try {
            int counter = 0;
            for ( HealthProfessional professional : professionals )
            {
                Node node = database.createNode();
                node.setProperty( "type", HealthProfessional.class.getSimpleName() );
                node.setProperty( "id", professional.getId().toString() );
                node.setProperty( "gender", professional.getGender().name() );
                node.setProperty( "firstName", professional.getName().getFirstName() );
                node.setProperty( "lastName", professional.getName().getLastName() );

                if (counter % 1000 == 0) {
                    transaction.success();
                    transaction.finish();
                    transaction = database.beginTx();
                }
            }
            transaction.success();
        } finally {
            transaction.finish();
        }
    }
}
