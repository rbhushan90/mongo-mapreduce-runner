package fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.repository.mongodb;

import com.mongodb.DB;

/**
 * @author Gregory Boissinot
 */
public interface MongoDBDataSource {

    public abstract DB getMongoDB();

}
