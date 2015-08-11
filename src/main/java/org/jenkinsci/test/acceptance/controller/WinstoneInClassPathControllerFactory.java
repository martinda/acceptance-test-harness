package org.jenkinsci.test.acceptance.controller;

import com.cloudbees.sdk.extensibility.Extension;
import hudson.remoting.Which;
import org.jenkinsci.test.acceptance.controller.LocalController.LocalFactoryImpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * {@link JenkinsController} for co-locating acceptance tests with plugin source tree.
 *
 * <p>
 * In that environment, {@code jenkins.war} is renamed as {@code jenkins-war-1.XXX-war-for-test.jar}
 * and in the classpath, so pick that up and use that as the war file.
 *
 * <p>
 * See {@code WarExploder}.
 *
 * @author Kohsuke Kawaguchi
 */
@Extension
public class WinstoneInClassPathControllerFactory extends LocalFactoryImpl {
    @Override
    public String getId() {
        return "winstone-classpath";
    }

    /**
     * jenkins-war-for-test.jar is a test-scoped dependency, so look for this war.
     */
    protected File getWarFile() {
        try {
            URL winstone = WinstoneInClassPathControllerFactory.class.getResource("/winstone.jar");
            if(winstone==null)
                throw new AssertionError("jenkins.war is not in the classpath");
            return Which.jarFile(Class.forName("executable.Executable"));
        } catch (IOException | ClassNotFoundException e) {
            throw new Error(e);
        }
    }

    @Override
    public JenkinsController create() {
        return new WinstoneController(getWarFile());
    }
}
