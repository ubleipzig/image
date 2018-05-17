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

import static de.ubleipzig.image.metadata.DefaultFileTypes.TIFF;
import static de.ubleipzig.image.metadata.JP2Utils.convertAndDeleteJP2;
import static org.apache.commons.io.FilenameUtils.EXTENSION_SEPARATOR;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class JP2UtilsTest {

    @Test
    void convertJP2() {
        final File file = new File(JP2UtilsTest.class.getResource("/j2k/00000040.jpx").getPath());
        convertAndDeleteJP2(file);
        final String tiff = removeExtension(file.getPath()) + EXTENSION_SEPARATOR + TIFF;
        assertTrue(new File(tiff).exists());
    }
}
