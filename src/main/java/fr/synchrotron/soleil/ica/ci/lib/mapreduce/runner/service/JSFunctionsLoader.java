package fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.service;

import fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.domain.SystemJSDocument;
import fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.repository.MongoDBRepository;
import fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.repository.mongodb.BasicMongoDBDataSource;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Gregory Boissinot
 */
public class JSFunctionsLoader {

    private MongoDBRepository mongoDBRepository;

    public JSFunctionsLoader(MongoDBRepository mongoDBRepository) {
        this.mongoDBRepository = mongoDBRepository;
    }

    public void loadJSFUnctionFile(String zipFilePath) {
        try {
            File file = new File(zipFilePath);
            final ZipFile zipFile = new ZipFile(file);
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                String jsFileName = entry.getName();
                if (jsFileName.endsWith(".js")) {
                    InputStream input = zipFile.getInputStream(entry);
                    String jsFunction = IOUtils.toString(input);
                    mongoDBRepository.saveSystemJSFunction(
                            new SystemJSDocument(jsFileName, jsFunction));
                }

            }

        } catch (IOException ioe) {

        }
    }
}
