/*
 * Image
 * Copyright (C) 2018 Leipzig University Library <info@ub.uni-leipzig.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package de.ubleipzig.image.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.validation.Validators;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageMetadataServiceConfigTest extends CommonTest {

    private String randomString;

    @BeforeEach
    void setup() {
        randomString = randomString();
    }

    @Test
    void testImageMetadataServiceConfig() throws Exception {
        final ImageMetadataServiceConfig config = new YamlConfigurationFactory<>(
                ImageMetadataServiceConfig.class, Validators.newValidator(), Jackson.newObjectMapper(), "").build(
                new File(getClass().getResource("/imageMetadataServiceConfig-test.yml").toURI()));
        assertEquals("/images", config.getImageSourceDir());
        assertEquals(
                "/manifests/dimension-manifest-test-ff5fd8bb-859d-4a98-82ce-57aada0c5bb0.json",
                config.getDimensionManifestFilePath());
        assertEquals(
                "/manifests/image-manifest-test-73ed7acb-03cd-4122-8d7a-d2b36ec8fc0f.json",
                config.getImageMetadataFilePath());
    }

    @Test
    void testConfigDimensionManifest() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setDimensionManifest(randomString);
        assertEquals(randomString, config.getDimensionManifest());
    }

    @Test
    void testConfigImageMetadataManifest() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setImageMetadataManifest(randomString);
        assertEquals(randomString, config.getImageMetadataManifest());
    }
}
