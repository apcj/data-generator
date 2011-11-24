package org.neo4j.data.generator.domains.medicalrecords.encounters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class EncounterSequenceGenerator
{
    public static final int MAX_YEARS_BETWEEN_ENCOUNTERS = 10;
    private EncounterGenerator encounterGenerator = new EncounterGenerator();

    public List<Encounter> encountersSince( LocalDate dateOfBirth )
    {
        ArrayList<Encounter> encounters = new ArrayList<Encounter>();
        LocalDate currentDate =dateOfBirth;
        LocalDate today = new LocalDate();
        while (currentDate.isBefore( today ))
        {
            encounters.add( encounterGenerator.nextEncounter( today ) );
            currentDate = currentDate.plusDays( (int) (Math.random() * 365 * MAX_YEARS_BETWEEN_ENCOUNTERS) );
        }
        return encounters;
    }
}
