package com.julianduru.learning.mvnplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * created by julian on 29/01/2023
 */
@Mojo(name = "test", defaultPhase = LifecyclePhase.NONE)
public class SampleMojo extends AbstractMojo {


    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;


    @Parameter(property = "text")
    String text;



    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Sample Plugin is running now");
        getLog().info("Got parameter: " + text);
    }


}
