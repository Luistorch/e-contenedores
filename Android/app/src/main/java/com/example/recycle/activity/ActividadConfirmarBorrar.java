package com.example.recycle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycle.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActividadConfirmarBorrar extends Activity {

    // Firestore
    private FirebaseFirestore db = null;
    private FirebaseUser usuario = null;

    String cuboID = null;
    Switch registrosSwitch = null;

    Activity activity = null;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmar_borrar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        cuboID = bundle.getString("cuboID");

        activity = this;

        registrosSwitch = findViewById(R.id.switch1);
    }

    // Eliminar un cubo
    public void eliminarCubo(View view) {
        // Recogemos el check para ver si eliminamos también los registros
        boolean registrosEliminar = registrosSwitch.isChecked();

        db = FirebaseFirestore.getInstance();
        usuario = FirebaseAuth.getInstance().getCurrentUser();

        if(registrosEliminar) {

            db.collection("cubos")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(document.getId().equals(cuboID)) {
                                        Map<String, Object> medidas = new ArrayMap<>();

                                        medidas = document.getData();

                                        // Ponemos el nombre del cubo
                                        String nombre = medidas.get("nombre").toString();

                                        medidas.clear();

                                        medidas.put("nombre", nombre);
                                        db.collection("cubos").document(cuboID).set(medidas);
                                    }
                                }
                            }
                        }
                    });
        }

        DocumentReference docRef = db.collection("usuarios").document(usuario.getEmail());

        // Remove the 'capital' field from the document
        Map<String,Object> updates = new HashMap<>();
        updates.put("cubos", FieldValue.arrayRemove(cuboID));

        docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                }
            }
        });
        finish();
    }

    // Cancelar eliminar cubo
    public void eliminarCancelar(View view) {
        finish();
    }
}
