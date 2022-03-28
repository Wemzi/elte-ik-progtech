package persistence;



import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import static com.google.api.client.util.Lists.*;
import static com.google.common.collect.Lists.*;

public class ConnectionFactory {
    private static Connection conn;
    Storage storage = StorageOptions.getDefaultInstance().getService();



    public ConnectionFactory(){}
    public static final String CREDENTIALS_STRING ="jdbc:mysql://test?cloudSqlInstance=helical-element-345409:europe-west6:labyrinthadventure&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=test&password=password";
    public static Connection  getConnection() {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        storage.toString();
        Storag eOptions.newBuilder
                .setCredentials(ServiceAccountCredentials.fromStream(credentialStream))
                .setProjectId("helical-element-345409")
                .build
                .getService
        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
        // You can specify a credential file by providing a path to GoogleCredentials.
        // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
        GoogleCredentials credentials = null;
        try {
            credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\lkcsdvd\\IdeaProjects\\progtech\\Labyrinth Adventure\\app\\src\\main\\resources\\app-secret.json"))
                    .createScoped(newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(CREDENTIALS_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}