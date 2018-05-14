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

import static org.apache.commons.io.FilenameUtils.removeExtension;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class OPJDecompressTest {

    @Test
    void convertJP2() throws IOException {
        final String baseFilePath = removeExtension(OPJDecompressTest.class.getResource("/j2k/00000040.jpx").getPath());
        final String inputFile = baseFilePath + ".jpx";
        final String outputFile = baseFilePath + ".tif";
        final String[] commands = {"opj_decompress", "-i", inputFile, "-o", outputFile};
        final ProcessBuilder pb = new ProcessBuilder(commands);
        final Process p1 = pb.start();
    }
}
