package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Set;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Amenity;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.AmenityService;

public class PropertyFilterDialog extends AlertDialog {

    private final FilterParams filterParams;
    private final AmenitiesSelectorViewAdapter amenitiesSelectorViewAdapter;

    public PropertyFilterDialog(Context context, FilterParams filterParams) {
        super(context);
        this.filterParams = filterParams;

        setTitle("Filter properties");

        View propertyFilterView = LayoutInflater.from(getContext()).inflate(R.layout.property_filter, null);
        setView(propertyFilterView);

        EditText filterAvailableFrom = propertyFilterView.findViewById(R.id.filter_available_from);
        filterAvailableFrom.setText(DateUtils.toString(filterParams.availableFrom));
        filterAvailableFrom.setOnClickListener(getDateOnClickListener(filterParams.availableFrom));

        EditText filterAvailableTo = propertyFilterView.findViewById(R.id.filter_available_to);
        filterAvailableTo.setText(DateUtils.toString(filterParams.availableTo));
        filterAvailableTo.setOnClickListener(getDateOnClickListener(filterParams.availableTo));

        RecyclerView amenitiesSelectorRecyclerView = propertyFilterView.findViewById(R.id.amenities_selector_recycler_view);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        amenitiesSelectorRecyclerView.setLayoutManager(layoutManager);
        amenitiesSelectorViewAdapter = new AmenitiesSelectorViewAdapter(getContext(), filterParams.selectedAmenities);
        amenitiesSelectorRecyclerView.setAdapter(amenitiesSelectorViewAdapter);

        AmenityService.getInstance().getAll(amenitiesSelectorViewAdapter::setAmenities);
    }

    private View.OnClickListener getDateOnClickListener(Calendar calendar) {
        return view -> {
            EditTextDatePickerDialog dialog = new EditTextDatePickerDialog(getContext(), (EditText) view, calendar);
            dialog.show();
        };
    }

    public void resetFilters() {
        filterParams.availableFrom = Calendar.getInstance();
        filterParams.availableTo = Calendar.getInstance();
        amenitiesSelectorViewAdapter.resetSelectedAmenities();
    }

    public static class FilterParams {
        private Calendar availableFrom;
        private Calendar availableTo;
        private final Set<Amenity> selectedAmenities;

        public FilterParams(Calendar availableFrom, Calendar availableTo, Set<Amenity> selectedAmenities) {
            this.availableFrom = availableFrom;
            this.availableTo = availableTo;
            this.selectedAmenities = selectedAmenities;
        }

        public Calendar getAvailableFrom() {
            return availableFrom;
        }

        public Calendar getAvailableTo() {
            return availableTo;
        }

        public Set<Amenity> getSelectedAmenities() {
            return selectedAmenities;
        }
    }
}
