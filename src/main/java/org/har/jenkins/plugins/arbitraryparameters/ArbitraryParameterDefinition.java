package org.har.jenkins.plugins.arbitraryparameters;

import hudson.Extension;
import hudson.model.ParameterValue;
import hudson.model.SimpleParameterDefinition;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * String based parameter definition that accepts a string
 * in properties file format (KEY1=VALUE1 KEY2=VALUE2)
 * 
 * @author Louis Li
 * @see {@link ParameterDefinition}
 */
public class ArbitraryParameterDefinition extends SimpleParameterDefinition {
    
    private String defaultValue;
    @DataBoundConstructor
    public ArbitraryParameterDefinition(String name, String defaultValue, String description)
    {
        super(name, description);
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue()
    {
        return this.defaultValue;
    }
    
    @Override
    public ArbitraryParameterValue getDefaultParameterValue()
    {
        return new ArbitraryParameterValue(getName(), getDefaultValue(), getDescription());
    }
    
    @Override
    public ParameterValue createValue(StaplerRequest sr, JSONObject jsono)
    {
        ArbitraryParameterValue newValue = sr.bindJSON(ArbitraryParameterValue.class, jsono);
        newValue.setDescription(getDescription());
        return newValue;
    }
  
    @Override
    public ParameterValue createValue(String value)
    {
        return new ArbitraryParameterValue(getName(), value, getDescription());
    }

    @Extension
    public static class DescriptorImpl extends ParameterDescriptor 
    {

        @Override
        public String getDisplayName()
        {
            return "Arbitrary Parameters";
        }

        @Override
        public String getHelpFile()
        {
            return "/plugin/arbitrary-parameters/paramhelp.html";
        }
    }
}
