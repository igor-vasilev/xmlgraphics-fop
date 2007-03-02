/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id$ */

package org.apache.fop.fonts;

// FOP (base 14 fonts)
import org.apache.fop.fonts.base14.Helvetica;
import org.apache.fop.fonts.base14.HelveticaBold;
import org.apache.fop.fonts.base14.HelveticaOblique;
import org.apache.fop.fonts.base14.HelveticaBoldOblique;
import org.apache.fop.fonts.base14.TimesRoman;
import org.apache.fop.fonts.base14.TimesBold;
import org.apache.fop.fonts.base14.TimesItalic;
import org.apache.fop.fonts.base14.TimesBoldItalic;
import org.apache.fop.fonts.base14.Courier;
import org.apache.fop.fonts.base14.CourierBold;
import org.apache.fop.fonts.base14.CourierOblique;
import org.apache.fop.fonts.base14.CourierBoldOblique;
import org.apache.fop.fonts.base14.Symbol;
import org.apache.fop.fonts.base14.ZapfDingbats;
import org.apache.fop.render.PrintRenderer;

// commons logging
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Avalon
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;

// Java
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Default fonts for FOP application; currently this uses PDF's fonts
 * by default.
 *
 * Assigns the font (with metrics) to internal names like "F1" and
 * assigns family-style-weight triplets to the fonts
 */
public class FontSetup {

    /**
     * logging instance
     */
    protected static Log log = LogFactory.getLog("org.apache.fop.fonts");
    
    /**
     * Sets up the font info object.
     *
     * Adds metrics for basic fonts and useful family-style-weight
     * triplets for lookup.
     *
     * @param fontInfo the font info object to set up
     * @param embedList a list of EmbedFontInfo objects
     * @param resolver the font resolver
     */
    public static void setup(FontInfo fontInfo, List embedList, FontResolver resolver) {
        setup(fontInfo, embedList, resolver, false);
    }

