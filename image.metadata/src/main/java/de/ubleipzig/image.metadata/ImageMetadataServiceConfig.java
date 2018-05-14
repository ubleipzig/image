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

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

/**
 * ImageMetadataServiceConfig.
 *
 * @author christopher-johnson
 */
public class ImageMetadataServiceConfig extends Configuration {

    private String imageSourceDir;
    private String imageMetadataFilePath;
    private String dimensionManifestFilePath;
    private String dimensionManifest;
    private String imageMetadataManifest;
    private String imageMetadataManifestOutputPath;
    private String dimensionManifestOutputPath;
    private String defaultInputImageExtension;

    /**
     * getImageSourceDir.
     *
     * @return {@link String}
     */
    @JsonProperty
    public String getImageSourceDir() {
        return this.imageSourceDir;
    }

    /**
     * setImageSourceDir.
     *
     * @param imageSourceDir imageSourceDir
     */
    @JsonProperty
    public final void setImageSourceDir(final String imageSourceDir) {
        this.imageSourceDir = imageSourceDir;
    }

    /**
     * getImageMetadataManifestOutputPath.
     *
     * @return {@link String}
     */
    @JsonProperty
    public String getImageMetadataManifestOutputPath() {
        return this.imageMetadataManifestOutputPath;
    }

    /**
     * setImageMetadataManifestOutputPath.
     *
     * @param imageMetadataManifestOutputPath imageMetadataManifestOutputPath
     */
    @JsonProperty
    public final void setImageMetadataManifestOutputPath(final String imageMetadataManifestOutputPath) {
        this.imageMetadataManifestOutputPath = imageMetadataManifestOutputPath;
    }

    /**
     * getDimensionManifestOutputPath.
     *
     * @return {@link String}
     */
    @JsonProperty
    public String getDimensionManifestOutputPath() {
        return this.dimensionManifestOutputPath;
    }

    /**
     * setImageMetadataManifestOutputPath.
     *
     * @param dimensionManifestOutputPath dimensionManifestOutputPath
     */
    @JsonProperty
    public final void setDimensionManifestOutputPath(final String dimensionManifestOutputPath) {
        this.dimensionManifestOutputPath = dimensionManifestOutputPath;
    }

    /**
     * getDimensionManifestFilePath.
     *
     * @return {@link String}
     */
    @JsonProperty
    public String getDimensionManifestFilePath() {
        return this.dimensionManifestFilePath;
    }

    /**
     * setDimensionManifestFilePath.
     *
     * @param dimensionManifestFilePath dimensionManifestFilePath
     */
    @JsonProperty
    public final void setDimensionManifestFilePath(final String dimensionManifestFilePath) {
        this.dimensionManifestFilePath = dimensionManifestFilePath;
    }

    /**
     * getDimensionManifest.
     *
     * @return {@link String}
     */
    public final String getDimensionManifest() {
        return this.dimensionManifest;
    }

    /**
     * setDimensionManifest.
     *
     * @param dimensionManifest dimensionManifest
     */
    public final void setDimensionManifest(final String dimensionManifest) {
        this.dimensionManifest = dimensionManifest;
    }

    /**
     * getDimensionManifest.
     *
     * @return {@link String}
     */
    public final String getImageMetadataManifest() {
        return this.imageMetadataManifest;
    }

    /**
     * setDimensionManifest.
     *
     * @param imageMetadataManifest imageMetadataManifest
     */
    public final void setImageMetadataManifest(final String imageMetadataManifest) {
        this.imageMetadataManifest = imageMetadataManifest;
    }

    /**
     * getImageMetadataFilePath.
     *
     * @return {@link String}
     */
    @JsonProperty
    public String getImageMetadataFilePath() {
        return this.imageMetadataFilePath;
    }

    /**
     * setMetadataFile.
     *
     * @param imageMetadataFilePath imageMetadataFilePath
     */
    @JsonProperty
    public final void setImageMetadataFilePath(final String imageMetadataFilePath) {
        this.imageMetadataFilePath = imageMetadataFilePath;
    }
}
