package musicp.firebok.com.muscip;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.net.Uri;

//import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST=1;
    ArrayList<String> arrayList;
    ListView listview;
    ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        }else {
            doStuff();
        }
    }

    public void doStuff(){

        ListView listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        getMusic();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

            }
        });



    }
    public void getMusic(){
        ContentResolver contentResolver = getContentResolver();
        URI songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor= contentResolver.query(songUri,null,null,null,null);
        //Cursor songCursor =contentResolver.query(songUri, null , null, null, null);

        if (songCursor != null && Cursor.moveToFirst()) {
            int songTitle = ((android.database.Cursor) songCursor).getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtista = ((android.database.Cursor) songCursor).getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                String currentTitle = ((android.database.Cursor) songCursor).getString(songTitle);
                String currentArtista = ((android.database.Cursor) songCursor).getString(songArtista);
                arrayList.add(currentTitle + "\n" + currentArtista);
            } while (((android.database.Cursor) songCursor).moveToNext());
        }
    }

    @Override
    public   void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSION_REQUEST:
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted",Toast.LENGTH_SHORT).show();

                        doStuff();
                    }else {
                        Toast.makeText(this, "not Permission granted",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                return;
        }
}
