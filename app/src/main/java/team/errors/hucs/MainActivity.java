package team.errors.hucs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import team.errors.hucs.RegisterActivity;
import team.errors.hucs.fragment.CoursesFragment;
import team.errors.hucs.fragment.DownloadFragment;
import team.errors.hucs.fragment.FavoritesFragment;
import team.errors.hucs.fragment.HomeFragment;
import team.errors.hucs.fragment.LiveClassFragment;
import team.errors.hucs.fragment.ProfileFragment;
import team.errors.hucs.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton drawerMenuButton;
    private TextView userName, userEmail;
    private CircleImageView profileImage;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout container;

    // CardView navigation items
    private CardView navHome, navProfile, navCourses, navSettings, navLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerMenuButton = findViewById(R.id.drawermenuimage);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        container = findViewById(R.id.container);

        // Initialize custom drawer views
        View customDrawer = findViewById(R.id.custom_drawer);
        userName = customDrawer.findViewById(R.id.user_name);
        userEmail = customDrawer.findViewById(R.id.user_email);
        profileImage = customDrawer.findViewById(R.id.profile_image);

        navHome = customDrawer.findViewById(R.id.nav_home);
        navProfile = customDrawer.findViewById(R.id.nav_profile);
        navCourses = customDrawer.findViewById(R.id.nav_courses);
        navSettings = customDrawer.findViewById(R.id.nav_settings);
        navLogout = customDrawer.findViewById(R.id.nav_logout);

        // Set user data
        setUserData("John Doe", "john.doe@example.com");

        // Set up drawer toggle
        drawerMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // Notification Icon Click
        ImageView notificationIcon = findViewById(R.id.notificationIcon);
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });


        // Set up navigation item click listeners for custom drawer
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(navHome);
                loadFragment(new HomeFragment());
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(navProfile);

                // ProfileActivity open karo
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        navCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(navCourses);
                loadFragment(new CoursesFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        navSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(navSettings);
                loadFragment(new SettingsFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        navLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmation();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Set profile image click listener
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ProfileFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Set up bottom navigation
        setupBottomNavigation();

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    loadFragment(new HomeFragment());
                    return true;
                } else if (itemId == R.id.nav_download) {
                    loadFragment(new DownloadFragment());
                    return true;
                } else if (itemId == R.id.nav_fav) {
                    loadFragment(new FavoritesFragment());
                    return true;
                } else if (itemId == R.id.nav_liveClass) {
                    loadFragment(new LiveClassFragment());
                    return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void setUserData(String name, String email) {
        userName.setText(name);
        userEmail.setText(email);
        // You can also load profile image from URL using Picasso/Glide
    }

    private void selectItem(CardView selectedCard) {
        // Reset all cards to default state
        resetAllCards();

        // Highlight the selected card
        selectedCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.selected_item_color));
    }

    private void resetAllCards() {
        int defaultColor = ContextCompat.getColor(this, R.color.white);
        navHome.setCardBackgroundColor(defaultColor);
        navProfile.setCardBackgroundColor(defaultColor);
        navCourses.setCardBackgroundColor(defaultColor);
        navSettings.setCardBackgroundColor(defaultColor);
        // Don't reset logout as it has a different color
    }

    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performLogout();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void performLogout() {
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}