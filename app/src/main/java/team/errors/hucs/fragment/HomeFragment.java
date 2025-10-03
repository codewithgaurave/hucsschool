package team.errors.hucs.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team.errors.hucs.R;

public class HomeFragment extends Fragment {

    private TextView tvWelcome, tvCourse, tvCollege, tvFeeStatus;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        tvWelcome = view.findViewById(R.id.tvWelcome);
        tvCourse = view.findViewById(R.id.tvCourse);
        tvCollege = view.findViewById(R.id.tvCollege);

        String username = "Gaurav";
        String course = "B.Tech CSE";
        String college = "ABC Institute";

        tvWelcome.setText("Hello, " + username + " \uD83D\uDC4B");
        tvCourse.setText(course);
        tvCollege.setText(college);

        return view;
    }
}