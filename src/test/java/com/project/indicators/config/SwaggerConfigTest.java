package com.project.indicators.config;

import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.junit.Before;
import org.junit.Test;
import springfox.documentation.service.ApiInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.assertj.core.api.Assertions.assertThat;

public class SwaggerConfigTest {

    private SwaggerConfig config;

    @Before
    public void init() {config = new SwaggerConfig();}

    @Test
    public void swagger_version_must_be_the_same_as_pom () throws Exception {
        final String pomVersion = getVersion();
        final ApiInfo apiInfo = config.apiInfo();
        assertThat(pomVersion).containsSequence(apiInfo.getVersion());
    }

    private String getVersion() throws Exception {
        final String POM_FILE_NAME = "pom.xml";
        if ((new File(POM_FILE_NAME)).exists()) {
            return new MavenXpp3Reader().read(new FileReader(POM_FILE_NAME)).getVersion().split("-")[0];
        }
        throw new FileNotFoundException("pom.xml must exist");
    }
}