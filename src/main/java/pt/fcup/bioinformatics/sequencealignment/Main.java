package pt.fcup.bioinformatics.sequencealignment;

import org.apache.commons.cli.*;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	private static final String APP_VERSION = loadVersion();

    public static void startApp(String configFilePath) {

        try {
            AbstractConfiguration.setDefaultListDelimiter(';');

            logger.debug("loading input file : " + configFilePath);

            PropertiesConfiguration clientConfig = new PropertiesConfiguration(configFilePath);

            CompositeConfiguration config = new CompositeConfiguration();

            config.addProperty("agent.version",APP_VERSION);
            config.addProperty("config.file.path", configFilePath);

            config.addConfiguration(clientConfig);

//          Print all properties
            Iterator<String> keys = config.getKeys();

            logger.info("[configuration] *******************************************");
            while ( keys.hasNext() ){
                String configKey = keys.next();

                logger.info(configKey + ": " + config.getProperty(configKey));
            }
            logger.info("************************************************************");

//            initialize app
            IApp app = new SequenceAlignmentApplication(config);
            ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(app);
            Runtime.getRuntime().addShutdownHook(shutdownInterceptor);

            app.start();

        } catch (ConfigurationException e) {
            logger.error("Unable to read config file" ,e);
        }
    }

	/**
	 * Main entry of this application.
	 */
	public static void main(String[] args) {

        logger.info("************************************************************");
        logger.info("Version : " + APP_VERSION);
        logger.info("************************************************************");

        Option inputSequenceFile = OptionBuilder.withArgName("input")
                .hasArg()
                .withDescription(  "inpnut file with the pair of sequences" )
                .create( "input" );

        inputSequenceFile.setRequired(false);

        // create Options object
        Options options = new Options();

        // add t option
        options.addOption(inputSequenceFile);

        CommandLineParser parser = new GnuParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );

            // validate that block-size has been set
            if(  !line.hasOption( "input" )) {

                // automatically generate the help statement
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( " ", options );

                System.exit(0);
            }

            loadConfigFiles(line);

        }
        catch( ParseException exp ) {
            // oops, something went wrong
            logger.error("Parsing failed.  Reason: ", exp);
        }
    }

    private static void loadConfigFiles(CommandLine line) {
        logger.info("Loading configuration files...");

        startApp(line.getOptionValue("input"));
    }

    private static String loadVersion() {
        String userAgent = "FCUP Sequence-Alignment-CLI";
        try {
            InputStream stream = Main.class.getClassLoader().getResourceAsStream("build.properties");
            if(stream != null){
                try {
                    Properties prop = new Properties();
                    prop.load(stream);

                    String version = prop.getProperty("version");
                    userAgent += " " + version;
                } finally {
                    stream.close();
                }
            }

        } catch (IOException ex) {
            logger.error("fail to load build properties info",ex);
            // ignore
        }
        return userAgent;
    }
}