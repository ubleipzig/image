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
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import de.ubleipzig.image.metadata.templates.ImageDimensionManifest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class TemplateTest {

    @Mock
    private ImageDimensionManifest mockDimensionManifest;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        when(mockDimensionManifest.getCollection()).thenReturn("/some/test/collection");
    }

    @Test
    void testGetCollection() {
        assertEquals("/some/test/collection", mockDimensionManifest.getCollection());
    }
}
