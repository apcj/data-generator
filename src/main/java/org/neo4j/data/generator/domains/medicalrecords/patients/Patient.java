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
package org.neo4j.data.generator.domains.medicalrecords.patients;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.neo4j.data.generator.domains.gender.Gender;
import org.neo4j.data.generator.domains.medicalrecords.encounters.Encounter;
import org.neo4j.data.generator.domains.names.FullName;

public class Patient
{
    private List<Encounter> encounters = new ArrayList<Encounter>();

    public Patient( HealthServicePatientId patientId, Gender gender, LocalDate dateOfBirth, FullName fullName )
    {

    }

    public void addEncounter( Encounter encounter )
    {
        encounters.add( encounter );
    }
}
