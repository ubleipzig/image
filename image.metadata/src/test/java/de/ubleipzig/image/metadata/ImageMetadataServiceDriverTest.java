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

import org.junit.jupiter.api.Test;

public class ImageMetadataServiceDriverTest extends CommonTest {

    private String imageSourceDir;

    @Test
    public void testStandardType() {
        imageSourceDir = ArgParserTest.class.getResource(getImageMetadataServiceConfig().getImageSourceDir()).getPath();
        final String[] args = new String[]{"-i", imageSourceDir, "-z", "/tmp/metadata-manifest.json", "-o",
                "/tmp/output.json"};
        ImageMetadataServiceDriver.main(args);
    }

    @Test
    public void testJ2KType() {
        imageSourceDir = ArgParserTest.class.getResource(getImageMetadataServiceConfig2().getImageSourceDir())
                                            .getPath();
        final String[] args = new String[]{"-i", imageSourceDir, "-z", "/tmp/metadata-manifest.json", "-o",
                "/tmp/output.json"};
        ImageMetadataServiceDriver.main(args);
    }
}
