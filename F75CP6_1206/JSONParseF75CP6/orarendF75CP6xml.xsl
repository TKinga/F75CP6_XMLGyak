<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="xml" encoding="utf-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <body>
                <h2>Tamás Kinga Órarend – 2023/24. I. félév.</h2>

                <table border="4">
                    <tr bgcolor="#90EE90">
                        <th>ID</th>
                        <th>Tipus</th>
                        <th>Targy</th>
                        <th colspan="2">Idopont</th>
                        <th>Helyszin</th>
                        <th>Oktato</th>
                        <th>Szak</th>
                    </tr>

                    <xsl:apply-templates select="orarend/F75CP6_orarendje/ora"/>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="ora">
        <tr>
            <td><xsl:value-of select="@id"/></td>
            <td><xsl:value-of select="@tipus"/></td>
            <td><xsl:value-of select="targynev"/></td>
            <td><xsl:value-of select="idopont/nap"/></td>
            <td><xsl:value-of select="concat(idopont/kezdes, ' - ', idopont/befejezes)"/></td>
            <td><xsl:value-of select="helyszin"/></td>
            <td><xsl:value-of select="oktato_neve"/></td>
            <td><xsl:value-of select="szak"/></td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
