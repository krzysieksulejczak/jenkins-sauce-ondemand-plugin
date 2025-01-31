package hudson.plugins.sauce_ondemand;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class SauceEnvironmentUtilTest {

    @Test
    public void generateTunnelName_should_use_the_project_name_in_the_tunnel_id(){
        String expectedProjectName = "My_Project_Name";

        assertThat(SauceEnvironmentUtil.generateTunnelName(expectedProjectName), containsString(expectedProjectName));
    }

    @Test
    public void generateTunnelName_should_sanitize_the_project_name(){
        String expectedProjectName = "My Project!@#$%&*()Name-012345689";

        assertThat(SauceEnvironmentUtil.generateTunnelName(expectedProjectName), containsString("My_Project_Name-012345689"));
    }

    @Test
    public void generateTunnelName_should_include_an_epoch_timestamp(){
        String expectedProjectName = "My Project";

        String tunnelName = SauceEnvironmentUtil.generateTunnelName(expectedProjectName);

        String epochTimeMSStr = tunnelName.split("-")[1];
        long epochTimeMS = Long.parseLong(epochTimeMSStr);
        long now = System.currentTimeMillis();
        int deltaBetweenNowAndTunnelName = (int) (now - epochTimeMS);

        assertThat(deltaBetweenNowAndTunnelName, is(lessThan(12)));
    }
}
