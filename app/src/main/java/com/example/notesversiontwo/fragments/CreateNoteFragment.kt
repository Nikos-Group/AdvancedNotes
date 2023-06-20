package com.example.notesversiontwo.fragments

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.notesversiontwo.Manifest
import com.example.notesversiontwo.activities.MainActivity
import com.example.notesversiontwo.R
import com.example.notesversiontwo.databinding.FragmentCreateNoteBinding
import com.example.notesversiontwo.databinding.LayoutAddUrlBinding
import com.example.notesversiontwo.databinding.LayoutMiscellaneousBinding
import com.example.notesversiontwo.helper.toast
import com.example.notesversiontwo.model.Note
import com.example.notesversiontwo.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateNoteFragment : Fragment(R.layout.fragment_create_note),
    OnClickListener {

    companion object {
        const val REQUEST_CODE_STORAGE_PERMISSION = 99
        const val REQUEST_CODE_SELECT_IMAGE = 100
    }

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel

    private lateinit var mView: View
    private lateinit var viewSubtitleIndicator: View

    private lateinit var dialogAddURL: AlertDialog

    private lateinit var layoutWebURL: LinearLayout

    private lateinit var inputNoteTitle: EditText
    private lateinit var inputNoteSubtitle: EditText
    private lateinit var inputNoteBody: EditText

    private lateinit var textDateTime: TextView
    private lateinit var textWebURL: TextView

    private lateinit var back: ImageView
    private lateinit var save: ImageView
    private lateinit var imageNote: ImageView
    private lateinit var imageRemoveImage: ImageView
    private lateinit var imageRemoveWebURL: ImageView

    private lateinit var selectedNoteColor: String
    private lateinit var selectedImagePath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateNoteBinding.inflate(
            inflater,
            container,
            false
        )

        initViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        mView = view

        back.setOnClickListener(this)
        save.setOnClickListener(this)
        imageRemoveImage.setOnClickListener(this)
        imageRemoveWebURL.setOnClickListener(this)

        settingUpAnInitialValues()

        initMiscellaneous()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViews() {
        viewSubtitleIndicator = binding.viewSubtitleIndicator

        layoutWebURL = binding.layoutWebURL

        inputNoteTitle = binding.inputNoteTitle
        inputNoteSubtitle = binding.inputNoteSubtitle
        inputNoteBody = binding.inputNoteBody

        textDateTime = binding.textDateItem
        textWebURL = binding.textWebURL

        back = binding.imageBack
        save = binding.imageSave
        imageNote = binding.imageNote
        imageRemoveImage = binding.imageRemoveImage
        imageRemoveWebURL = binding.imageRemoveWebURL
    }

    private fun settingUpAnInitialValues() {
        textDateTime.text =
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

        selectedNoteColor = "#333333"
        selectedImagePath = ""
    }

    private fun initMiscellaneous() {
        val layoutMiscellaneous:
                LayoutMiscellaneousBinding =
            binding.layoutMiscellaneous

        /**
         * BottomSheetBehavior отвечает за поведение
         * нижнего листа (bottom sheet) на экране
         *
         * Он может управлять раскрытием и
         * скрытием листа,
         * его высотой,
         * а также обработкой пользовательских событий
         *
         */
        val bottomSheetBehavior:
                BottomSheetBehavior<LinearLayout> =
            BottomSheetBehavior
                .from(layoutMiscellaneous as LinearLayout)

        /**
            * При нажатии на textMiscellaneous
            * происходит проверка состояния объекта
            * bottomSheetBehavior,
            * указывающего на текущее состояние панели (листа)
            *
            * Если она не раскрыта,
            * то она раскрывается,
            * а если она уже открыта,
            * то она сворачивается.
         */
        layoutMiscellaneous.textMiscellaneous
            .setOnClickListener {
                if (bottomSheetBehavior.state
                    != BottomSheetBehavior.STATE_EXPANDED
                ) {
                    bottomSheetBehavior.setState(
                        BottomSheetBehavior
                            .STATE_EXPANDED
                    )
                } else {
                    bottomSheetBehavior.setState(
                        BottomSheetBehavior
                            .STATE_COLLAPSED
                    )
                }
            }

        val viewColor1 = layoutMiscellaneous.viewColor1
        val viewColor2 = layoutMiscellaneous.viewColor2
        val viewColor3 = layoutMiscellaneous.viewColor3
        val viewColor4 = layoutMiscellaneous.viewColor4
        val viewColor5 = layoutMiscellaneous.viewColor5

        val imageColor1 = layoutMiscellaneous.imageColor1
        val imageColor2 = layoutMiscellaneous.imageColor2
        val imageColor3 = layoutMiscellaneous.imageColor3
        val imageColor4 = layoutMiscellaneous.imageColor4
        val imageColor5 = layoutMiscellaneous.imageColor5

        viewColor1.setOnClickListener {
            selectedNoteColor = "#333333"
            imageColor1.setImageResource(R.drawable.ic_done)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }

        viewColor2.setOnClickListener {
            selectedNoteColor = "#FDBE3B"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(R.drawable.ic_done)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }

        viewColor3.setOnClickListener {
            selectedNoteColor = "#FF4842"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(R.drawable.ic_done)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }

        viewColor4.setOnClickListener {
            selectedNoteColor = "#3A52Fc"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(R.drawable.ic_done)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }

        viewColor5.setOnClickListener {
            selectedNoteColor = "#000000"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(R.drawable.ic_done)
            setSubtitleIndicatorColor()
        }

        layoutMiscellaneous.layoutAddImage.setOnClickListener {

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED


            if (ContextCompat.checkSelfPermission(
                    activity?.applicationContext!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_STORAGE_PERMISSION
                )
            } else {
                selectImage()
            }
        }

        layoutMiscellaneous.layoutAddUrl.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            showAddURLDialog()
        }
    }

    private fun setSubtitleIndicatorColor() {
        val gradientColor: GradientDrawable = viewSubtitleIndicator.background
                as GradientDrawable
        gradientColor.setColor(Color.parseColor(selectedNoteColor))
    }
    
    private fun setViewOrUpdateNote() {
        // todo с помощью сеттеров проставляем данные той заметки, на которую мы кликнули
    }

    private fun saveNote(view: View) {
        val noteTitle = inputNoteTitle.text.toString().trim()
        val dateTime = textDateTime.text.toString().trim()
        val noteSubTitle = inputNoteSubtitle.text.toString().trim()
        val noteBody = inputNoteBody.text.toString().trim()

        if (noteTitle.isEmpty()) {
            activity?.toast("Note title can't be empty!")
            return
        } else if (noteSubTitle.isEmpty() && noteBody.isEmpty()) {
            activity?.toast("Note can't be empty!")
            return
        }

        val note = Note(
            noteTitle = noteTitle,
            dateTime = dateTime,
            noteSubtitle = dateTime,
            noteBody = noteBody,
            imagePath = selectedImagePath,
            noteColor = selectedNoteColor
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

        // TODO ВОЗВРАЩАЕМCЯ НА HomeFragment
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION
            && grantResults.isNotEmpty()
        ) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage()
            } else {
                activity?.toast("Permission Denied!")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            data?.let {
                val selectedImageUri = it.data
                selectedImageUri?.also {
                    try {
                        val inputStream = activity?.contentResolver!!.openInputStream(it)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        imageNote.setImageBitmap(bitmap)
                        imageNote.visibility = View.VISIBLE
                        binding.imageRemoveImage.visibility = View.VISIBLE

                        selectedImagePath = getPathFromUri(selectedImageUri)

                    } catch (exception: Exception) {
                        activity?.toast(exception.message.toString())
                    }
                }
            }
        }
    }

    private fun getPathFromUri(contentUri: Uri) : String {
        var filePath: String? = null
        var cursor = activity?.contentResolver!!.query(
            contentUri, null, null, null
        )
        cursor?.let {
            filePath = contentUri.path!!
        } ?: { cursor: Cursor ->
            cursor.moveToFirst()
            val index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath ?: "string" // TODO потом проверить вот этот момент
    }

    private fun selectImage() {
        val intent = Intent(
            Intent.ACTION_PICK, MediaStore.Images
                .Media.EXTERNAL_CONTENT_URI
        )
        intent.resolveActivity(activity?.packageManager!!)?.let {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
        }
    }

    private fun showAddURLDialog() {
        dialogAddURL.also {
            val builder = AlertDialog.Builder(activity)

            val layoutAddUrlBinding = LayoutAddUrlBinding.inflate(
                LayoutInflater.from(activity),
                R.id.layoutNote as ViewGroup,
                false
            )

            builder.setView(view)

            dialogAddURL = builder.create()

            dialogAddURL.window?.setBackgroundDrawable(ColorDrawable(0))

            val inputURL = layoutAddUrlBinding.inputURL
            inputURL.requestFocus()

            layoutAddUrlBinding.textAdd.setOnClickListener {
                if (inputURL.text.toString().trim().isEmpty()) {
                    activity?.toast("Enter URL")
                } else if (!Patterns.WEB_URL
                        .matcher(inputURL.text.toString())
                        .matches()) {
                    activity?.toast("Enter valid URL")
                } else {
                    textWebURL.text = inputURL.text.toString()
                    layoutWebURL.visibility = View.VISIBLE
                    dialogAddURL.dismiss()
                }
            }

            layoutAddUrlBinding.root.setOnClickListener {
                dialogAddURL.dismiss()
            }

            dialogAddURL.show()
        }

        // todo а может все таки вот тут написать строчку
        // todo dialogAddURL.show()
    }

    override fun onClick(view: View?) {
        when (view) {
            back -> {
                mView.findNavController().navigate(
                    R.id.action_createNoteFragment_to_homeFragment
                )
            }

            save -> {
                saveNote(mView)
            }

            imageRemoveImage -> {
                imageNote.setImageBitmap(null)
                imageNote.visibility = View.GONE
                imageRemoveImage.visibility = View.GONE // todo повторение имени переменной
                selectedImagePath = ""
            }

            imageRemoveWebURL -> {
                textWebURL.text = null
                layoutWebURL.visibility = View.GONE
            }
        }
    }
}