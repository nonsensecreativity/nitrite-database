package org.dizitart.no2;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

import static org.dizitart.no2.DbTestOperations.getRandomTempDbFile;

/**
 * @author Anindya Chatterjee
 */
@Ignore
public class StressTest {
    private String fileName = getRandomTempDbFile();
    private Nitrite db;
    private NitriteCollection collection;

    @Before
    public void before() {
        db = Nitrite
                .builder()
                .compressed()
                .filePath(fileName)
                .openOrCreate();
        collection = db.getCollection("test");
        System.out.println(fileName);
    }

    @Test
    public void testIssue41() throws InterruptedException {
        collection.createIndex("number", IndexOptions.indexOptions(IndexType.NonUnique));
        collection.createIndex("name", IndexOptions.indexOptions(IndexType.NonUnique));
        collection.createIndex("counter", IndexOptions.indexOptions(IndexType.Unique));

        Random random = new Random();
        AtomicLong counter = new AtomicLong(System.currentTimeMillis());
        PodamFactory factory = new PodamFactoryImpl();

        long start= System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Document doc = new Document();
            doc.put("number", random.nextDouble());
            doc.put("name", factory.manufacturePojo(String.class));
            doc.put("counter", counter.getAndIncrement());
            collection.insert(doc);
            if (i % 10000 == 0) {
                System.out.println(i + " entries written");
            }
        }
        System.out.println("Records inserted in " + ((System.currentTimeMillis() - start) / (1000 * 60)) + " minutes");

        if (db.hasUnsavedChanges()) {
            db.commit();
        }

        start= System.currentTimeMillis();
        Cursor cursor = collection.find();
        System.out.println("Size ->" + cursor.size());
        System.out.println("Records size calculated in " + ((System.currentTimeMillis() - start) / (1000)) + " seconds");

        int i = 0;
        for (Object element : cursor) {
            assert element instanceof Document;
            i++;
            if (i % 10000 == 0) {
                System.out.println(i + " entries processed");
            }
        }
    }

    @After
    public void clear() throws IOException {
        if (db != null && !db.isClosed()) {
            long start = System.currentTimeMillis();
            db.close();
            System.out.println("Time to compact and close - " + (System.currentTimeMillis() - start) / 1000 + " seconds");
        }
        Files.delete(Paths.get(fileName));
    }
}
