package com.simple.list.presentation.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.simple.list.R;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;

  private NavController navController;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    setupNavHost();
  }

  private void setupNavHost() {
    navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
    NavigationUI.setupActionBarWithNavController(this, navController);
  }

  @Override public boolean onSupportNavigateUp() {
    return navController.navigateUp() || super.onSupportNavigateUp();
  }
}
