package org.neo4j.data.generator.domains.birth;

import org.joda.time.LocalDate;

public class DateOfBirthGenerator
{
    int MAX_AGE = 100;

    public LocalDate nextDateOfBirth() {
        return new LocalDate().minusDays( (int) (Math.random() * MAX_AGE * 365) );
    }
}
