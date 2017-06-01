/* Copyright (C) 2015-2017 Stephan Kreutzer
 *
 * This file is part of wiktionary_article_to_xml_1, a submodule of the
 * wiktionary_dump_to_xml_1 package.
 *
 * wiktionary_article_to_xml_1 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3 or any later version,
 * as published by the Free Software Foundation.
 *
 * wiktionary_article_to_xml_1 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License 3 for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 3
 * along with wiktionary_article_to_xml_1. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @file $/org.wiktionary/wiktionary_dump_to_xml_1/wiktionary_article_to_xml_1/ProgramTerminationException.java
 * @brief Converts a wiktionary.org article in Mediawiki wikitext format into XML.
 * @author Stephan Kreutzer
 * @since 2016-02-20
 */



import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



class ProgramTerminationException extends RuntimeException
{
    public ProgramTerminationException(String id,
                                       Exception cause,
                                       String message,
                                       String bundle,
                                       Object ... arguments)
    {
        super(message, cause);

        this.id = id;
        this.bundle = bundle;
        this.arguments = arguments;
        this.normalTermination = false;

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(timeZone);
        this.timestamp = dateFormat.format(new Date());
    }

    public String getId()
    {
        return this.id;
    }

    public String getBundle()
    {
        return this.bundle;
    }

    public Object[] getArguments()
    {
        return this.arguments;
    }

    public String getTimestamp()
    {
        return this.timestamp;
    }

    public boolean isNormalTermination()
    {
        return this.normalTermination;
    }

    public void setNormalTermination(boolean normalTermination)
    {
        this.normalTermination = normalTermination;
    }

    protected String id;
    protected String bundle;
    protected Object[] arguments;
    protected String timestamp;
    protected boolean normalTermination;
}
