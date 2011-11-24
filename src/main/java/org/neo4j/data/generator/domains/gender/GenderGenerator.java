package org.neo4j.data.generator.domains.gender;

public class GenderGenerator
{
    public Gender nextGender() {
        double random = Math.random();
        return random < .5 ? Gender.Male : Gender.Female;
    }
}
