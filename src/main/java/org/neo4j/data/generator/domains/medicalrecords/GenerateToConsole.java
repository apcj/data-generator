package org.neo4j.data.generator.domains.medicalrecords;

public class GenerateToConsole
{
    public static void main( String[] args )
    {
        int numberOfPatients = 60000000;
        int reportingInterval = 1000000;
        long startTime = System.currentTimeMillis();

        MedicalRecordGenerator generator = new MedicalRecordGenerator();

        for (int i = 0; i < numberOfPatients; i++) {
            generator.nextPatient();

            if (i % reportingInterval == 0) {
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.printf( "Generated %d patient records in %d s%n", i, elapsed / 1000 );
                System.out.flush();
            }
        }
    }
}
