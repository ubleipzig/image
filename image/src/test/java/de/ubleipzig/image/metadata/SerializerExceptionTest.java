/*
 * Image Metadata
 * Copyright (C) 2017 Leipzig University Library <info@ub.uni-leipzig.de>
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.ubleipzig.image.metadata.templates.ImageDimensions;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class SerializerExceptionTest {

    @Mock
    private ImageDimensions mockDimensions;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        when(mockDimensions.getHeight()).thenReturn(1040);
    }

    @Test
    public void testError() {
        when(mockDimensions.getHeight()).thenAnswer(inv -> {
            sneakyJsonException();
            return 1040;
        });

        final Optional<String> json = JsonSerializer.serialize(mockDimensions);
        assertFalse(json.isPresent());
    }

    @Test
    void testIOException() {
        final String json = "{}";
        final File outfile = new File("/an-invalid-path");
        assertEquals(false, JsonSerializer.writeToFile(json, outfile));
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void sneakyThrow(final Throwable e) throws T {
        throw (T) e;
    }

    private static void sneakyJsonException() {
        sneakyThrow(new JsonProcessingException("expected") {
        });
    }
}
