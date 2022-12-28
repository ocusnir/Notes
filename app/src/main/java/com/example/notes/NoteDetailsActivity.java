package com.example.notes;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

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

        if(noteTitle == null || noteTitle.isEmpty()){
            NoteTitle.setError("Title is required");
            return;
        }

        Note note = new Note();
        note.setTitle(noteTitle);
        note.setDescription(noteDesc);
        note.setTimestamp(Timestamp.now());
        saveNoteToFirebase(note);
    }

    void saveNoteToFirebase(Note note){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document();

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
                //notes has been added
                Utility.showToast(NoteDetailsActivity.this, "Your note has been successfully added");
                finish();
                }else{
                    //error
                Utility.showToast(NoteDetailsActivity.this, "Something went wrong!");
                }
            }
        });
    }


}