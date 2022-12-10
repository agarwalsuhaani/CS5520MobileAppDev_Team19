package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.AmenityService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.FirebaseStorageService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.AmenitiesSelectorViewAdapter;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.DateUtils;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.EditTextDatePickerDialog;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.ImageListViewAdapter;

public class AddPropertyActivity extends AppCompatActivity {
    private EditText editTextStreetAddress;
    private EditText editTextBedCount;
    private EditText editTextBathCount;
    private CheckBox checkBoxIsStudio;
    private EditText editTextRent;
    private EditText editTextArea;
    private Calendar availableFrom;
    private Calendar availableTo;
    private AmenitiesSelectorViewAdapter amenitiesSelectorViewAdapter;
    private ImageListViewAdapter imageListViewAdapter;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        editTextStreetAddress = findViewById(R.id.edit_text_street_address);
        editTextBedCount = findViewById(R.id.edit_text_bed_count);
        editTextBathCount = findViewById(R.id.edit_text_bath_count);
        checkBoxIsStudio = findViewById(R.id.check_box_is_studio);
        editTextRent = findViewById(R.id.edit_text_rent);
        editTextArea = findViewById(R.id.edit_text_area);

        EditText editTextAvailableFrom = findViewById(R.id.edit_text_available_from);
        availableFrom = Calendar.getInstance();
        editTextAvailableFrom.setText(DateUtils.toString(availableFrom));
        editTextAvailableFrom.setOnClickListener(getDateOnClickListener(this, availableFrom));

        EditText editTextAvailableTo = findViewById(R.id.edit_text_available_to);
        availableTo = Calendar.getInstance();
        editTextAvailableTo.setText(DateUtils.toString(availableTo));
        editTextAvailableTo.setOnClickListener(getDateOnClickListener(this, availableTo));

        RecyclerView amenitiesSelectorRecyclerView = findViewById(R.id.amenities_selector_recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        amenitiesSelectorRecyclerView.setLayoutManager(layoutManager);
        amenitiesSelectorViewAdapter = new AmenitiesSelectorViewAdapter(this);
        amenitiesSelectorRecyclerView.setAdapter(amenitiesSelectorViewAdapter);

        AmenityService.getInstance().getAll(amenitiesSelectorViewAdapter::setAmenities);

        RecyclerView imageListRecycler = findViewById(R.id.image_list_recycler_view);
        RecyclerView.LayoutManager imageListLayoutManager = new GridLayoutManager(this, 2);
        imageListRecycler.setLayoutManager(imageListLayoutManager);
        imageListViewAdapter = new ImageListViewAdapter(new ArrayList<>(), this, true);
        imageListRecycler.setAdapter(imageListViewAdapter);

        setUpSubmitButton();
        setupActivityResultListeners();
    }

    private void setupActivityResultListeners() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (result) -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        assert data != null;
                        consumeImages(data);
                    } else {
                        Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setUpSubmitButton() {
        Geocoder geocoder = new Geocoder(this);
        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(view -> {
            try {
                List<Address> addressList = geocoder.getFromLocationName(editTextStreetAddress.getText().toString(), 1);
                if (!addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    Property newProperty = new Property(
                            null,
                            Integer.parseInt(editTextBedCount.getText().toString()),
                            Integer.parseInt(editTextBathCount.getText().toString()),
                            checkBoxIsStudio.isChecked(),
                            address.getLatitude(),
                            address.getLongitude(),
                            address.getAddressLine(0),
                            address.getLocality(),
                            address.getAdminArea(),
                            Integer.parseInt(address.getPostalCode()),
                            Double.parseDouble(editTextRent.getText().toString()),
                            Double.parseDouble(editTextArea.getText().toString()),
                            DateUtils.toLocalDate(availableFrom),
                            DateUtils.toLocalDate(availableTo),
                            amenitiesSelectorViewAdapter.getSelectedAmenities());

                    List<Uri> images = imageListViewAdapter.getImages();
                    if (images.size() > 0) {
                        FirebaseStorageService firebaseStorageService = FirebaseStorageService.getInstance();
                        firebaseStorageService.upload(this, images, (files) -> {
                            newProperty.setImages(files);
                            PropertyService.getInstance().add(newProperty, this::callbackResult);
                        });
                    } else {
                        PropertyService.getInstance().add(newProperty, this::callbackResult);
                    }
                } else {
                    showAlert("Please enter a valid address");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void callbackResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(this, "Property created successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    private View.OnClickListener getDateOnClickListener(Context context, Calendar calendar) {
        return view -> {
            EditTextDatePickerDialog dialog = new EditTextDatePickerDialog(context, (EditText) view, calendar);
            dialog.show();
        };
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.setMessage(message).setCancelable(true).create();
        alertDialog.show();
    }

    public void selectImages(View view) {
        // initialising intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent galleryIntent = new Intent();
        // setting type to select to be image
        galleryIntent.setType("image/*");

        // allowing multiple image to be selected
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooser = Intent.createChooser(intent, "Select photo(s)");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{galleryIntent});
        activityResultLauncher.launch(chooser);
    }

    private void consumeImages(Intent data) {
        if (data.getClipData() != null) {
            ClipData mClipData = data.getClipData();
            int itemCount = mClipData.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                Uri imageUri = mClipData.getItemAt(i).getUri();
                imageListViewAdapter.addImage(imageUri);
            }
        } else if (data.getData() != null) {
            Uri tempUri = data.getData();
            imageListViewAdapter.addImage(tempUri);
        } else if (data.getExtras() != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            imageListViewAdapter.addImage(tempUri);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, UUID.randomUUID().toString(), null);
        return Uri.parse(path);
    }
}