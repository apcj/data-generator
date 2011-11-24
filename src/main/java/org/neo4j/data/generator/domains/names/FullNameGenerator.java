package org.neo4j.data.generator.domains.names;

import org.neo4j.data.generator.domains.gender.Gender;

public class FullNameGenerator
{
    private NameGenerator maleFirstNameGenerator = new NameGenerator( NameGenerator.NameType.MaleFirstName );
    private NameGenerator femaleFirstNameGenerator = new NameGenerator( NameGenerator.NameType.FemaleFirstName );
    private NameGenerator lastNameGenerator = new NameGenerator( NameGenerator.NameType.LastName );

    public FullName nextFullName(Gender gender) {
        String firstName = Gender.Male.equals( gender ) ?
                maleFirstNameGenerator.nextName() : femaleFirstNameGenerator.nextName();
        return new FullName( firstName, lastNameGenerator.nextName() );
    }
}
