package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.northeastern.cs5520_mobileappdev_team19.ChatActivity;
import edu.northeastern.cs5520_mobileappdev_team19.MessageChatActivity;
import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.databinding.ActivityPropertyDetailBinding;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.AmenitiesListViewAdapter;

public class PropertyDetailActivity extends AppCompatActivity {
    public static final String PROPERTY_ID = "PROPERTY_ID";
    private TextView streetAddressTextView;
    private ProgressBar progressBar;

    private ActivityPropertyDetailBinding binding;
    private CollapsingToolbarLayout toolBarLayout;
    private Property property;
    private FirebaseUser loggedInUser;
    private FloatingActionButton sendMessageBtn;
    private LinearLayout propertyLayout;
    private RecyclerView amenitiesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        binding = ActivityPropertyDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
//        streetAddressTextView = this.findViewById(R.id.property_detail_street_address);

        loggedInUser = FirebaseAuth.getInstance().getCurrentUser();

        progressBar = this.findViewById(R.id.progress_bar_property_detail);
        sendMessageBtn = this.findViewById(R.id.property_detail_message_button);
        propertyLayout = this.findViewById(R.id.property_detail_content);
        amenitiesRecyclerView = this.findViewById(R.id.amenities_list_recycler_view);

        String propertyId = getIntent().getStringExtra(PROPERTY_ID);
        if (propertyId != null) {
            PropertyService.getInstance().get(propertyId, property -> {
//                streetAddressTextView.setText(property.getStreetAddress());
                progressBar.setVisibility(View.GONE);
                this.property = property;
                if (loggedInUser != null && loggedInUser.getUid().equals(property.getUser().getId())) {
                    sendMessageBtn.setVisibility(View.GONE);
                }
                propertyLayout.setVisibility(View.VISIBLE);
                toolBarLayout.setTitle(property.getStreetAddress());
                binding.setProperty(property);
                binding.setActivity(this);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
                amenitiesRecyclerView.setLayoutManager(layoutManager);
                AmenitiesListViewAdapter amenitiesListViewAdapter = new AmenitiesListViewAdapter(property.getAmenities(), this);
                amenitiesRecyclerView.setAdapter(amenitiesListViewAdapter);
            });
        }
    }

    public void sendMessage(View view) {
        Intent chatActivity = new Intent(this, MessageChatActivity.class);
        chatActivity.putExtra(ChatActivity.SENDER_ID, loggedInUser.getUid());
        chatActivity.putExtra(ChatActivity.RECIPIENT_ID, property.getUser().getId());
        chatActivity.putExtra(ChatActivity.RECIPIENT_USERNAME, property.getUser().getFullName());
        startActivity(chatActivity);
    }

    public void openMaps(Property property) {
        @SuppressLint("DefaultLocale") Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?q=%f,%f(%s)", property.getLocation().getLatitude(), property.getLocation().getLongitude(), property.getLocation().getLatitude(), property.getLocation().getLongitude(), property.getStreetAddress()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void openEmail(String email, String address) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse(String.format("mailto:%s?subject=Enquiry for %s", email, address));
        intent.setData(data);
        startActivity(intent);
    }
}