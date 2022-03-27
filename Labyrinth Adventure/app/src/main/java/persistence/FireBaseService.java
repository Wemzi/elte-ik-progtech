package persistence;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FireBaseService
{
    public FireBaseService() throws IOException {
       FileInputStream serviceAccount =
                new FileInputStream("app-secret.json");

               FirebaseOptions options = new FirebaseOptions.Builder()
               .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl("https://labyrinth-adventure-9a289-default-rtdb.europe-west1.firebasedatabase.app")
            .build();

        FirebaseApp.initializeApp(options);
    }

}

