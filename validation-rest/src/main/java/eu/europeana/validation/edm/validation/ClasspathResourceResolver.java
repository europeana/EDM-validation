package eu.europeana.validation.edm.validation;

import org.apache.log4j.Logger;
import org.w3c.dom.ls.LSInput;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Class enabling classpath XSD reading for split XSDs. This is because of an issue with JAXP XSD loading
 * Created by ymamakis on 12/21/15.
 */
public class ClasspathResourceResolver implements AbstractLSResourceResolver {
    private String prefix;
    private static final Logger logger =  Logger.getRootLogger();
    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
        try {
            LSInput input = new ClasspathLSInput();
            InputStream stream;
            if(!systemId.startsWith("http")) {
                stream = new FileInputStream(prefix+"/"+systemId);
            }else {
                stream = new URL(systemId).openStream();
            }
            input.setPublicId(publicId);
            input.setSystemId(systemId);
            input.setBaseURI(baseURI);
            input.setCharacterStream(new InputStreamReader(stream));

            return input;
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        } return null;
    }
    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    @Override
    public SwiftProvider getSwiftProvider() {
        return null;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}

