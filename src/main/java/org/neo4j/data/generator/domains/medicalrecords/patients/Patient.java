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
    private HealthServicePatientId id;
    private Gender gender;
    private LocalDate dateOfBirth;
    private FullName name;

    public Patient( HealthServicePatientId id, Gender gender, LocalDate dateOfBirth, FullName name )
    {
        this.id = id;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
    }

    public void addEncounter( Encounter encounter )
    {
        encounters.add( encounter );
    }

    public List<Encounter> getEncounters()
    {
        return encounters;
    }

    public HealthServicePatientId getId()
    {
        return id;
    }

    public Gender getGender()
    {
        return gender;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public FullName getName()
    {
        return name;
    }
}