    /**
     * Sets up the font info object.
     *
     * Adds metrics for basic fonts and useful family-style-weight
     * triplets for lookup.
     *
     * @param fontInfo the font info object to set up
     * @param embedList a list of EmbedFontInfo objects
     * @param resolver the font resolver
     * @param enableBase14Kerning true if kerning should be enabled for base 14 fonts
     */
    public static void setup(FontInfo fontInfo, List embedList, FontResolver resolver, 
            boolean enableBase14Kerning) {

        fontInfo.addMetrics("F1", new Helvetica(enableBase14Kerning));
        fontInfo.addMetrics("F2", new HelveticaOblique(enableBase14Kerning));
        fontInfo.addMetrics("F3", new HelveticaBold(enableBase14Kerning));
        fontInfo.addMetrics("F4", new HelveticaBoldOblique(enableBase14Kerning));
        fontInfo.addMetrics("F5", new TimesRoman(enableBase14Kerning));
        fontInfo.addMetrics("F6", new TimesItalic(enableBase14Kerning));
        fontInfo.addMetrics("F7", new TimesBold(enableBase14Kerning));
        fontInfo.addMetrics("F8", new TimesBoldItalic(enableBase14Kerning));
        fontInfo.addMetrics("F9", new Courier(enableBase14Kerning));
        fontInfo.addMetrics("F10", new CourierOblique(enableBase14Kerning));
        fontInfo.addMetrics("F11", new CourierBold(enableBase14Kerning));
        fontInfo.addMetrics("F12", new CourierBoldOblique(enableBase14Kerning));
        fontInfo.addMetrics("F13", new Symbol());
        fontInfo.addMetrics("F14", new ZapfDingbats());

        // Custom type 1 fonts step 1/2
        // fontInfo.addMetrics("F15", new OMEP());
        // fontInfo.addMetrics("F16", new GaramondLightCondensed());
        // fontInfo.addMetrics("F17", new BauerBodoniBoldItalic());

        /* any is treated as serif */
        fontInfo.addFontProperties("F5", "any", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F6", "any", "italic", Font.NORMAL);
        fontInfo.addFontProperties("F6", "any", "oblique", Font.NORMAL);
        fontInfo.addFontProperties("F7", "any", "normal", Font.BOLD);
        fontInfo.addFontProperties("F8", "any", "italic", Font.BOLD);
        fontInfo.addFontProperties("F8", "any", "oblique", Font.BOLD);

        fontInfo.addFontProperties("F1", "sans-serif", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F2", "sans-serif", "oblique", Font.NORMAL);
        fontInfo.addFontProperties("F2", "sans-serif", "italic", Font.NORMAL);
        fontInfo.addFontProperties("F3", "sans-serif", "normal", Font.BOLD);
        fontInfo.addFontProperties("F4", "sans-serif", "oblique", Font.BOLD);
        fontInfo.addFontProperties("F4", "sans-serif", "italic", Font.BOLD);
        fontInfo.addFontProperties("F5", "serif", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F6", "serif", "oblique", Font.NORMAL);
        fontInfo.addFontProperties("F6", "serif", "italic", Font.NORMAL);
        fontInfo.addFontProperties("F7", "serif", "normal", Font.BOLD);
        fontInfo.addFontProperties("F8", "serif", "oblique", Font.BOLD);
        fontInfo.addFontProperties("F8", "serif", "italic", Font.BOLD);
        fontInfo.addFontProperties("F9", "monospace", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F10", "monospace", "oblique", Font.NORMAL);
        fontInfo.addFontProperties("F10", "monospace", "italic", Font.NORMAL);
        fontInfo.addFontProperties("F11", "monospace", "normal", Font.BOLD);
        fontInfo.addFontProperties("F12", "monospace", "oblique", Font.BOLD);
        fontInfo.addFontProperties("F12", "monospace", "italic", Font.BOLD);

        fontInfo.addFontProperties("F1", "Helvetica", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F2", "Helvetica", "oblique", Font.NORMAL);
        fontInfo.addFontProperties("F2", "Helvetica", "italic", Font.NORMAL);
        fontInfo.addFontProperties("F3", "Helvetica", "normal", Font.BOLD);
        fontInfo.addFontProperties("F4", "Helvetica", "oblique", Font.BOLD);
        fontInfo.addFontProperties("F4", "Helvetica", "italic", Font.BOLD);
        fontInfo.addFontProperties("F5", "Times", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F6", "Times", "oblique", Font.NORMAL);
        fontInfo.addFontProperties("F6", "Times", "italic", Font.NORMAL);
        fontInfo.addFontProperties("F7", "Times", "normal", Font.BOLD);
        fontInfo.addFontProperties("F8", "Times", "oblique", Font.BOLD);
        fontInfo.addFontProperties("F8", "Times", "italic", Font.BOLD);
        fontInfo.addFontProperties("F9", "Courier", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F10", "Courier", "oblique", Font.NORMAL);
        fontInfo.addFontProperties("F10", "Courier", "italic", Font.NORMAL);
        fontInfo.addFontProperties("F11", "Courier", "normal", Font.BOLD);
        fontInfo.addFontProperties("F12", "Courier", "oblique", Font.BOLD);
        fontInfo.addFontProperties("F12", "Courier", "italic", Font.BOLD);
        fontInfo.addFontProperties("F13", "Symbol", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F14", "ZapfDingbats", "normal", Font.NORMAL);

        // Custom type 1 fonts step 2/2
        // fontInfo.addFontProperties("F15", "OMEP", "normal", FontInfo.NORMAL);
        // fontInfo.addFontProperties("F16", "Garamond-LightCondensed", "normal", FontInfo.NORMAL);
        // fontInfo.addFontProperties("F17", "BauerBodoni", "italic", FontInfo.BOLD);

        /* for compatibility with PassiveTex */
        fontInfo.addFontProperties("F5", "Times-Roman", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F6", "Times-Roman", "oblique", Font.NORMAL);
        fontInfo.addFontProperties("F6", "Times-Roman", "italic", Font.NORMAL);
        fontInfo.addFontProperties("F7", "Times-Roman", "normal", Font.BOLD);
        fontInfo.addFontProperties("F8", "Times-Roman", "oblique", Font.BOLD);
        fontInfo.addFontProperties("F8", "Times-Roman", "italic", Font.BOLD);
        fontInfo.addFontProperties("F5", "Times Roman", "normal", Font.NORMAL);
        fontInfo.addFontProperties("F6", "Times Roman", "oblique", Font.NORMAL);
        fontInfo.addFontProperties("F6", "Times Roman", "italic", Font.NORMAL);
        fontInfo.addFontProperties("F7", "Times Roman", "normal", Font.BOLD);
        fontInfo.addFontProperties("F8", "Times Roman", "oblique", Font.BOLD);
        fontInfo.addFontProperties("F8", "Times Roman", "italic", Font.BOLD);
        fontInfo.addFontProperties("F9", "Computer-Modern-Typewriter",
                                   "normal", Font.NORMAL);

        /* Add configured fonts */
        addConfiguredFonts(fontInfo, embedList, 15, resolver);
    }

    /**
     * Add fonts from configuration file starting with internal name F<num>.
     * @param fontInfo the font info object to set up
     * @param fontInfoList a list of EmbedFontInfo objects
     * @param num starting index for internal font numbering
     * @param resolver the font resolver
     */
    public static void addConfiguredFonts(FontInfo fontInfo, List fontInfoList
                                        , int num, FontResolver resolver) {
        if (fontInfoList == null) {
            return; //No fonts to process
        }

        if (resolver == null) {
            //Ensure that we have minimal font resolution capabilities
            resolver = createMinimalFontResolver();
        }
        
        String internalName = null;
        //FontReader reader = null;

        for (int i = 0; i < fontInfoList.size(); i++) {
            EmbedFontInfo configFontInfo = (EmbedFontInfo) fontInfoList.get(i);

            String metricsFile = configFontInfo.getMetricsFile();
            internalName = "F" + num;
            num++;
            /*
            reader = new FontReader(metricsFile);
            reader.useKerning(configFontInfo.getKerning());
            reader.setFontEmbedPath(configFontInfo.getEmbedFile());
            fontInfo.addMetrics(internalName, reader.getFont());
            */
            LazyFont font = new LazyFont(configFontInfo.getEmbedFile(),
                                         metricsFile,
                                         configFontInfo.getKerning(), 
                                         resolver);
            fontInfo.addMetrics(internalName, font);

            List triplets = configFontInfo.getFontTriplets();
            for (int c = 0; c < triplets.size(); c++) {
                FontTriplet triplet = (FontTriplet) triplets.get(c);

                if (log.isDebugEnabled()) {
                    log.debug("Registering: " + triplet + " under " + internalName);
                }
                fontInfo.addFontProperties(internalName, triplet);
            }
        }
    }

    /** @return a new FontResolver to be used by the font subsystem */
    private static FontResolver createMinimalFontResolver() {
        return new FontResolver() {

            /** @see org.apache.fop.fonts.FontResolver#resolve(java.lang.String) */
            public Source resolve(String href) {
                //Minimal functionality here
                return new StreamSource(href);
            }
            
        };
    }
   
    /**
     * Builds a list of EmbedFontInfo objects for use with the setup() method.
     * 
     * @param cfg Configuration object
     * @param renderer calling Renderer object
     * @return List the newly created list of fonts
     * @throws ConfigurationException if something's wrong with the config data
     */
    public static List buildFontListFromConfiguration(Configuration cfg, PrintRenderer renderer)
            throws ConfigurationException {
        List fontList = new java.util.ArrayList();
               
        FontResolver fontResolver = (renderer != null ? renderer.getFontResolver() : null);
        if (fontResolver == null) {
            //Ensure that we have minimal font resolution capabilities
            fontResolver = FontSetup.createMinimalFontResolver();
        }
       
        boolean strict = false;
        if (renderer != null) {
            strict = renderer.getUserAgent().getFactory().validateUserConfigStrictly();
        }
        
        Configuration[] fonts = cfg.getChildren("fonts");
        for (int f = 0; f < fonts.length; f++) {
                
            Configuration[] font = fonts[f].getChildren("font");
            for (int i = 0; i < font.length; i++) {
    
                String metricsUrl = font[i].getAttribute("metrics-url", null);
                String embedUrl = font[i].getAttribute("embed-url", null);
    
                if (metricsUrl == null && embedUrl == null) {
                    if (strict) {
                        throw new ConfigurationException(
                                "Font configuration without metric-url or embed-url");
                    }
                    log.error("Font configuration without metric-url or embed-url");
                    continue;
                }
                
                if (metricsUrl != null && fontResolver.resolve(metricsUrl) == null) {
                    if (strict) {
                        throw new ConfigurationException("Failed to resolve font metric-url '"
                            + metricsUrl + "'");                    
                    }
                    log.error("Failed to resolve font metric-url '" + metricsUrl + "'");
                    continue;
                }
                
                if (embedUrl != null && fontResolver.resolve(embedUrl) == null) {
                    if (strict) {
                        throw new ConfigurationException("Failed to resolve font with embed-url '"
                                + embedUrl + "'");
                    }
                    log.error("Failed to resolve font with embed-url '" + embedUrl + "'");
                    continue;
                }
            
                boolean useKerning = font[i].getAttributeAsBoolean("kerning", false);
    
                Configuration[] triple = font[i].getChildren("font-triplet");
                List tripleList = new java.util.ArrayList();
                for (int j = 0; j < triple.length; j++) {
                    String name = triple[j].getAttribute("name");
                    if (name == null) {
                        if (strict) {
                            throw new ConfigurationException("font-triplet without name");
                        }
                        log.error("font-triplet without name");
                        continue;
                    }
                    
                    String weightStr = triple[j].getAttribute("weight");
                    if (weightStr == null) {
                        if (strict) {
                            throw new ConfigurationException("font-triplet without weight");
                        }
                        log.error("font-triplet without weight");
                        continue;
                    }
                    int weight = FontUtil.parseCSS2FontWeight(weightStr);
    
                    String style = triple[j].getAttribute("style");
                    if (style == null) {
                        if (strict) {
                            throw new ConfigurationException("font-triplet without style");
                        }
                        log.error("font-triplet without style");
                        continue;
                    }
                    
                    tripleList.add(FontInfo.createFontKey(name,
                            style, weight));
                }
    
                EmbedFontInfo configFontInfo = new EmbedFontInfo(metricsUrl, 
                        useKerning, tripleList, embedUrl);
                
                if (log.isDebugEnabled()) {
                    log.debug("Adding font " + configFontInfo.getEmbedFile()
                            + ", metric file " + configFontInfo.getMetricsFile());
                    for (int j = 0; j < tripleList.size(); ++j) {
                        FontTriplet triplet = (FontTriplet) tripleList.get(j);
                        log.debug("Font triplet "
                                    + triplet.getName() + ", "
                                    + triplet.getStyle() + ", "
                                    + triplet.getWeight());
                    }
                }
                fontList.add(configFontInfo);
            }
        }
        return fontList;
    }    

    /**
     * Builds a list of EmbedFontInfo objects for use with the setup() method.
     * 
     * @param cfg Configuration object
     * @return List the newly created list of fonts
     * @throws ConfigurationException if something's wrong with the config data
     */
    public static List buildFontListFromConfiguration(Configuration cfg)
    throws ConfigurationException {
        return buildFontListFromConfiguration(cfg, null);
    }
}
