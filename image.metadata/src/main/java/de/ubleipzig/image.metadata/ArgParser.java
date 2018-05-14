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

import static java.lang.System.out;
import static org.apache.commons.cli.Option.builder;

import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * ArgParser.
 *
 * @author christopher-johnson
 */
class ArgParser {

    private static final Options configOptions = new Options();

    static {
        configOptions.addOption(builder("i").longOpt("input").hasArg(true).desc("Source").required(true).build());

        configOptions.addOption(builder("o").longOpt("output").hasArg(true).desc("Output").required(true).build());

        configOptions.addOption(
                builder("z").longOpt("manifest").hasArg(true).desc("Image Manifest Output").required(false).build());
    }

    /**
     * Parse command line options based on the provide Options.
     *
     * @param configOptions valid set of Options
     * @param args command line arguments
     * @return the list of option and values
     * @throws ParseException if invalid/missing option is found
     */
    private static CommandLine parseConfigArgs(final Options configOptions, final String[] args) throws ParseException {
        return new DefaultParser().parse(configOptions, args);
    }

    /**
     * Parse command-line arguments.
     *
     * @param args Command-line arguments
     * @return A configured IIIFProducer instance.
     **/
    ImageMetadataService init(final String[] args) {
        final ImageMetadataServiceConfig config = parseConfiguration(args);
        return new ImageMetadataServiceImpl(config);
    }

    /**
     * Parse command line arguments into a Config object.
     *
     * @param args command line arguments
     * @return the parsed config file or command line args.
     */
    public ImageMetadataServiceConfig parseConfiguration(final String[] args) {
        final CommandLine c;
        ImageMetadataServiceConfig config = null;

        try {
            c = parseConfigArgs(configOptions, args);
            config = parseConfigurationArgs(c);
        } catch (final ParseException e) {
            printHelp("Error parsing args: " + e.getMessage());
        }

        return config;
    }

    /**
     * This method parses the command-line args.
     *
     * @param cmd command line options
     * @return Config
     */
    private ImageMetadataServiceConfig parseConfigurationArgs(final CommandLine cmd) {
        final ImageMetadataServiceConfig config = new ImageMetadataServiceConfig();

        config.setImageSourceDir(cmd.getOptionValue('i'));
        config.setDimensionManifestOutputPath(cmd.getOptionValue('o'));
        config.setImageMetadataManifestOutputPath(cmd.getOptionValue('z'));
        return config;
    }

    /**
     * Print help/usage information.
     *
     * @param message the message or null for none
     */
    private void printHelp(final String message) {
        final HelpFormatter formatter = new HelpFormatter();
        final PrintWriter writer = new PrintWriter(out);
        writer.println("\n-----------------------\n" + message + "\n-----------------------\n");
        writer.println("Running Image Manifest Service from command line arguments");
        formatter.printHelp(writer, 80, "./image.metadata", "", configOptions, 4, 4, "", true);
        writer.println("\n");
        writer.flush();
        throw new RuntimeException(message);
    }
}
