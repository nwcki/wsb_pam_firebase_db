package pl.mkonkel.wsb.firebasedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.text.Editable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import pl.mkonkel.wsb.firebasedb.model.Note
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {
//    TODO: Add FirebaseDatabase Instance here
    private val db = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseListener()

        button_add_note.setOnClickListener{
            val title: String = note_title.text.toString()
            val body: String = note_body.text.toString()
            addNote(title,body)
        }
    }

    private fun addNote(title: String, message: String) {
        val note = Note(
            title = title,
            message = message
        )

        val uuid = UUID.randomUUID().toString()

        db.child(NOTE)
            .child(uuid)
            .setValue(note)
            .addOnSuccessListener {
                Timber.i("Successful note adding")
            }
            .addOnFailureListener{
                Timber.e("Failure during adding note")
            }
    }

    private fun databaseListener(){
        db.child(NOTE)
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Timber.e("Request was cancelled")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    Timber.i("Something was changed")
                }

            })
    }



//    TODO: Add DatabaseListener
//    Get child("childName") - first branch
//    Get child("branchName") - get element in this branch
//    addValueEventListener
//
//    onDataChane() will be invoked when any data will be modified in whole child branch.
//    You can print changed data in the log using Timber.i(data)

//    TODO: Add Helper method for adding "news" to the DB.
//    child() - go to notes branch
//    child() - go to element in branch (ec. User One notes).
//    child() - value in branch
//    setValue(givenValue)
//
//    You can additional add onSuccess and onFailureListener

    companion object {
        const val NOTE = "note"
    }
}
