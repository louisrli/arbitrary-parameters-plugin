/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.har.jenkins.plugins.arbitraryparameters;

import org.har.jenkins.plugins.arbitraryparameters.ArbitraryParameterDefinition;
import org.har.jenkins.plugins.arbitraryparameters.ArbitraryParameterValue;
import hudson.EnvVars;
import hudson.model.ParameterValue;
import java.util.HashMap;
import org.jvnet.hudson.test.HudsonTestCase;



/**
 *
 * @author louisli
 */
public class ArbitraryParameterDefinitionTest extends HudsonTestCase
{
    
    public void test()
    {
        // Test default value
        ArbitraryParameterDefinition testDefinition = new ArbitraryParameterDefinition("param_name", "FOo1=BARVALUE1\nfoo2=BAR2", "This is the description.");
        ArbitraryParameterValue testDefaultValue = testDefinition.getDefaultParameterValue();
        EnvVars testVars = new EnvVars(new HashMap<String, String>());
        testDefaultValue.buildEnvVars(null, testVars);
        assertEquals("BARVALUE1", testVars.get("FOo1"));
        assertEquals("BAR2", testVars.get("foo2"));
        
        // Test custom value
        ParameterValue testCustomValue = testDefinition.createValue("param=val val val\nA_Param='vel vel vel'\n");
        testCustomValue.buildEnvVars(null, testVars);
        assertEquals("val val val", testVars.get("param"));
        assertEquals("\'vel vel vel\'", testVars.get("A_Param"));
    }

}
