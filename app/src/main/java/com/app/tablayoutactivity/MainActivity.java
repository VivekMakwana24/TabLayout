package com.app.tablayoutactivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.app.tablayoutactivity.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private String[] tabTitles;
    private int[] tabIcons = {R.drawable.pending, R.drawable.ongoing_white, R.drawable.completed_white};
    private ViewPagerAdapter pageAdapter;
    private static final String TAG = "CustomerBookedServices";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        context = this;
        tabTitles = new String[]{"Tab 1", "Tab 2", "Tab 3"};

        setUpTabs();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                LinearLayout linearLayout = (LinearLayout) tab.getCustomView();
                TextView text = linearLayout.findViewById(R.id.tabContent);
                ImageView img = linearLayout.findViewById(R.id.tabImg);

                if (text != null) {
                    text.setText(tabTitles[tab.getPosition()]);
                    img.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
                img.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);

                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                LinearLayout linearLayout = (LinearLayout) tab.getCustomView();
                TextView tabContent = linearLayout.findViewById(R.id.tabContent);
                ImageView img = linearLayout.findViewById(R.id.tabImg);

                if (tabContent != null) {
                    tabContent.setText("");
                    img.setColorFilter(ContextCompat.getColor(context, R.color.dark_green), android.graphics.PorterDuff.Mode.MULTIPLY);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("TabPosition", "" + tab.getPosition());

            }
        });

    }

    private void setUpTabs() {
        binding.tabLayout.addTab(binding.tabLayout.newTab());
        binding.tabLayout.addTab(binding.tabLayout.newTab());
        binding.tabLayout.addTab(binding.tabLayout.newTab());

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            LinearLayout tabLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            TextView tabContent = tabLinearLayout.findViewById(R.id.tabContent);
            ImageView tabImg = tabLinearLayout.findViewById(R.id.tabImg);
            tabImg.setColorFilter(ContextCompat.getColor(context, R.color.dark_green), android.graphics.PorterDuff.Mode.MULTIPLY);

            if (i == 0) {
                tabContent.setText(tabTitles[i]);
                tabImg.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
            tabImg.setImageResource(tabIcons[i]);

            binding.tabLayout.getTabAt(i).setCustomView(tabLinearLayout);
        }
        Log.e(TAG, "onCreate: " + binding.tabLayout.getTabCount());

        pageAdapter = new ViewPagerAdapter(getSupportFragmentManager(), binding.tabLayout.getTabCount());

        binding.viewPager.setAdapter(pageAdapter);

        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));

        pageAdapter.notifyDataSetChanged();

    }
}
