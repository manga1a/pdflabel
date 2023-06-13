<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
    <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes" />
    <xsl:param name="versionParam" select="'1.0'" />
    <!-- ========================= -->
    <!-- root element: projectteam -->
    <!-- ========================= -->
    <xsl:template match="MXCASN">
        <xsl:variable name="STORECODE" select="DETAIL/SHIPMENT/STORE/STORECODE" />
        <xsl:variable name="COMPANY" select="HEADER/FROMBUSS" />
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="first" page-width="105mm" page-height="148mm" margin-top="0mm" margin-bottom="0mm" margin-left="0mm" margin-right="0mm">
                    <fo:region-body writing-mode="lr-tb" />
                    <!-- <fo:region-after extent="0mm" /> -->
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="first">
                <fo:flow flow-name="xsl-region-body" font-size="12pt">
                    <xsl:for-each select="DETAIL/SHIPMENT/STORE/TARE">
                        <xsl:variable name="FONT_SIZE" select="'26pt'" />
                        <xsl:variable name="FONT_SIZE_LABELS" select="'11pt'" />
                        <xsl:variable name="SSCC" select="PACK/SSCC" />
                        <xsl:variable name="EAN" select="PACK/LINGRP/LIN/EAN" />
                        <xsl:variable name="BATCH" select="PACK/LINGRP/RFF/BATCHNO" />
                        <!-- 20220802 -->
                        <xsl:variable name="FULL_DATE" select="PACK/LINGRP/DTM/BESTBEFOREDATE" />
                        <xsl:variable name="QTY" select="PACK/LINGRP/QTY/TOTALSHIPQUANTITY" />
                        <xsl:variable name="YEAR" select="substring($FULL_DATE, 3,2 )" />
                        <xsl:variable name="MONTH" select="substring($FULL_DATE, 5,2)" />
                        <xsl:variable name="DAY" select="substring($FULL_DATE, 7,2 )" />
                        <fo:block-container>
                            <fo:block text-align="center" font-family="Helvetica" font-size="18pt" margin-top="2pt">
                                <xsl:value-of select="$COMPANY" />
                            </fo:block>
                            <fo:block text-align="left" margin-left="3mm" font-family="Helvetica" font-size="10pt">
                                <xsl:attribute name="font-size">
                                    <xsl:value-of select="$FONT_SIZE_LABELS" />
                                </xsl:attribute>
                                SSCC
                            </fo:block>
                            <fo:block text-align="left" margin-left="3mm" font-family="Helvetica" font-size="24pt">
                                <xsl:attribute name="font-size">
                                    <xsl:value-of select="$FONT_SIZE" />
                                </xsl:attribute>
                                <xsl:value-of select="substring($SSCC, 3)" />
                            </fo:block>
                            <fo:block text-align="left" margin-left="3mm" font-family="Helvetica" font-size="14pt" space-after="8pt">
                                <xsl:value-of select="PACK/LINGRP/IMD" />
                            </fo:block>
                            <fo:table width="10cm" table-layout="fixed" margin-left="1.5mm">
                                <fo:table-column column-width="6.7cm" />
                                <fo:table-column column-width="3.2cm" />
                                <fo:table-body font-family="sans-serif" font-weight="normal" font-size="10pt">
                                    <fo:table-row>
                                        <fo:table-cell>
                                            <fo:block text-align="start">
                                                <xsl:attribute name="font-size">
                                                    <xsl:value-of select="$FONT_SIZE_LABELS" />
                                                </xsl:attribute>
                                                ITEM NO
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block text-align="end">
                                                <xsl:attribute name="font-size">
                                                    <xsl:value-of select="$FONT_SIZE_LABELS" />
                                                </xsl:attribute>
                                                QUANTITY
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell>
                                            <fo:block text-align="start" font-size="26pt">
                                                <xsl:attribute name="font-size">
                                                    <xsl:value-of select="$FONT_SIZE" />
                                                </xsl:attribute>
                                                <xsl:value-of select="$EAN" />
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block text-align="end" font-size="10pt">
                                                <xsl:attribute name="font-size">
                                                    <xsl:value-of select="$FONT_SIZE" />
                                                </xsl:attribute>
                                                <xsl:value-of select="$QTY" />
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-body>
                            </fo:table>
                            <fo:table width="10cm" table-layout="fixed" margin-left="1.5mm">
                                <fo:table-column column-width="5cm" />
                                <fo:table-column column-width="3.0cm" />
                                <fo:table-column column-width="2cm" />
                                <fo:table-body font-family="sans-serif" font-weight="normal" font-size="10pt">
                                    <fo:table-row>
                                        <fo:table-cell>
                                            <xsl:attribute name="font-size">
                                                <xsl:value-of select="$FONT_SIZE_LABELS" />
                                            </xsl:attribute>
                                            <fo:block text-align="start">BEST BEFORE (ddmmyy)</fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block text-align="center">
                                                <xsl:attribute name="font-size">
                                                    <xsl:value-of select="$FONT_SIZE_LABELS" />
                                                </xsl:attribute>
                                                BATCH
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block text-align="end">
                                                <xsl:attribute name="font-size">
                                                    <xsl:value-of select="$FONT_SIZE_LABELS" />
                                                </xsl:attribute>
                                                DC
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell>
                                            <fo:block text-align="start" font-size="24pt">
                                                <xsl:attribute name="font-size">
                                                    <xsl:value-of select="$FONT_SIZE" />
                                                </xsl:attribute>
                                                <xsl:value-of select="concat($DAY,'.' , $MONTH,'.' , $YEAR)" />
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block text-align="center" font-size="22pt">
                                                <xsl:attribute name="font-size">
                                                    <xsl:value-of select="$FONT_SIZE" />
                                                </xsl:attribute>
                                                <xsl:value-of select="$BATCH" />
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell vertical-align="top">
                                            <fo:block text-align="end" font-size="16pt">
                                                <xsl:attribute name="font-size">
                                                    <xsl:value-of select="$FONT_SIZE_LABELS" />
                                                </xsl:attribute>
                                                <xsl:value-of select="$STORECODE" />
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-body>
                            </fo:table>
                            <fo:block line-height="1pt" text-align="center" left="0mm" space-before="0pt" space-after="10pt" padding="0pt">
                                <fo:leader line-height="1pt" leader-pattern="rule" rule-thickness="1pt" leader-length="100%" />
                            </fo:block>
                            <fo:block text-align="center">
                                <fo:instream-foreign-object>
                                    <xsl:variable name="PAD_QTY" select="format-number($QTY, '00')" />
                                    <xsl:variable name="BARCODE" select="concat('02' , substring($EAN,1,13) ,'&#x00f0;37' , $PAD_QTY, '&#x00f1;15' , $YEAR , $MONTH , $DAY, '10', $BATCH )" />
                                    <barcode:barcode xmlns:barcode="http://barcode4j.krysalis.org/ns" orientation="0">
                                        <xsl:message>
                                            <xsl:value-of select="$BARCODE" />
                                        </xsl:message>
                                        <xsl:attribute name="message">
                                            <xsl:value-of select="$BARCODE" />
                                        </xsl:attribute>
                                        <barcode:ean-128>
                                            <barcode:check-digit-marker>&#x00f0;</barcode:check-digit-marker>
                                            <barcode:module-width>0.32mm</barcode:module-width>
                                            <barcode:template>(02)n13+cd(37)n1-8(15)n6(10)an1-20</barcode:template>
                                            <barcode:height>36mm</barcode:height>
                                            <barcode:human-readable>
                                                <barcode:placement>bottom</barcode:placement>
                                                <barcode:font-size>10pt</barcode:font-size>
                                            </barcode:human-readable>
                                        </barcode:ean-128>
                                    </barcode:barcode>
                                </fo:instream-foreign-object>
                            </fo:block>
                            <fo:block text-align="center" space-after="0pt">
                                <fo:instream-foreign-object>
                                    <barcode:barcode xmlns:barcode="http://barcode4j.krysalis.org/ns" message="REPLACEDBYATTRIBUTE" orientation="0">
                                        <xsl:attribute name="message">
                                            <!-- strip the checkdigit and calc for ourselves '&#x00f0;' is the char that barcode4j replaces with its
                                                own checkdigit
                                            -->
                                            <xsl:value-of select="concat(substring($SSCC,1,19), '&#x00f0;')" />
                                        </xsl:attribute>
                                        <barcode:ean-128>
                                            <barcode:module-width>0.49mm</barcode:module-width>
                                            <barcode:template>(00)n17+cd</barcode:template>
                                            <barcode:height>36mm</barcode:height>
                                            <barcode:human-readable>
                                                <barcode:font-size>10pt</barcode:font-size>
                                            </barcode:human-readable>
                                        </barcode:ean-128>
                                    </barcode:barcode>
                                </fo:instream-foreign-object>
                            </fo:block>
                        </fo:block-container>
                    </xsl:for-each>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>