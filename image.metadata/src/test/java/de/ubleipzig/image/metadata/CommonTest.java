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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class CommonTest {

    static ImageMetadataServiceConfig getImageMetadataServiceConfig() {
        final ImageMetadataServiceConfig imageMetadataServiceConfig;
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            imageMetadataServiceConfig = mapper.readValue(
                    new File(CommonTest.class.getResource("/imageMetadataServiceConfig-test.yml").toURI()),
                    ImageMetadataServiceConfig.class);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
        return imageMetadataServiceConfig;
    }

    static ImageMetadataServiceConfig getImageMetadataServiceConfig2() {
        final ImageMetadataServiceConfig imageMetadataServiceConfig;
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            imageMetadataServiceConfig = mapper.readValue(
                    new File(CommonTest.class.getResource("/imageMetadataServiceConfig-test2.yml").toURI()),
                    ImageMetadataServiceConfig.class);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
        return imageMetadataServiceConfig;
    }

    static String read(final InputStream input) {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    String randomString() {
        final byte[] array = new byte[7];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
}
