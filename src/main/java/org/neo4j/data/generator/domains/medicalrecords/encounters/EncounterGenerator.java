package org.neo4j.data.generator.domains.medicalrecords.encounters;

import org.joda.time.LocalDate;
import org.neo4j.data.generator.domains.medicalrecords.locations.HealthLocationPool;
import org.neo4j.data.generator.domains.medicalrecords.professionals.HealthProfessionalPool;

public class EncounterGenerator
{
    private HealthProfessionalPool healthProfessionalPool = new HealthProfessionalPool( HealthProfessionalPool.POOL_SIZE );
    private HealthLocationPool healthLocationPool = new HealthLocationPool();

    public Encounter nextEncounter( LocalDate today )
    {
        return new Encounter(today, healthProfessionalPool.nextProfessional(), healthLocationPool.nextLocation() );
    }
}
