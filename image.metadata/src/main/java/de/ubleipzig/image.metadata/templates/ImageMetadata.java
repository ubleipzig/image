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

package de.ubleipzig.image.metadata.templates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * ImageMetadata.
 *
 * @author christopher-johnson
 */
public class ImageMetadata {

    @JsonProperty
    private String filename;

    @JsonProperty
    private String digest;

    @JsonProperty
    private List<ImageMetadataDirectory> directories;

    /**
     * getDirectories.
     *
     * @return List
     */
    @JsonIgnore
    public List<ImageMetadataDirectory> getDirectories() {
        return this.directories;
    }

    /**
     * setDirectories.
     *
     * @param directories List
     */
    public void setDirectories(final List<ImageMetadataDirectory> directories) {
        this.directories = directories;
    }

    /**
     * getFilename.
     *
     * @return String
     */
    @JsonIgnore
    public String getFilename() {
        return this.filename;
    }

    /**
     * setFilename.
     *
     * @param filename String
     */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
     * getDigest.
     *
     * @return String
     */
    @JsonIgnore
    public String getDigest() {
        return this.digest;
    }

    /**
     * setDigest.
     *
     * @param digest String
     */
    public void setDigest(final String digest) {
        this.digest = digest;
    }

}
