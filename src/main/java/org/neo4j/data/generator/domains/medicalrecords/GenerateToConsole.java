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

import java.io.File;

import org.neo4j.data.generator.domains.medicalrecords.locations.HealthLocationPool;
import org.neo4j.data.generator.domains.medicalrecords.professionals.HealthProfessionalPool;
import org.neo4j.data.generator.mapping.medicalrecords.encounters.EncounterRepository;
import org.neo4j.data.generator.mapping.medicalrecords.locations.HealthLocationRepository;
import org.neo4j.data.generator.mapping.medicalrecords.patients.PatientRepository;
import org.neo4j.data.generator.mapping.medicalrecords.professionals.HealthProfessionalsRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class GenerateToConsole
{
    public static void main( String[] args )
    {
        int numberOfPatients = 1000;
        int commitInterval = 100;
        int reportingInterval = 100;
        long startTime = System.currentTimeMillis();

        String storeDir = "generated";
        if (new File( storeDir ).exists()) {
            throw new IllegalArgumentException( "store directory already exists" );
        }
        new File( storeDir ).mkdirs();
        GraphDatabaseService database = new EmbeddedGraphDatabase( storeDir );

        HealthProfessionalPool professionalPool = new HealthProfessionalPool( HealthProfessionalPool.POOL_SIZE );
        HealthLocationPool locationPool = new HealthLocationPool();
        MedicalRecordGenerator generator = new MedicalRecordGenerator( professionalPool, locationPool );

        HealthProfessionalsRepository professionalsRepository = new HealthProfessionalsRepository( database );
        HealthLocationRepository locationRepository = new HealthLocationRepository( database );

        professionalsRepository.store( professionalPool.allProfessionals() );
        locationRepository.store( locationPool.allLocations() );

        EncounterRepository encounterRepository = new EncounterRepository( database, professionalsRepository, locationRepository );
        PatientRepository patientRepository = new PatientRepository( database, encounterRepository );

        Transaction transaction = database.beginTx();
        try {
            for ( int i = 0; i < numberOfPatients; i++ )
            {
                patientRepository.store( generator.nextPatient() );

                if ( i % commitInterval == 0 )
                {
                    transaction.success();
                    transaction.finish();
                    transaction = database.beginTx();
                }

                if ( i % reportingInterval == 0 )
                {
                    long elapsed = System.currentTimeMillis() - startTime;
                    System.out.printf( "Generated %d patient records in %d s%n", i, elapsed / 1000 );
                    System.out.flush();
                }
            }
            transaction.success();
        } finally {
            transaction.finish();
        }
    }
}
