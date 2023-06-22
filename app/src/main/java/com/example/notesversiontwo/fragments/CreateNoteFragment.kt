package com.example.notesversiontwo.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.notesversiontwo.activities.MainActivity
import com.example.notesversiontwo.R
import com.example.notesversiontwo.databinding.FragmentCreateNoteBinding
import com.example.notesversiontwo.dialogs.DialogAddURL
import com.example.notesversiontwo.helper.toast
import com.example.notesversiontwo.miscellaneous.MiscellaneousLayout
import com.example.notesversiontwo.model.Note
import com.example.notesversiontwo.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateNoteFragment :
    Fragment(R.layout.fragment_create_note),
    OnClickListener {

    companion object {
        const val REQUEST_CODE_STORAGE_PERMISSION = 99
        const val REQUEST_CODE_SELECT_IMAGE = 100
    }

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View

    private lateinit var layoutMiscellaneous: MiscellaneousLayout

    private lateinit var viewSubtitleIndicator: View
    private lateinit var layoutWebURL: LinearLayout

    private lateinit var textDateItem: TextView
    private lateinit var textWebURL: TextView

    private val inputFields = mutableListOf<EditText>()
    private val imageViews = mutableListOf<ImageView>()

    private lateinit var noteColor: String
    private lateinit var imagePath: String

    private val dialogAddRL = DialogAddURL(requireContext())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        mView = view

        init_layoutMiscellaneous()
        DialogAddURL.function_for_dialog = {inputURL ->
            textWebURL.text = inputURL
            layoutWebURL.visibility = View.VISIBLE
        }
        init_companion_properties_of_layoutMiscellaneous()

        viewSubtitleIndicator = binding.viewSubtitleIndicator
        layoutWebURL = binding.layoutWebURL

        textDateItem = binding.textDateItem
        textWebURL = binding.textWebURL

        filling_in_the_lists()
        setting_up_an_initial_values()
        setting_up_clicks()
    }

    private fun `init_layoutMiscellaneous`() {
        layoutMiscellaneous = MiscellaneousLayout(requireContext())
    }

    private fun `init_companion_properties_of_layoutMiscellaneous`() {
        MiscellaneousLayout.function_for_put_image = putImage()
        MiscellaneousLayout.function_for_subtitle_indicator = {color ->
            color?.let {
                noteColor = color
                val gradientColor: GradientDrawable =
                    viewSubtitleIndicator.background as GradientDrawable
                gradientColor.setColor(Color.parseColor(noteColor))
            }
        }
        MiscellaneousLayout.function_for_alert_dialog = dialogAddRL.showAddURLDialog()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun `filling_in_the_lists`() {
        filling_in_the_list_of_inputFields()
        filling_in_the_list_of_imageViews()
    }

    private fun `filling_in_the_list_of_inputFields`() {
        inputFields[0] = binding.inputNoteTitle
        inputFields[1] = binding.inputNoteSubtitle
        inputFields[2] = binding.inputNoteBody
    }

    private fun `filling_in_the_list_of_imageViews`() {
        imageViews[0] = binding.imageBack
        imageViews[1] = binding.imageSave
        imageViews[2] = binding.imageNote
        imageViews[3] = binding.imageRemoveImage
        imageViews[4] = binding.imageRemoveWebURL
    }

    private fun `setting_up_an_initial_values`() {
        textDateItem.text =
            SimpleDateFormat(
                "EEEE, dd MMMM yyyy HH:mm a",
                Locale.getDefault()
            ).format(Date())

        /**
         * Этот код устанавливает текст для объекта textDateTime,
         * отображая текущую дату и время в указанном формате

         * "EEEE" указывает,
         * что нужно отображать день недели полностью,
         * например, "вторник"

         * "dd" указывает,
         * что нужно отображать день месяца,
         * например, "20"

         * "MMMM" указывает,
         * что нужно отображать название месяца полностью,
         * например, "июня"

         * "yyyy" указывает,
         * что нужно отображать год в четырехзначном формате,
         * например, "2023"

         * "HH" указывает,
         * что нужно отображать часы в 24 - часовом формате,
         * например, "22"

         * "mm" указывает,
         * что нужно отображать минуты,
         * например, "30"

         * "a" указывает,
         * что нужно отображать AM или PM,
         * в зависимости от времени суток,
         * например, "PM"

         * Locale.getDefault() используется для задания
         * региональных настроек по умолчанию
         */

        noteColor = "#333333"
        imagePath = ""
    }

    private fun `setting_up_clicks`() {
        imageViews[0].setOnClickListener(this)
        imageViews[1].setOnClickListener(this)
        imageViews[3].setOnClickListener(this)
        imageViews[4].setOnClickListener(this)
    }

    private fun saveNote(view: View) {
        val noteTitle = inputFields[0].text.toString().trim()
        val date = textDateItem.text.toString().trim()
        val noteSubTitle = inputFields[1].text.toString().trim()
        val noteBody = inputFields[2].text.toString().trim()

        if (noteTitle.isEmpty()) {
            activity?.toast("Note title can't be empty!")
            return
        } else if (noteSubTitle.isEmpty() && noteBody.isEmpty()) {
            activity?.toast("Note can't be empty!")
            return
        }

        val note = Note(
            0,
            noteTitle,
            date,
            noteSubTitle,
            noteBody,
            imagePath,
            noteColor
        )

        if (layoutWebURL.visibility == View.VISIBLE) {
            note.webLink = textWebURL.text.toString()
        }

        noteViewModel.addNote(note)

        Snackbar.make(
            view,
            "Note saved successfully",
            Snackbar.LENGTH_SHORT
        ).show()

        view.findNavController().navigate(
            R.id.action_createNoteFragment_to_homeFragment
        )
    }

    private fun `all_permissions_granted_read_external_storage`(): Boolean {
        return activity?.let {
            ContextCompat.checkSelfPermission(
                it.applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } == PackageManager.PERMISSION_GRANTED
    }

    private fun putImage() {
        if (all_permissions_granted_read_external_storage()) {
            selectImage()
        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (all_permissions_granted_read_external_storage()) {
            selectImage()
        } else activity?.toast("Permission Denied!")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE
            && resultCode == Activity.RESULT_OK
        ) {
            data?.let {
                val selectedImageUri = it.data
                selectedImageUri?.also {
                    try {
                        val inputStream =
                            activity?.contentResolver!!.openInputStream(it)

                        val bitmap = BitmapFactory.decodeStream(inputStream)

                        imageViews[2].setImageBitmap(bitmap)
                        imageViews[2].visibility = View.VISIBLE
                        binding.imageRemoveImage.visibility = View.VISIBLE

                        imagePath = getPathFromUri(selectedImageUri)

                    } catch (exception: Exception) {
                        activity?.toast(exception.message.toString())
                    }
                }
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity?.let {
            intent.resolveActivity(it.packageManager)?.let {
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
            }
        }
    }

    private fun getPathFromUri(contentUri: Uri) : String {
        var filePath: String? = null

        val cursor = activity?.contentResolver!!.query(
            contentUri,
            null,
            null,
            null
        )

        cursor?.let {
            filePath = contentUri.path!!
        } ?: { cursor: Cursor ->
            cursor.moveToFirst()
            val index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath ?: "string"
    }

    override fun onClick(view: View?) {
        when (view) {
            imageViews[0] -> {
                mView.findNavController().navigate(
                    R.id.action_createNoteFragment_to_homeFragment
                )
            }

            imageViews[1] -> {
                saveNote(mView)
            }

            imageViews[3] -> {
                imageViews[2].setImageBitmap(null)
                imageViews[2].visibility = View.GONE
                imageViews[3].visibility = View.GONE
                imagePath = ""
            }

            imageViews[4] -> {
                textWebURL.text = null
                layoutWebURL.visibility = View.GONE
            }
        }
    }
}