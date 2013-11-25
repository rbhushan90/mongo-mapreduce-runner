/**

 db.runCommand({mapReduce: map_version_function, reduce:reduce_latestversion_function, out: "artifacts.latest", scope:{ version_compare:version_compare} finalize:finalize_function})

 db.artifacts.mapReduce(map_version_function, reduce_latestversion_function,{ out:
 "artifacts.latest", scope:{ version_compare:version_compare }, finalize:finalize_function})

 db.runCommand(
 {
 mapReduce: <collection>,
 map: <function>,
 reduce: <function>,
 out: <output>,
 query: <document>,
 sort: <document>,
 limit: <number>,
 finalize: <function>,
 scope: <document>,
 jsMode: <boolean>,
 verbose: <boolean>
 }
 )







 */

import com.mongodb.*;
import fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.repository.MongoDBRepository;
import fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.repository.mongodb.BasicMongoDBDataSource;
import fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.service.JSFunctionsLoader;
import org.apache.commons.io.IOUtils;
import org.bson.types.Code;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Gregory Boissinot
 */
public class Test {


    public static void main(String[] args) throws IOException {
        JSFunctionsLoader jsFunctionsLoader = new JSFunctionsLoader(new MongoDBRepository(new BasicMongoDBDataSource("localhost", 27017, "repo")));
        jsFunctionsLoader.loadJSFUnctionFile("/Users/gregory/Dev/Soleil/mongo-scripts/artifacts/fr/synchrotron/soleil/ica/ci/lib/maven/mongo-scripts/1.0.0.RELEASE/mongo-scripts-1.0.0.RELEASE.zip");
    }


    private void save() throws IOException {
        final Mongo mongo = new Mongo();
        DB db = mongo.getDB("repo");
        //DBCollection c1 = db.getCollection( "system.js" );
        Jongo jongo = new Jongo(db);
        final MongoCollection jongoCollection = jongo.getCollection("system.js");

        final URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:/Users/gregory/Dev/Soleil/mongo-scripts/artifacts/fr/synchrotron/soleil/ica/ci/lib/maven/mongo-scripts/1.0.0.RELEASE/mongo-scripts-1.0.0.RELEASE.zip")});

        File file = new File("/Users/gregory/Dev/Soleil/mongo-scripts/artifacts/fr/synchrotron/soleil/ica/ci/lib/maven/mongo-scripts/1.0.0.RELEASE/mongo-scripts-1.0.0.RELEASE.zip");
        final ZipFile zipFile = new ZipFile(file);
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = entries.nextElement();
            System.out.println(entry);
            InputStream input = zipFile.getInputStream(entry);
            System.out.println(IOUtils.toString(input));
        }
        //final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("artifacts_map_func.js");
        //final String s = IOUtils.toString(urlClassLoader.getResourceAsStream("artifacts_map_func.js"));
        //System.out.println(s);
    }


    private void m() throws UnknownHostException {
        final Mongo mongo = new Mongo();
        DB db = mongo.getDB("repo");

        //db.doEval()
        Jongo jongo = new Jongo(db);

        final CommandResult result = db.doEval("vp(\"1.48\",\"1.9\",\"<\")");
        System.out.println(result);

//        final MongoCollection systemJsColl = jongo.getCollection("system.js");
//        final Iterable<FunctionClass> iterable = systemJsColl.find().as(FunctionClass.class);
//        for (FunctionClass functionClass : iterable) {
//            System.out.println(functionClass);
//        }

//        final MongoCollection systemJsColl = jongo.getCollection("system.js");
//        final Iterable<FunctionClass> iterable = systemJsColl.find().as(FunctionClass.class);
//        for (FunctionClass functionClass : iterable) {
//            System.out.println(functionClass);
//
//            db.doEval(functionClass.get_id()+"="+ functionClass.getValue().getCode());
//        }
//

        Map<String, Code> ff = new HashMap<String, Code>();
        DBCollection c1 = db.getCollection("system.js");
        final DBCursor dbCursor = c1.find();
        while (dbCursor.hasNext()) {
            final DBObject dbObject = dbCursor.next();
            System.out.println(dbObject);
            final Object o = dbObject.get("value");
//            System.out.println(o.getClass());
//            System.out.println(o);
            ff.put((String) dbObject.get("_id"), (Code) o);
        }


        DBCollection c = db.getCollection("artifacts");
        //final MapReduceOutput mapReduceOutput = c.mapReduce("m", "r", "toto", null);

        Map<String, Object> scope = new HashMap<String, Object>();
        //scope.put("version_compare", "vp");
        MapReduceCommand mrc =
                new MapReduceCommand(c, ff.get("m").getCode(), ff.get("r").getCode().replace("version_compare", "vp"), "toto", MapReduceCommand.OutputType.REPLACE, null);
        mrc.setScope(scope);
        mrc.setFinalize(ff.get("ff").getCode());
        MapReduceOutput out = c.mapReduce(mrc);
        System.out.println(out);

        System.out.println(db.getLastError().getErrorMessage());


        //jongo.runCommand("db.runCommand(db.loadServerScripts())");
        //jongo.runCommand("load('/Users/gregory/Dev/Soleil/maven-mongoimporter/infra/mongodb/mapReduce.js')");
        //String mapReduceCommand="db.runCommand({mapReduce: \"artifacts\", map:map_version_function, reduce:reduce_latestversion_function, out: \"artifacts.latest\", scope:{ version_compare:version_compare}, finalize:finalize_function})";
//        String loadCommand="db.system.js.find().forEach(function(u){eval(u._id + \" = \" +\n" + "u.value)})";
//        db.
//
        // String mapReduceCommand="{mapReduce: \"artifacts\", map:m, reduce:r, out: \"artifacts.latest\", scope:{ version_compare:vp}, finalize:ff}";
        //final CommandResult commandResult = db.command(mapReduceCommand);
        //System.out.println(commandResult);


//        jongo.runCommand(mapReduceCommand);
        //db.
        //final MongoCollection artifacts = jongo.getCollection("artifacts");


    }
}
