package org.neo4j.data.generator.domains.medicalrecords.patients;

public class HealthServicePatientId
{
    private String value;

    public HealthServicePatientId( String value )
    {
        this.value = value;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        HealthServicePatientId that = (HealthServicePatientId) o;

        if ( value != null ? !value.equals( that.value ) : that.value != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return value != null ? value.hashCode() : 0;
    }
}
