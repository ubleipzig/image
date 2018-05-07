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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArgParserTest {

    private ArgParser parser;
    private static String imageSourceDir;

    @BeforeAll
    static void init() {
        try {
            imageSourceDir = Paths.get(
                    ImageMetadataServiceTest.class.getResource("/00000001.jpg").toURI()).toFile().getParent();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRequiredArgs1() {
        parser = new ArgParser();
        final String[] args;
        args = new String[]{"-i", imageSourceDir, "-o", "/tmp/output.json"};
        final ImageMetadataService service = parser.init(args);
        service.run();
    }

    @Test
    void testOptionalArgs2() {
        parser = new ArgParser();
        final String[] args;
        args = new String[]{"-i", imageSourceDir, "-z", "/tmp/metadata-manifest.json", "-o", "/tmp/output.json"};
        final ImageMetadataService service = parser.init(args);
        service.run();
    }

    @Test
    void testInvalidArgs() {
        parser = new ArgParser();
        final String[] args;
        args = new String[]{"-q", imageSourceDir, "-x", "/tmp/metadata-manifest.json", "-p", "/tmp/output.json"};
        assertThrows(RuntimeException.class, () -> {
            parser.parseConfiguration(args);
        });
    }

    @Test
    void testValidArgs() {
        parser = new ArgParser();
        final String[] args;
        args = new String[]{"-i", imageSourceDir, "-z", "/tmp/metadata-manifest.json", "-o", "/tmp/output.json"};
        final ImageMetadataServiceConfig config = parser.parseConfiguration(args);
        assertEquals("/tmp/output.json", config.getDimensionManifestOutputPath());
    }
}
