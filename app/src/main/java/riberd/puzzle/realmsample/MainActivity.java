package riberd.puzzle.realmsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Riberd on 2017/04/06.
 */

public class MainActivity extends AppCompatActivity {
    private EditText titleEditText;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        setupViews();
    }

    private void setupViews() {
        titleEditText = (EditText) findViewById(R.id.edit_text);
        findViewById(R.id.submit_button).setOnClickListener(onSubmitButtonClick);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this, getListData());
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private View.OnClickListener onSubmitButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (titleEditText.getText().toString().isEmpty()) return;

            saveListData();
            recyclerViewAdapter.refreshItem(getListData());
        }
    };

    private void saveListData() {
        realm.beginTransaction();
        MemoObject memoObject = realm.createObject(MemoObject.class);
        memoObject.setTitle(titleEditText.getText().toString());
        memoObject.setDate(getNowDateToString());
        realm.commitTransaction();
    }

    private RealmResults<MemoObject> getListData() {
        RealmQuery<MemoObject> realmQuery = realm.where(MemoObject.class);
        RealmResults<MemoObject> realmResults = realmQuery
                .findAll();
        realmResults.asObservable();

        return realmResults;
    }

    private String getNowDateToString() {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/hh:mm");
        final Date date = new Date(System.currentTimeMillis());

        return df.format(date);
    }
}
