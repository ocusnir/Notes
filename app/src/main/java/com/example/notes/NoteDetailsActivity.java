package com.example.notes;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText NoteTitle, NoteDescription;
    ImageButton SaveNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        NoteTitle = findViewById(R.id.note_title);
        NoteDescription = findViewById(R.id.note_description);
        SaveNoteBtn = findViewById(R.id.save_note_btn);

        SaveNoteBtn.setOnClickListener((v)-> saveNote());

    }

    void saveNote(){
        String noteTitle = NoteTitle.getText().toString();
        String noteDesc = NoteDescription.getText().toString();

        if(noteTitle==null || noteTitle.isEmpty()){
            NoteTitle.setText("");
        }
    }
}