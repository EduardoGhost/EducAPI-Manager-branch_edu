package br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.ufpb.dcx.apps4society.educapimanager.R;
import br.ufpb.dcx.apps4society.educapimanager.adapter.ChallengesByContextAdapter;
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Challenge;

public class ChallengesByContextActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Challenge> challengesByContext = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_by_context);

        recyclerView = findViewById(R.id.recyclerChallengesBycContext);

        Toolbar register_toolbar = findViewById(R.id.toolbar_challenges);
        register_toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        challengesByContext = (ArrayList<Challenge>) intent.getSerializableExtra("list");

        configureRecyclerView();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Configura o RecyclerView.
     */
    private void configureRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        ChallengesByContextAdapter adapter = new ChallengesByContextAdapter(ChallengesByContextActivity.this, challengesByContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}