/* Copyright (C) 2016  Stephan Kreutzer
 *
 * This file is part of langsam_gesprochene_nachrichten_downloader_1.
 *
 * langsam_gesprochene_nachrichten_downloader_1 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3 or any later version,
 * as published by the Free Software Foundation.
 *
 * langsam_gesprochene_nachrichten_downloader_1 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License 3 for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 3
 * along with langsam_gesprochene_nachrichten_downloader_1. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @file $/com.dw/langsam_gesprochene_nachrichten_downloader_1/langsam_gesprochene_nachrichten_downloader_1.java
 * @author Stephan Kreutzer
 * @since 2016-04-18
 */



import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;
import java.io.File;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import javax.xml.stream.XMLInputFactory;
import java.io.InputStream;
import java.io.FileInputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.Attribute;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.ArrayList;



public class langsam_gesprochene_nachrichten_downloader_1
{
    public static void main(String args[])
    {
        System.out.print("langsam_gesprochene_nachrichten_downloader_1 Copyright (C) 2016 Stephan Kreutzer\n" +
                         "This program comes with ABSOLUTELY NO WARRANTY.\n" +
                         "This is free software, and you are welcome to redistribute it\n" +
                         "under certain conditions. See the GNU Affero General Public License 3\n" +
                         "or any later version for details. Also, see the source code repository\n" +
                         "https://github.com/refugee-it/clients/ and the project website\n" +
                         "http://www.refugee-it.de.\n\n");


        langsam_gesprochene_nachrichten_downloader_1 client = new langsam_gesprochene_nachrichten_downloader_1();
        client.execute(args);
    }

