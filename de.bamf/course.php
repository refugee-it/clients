<?php
/* Copyright (C) 2017  Stephan Kreutzer
 *
 * This script is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3 or any later version,
 * as published by the Free Software Foundation.
 *
 * This script is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License 3 for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 3
 * along with this script. If not, see <http://www.gnu.org/licenses/>.
 */

$id = null;

if (isset($_GET['id']) === true)
{
    if (is_numeric($_GET['id']) === true)
    {
        $id = (int)$_GET['id'];
    }
}

$links = array("https://www.bamf.de/SharedDocs/Anlagen/EN/Downloads/Infothek/Integrationskurse/Kursteilnehmer/MerkblaetterAuslaender/630-121_merkblatt-oeffnung-Integrationskurse_englisch.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/Merkblaetter/630-121_merkblatt-oeffnung-Integrationskurse.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/Merkblaetter/630-121_merkblatt-oeffnung-Integrationskurse_tigrinya.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/AntraegeAlle/630-120_antrag-zulassung-integrationskurs-auslar_tg_pdf.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/Merkblaetter/630-121_merkblatt-oeffnung-Integrationskurse_farsi.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/AntraegeAlle/630-120_antrag-zulassung-integrationskurs-auslar_fr_pdf.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/Merkblaetter/630-121_merkblatt-oeffnung-Integrationskurse_arabisch.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/AntraegeAlle/630-120_antrag-zulassung-integrationskurs-ausl_pdf_ar.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/Merkblaetter/630-121_merkblatt-oeffnung-Integrationskurse_kurdisch-kurmanci.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/AntraegeAlle/630-120_antrag-zulassung-integrationskurs-ausl_kr_pdf.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/Merkblaetter/630-121_merkblatt-oeffnung-Integrationskurse_somali.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/AntraegeAlle/630-120_antrag-zulassung-integrationskurs-ausl_so_pdf.pdf?__blob=publicationFile",
               "https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/Merkblaetter/630-121_merkblatt-oeffnung-Integrationskurse_franzoesisch.pdf?__blob=publicationFile");

if ($id === 1 ||
    $id === 2 ||
    $id === 3 ||
    $id === 4 ||
    $id === 5 ||
    $id === 6 ||
    $id === 7 ||
    $id === 8 ||
    $id === 9 ||
    $id === 10 ||
    $id === 11 ||
    $id === 12 ||
    $id === 13)
{
    header("Location: ".$links[$id-1], true, 301);
    exit(0);
}

$names = array("Information English",
               "Information German/<span lang=\"de\" xml:lang=\"de\">Deutsch</span>",
               "Information Tigrinya/<span lang=\"ti\" xml:lang=\"ti\">ትግርኛ</span>",
               "Request Tigrinya/<span lang=\"ti\" xml:lang=\"ti\">ትግርኛ</span>",
               "Information Persian/Fārsi/فارسی",
               "Request Persian/Fārsi/فارسی",
               "Information Arabic/العَرَبِيَّة/عَرَبِيّ",
               "Request Arabic/العَرَبِيَّة/عَرَبِيّ",
               "Information Kurdish/Kurdî/کوردی, Northern Kurdish/Kurmancî/کورمانجی",
               "Request Kurdish/Kurdî/کوردی, Northern Kurdish/Kurmancî/کورمانجی",
               "Information Somali/اف سومالى‎",
               "Request Somali/اف سومالى‎",
               "Information French/<span lang=\"fr\" xml:lang=\"fr\">français</span>");

echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".
     "<!DOCTYPE html\n".
     "    PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n".
     "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n".
     "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n".
     "  <head>\n".
     "    <meta http-equiv=\"content-type\" content=\"application/xhtml+xml; charset=UTF-8\"/>\n".
     "    <title>Integration Course by BAMF</title>\n".
     "    <style type=\"text/css\">\n".
     "      body\n".
     "      {\n".
     "          font-family: sans-serif;\n".
     "          background-color: #FFE9C5;\n".
     "          margin: 0px;\n".
     "          padding: 0px;\n".
     "      }\n".
     "\n".
     "      .mainbox\n".
     "      {\n".
     "          /* Centering. */\n".
     "          margin-left: auto;\n".
     "          margin-right: auto;\n".
     "\n".
     "          border-style: solid;\n".
     "          border-width: 0px;\n".
     "          background-color: #FFFFFF;\n".
     "\n".
     "          padding: 5px;\n".
     "      }\n".
     "\n".
     "      .mainbox_header\n".
     "      {\n".
     "          background-color: #FFE9C5;\n".
     "          border-style: solid;\n".
     "          border-width: 1px;\n".
     "          border-color: #FFE9C5;\n".
     "      }\n".
     "\n".
     "      .mainbox_header_h1\n".
     "      {\n".
     "          color: #000000;\n".
     "          text-align: center;\n".
     "      }\n".
     "\n".
     "      .mainbox_body\n".
     "      {\n".
     "          background-color: #FFFFFF;\n".
     "          text-align: justify;\n".
     "      }\n".
     "\n".
     "      .license\n".
     "      {\n".
     "          font-family: monospace;\n".
     "          text-align: left;\n".
     "          margin-top: 50px;\n".
     "          margin-bottom: 50px;\n".
     "      }\n".
     "    </style>\n".
     "  </head>\n".
     "  <body>\n".
     "    <div class=\"mainbox\">\n".
     "      <div class=\"mainbox_header\">\n".
     "        <h1 class=\"mainbox_header_h1\">Integration Course by BAMF</h1>\n".
     "      </div>\n".
     "      <div class=\"mainbox_body\">\n".
     "        <ul>\n";

for ($i = 0; $i < sizeof($links); $i++)
{
    echo "          <li><a href=\"".$links[$i]."\">".$names[$i]."</a></li>\n";
}

echo "        </ul>\n".
     "        <ul>\n".
     "          <li><a href=\"https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/Merkblaetter/630-121_merkblatt-oeffnung-Integrationskurse.html\">Information List</a></li>\n".
     "          <li><a href=\"https://www.bamf.de/SharedDocs/Anlagen/DE/Downloads/Infothek/Integrationskurse/Kursteilnehmer/AntraegeAlle/630-120_antrag-zulassung-integrationskurs-ausl_pdf.html\">Request List</a></li>\n".
     "        </ul>\n".
     "        <p class=\"license\">\n".
     "          Copyright (C) 2017  <span lang=\"de\" xml:lang=\"de\">Stephan Kreutzer</span><br/>\n".
     "          <br/>\n".
     "          This script is free software: you can redistribute it and/or modify\n".
     "          it under the terms of the GNU Affero General Public License version 3 or any later version,\n".
     "          as published by the Free Software Foundation.<br/>\n".
     "          <br/>\n".
     "          This script is distributed in the hope that it will be useful,\n".
     "          but WITHOUT ANY WARRANTY; without even the implied warranty of\n".
     "          MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\n".
     "          GNU Affero General Public License 3 for more details.<br/>\n".
     "          <br/>\n".
     "          You should have received a copy of the GNU Affero General Public License 3\n".
     "          along with this script. If not, see &lt;<a href=\"http://www.gnu.org/licenses/\">http://www.gnu.org/licenses/</a>&gt;.<br/>\n".
     "          <br/>\n".
     "          The complete source code is available at &lt;<a href=\"https://github.com/refugee-it/clients/\">https://github.com/refugee-it/clients/</a>&gt;.<br/>\n".
     "        </p>\n".
     "      </div>\n".
     "    </div>\n".
     "  </body>\n".
     "</html>\n";




?>