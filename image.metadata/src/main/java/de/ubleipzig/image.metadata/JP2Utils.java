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

import static de.ubleipzig.image.metadata.DefaultFileTypes.JP2;
import static de.ubleipzig.image.metadata.DefaultFileTypes.JPX;
import static de.ubleipzig.image.metadata.DefaultFileTypes.TIFF;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.FilenameUtils.EXTENSION_SEPARATOR;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

/**
 * JP2Utils.
 */
public final class JP2Utils {

    private static Logger logger = getLogger(JP2Utils.class);

    /**
     * @param file File
     * @return File
     */
    public static File convertAndDeleteJP2(final File file) {
        final String baseFileExtension = getExtension(file.getPath());
        final String baseFilePath = removeExtension(file.getPath());
        final String inputFile = baseFilePath + EXTENSION_SEPARATOR + JP2;
        final String outputFile = baseFilePath + EXTENSION_SEPARATOR + TIFF;
        if (baseFileExtension.equals(JPX)) {
            final String[] commands1 = {"mv", baseFilePath + "." + JPX, baseFilePath + EXTENSION_SEPARATOR + JP2};
            final ProcessBuilder pb = new ProcessBuilder(commands1);
            try {
                pb.start();
                logger.info("Moving {} to {}", baseFilePath + "." + JPX, baseFilePath + EXTENSION_SEPARATOR + JP2);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        final String[] commands1 = {"opj_decompress", "-i", inputFile, "-o", outputFile};
        final ProcessBuilder pb1 = new ProcessBuilder(commands1);
        final String[] commands2 = {"rm", inputFile};
        final ProcessBuilder pb2 = new ProcessBuilder(commands2);
        try {
            final Process p1 = pb1.start();
            logger.info("Decompressing {} to {}", inputFile, outputFile);
            p1.waitFor();
            pb2.start();
            logger.info("Removing {}", inputFile);
            return new File(outputFile);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     * @param file file
     * @return imageWidth
     */
    public static String getJP2ImageWidth(final File file) {
        final String inputFile = file.getPath();
        final String[] commands1 = {"identify", "-format", "%w", inputFile};
        final ProcessBuilder getWidth = new ProcessBuilder(commands1);
        try {
            final String width = IOUtils.toString(getWidth.start().getInputStream(), UTF_8);
            logger.info("Width is {} for {}", width, inputFile);
            return width;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     * @param file file
     * @return imageHeight
     */
    public static String getJP2ImageHeight(final File file) {
        final String inputFile = file.getPath();
        final String[] commands1 = {"identify", "-format", "%w", inputFile};
        final ProcessBuilder getWidth = new ProcessBuilder(commands1);
        final String[] commands2 = {"identify", "-format", "%h", inputFile};
        final ProcessBuilder getHeight = new ProcessBuilder(commands2);
        try {
            final String height = IOUtils.toString(getHeight.start().getInputStream(), UTF_8);
            logger.info("Height is {} for {}",height, inputFile);
            return height;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private JP2Utils() {
    }
}
