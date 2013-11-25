package fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.repository;

import fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.domain.SystemJSDocument;
import fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.repository.mongodb.MongoDBDataSource;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * @author Gregory Boissinot
 */
public class MongoDBRepository {

    private MongoDBDataSource mongoDBDataSource;

    public MongoDBRepository(MongoDBDataSource mongoDBDataSource) {
        this.mongoDBDataSource = mongoDBDataSource;
    }

    public void saveSystemJSFunction(SystemJSDocument jsDocument) {
        MongoCollection systemJSCollection = getCollection("system.js");
        systemJSCollection.save(jsDocument);
    }

    public MongoCollection getCollection(String collectionName) {
        Jongo jongo = new Jongo(mongoDBDataSource.getMongoDB());
        return jongo.getCollection(collectionName);
    }
}