    public int execute(String args[])
    {
        if (args.length < 1)
        {
            constructTermination("messageArgumentsMissing", null, getI10nString("messageArgumentsMissingUsage") + "\n\tlangsam_gesprochene_nachrichten_downloader_1 " + getI10nString("messageParameterList") + "\n");
        }


        String programPath = langsam_gesprochene_nachrichten_downloader_1.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        try
        {
            programPath = new File(programPath).getCanonicalPath() + File.separator;
            programPath = URLDecoder.decode(programPath, "UTF-8");
        }
        catch (UnsupportedEncodingException ex)
        {
            constructTermination("messageCantDetermineProgramPath", ex, null);
        }
        catch (IOException ex)
        {
            constructTermination("messageCantDetermineProgramPath", ex, null);
        }

        File tempDirectory = new File(programPath + "temp");

        if (tempDirectory.exists() == true)
        {
            if (tempDirectory.isDirectory() == true)
            {
                if (tempDirectory.canWrite() != true)
                {
                    constructTermination("messageTempDirectoryIsntWritable", null, null, tempDirectory.getAbsolutePath());
                }
            }
            else
            {
                constructTermination("messageTempPathIsntDirectory", null, null, tempDirectory.getAbsolutePath());
            }
        }
        else
        {
            try
            {
                tempDirectory.mkdirs();
            }
            catch (SecurityException ex)
            {
                constructTermination("messageTempDirectoryCantCreate", null, null, tempDirectory.getAbsolutePath());
            }
        }

        File settingsFile = new File(programPath + "settings.xml");

        try
        {
            settingsFile = settingsFile.getCanonicalFile();
        }
        catch (SecurityException ex)
        {
            constructTermination("messageSettingsFileCantGetCanonicalPath", ex, null, settingsFile.getAbsolutePath());
        }
        catch (IOException ex)
        {
            constructTermination("messageSettingsFileCantGetCanonicalPath", ex, null, settingsFile.getAbsolutePath());
        }

        if (settingsFile.exists() != true)
        {
            constructTermination("messageSettingsFileDoesntExist", null, null, settingsFile.getAbsolutePath());
        }

        if (settingsFile.isFile() != true)
        {
            constructTermination("messageSettingsPathIsntAFile", null, null, settingsFile.getAbsolutePath());
        }

        if (settingsFile.canRead() != true)
        {
            constructTermination("messageSettingsFileIsntReadable", null, null, settingsFile.getAbsolutePath());
        }


        String inputURL = null;
        File outputDirectory = null;

        try
        {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(settingsFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            while (eventReader.hasNext() == true)
            {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement() == true)
                {
                    String tagName = event.asStartElement().getName().getLocalPart();

                    if (tagName.equals("input") == true)
                    {
                        StartElement inputElement = event.asStartElement();
                        Attribute urlAttribute = inputElement.getAttributeByName(new QName("url"));

                        if (urlAttribute == null)
                        {
                            constructTermination("messageSettingsFileEntryIsMissingAnAttribute", null, null, settingsFile.getAbsolutePath(), tagName, "url");
                        }

                        inputURL = urlAttribute.getValue();
                    }
                    else if (tagName.equals("output-directory") == true)
                    {
                        StartElement outputDirectoryElement = event.asStartElement();
                        Attribute pathAttribute = outputDirectoryElement.getAttributeByName(new QName("path"));

                        if (pathAttribute == null)
                        {
                            constructTermination("messageSettingsFileEntryIsMissingAnAttribute", null, null, settingsFile.getAbsolutePath(), tagName, "path");
                        }

                        String outputPath = pathAttribute.getValue();
                        outputDirectory = new File(outputPath);

                        if (outputDirectory.isAbsolute() != true)
                        {
                            outputDirectory = new File(settingsFile.getAbsoluteFile().getParent() + File.separator + outputPath);
                        }

                        try
                        {
                            outputDirectory = outputDirectory.getCanonicalFile();
                        }
                        catch (SecurityException ex)
                        {
                            constructTermination("messageOutputDirectoryCantGetCanonicalPath", ex, null, outputDirectory.getAbsolutePath(), settingsFile.getAbsolutePath());
                        }
                        catch (IOException ex)
                        {
                            constructTermination("messageOutputDirectoryCantGetCanonicalPath", ex, null, outputDirectory.getAbsolutePath(), settingsFile.getAbsolutePath());
                        }

                        if (outputDirectory.exists() == true)
                        {
                            if (outputDirectory.isDirectory() == true)
                            {
                                if (outputDirectory.canWrite() != true)
                                {
                                    constructTermination("messageOutputDirectoryIsntWritable", null, null, outputDirectory.getAbsolutePath(), settingsFile.getAbsolutePath());
                                }
                            }
                            else
                            {
                                constructTermination("messageOutputPathIsntDirectory", null, null, outputDirectory.getAbsolutePath(), settingsFile.getAbsolutePath());
                            }
                        }
                        else
                        {
                            try
                            {
                                outputDirectory.mkdirs();
                            }
                            catch (SecurityException ex)
                            {
                                constructTermination("messageOutputDirectoryCantCreate", null, null, outputDirectory.getAbsolutePath(), settingsFile.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
        catch (XMLStreamException ex)
        {
            constructTermination("messageSettingsFileErrorWhileReading", ex, null, settingsFile.getAbsolutePath());
        }
        catch (SecurityException ex)
        {
            constructTermination("messageSettingsFileErrorWhileReading", ex, null, settingsFile.getAbsolutePath());
        }
        catch (IOException ex)
        {
            constructTermination("messageSettingsFileErrorWhileReading", ex, null, settingsFile.getAbsolutePath());
        }

        if (inputURL == null)
        {
            constructTermination("messageSettingsFileNoInputURL", null, null, settingsFile.getAbsolutePath());
        }

        if (outputDirectory == null)
        {
            constructTermination("messageSettingsFileNoOutputDirectory", null, null, settingsFile.getAbsolutePath());
        }

        File lastRetrievalFile = new File(tempDirectory.getAbsolutePath() + File.separator + "last_retrieval.txt");

        if (lastRetrievalFile.exists() == true)
        {
            if (lastRetrievalFile.isFile() == true)
            {
                if (lastRetrievalFile.canRead() != true)
                {
                    constructTermination("messageLastRetrievalFileIsntReadable", null, null, lastRetrievalFile.getAbsolutePath());
                }
            }
            else
            {
                constructTermination("messageLastRetrievalPathIsntAFile", null, null, lastRetrievalFile.getAbsolutePath());
            }
        }

        Date lastRetrieval = null;

        if (lastRetrievalFile.exists() == true)
        {
            String lastRetrievalString = null;

            try
            {
                StringBuilder sb = new StringBuilder();

                BufferedReader reader = new BufferedReader(
                                        new FileReader(lastRetrievalFile));

                lastRetrievalString = reader.readLine();

                while (lastRetrievalString != null)
                {
                    sb.append(lastRetrievalString);
                    lastRetrievalString = reader.readLine();
                }

                reader.close();

                lastRetrievalString = sb.toString();

                if (lastRetrievalString != null)
                {
                    lastRetrieval = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US).parse(lastRetrievalString);
                }
            }
            catch (ParseException ex)
            {
                constructTermination("messageLastRetrievalFileErrorWhileReading", ex, null, lastRetrievalFile.getAbsolutePath());
            }
            catch (FileNotFoundException ex)
            {
                constructTermination("messageLastRetrievalFileErrorWhileReading", ex, null, lastRetrievalFile.getAbsolutePath());
            }
            catch (IOException ex)
            {
                constructTermination("messageLastRetrievalFileErrorWhileReading", ex, null, lastRetrievalFile.getAbsolutePath());
            }
        }


        File httpsClient1JobFile = new File(tempDirectory + File.separator + "jobfile_https_client_1.xml");

        if (httpsClient1JobFile.exists() == true)
        {
            if (httpsClient1JobFile.isFile() == true)
            {
                boolean deleteSuccessful = false;

                try
                {
                    deleteSuccessful = httpsClient1JobFile.delete();
                }
                catch (SecurityException ex)
                {

                }

                if (deleteSuccessful != true)
                {
                    if (httpsClient1JobFile.canWrite() != true)
                    {
                        constructTermination("messageHttpsClient1JobFileIsntWritable", null, null, httpsClient1JobFile.getAbsolutePath());
                    }
                }
            }
            else
            {
                constructTermination("messageHttpsClient1JobPathIsntAFile", null, null, httpsClient1JobFile.getAbsolutePath());
            }
        }

        try
        {
            BufferedWriter writer = new BufferedWriter(
                                    new OutputStreamWriter(
                                    new FileOutputStream(httpsClient1JobFile),
                                    "UTF-8"));

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<!-- This file was created by langsam_gesprochene_nachrichten_downloader_1, which is free software licensed under the GNU Affero General Public License 3 or any later version (see https://github.com/refugee-it/clients/ and http://www.refugee-it.de). -->\n");
            writer.write("<https-client-1-jobfile>\n");
            writer.write("  <request url=\"" + inputURL + "\" method=\"GET\"/>\n");
            writer.write("  <response destination=\"" + tempDirectory.getAbsolutePath() + File.separator + "response.rss\"/>\n");
            writer.write("</https-client-1-jobfile>\n");

            writer.flush();
            writer.close();
        }
        catch (FileNotFoundException ex)
        {
            constructTermination("messageHttpsClient1JobFileWritingError", ex, null, httpsClient1JobFile.getAbsolutePath());
        }
        catch (UnsupportedEncodingException ex)
        {
            constructTermination("messageHttpsClient1JobFileWritingError", ex, null, httpsClient1JobFile.getAbsolutePath());
        }
        catch (IOException ex)
        {
            constructTermination("messageHttpsClient1JobFileWritingError", ex, null, httpsClient1JobFile.getAbsolutePath());
        }

        File httpsClient1ResultInfoFile = new File(tempDirectory.getAbsolutePath() + File.separator + "resultinfo_https_client_1.xml");

        if (httpsClient1ResultInfoFile.exists() == true)
        {
            if (httpsClient1ResultInfoFile.isFile() == true)
            {
                boolean deleteSuccessful = false;

                try
                {
                    deleteSuccessful = httpsClient1ResultInfoFile.delete();
                }
                catch (SecurityException ex)
                {

                }

                if (deleteSuccessful != true)
                {
                    if (httpsClient1ResultInfoFile.canWrite() != true)
                    {
                        constructTermination("messageHttpsClient1ResultInfoFileExistsButIsntWritable", null, null, httpsClient1ResultInfoFile.getAbsolutePath());
                    }
                }
            }
            else
            {
                constructTermination("messageHttpsClient1ResultInfoPathExistsButIsntAFile", null, null, httpsClient1ResultInfoFile.getAbsolutePath());
            }
        }

        ProcessBuilder builder = new ProcessBuilder("java", "https_client_1", httpsClient1JobFile.getAbsolutePath(), httpsClient1ResultInfoFile.getAbsolutePath());
        builder.directory(new File(programPath + File.separator + ".." + File.separator + ".." + File.separator + "org.publishing-systems" + File.separator + "digital_publishing_workflow_tools" + File.separator + "https_client" + File.separator + "https_client_1"));
        builder.redirectErrorStream(true);

        try
        {
            Process process = builder.start();
            Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\n");

            while (scanner.hasNext() == true)
            {
                System.out.println(scanner.next());
            }

            scanner.close();
        }
        catch (IOException ex)
        {
            constructTermination("messageHttpsClient1ErrorWhileReadingOutput", ex, null);
        }

        if (httpsClient1ResultInfoFile.exists() != true)
        {
            constructTermination("messageHttpsClient1ResultInfoFileDoesntExistButShould", null, null, httpsClient1ResultInfoFile.getAbsolutePath());
        }

        if (httpsClient1ResultInfoFile.isFile() != true)
        {
            constructTermination("messageHttpsClient1ResultInfoPathExistsButIsntAFile", null, null, httpsClient1ResultInfoFile.getAbsolutePath());
        }

        if (httpsClient1ResultInfoFile.canRead() != true)
        {
            constructTermination("messageHttpsClient1ResultInfoFileIsntReadable", null, null, httpsClient1ResultInfoFile.getAbsolutePath());
        }

        boolean wasSuccess = false;

        try
        {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(httpsClient1ResultInfoFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            while (eventReader.hasNext() == true)
            {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement() == true)
                {
                    String tagName = event.asStartElement().getName().getLocalPart();

                    if (tagName.equals("success") == true)
                    {
                        wasSuccess = true;
                        break;
                    }
                }
            }
        }
        catch (XMLStreamException ex)
        {
            constructTermination("messageHttpsClient1ResultInfoFileErrorWhileReading", ex, null, httpsClient1ResultInfoFile.getAbsolutePath());
        }
        catch (SecurityException ex)
        {
            constructTermination("messageHttpsClient1ResultInfoFileErrorWhileReading", ex, null, httpsClient1ResultInfoFile.getAbsolutePath());
        }
        catch (IOException ex)
        {
            constructTermination("messageHttpsClient1ResultInfoFileErrorWhileReading", ex, null, httpsClient1ResultInfoFile.getAbsolutePath());
        }

        if (wasSuccess != true)
        {
            constructTermination("messageHttpsClient1CallWasntSuccessful", null, null, httpsClient1ResultInfoFile.getAbsolutePath());
        }


        File rssFile = new File(tempDirectory + File.separator + "response.rss");

        if (rssFile.exists() != true)
        {
            constructTermination("messageRssFileDoesntExistButShould", null, null, rssFile.getAbsolutePath());
        }

        if (rssFile.isFile() != true)
        {
            constructTermination("messageRssPathExistsButIsntAFile", null, null, rssFile.getAbsolutePath());
        }

        if (rssFile.canRead() != true)
        {
            constructTermination("messageRssFileIsntReadable", null, null, rssFile.getAbsolutePath());
        }


        String firstPubDate = null;

        try
        {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(rssFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            boolean isInRss = false;
            boolean isInChannel = false;
            boolean isInItem = false;

            Date pubDate = null;
            StringBuilder sbDescription = null;
            ArrayList<String> enclosure = null;

            while (eventReader.hasNext() == true)
            {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement() == true)
                {
                    QName elementName = event.asStartElement().getName();
                    String fullElementName = elementName.getLocalPart();

                    if (elementName.getPrefix().isEmpty() != true)
                    {
                        fullElementName = elementName.getPrefix() + ":" + fullElementName;
                    }

                    if (fullElementName.equals("rss") == true)
                    {
                        if (isInRss == true)
                        {
                            constructTermination("messageRssFileNestedElement", null, null, rssFile.getAbsolutePath(), fullElementName);
                        }
                        else
                        {
                            isInRss = true;
                        }
                    }
                    else if(fullElementName.equals("channel") &&
                            isInRss == true)
                    {
                        if (isInChannel == true)
                        {
                            constructTermination("messageRssFileNestedElement", null, null, rssFile.getAbsolutePath(), fullElementName);
                        }
                        else
                        {
                            isInChannel = true;
                        }
                    }
                    else if (fullElementName.equals("item") &&
                             isInChannel == true)
                    {
                        if (isInItem == true)
                        {
                            constructTermination("messageRssFileNestedElement", null, null, rssFile.getAbsolutePath(), fullElementName);
                        }
                        else
                        {
                            isInItem = true;
                        }
                    }
                    else if (fullElementName.equals("pubDate") == true &&
                             isInItem == true)
                    {
                        String pubDateString = new String();

                        while (eventReader.hasNext() == true)
                        {
                            event = eventReader.nextEvent();

                            if (event.isCharacters() == true)
                            {
                                pubDateString += event.asCharacters();
                            }
                            else if (event.isEndElement() == true)
                            {
                                QName abortElementName = event.asEndElement().getName();
                                String abortFullElementName = abortElementName.getLocalPart();

                                if (abortElementName.getPrefix().isEmpty() != true)
                                {
                                    abortFullElementName = abortElementName.getPrefix() + ":" + abortFullElementName;
                                }

                                if (abortFullElementName.equals("pubDate") == true)
                                {
                                    break;
                                }
                            }
                        }

                        pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US).parse(pubDateString);

                        if (lastRetrieval != null)
                        {
                            if (pubDate.before(lastRetrieval) == true || pubDate.equals(lastRetrieval) == true)
                            {
                                break;
                            }
                        }

                        if (firstPubDate == null)
                        {
                            firstPubDate = pubDateString;
                        }
                    }
                    else if (fullElementName.equals("description") == true &&
                             isInItem == true)
                    {
                        if (sbDescription == null)
                        {
                            sbDescription = new StringBuilder();
                        }

                        while (eventReader.hasNext() == true)
                        {
                            event = eventReader.nextEvent();

                            if (event.isCharacters() == true)
                            {
                                sbDescription.append(event.asCharacters());
                            }
                            else if (event.isEndElement() == true)
                            {
                                QName abortElementName = event.asEndElement().getName();
                                String abortFullElementName = abortElementName.getLocalPart();

                                if (abortElementName.getPrefix().isEmpty() != true)
                                {
                                    abortFullElementName = abortElementName.getPrefix() + ":" + abortFullElementName;
                                }

                                if (abortFullElementName.equals("description") == true)
                                {
                                    break;
                                }
                            }
                        }
                    }
                    else if (fullElementName.equals("enclosure") == true &&
                             isInItem == true)
                    {
                        if (enclosure == null)
                        {
                            enclosure = new ArrayList<String>();
                        }

                        StartElement inputElement = event.asStartElement();
                        Attribute urlAttribute = event.asStartElement().getAttributeByName(new QName("url"));

                        if (urlAttribute == null)
                        {
                            constructTermination("messageRssFileElementIsMissingAnAttribute", null, null, rssFile.getAbsolutePath(), fullElementName, "url");
                        }

                        enclosure.add(urlAttribute.getValue());
                    }
                }
                else if (event.isEndElement() == true)
                {
                    QName elementName = event.asEndElement().getName();
                    String fullElementName = elementName.getLocalPart();

                    if (elementName.getPrefix().isEmpty() != true)
                    {
                        fullElementName = elementName.getPrefix() + ":" + fullElementName;
                    }

                    if (fullElementName.equals("item") == true &&
                        isInItem == true)
                    {
                        if (pubDate == null)
                        {
                            constructTermination("messageRssFileNoPubDate", null, null, rssFile.getAbsolutePath());
                        }

                        String pubDateString = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US).format(pubDate);

                        if (sbDescription == null)
                        {
                            constructTermination("messageRssFileNoDescription", null, null, rssFile.getAbsolutePath(), pubDateString);
                        }

                        if (enclosure == null)
                        {
                            constructTermination("messageRssFileNoEnclosure", null, null, rssFile.getAbsolutePath(), pubDateString);
                        }

                        File resultDirectory = new File(outputDirectory.getAbsolutePath() + File.separator + new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(pubDate));

                        if (resultDirectory.exists() != true)
                        {
                            try
                            {
                                resultDirectory.mkdirs();
                            }
                            catch (SecurityException ex)
                            {
                                constructTermination("messageResultDirectoryCantCreate", ex, null, resultDirectory.getAbsolutePath(), pubDateString);
                            }
                        }
                        else
                        {
                            // If the directory exists already, then it was retrieved in a previous run.
                            break;
                        }

                        System.out.println(getI10nStringFormatted("messageRetrieving", pubDateString));

                        File textFile = new File(resultDirectory.getAbsolutePath() + File.separator + "text.txt");

                        try
                        {
                            BufferedWriter writer = new BufferedWriter(
                                                    new OutputStreamWriter(
                                                    new FileOutputStream(textFile),
                                                    "UTF-8"));

                            writer.write(sbDescription.toString());

                            writer.flush();
                            writer.close();
                        }
                        catch (FileNotFoundException ex)
                        {
                            constructTermination("messageTextFileErrorWhileWriting", ex, null, textFile.getAbsolutePath(), pubDateString);
                        }
                        catch (UnsupportedEncodingException ex)
                        {
                             constructTermination("messageTextFileErrorWhileWriting", ex, null, textFile.getAbsolutePath(), pubDateString);
                        }
                        catch (IOException ex)
                        {
                             constructTermination("messageTextFileErrorWhileWriting", ex, null, textFile.getAbsolutePath(), pubDateString);
                        }

                        for (int i = 0, max = enclosure.size(); i < max; i++)
                        {
                            builder = new ProcessBuilder("java", "downloader1", enclosure.get(i), resultDirectory.getAbsolutePath() + File.separator + "audio_" + i + ".mp3");
                            builder.directory(new File(programPath + File.separator + ".." + File.separator + ".." + File.separator + "org.publishing-systems" + File.separator + "automated_digital_publishing" + File.separator + "downloader" + File.separator + "downloader1"));
                            builder.redirectErrorStream(true);

                            try
                            {
                                Process process = builder.start();
                                Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\n");

                                while (scanner.hasNext() == true)
                                {
                                    System.out.println(scanner.next());
                                }

                                scanner.close();
                            }
                            catch (IOException ex)
                            {
                                constructTermination("messageDownloader1ErrorWhileReadingOutput", ex, null);
                            }
                        }

                        enclosure = null;
                        sbDescription = null;
                        pubDate = null;

                        isInItem = false;
                    }
                    else if (fullElementName.equals("channel") == true &&
                             isInChannel == true)
                    {
                        isInItem = false;
                        isInChannel = false;
                    }
                    else if (fullElementName.equals("rss") == true &&
                             isInRss == true)
                    {
                        isInItem = false;
                        isInChannel = false;
                        isInRss = false;
                    }
                }
            }
        }
        catch (ParseException ex)
        {
            constructTermination("messageRssFileErrorWhileReading", ex, null, rssFile.getAbsolutePath());
        }
        catch (XMLStreamException ex)
        {
            constructTermination("messageRssFileErrorWhileReading", ex, null, rssFile.getAbsolutePath());
        }
        catch (SecurityException ex)
        {
            constructTermination("messageRssFileErrorWhileReading", ex, null, rssFile.getAbsolutePath());
        }
        catch (IOException ex)
        {
            constructTermination("messageRssFileErrorWhileReading", ex, null, rssFile.getAbsolutePath());
        }

        if (firstPubDate != null)
        {
            try
            {
                BufferedWriter writer = new BufferedWriter(
                                        new OutputStreamWriter(
                                        new FileOutputStream(lastRetrievalFile),
                                        "UTF-8"));

                writer.write(firstPubDate);

                writer.flush();
                writer.close();
            }
            catch (FileNotFoundException ex)
            {
                constructTermination("messageLastRetrievalFileErrorWhileWriting", ex, null, lastRetrievalFile.getAbsolutePath());
            }
            catch (UnsupportedEncodingException ex)
            {
                constructTermination("messageLastRetrievalFileErrorWhileWriting", ex, null, lastRetrievalFile.getAbsolutePath());
            }
            catch (IOException ex)
            {
                constructTermination("messageLastRetrievalFileErrorWhileWriting", ex, null, lastRetrievalFile.getAbsolutePath());
            }
        }

        return 0;
    }

    public void constructTermination(String id, Exception cause, String message, Object ... arguments)
    {
        if (message == null)
        {
            if (arguments == null)
            {
                message = "langsam_gesprochene_nachrichten_downloader_1: " + getI10nString(id);
            }
            else
            {
                message = "langsam_gesprochene_nachrichten_downloader_1: " + getI10nStringFormatted(id, arguments);
            }
        }

        if (message != null)
        {
            System.out.println(message);
        }

        if (cause != null)
        {
            cause.printStackTrace();
        }

        System.exit(-1);
    }

    public Locale getLocale()
    {
        return Locale.getDefault();
    }

    /**
     * @brief This method interprets l10n strings from a .properties file as encoded in UTF-8.
     */
    private String getI10nString(String key)
    {
        if (this.l10nConsole == null)
        {
            this.l10nConsole = ResourceBundle.getBundle(L10N_BUNDLE, this.getLocale());
        }

        try
        {
            return new String(this.l10nConsole.getString(key).getBytes("ISO-8859-1"), "UTF-8");
        }
        catch (UnsupportedEncodingException ex)
        {
            return this.l10nConsole.getString(key);
        }
    }

    private String getI10nStringFormatted(String i10nStringName, Object ... arguments)
    {
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(this.getLocale());

        formatter.applyPattern(getI10nString(i10nStringName));
        return formatter.format(arguments);
    }

    private static final String L10N_BUNDLE = "l10n.l10nLangsamGesprocheneNachrichtenDownloader1Console";
    private ResourceBundle l10nConsole;
}

