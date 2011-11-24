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

    public Patient nextPatient()
    {
        HealthServicePatientId patientId = healthServicePatientIdGenerator.nextId();
        Gender gender = genderGenerator.nextGender();
        LocalDate dateOfBirth = dateOfBirthGenerator.nextDateOfBirth();
        FullName name = fullNameGenerator.nextFullName( gender );

        Patient patient = new Patient( patientId, gender, dateOfBirth, name );
        List<Encounter> encounters = new EncounterSequenceGenerator().encountersSince( dateOfBirth );
        for ( Encounter encounter : encounters )
        {
            patient.addEncounter(encounter);
        }

        return patient;
    }
}
