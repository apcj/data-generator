package org.neo4j.data.generator.domains.medicalrecords.locations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Data from http://www.nhs.uk/servicedirectories/pages/acutetrustlisting.aspx
 */
public class HealthLocationPool
{
    private List<HealthLocation> names = new ArrayList<HealthLocation>();

    public HealthLocationPool()
    {
        InputStream resource = getClass().getResourceAsStream( "uk-acute-trusts.txt" );
        BufferedReader reader = new BufferedReader( new InputStreamReader( resource ) );
        try
        {
            String line;
            while ( (line = reader.readLine()) != null )
            {
                names.add( new HealthLocation( line ) );
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    public HealthLocation nextLocation()
    {
        return names.get( (int) (Math.random() * names.size()) );
    }
}
