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
package org.neo4j.data.generator.mapping.medicalrecords.patients;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.neo4j.data.generator.domains.medicalrecords.encounters.Encounter;
import org.neo4j.data.generator.domains.medicalrecords.patients.Patient;
import org.neo4j.data.generator.mapping.medicalrecords.RelationshipTypes;
import org.neo4j.data.generator.mapping.medicalrecords.encounters.EncounterRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

public class PatientRepository
{
    private DateTimeFormatter dateFormatter = DateTimeFormat.shortDate();
    private GraphDatabaseService database;
    private EncounterRepository encounterRepository;

    public PatientRepository( GraphDatabaseService database, EncounterRepository encounterRepository )
    {
        this.database = database;
        this.encounterRepository = encounterRepository;
    }

    public void store( Patient patient )
    {
        Node patientNode = database.createNode();
        patientNode.setProperty( "type", Patient.class.getSimpleName() );
        patientNode.setProperty( "patientId", patient.getId().toString() );
        patientNode.setProperty( "gender", patient.getGender().name() );
        patientNode.setProperty( "dateOfBirth", dateFormatter.print( patient.getDateOfBirth() ) );
        patientNode.setProperty( "firstName", patient.getName().getFirstName() );
        patientNode.setProperty( "lastName", patient.getName().getLastName() );

        for ( Encounter encounter : patient.getEncounters() )
        {
            Node encounterNode = encounterRepository.store( encounter );
            patientNode.createRelationshipTo( encounterNode, RelationshipTypes.HAD_ENCOUNTER );
        }
    }
}
