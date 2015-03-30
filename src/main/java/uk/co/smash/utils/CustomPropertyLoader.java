package uk.co.smash.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.log4j.Logger;

import uk.co.smash.model.LeagueDataModel;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomPropertyLoader {

	private static final Logger LOG = Logger.getLogger(CustomPropertyLoader.class.getName());

	private static final String LEAGUE_FILE = "/badmintonLeague.json";

	private static final String APPLICATION_PROPERTIES = "/application.properties";

	private static CustomPropertyLoader customPropertyLoder = null;

	private LeagueDataModel league = new LeagueDataModel();

	private Properties applicationProperties = new Properties();

	private CustomPropertyLoader() throws Exception {

		this.loadLeagueDetailsFromJson();
		this.loadApplicationProperties();
	}

	public static CustomPropertyLoader getInstance() throws Exception {

		if (customPropertyLoder == null) {
			customPropertyLoder = new CustomPropertyLoader();
		}
		return CustomPropertyLoader.customPropertyLoder;
	}

	public LeagueDataModel getLeague() {

		return this.league;
	}

	public Properties getApplicationProperties() {

		return this.applicationProperties;
	}

	private void loadLeagueDetailsFromJson() throws Exception {

		ObjectMapper jsonToObjectMapper = new ObjectMapper();
		InputStream leagueJsonStream = CustomPropertyLoader.class.getResourceAsStream(CustomPropertyLoader.LEAGUE_FILE);
		try {
			this.league = jsonToObjectMapper.readValue(leagueJsonStream, LeagueDataModel.class);
		} catch (JsonParseException e) {
			LOG.error("Error while parsing json file ", e);
			throw e;
		} catch (JsonMappingException e) {
			LOG.error("Error while mapping json to java object ", e);
			throw e;
		} catch (IOException e) {
			LOG.error("Error reading the json file ", e);
			throw e;
		} finally {
			leagueJsonStream.close();
		}
		LOG.debug("Loaded properties from json " + this.league.toString());
	}

	private void loadApplicationProperties() throws IOException {

		InputStream applicationPropertiesStream = CustomPropertyLoader.class.getResourceAsStream(CustomPropertyLoader.APPLICATION_PROPERTIES);
		try {
			this.applicationProperties.load(applicationPropertiesStream);
		} catch (FileNotFoundException e) {
			LOG.error("Application properties file not found", e);
			throw e;
		} catch (IOException e) {
			LOG.error("Error reading the application properties file ", e);
			throw e;
		} finally {
			applicationPropertiesStream.close();
		}
		LOG.debug("Loaded properties from a properties file " + this.applicationProperties.toString());
	}
}
