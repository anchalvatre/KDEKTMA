package com.katsamsoft.kdektma;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<AuthUI.IdpConfig> provider;
    private Button singin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singin = (Button) findViewById(R.id.singout);

        provider = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build()
        );
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSingInOptions();

            }
        });
    }
    public void showSingInOptions(){
        startActivityForResult(AuthUI.getInstance().
                        createSignInIntentBuilder().
                        setAvailableProviders(provider).
                        setTheme(R.style.FirebaseUITheme).
                        setLogo(R.drawable.ic_launcher_background).

                        build(),
                101);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode !=RESULT_CANCELED){
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (requestCode == 101){
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(this,SingOut.class);
            startActivity(intent);
            }

        else{
            Toast.makeText(this, idpResponse.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
        }

    }
}
