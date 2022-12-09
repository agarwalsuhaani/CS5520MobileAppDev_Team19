package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class FirebaseStorageService {

    private static FirebaseStorageService firebaseStorageService;

    private FirebaseStorageService() {
    }

    public static FirebaseStorageService getInstance() {
        if (firebaseStorageService == null) {
            firebaseStorageService = new FirebaseStorageService();
        }
        return firebaseStorageService;
    }

    public void upload(Context context, List<Uri> fileUris, Consumer<List<String>> uploadedFiles) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        List<String> fileUploadResults = new ArrayList<>();

        List<Task<String>> tasks = new ArrayList<>();
        for (Uri fileUri : fileUris) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            String extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(fileUri));
            String path = String.format("/property-images/%s.%s", UUID.randomUUID().toString(), extension);
            StorageReference storageRef = storage.getReference().child(path);
            UploadTask task = storageRef.putFile(fileUri);
            Task<String> currentUrlTask = task
                    .continueWith(new Continuation<UploadTask.TaskSnapshot, String>() {
                        @Override
                        public String then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException(); // rethrow any errors
                            }

                            return storageRef.getPath();
                        }
                    });
            tasks.add(currentUrlTask);
        }

        Tasks.whenAllComplete(tasks)
                .addOnCompleteListener((listTask) -> {
                    if (listTask.isSuccessful()) {
                        List<Task<?>> taskList = listTask.getResult();
                        List<Uri> failedUploads = new ArrayList<>();
                        fileUploadResults.clear();

                        for (Task<?> task : taskList) {
                            if (task.isSuccessful()) {
                                Object result = task.getResult();
                                if (result instanceof String) {
                                    String path = (String) result;
                                    fileUploadResults.add(path);
                                }
                            } else {
                                Uri imageUri = fileUris.get(taskList.indexOf(task));
                                failedUploads.add(imageUri);
                            }
                        }

                        uploadedFiles.accept(fileUploadResults);
                    }
                });
    }
}
