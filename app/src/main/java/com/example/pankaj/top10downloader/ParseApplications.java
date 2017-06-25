package com.example.pankaj.top10downloader;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pankaj on 6/24/2017.
 */

public class ParseApplications {
    private String xmlData;
    private List<Application> applications;

    ParseApplications(String xmlData) {
        this.xmlData = xmlData;
        this.applications = new ArrayList<Application>();
    }

    public List<Application> process() {

        boolean status = true;
        Application currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = xpp.getName();

                switch (eventType) {

                    case XmlPullParser.START_TAG:
                        if ("entry".equalsIgnoreCase(tagname)) {
                            currentRecord = new Application();
                            inEntry = true;
                        }

                        break;

                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();

                    case XmlPullParser.END_TAG:
                        if (inEntry) {
                            if ("entry".equalsIgnoreCase(tagname)) {
                                this.applications.add(currentRecord);
                                inEntry = false;
                            } else if ("title".equalsIgnoreCase(tagname)) {
                                currentRecord.setTitle(textValue);
                            } else if ("rights".equalsIgnoreCase(tagname)) {
                                currentRecord.setRights(textValue);
                            }
                        }

                        break;

                    default:
                }

                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.applications;
    }


}
