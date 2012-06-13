package org.har.jenkins.plugins.arbitraryparameters;

import hudson.EnvVars;
import hudson.model.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * A StringParameterValue that parses a string in properties file format
 * and adds the variables to the build as environmental variables
 *
 * @author Louis Li
 * @see {@link ParameterValue}
 */
public class ArbitraryParameterValue extends StringParameterValue {
    
    @DataBoundConstructor
    public ArbitraryParameterValue(String name, String value)
    {
        this(name, value, null);
    }
    
    public ArbitraryParameterValue(String name, String value, String description)
    {
        super(name, value, description);
    }
    
    public String getValue()
    {
        return value;
    }

    @Override
    public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
        InputStream is = new ByteArrayInputStream(getValue().getBytes(Charset.defaultCharset() ) );
        Properties props = new Properties();
        try {
            props.load(is);            
        } catch (IOException e) {
            // TODO error
        }
        
        env.remove(getName());
        env.putAll((Map) props);

        try { is.close(); } catch (IOException e) {} // TODO error
 
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (this.getClass() != o.getClass())
            return false;
        ArbitraryParameterValue other = (ArbitraryParameterValue) o;
        if (this.value == null && other.value != null)
            return false;
        if (!this.value.equals(other.value))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "(ArbitraryParameterValue) " + getName() + "='" + getValue() + "'";
    }
}
