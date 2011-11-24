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
package org.neo4j.data.generator.mapping.medicalrecords.encounters;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.neo4j.data.generator.domains.medicalrecords.encounters.Encounter;
import org.neo4j.data.generator.mapping.medicalrecords.RelationshipTypes;
import org.neo4j.data.generator.mapping.medicalrecords.locations.HealthLocationRepository;
import org.neo4j.data.generator.mapping.medicalrecords.professionals.HealthProfessionalsRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

public class EncounterRepository
{
    private DateTimeFormatter dateFormatter = DateTimeFormat.shortDate();
    private GraphDatabaseService database;
    private HealthProfessionalsRepository professionalsRepository;
    private HealthLocationRepository locationRepository;

    public EncounterRepository( GraphDatabaseService database, HealthProfessionalsRepository professionalsRepository, HealthLocationRepository locationRepository )
    {
        this.database = database;
        this.professionalsRepository = professionalsRepository;
        this.locationRepository = locationRepository;
    }

    public Node store( Encounter encounter )
    {
        Node encounterNode = database.createNode();
        encounterNode.setProperty( "date", dateFormatter.print( encounter.getDate() ) );
        encounterNode.createRelationshipTo(
                professionalsRepository.findFor( encounter.getHealthProfessional() ),
                RelationshipTypes.WITH_PROFESSIONAL );
        encounterNode.createRelationshipTo(
                locationRepository.findFor( encounter.getHealthLocation() ),
                RelationshipTypes.AT_LOCATION );

        return encounterNode;
    }
}
