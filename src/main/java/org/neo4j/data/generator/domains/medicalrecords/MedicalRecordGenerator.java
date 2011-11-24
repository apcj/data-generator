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

import java.util.List;

import org.joda.time.LocalDate;
import org.neo4j.data.generator.domains.birth.DateOfBirthGenerator;
import org.neo4j.data.generator.domains.gender.Gender;
import org.neo4j.data.generator.domains.gender.GenderGenerator;
import org.neo4j.data.generator.domains.medicalrecords.encounters.Encounter;
import org.neo4j.data.generator.domains.medicalrecords.encounters.EncounterSequenceGenerator;
import org.neo4j.data.generator.domains.medicalrecords.patients.HealthServicePatientId;
import org.neo4j.data.generator.domains.medicalrecords.patients.HealthServicePatientIdGenerator;
import org.neo4j.data.generator.domains.medicalrecords.patients.Patient;
import org.neo4j.data.generator.domains.names.FullName;
import org.neo4j.data.generator.domains.names.FullNameGenerator;

public class MedicalRecordGenerator
{
    private HealthServicePatientIdGenerator healthServicePatientIdGenerator = new HealthServicePatientIdGenerator();
    private FullNameGenerator fullNameGenerator = new FullNameGenerator();
    private GenderGenerator genderGenerator = new GenderGenerator();
    private DateOfBirthGenerator dateOfBirthGenerator = new DateOfBirthGenerator();
    private EncounterSequenceGenerator encounterSequenceGenerator = new EncounterSequenceGenerator();

    public Patient nextPatient()
    {
        HealthServicePatientId patientId = healthServicePatientIdGenerator.nextId();
        Gender gender = genderGenerator.nextGender();
        LocalDate dateOfBirth = dateOfBirthGenerator.nextDateOfBirth();
        FullName name = fullNameGenerator.nextFullName( gender );

        Patient patient = new Patient( patientId, gender, dateOfBirth, name );
        List<Encounter> encounters = encounterSequenceGenerator.encountersSince( dateOfBirth );
        for ( Encounter encounter : encounters )
        {
            patient.addEncounter(encounter);
        }

        return patient;
    }
}
