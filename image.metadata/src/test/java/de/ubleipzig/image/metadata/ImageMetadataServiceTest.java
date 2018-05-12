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

import static de.ubleipzig.image.metadata.JsonSerializer.serialize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ubleipzig.image.metadata.templates.ImageDimensionManifest;
import de.ubleipzig.image.metadata.templates.ImageDimensions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * ImageMetadataServiceTest.
 *
 * @author christopher-johnson
 */
public class ImageMetadataServiceTest extends CommonTest {

    private static String imageManifestPid;
    private static String dimensionManifestPid;
    private static String imageSourceDir;
    private static String dimensionManifestFilePath;
    private static String imageMetadataManifestFilePath;

    //Note: this is for testing large directories, only enable if you can wait ...
    private static String gigabyteSourceDir = "file:///media/gb/images";

    @Mock
    private File mockFilename = new File(randomString());

    @BeforeAll
    static void init() {
        {
            try {
                final URL resource = new URL(gigabyteSourceDir);
                System.out.println("Gigabyte Images Path: " + Paths.get(resource.toURI()));
                if (Paths.get(resource.toURI()).toFile().exists()) {
                    imageSourceDir = Paths.get(resource.toURI()).toFile().getPath();
                } else {
                    imageSourceDir = ImageMetadataServiceTest.class.getResource(
                            getImageMetadataServiceConfig().getImageSourceDir()).getPath();
                }
                dimensionManifestFilePath = ImageMetadataServiceTest.class.getResource(
                        getImageMetadataServiceConfig().getDimensionManifestFilePath()).getPath();
                imageMetadataManifestFilePath = ImageMetadataServiceTest.class.getResource(
                        getImageMetadataServiceConfig().getImageMetadataFilePath()).getPath();
            } catch (URISyntaxException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @BeforeEach
    void setup() {
        imageManifestPid = "image-manifest-test-" + UUID.randomUUID().toString() + ".json";
        dimensionManifestPid = "dimension-manifest-test-" + UUID.randomUUID().toString() + ".json";
    }

    @Test
    void testBuildImageMetadataManifest() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        try {
            Files.write(Paths.get("/tmp/" + imageManifestPid), generator.buildImageMetadataManifest().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testImageProcessingException() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setImageSourceDir(ImageMetadataServiceTest.class.getResource("/bad-images").getPath());
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        assertThrows(NullPointerException.class, () -> {
            generator.buildImageMetadataManifest();
        });
    }

    @Test
    void testImageMetadataIOException() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setImageSourceDir("/non-existing-directory");
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        assertThrows(RuntimeException.class, () -> {
            generator.buildImageMetadataManifest();
        });
    }


    @Test
    void testBuildImageDimensionsFromManifest() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        final ImageDimensionManifest dimManifest = generator.build();
        final String dimensionManifest = serialize(dimManifest).orElse("");
        System.out.println(dimensionManifest);
        try {
            Files.write(Paths.get("/tmp/" + dimensionManifestPid), dimensionManifest.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBuildImageDimensionManifestListFromImageMetadataManifest() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        final String imageMetadataManifest = generator.buildImageMetadataManifest();
        final List<ImageDimensions> dimList = generator.buildDimensionManifestListFromImageMetadataManifest(
                imageMetadataManifest);
        assertEquals(dimList.get(0).getFilename(), "00000001.jpg");
    }

    @Test
    void testGetFilenamesFromImageManifest() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        final List<String> filenameList;
        filenameList = generator.getFilenamesFromManifest();
        System.out.println(filenameList);
    }

    @Test
    void testGetDimensionManifestFromRemoteSource() {
        final InputStream is = ImageMetadataServiceTest.class.getResourceAsStream(
                getImageMetadataServiceConfig().getDimensionManifestFilePath());
        final String dimManifest = read(is);
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setDimensionManifest(dimManifest);
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        final List<ImageDimensions> dimList = generator.unmarshallDimensionManifestFromRemote();
        assertEquals(3, dimList.size());
    }

    @Test
    void testGetDimensionManifestFromDimensionFile() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setDimensionManifestFilePath(dimensionManifestFilePath);
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        final List<ImageDimensions> dimList = generator.unmarshallDimensionManifestFromFile();
        assertEquals(3, dimList.size());
    }

    @Test
    void testBuildDimensionManifestFromMetadataFile() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setImageMetadataFilePath(imageMetadataManifestFilePath);
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        final ImageDimensionManifest dimManifest = generator.buildDimensionManifest(null);
        assertEquals("jeUrOCoqaYw/89LmIo3gQlxhipE=", dimManifest.getImageMetadata().get(0).getDigest());
    }

    @Test
    void testBase64Digest() throws IOException {
        final FileBinaryService service = new FileBinaryService();
        final Stream<Path> paths = Files.walk(Paths.get(imageSourceDir)).filter(Files::isRegularFile);
        paths.forEach(p -> {
            final File file = new File(String.valueOf(p.toAbsolutePath()));
            final InputStream targetStream;
            try {
                targetStream = new FileInputStream(file);
                System.out.println(service.digest("SHA-1", targetStream).orElse(null));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void testSerializeImageDimensionManifest() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setImageMetadataFilePath(imageMetadataManifestFilePath);
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        final ImageDimensionManifest dimManifest = generator.buildDimensionManifest(null);
        generator.serializeImageDimensionManifest(dimManifest, "/tmp/" + dimensionManifestPid);
    }

    @Test
    void testFileNotFoundException() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        assertThrows(RuntimeException.class, () -> {
            generator.getDigest(mockFilename);
        });
    }

    @Test
    void testImageMetadataManifestIOException() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        final ImageMetadataService generator = new ImageMetadataServiceImpl(config);
        assertThrows(RuntimeException.class, () -> {
            generator.buildImageMetadataManifest();
        });
    }

    @Test
    void testImageDimensionManifestIOException() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        final ImageMetadataService service = new ImageMetadataServiceImpl(config);
        assertThrows(RuntimeException.class, () -> {
            service.buildDimensionManifest("/some/non-existing-file.json");
        });
    }

    @Test
    void testUnmarshallDimensionManifestFromFile() {
        final ImageMetadataServiceConfig config = getImageMetadataServiceConfig();
        config.setDimensionManifestFilePath(dimensionManifestFilePath);
        final ImageMetadataService service = new ImageMetadataServiceImpl(config);
        final List<ImageDimensions> dimList = service.unmarshallDimensionManifestFromFile();
        assertEquals(3, dimList.size());
    }

    @Test
    void testUnmarshallDimensionManifestFromFileIOException() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        config.setDimensionManifestFilePath(
                ImageMetadataServiceTest.class.getResource("/manifests/exception-manifest.json").getPath());
        final ImageMetadataService service = new ImageMetadataServiceImpl(config);
        assertThrows(RuntimeException.class, () -> {
            service.unmarshallDimensionManifestFromFile();
        });
    }

    @Test
    void testUnmarshallDimensionManifestFromRemote() {
        final ImageMetadataServiceConfig config = getImageMetadataServiceConfig();
        final InputStream is = ImageMetadataServiceTest.class.getResourceAsStream(
                config.getDimensionManifestFilePath());
        config.setDimensionManifest(read(is));
        final ImageMetadataService service = new ImageMetadataServiceImpl(config);
        final List<ImageDimensions> dimList = service.unmarshallDimensionManifestFromRemote();
        assertEquals(3, dimList.size());
    }

    @Test
    void testUnmarshallDimensionManifestFromRemoteIOException() {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();
        final InputStream is = ImageMetadataServiceTest.class.getResourceAsStream("/manifests/exception-manifest.json");
        config.setDimensionManifest(read(is));
        final ImageMetadataService service = new ImageMetadataServiceImpl(config);
        assertThrows(RuntimeException.class, () -> {
            service.unmarshallDimensionManifestFromRemote();
        });
    }
}
