package com.example.notesversiontwo.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.databinding.FragmentUpdateNoteBinding
import com.example.notes.databinding.LayoutAddUrlBinding
import com.example.notes.databinding.LayoutDeleteNoteBinding
import com.example.notes.databinding.LayoutMiscellaneousBinding
import com.example.notesversiontwo.activities.MainActivity
import com.example.notesversiontwo.helper.toast
import com.example.notesversiontwo.model.Note
import com.example.notesversiontwo.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.lang.Exception

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note),
    OnClickListener {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View

    companion object {
        const val REQUEST_CODE_STORAGE_PERMISSION = 99
        const val REQUEST_CODE_SELECT_IMAGE = 100
    }

    private lateinit var viewSubtitleIndicator: View
    private lateinit var layoutWebURL: LinearLayout

    private val colors = mutableListOf<String>()

    private val textFields = mutableListOf<TextView>()
    private val inputFields = mutableListOf<EditText>()
    private val imageViews = mutableListOf<ImageView>()

    private lateinit var selectedNoteColor: String
    private lateinit var imagePath: String

    // layoutMiscellaneous
    private lateinit var layoutMiscellaneous: LayoutMiscellaneousBinding

    private lateinit var textMiscellaneous: TextView

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private val layouts = mutableListOf<LinearLayout>()
    private val viewColors = mutableListOf<View>()
    private val imageColors = mutableListOf<ImageView>()

    // dialog
    private lateinit var dialogAddURL: AlertDialog
    private lateinit var dialogDeleteNote: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateNoteBinding.inflate(
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

        currentNote = arguments?.get("note") as Note

        viewSubtitleIndicator = binding.viewSubtitleIndicator
        layoutWebURL = binding.layoutWebURL

        filling_in_the_list_of_colors()

        filling_in_the_lists_of_views()
        setting_up_clicks_views()

        initialization_LayoutMiscellaneous()
        filling_in_the_lists_of_layoutMiscellaneous_views()
        settng_up_clicks_of_layoutMiscellaneous_views()
        layouts[2].visibility = View.VISIBLE

        setting_up_dialogAddUrl()
        setting_up_dialogDeleteNote()

        setting_up_noteData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun `filling_in_the_list_of_colors`() {
        val color1 = resources.getString(R.color.colorDefaultNoteColor.toInt())
        val color2 = resources.getString(R.color.colorNoteColor2.toInt())
        val color3 = resources.getString(R.color.colorNoteColor3.toInt())
        val color4 = resources.getString(R.color.colorNoteColor4.toInt())
        val color5 = resources.getString(R.color.colorNoteColor5.toInt())
        val color6 = resources.getString(R.color.colorNoteColor6.toInt())

        colors.add(0, color1)
        colors.add(1, color2)
        colors.add(2, color3)
        colors.add(3, color4)
        colors.add(4, color5)
        colors.add(5, color6)
    }

    private fun `filling_in_the_lists_of_views`() {
        filling_in_the_list_of_textFields()
        filling_in_the_list_of_inputFields()
        filling_in_the_list_of_imageViews()
    }

    private fun `filling_in_the_list_of_textFields`() {
        textFields.add(0, binding.textDateItem)
        textFields.add(1, binding.textWebURLUpdate)
    }

    private fun `filling_in_the_list_of_inputFields`() {
        inputFields.add(0, binding.inputNoteTitleUpdate)
        inputFields.add(1, binding.inputNoteBodyUpdate)
    }

    private fun `filling_in_the_list_of_imageViews`() {
        imageViews.add(0, binding.imageBack)
        imageViews.add(1, binding.imageUpdate)
        imageViews.add(2, binding.imageNoteUpdate)
        imageViews.add(3, binding.imageRemoveImage)
        imageViews.add(4, binding.imageRemoveWebURL)
    }

    private fun `setting_up_clicks_views`() {
        imageViews[0].setOnClickListener(this)
        imageViews[1].setOnClickListener(this)
        imageViews[3].setOnClickListener(this)
        imageViews[4].setOnClickListener(this)
    }

    private fun `initialization_LayoutMiscellaneous`() {
        init_layoutMiscellaneousBinding()
        init_textMiscellaneous()
        init_bottom_sheet_behavior()
    }

    private fun `init_layoutMiscellaneousBinding`() {
        layoutMiscellaneous = binding.layoutMiscellaneous
    }

    private fun `init_textMiscellaneous`() {
        textMiscellaneous = layoutMiscellaneous.textMiscellaneous
    }

    private fun `init_bottom_sheet_behavior`() {
        /**
            * BottomSheetBehavior отвечает за поведение
            * нижнего листа (bottom sheet) на экране
            *
            * Он может управлять раскрытием и
            * скрытием листа,
            * его высотой,
            * а также обработкой пользовательских событий
         */
        bottomSheetBehavior =
            BottomSheetBehavior.from(layoutMiscellaneous.miscellaneous)
    }

    private fun `filling_in_the_lists_of_layoutMiscellaneous_views`() {
        filling_in_the_list_of_layouts()
        filling_in_the_list_of_viewColors()
        filling_in_the_list_of_imageColors()
    }

    private fun `filling_in_the_list_of_layouts`() {
        layouts.add(0, layoutMiscellaneous.layoutAddImage)
        layouts.add(1, layoutMiscellaneous.layoutAddUrl)
        layouts.add(2, layoutMiscellaneous.layoutDeleteNote)
    }

    private fun `filling_in_the_list_of_viewColors`() {
        viewColors.add(0, layoutMiscellaneous.viewColor1)
        viewColors.add(1, layoutMiscellaneous.viewColor2)
        viewColors.add(2, layoutMiscellaneous.viewColor3)
        viewColors.add(3, layoutMiscellaneous.viewColor4)
        viewColors.add(4, layoutMiscellaneous.viewColor5)
        viewColors.add(5, layoutMiscellaneous.viewColor6)
    }

    private fun `filling_in_the_list_of_imageColors`() {
        imageColors.add(0, layoutMiscellaneous.imageColor1)
        imageColors.add(1, layoutMiscellaneous.imageColor2)
        imageColors.add(2, layoutMiscellaneous.imageColor3)
        imageColors.add(3, layoutMiscellaneous.imageColor4)
        imageColors.add(4, layoutMiscellaneous.imageColor5)
        imageColors.add(5, layoutMiscellaneous.imageColor6)
    }

    private fun `settng_up_clicks_of_layoutMiscellaneous_views`() {
        setting_up_click_textMiscellaneous()
        setting_up_clicks_layouts()
        setting_up_clicks_viewColors()
    }

    private fun `setting_up_click_textMiscellaneous`() {
        layoutMiscellaneous.textMiscellaneous.setOnClickListener {
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
    }

    private fun `setting_up_clicks_layouts`() {

        layouts[0].setOnClickListener { setImage() }

        layouts[1].setOnClickListener {
            bottomSheetBehavior.state =
                BottomSheetBehavior.STATE_COLLAPSED
            dialogAddURL.show()
        }

        layouts[2].setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            dialogDeleteNote.show()
        }
    }

    private fun `setting_up_clicks_viewColors`() {
        viewColors[0].setOnClickListener {
            selectedNoteColor = colors[0]
            imageColors[0].setImageResource(R.drawable.ic_done)
            imageColors[1].setImageResource(0)
            imageColors[2].setImageResource(0)
            imageColors[3].setImageResource(0)
            imageColors[4].setImageResource(0)
            imageColors[5].setImageResource(0)
            set_subtitle_indicator()
        }

        viewColors[1].setOnClickListener {
            selectedNoteColor = colors[1]
            imageColors[0].setImageResource(0)
            imageColors[1].setImageResource(R.drawable.ic_done)
            imageColors[2].setImageResource(0)
            imageColors[3].setImageResource(0)
            imageColors[4].setImageResource(0)
            imageColors[5].setImageResource(0)
            set_subtitle_indicator()
        }

        viewColors[2].setOnClickListener {
            selectedNoteColor = colors[2]
            imageColors[0].setImageResource(0)
            imageColors[1].setImageResource(0)
            imageColors[2].setImageResource(R.drawable.ic_done)
            imageColors[3].setImageResource(0)
            imageColors[4].setImageResource(0)
            imageColors[5].setImageResource(0)
            set_subtitle_indicator()
        }

        viewColors[3].setOnClickListener {
            selectedNoteColor = colors[3]
            imageColors[0].setImageResource(0)
            imageColors[1].setImageResource(0)
            imageColors[2].setImageResource(0)
            imageColors[3].setImageResource(R.drawable.ic_done)
            imageColors[4].setImageResource(0)
            imageColors[5].setImageResource(0)
            set_subtitle_indicator()
        }

        viewColors[4].setOnClickListener {
            selectedNoteColor = colors[4]
            imageColors[0].setImageResource(0)
            imageColors[1].setImageResource(0)
            imageColors[2].setImageResource(0)
            imageColors[3].setImageResource(0)
            imageColors[4].setImageResource(R.drawable.ic_done)
            imageColors[5].setImageResource(0)
            set_subtitle_indicator()
        }

        viewColors[5].setOnClickListener {
            selectedNoteColor = colors[5]
            imageColors[0].setImageResource(0)
            imageColors[1].setImageResource(0)
            imageColors[2].setImageResource(0)
            imageColors[3].setImageResource(0)
            imageColors[4].setImageResource(0)
            imageColors[5].setImageResource(R.drawable.ic_done)
            set_subtitle_indicator()
        }
    }

    private fun `set_subtitle_indicator`() {
        val gradientDrawable = viewSubtitleIndicator.background as GradientDrawable
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor))
    }

    private fun `setting_up_dialogAddUrl`() {
        val builder = AlertDialog.Builder(context)

        val view = LayoutAddUrlBinding.inflate(LayoutInflater.from(context))

        builder.setView(view.root)
        dialogAddURL = builder.create()

        dialogAddURL.window?.setBackgroundDrawable(
            ColorDrawable(0)
        )

        val inputURL = view.inputURL
        inputURL.requestFocus()

        view.textAdd.setOnClickListener {

            if (inputURL.text.toString()
                    .trim().isEmpty()
            )
                activity?.toast("Enter URL")
            else if (!Patterns.WEB_URL
                    .matcher(inputURL.text.toString())
                    .matches()
            ) activity?.toast("Enter valid URL")
            else {
                textFields[1].text = inputURL.text.toString()
                layoutWebURL.visibility = View.VISIBLE
                dialogAddURL.dismiss()
            }
        }

        view.textCancel.setOnClickListener {
            dialogAddURL.dismiss()
        }
    }

    private fun `setting_up_dialogDeleteNote`() {
        val builder = AlertDialog.Builder(context)

        val view = LayoutDeleteNoteBinding.inflate(LayoutInflater.from(context))

        builder.setView(view.root)
        dialogDeleteNote = builder.create()

        dialogDeleteNote.window?.setBackgroundDrawable(
            ColorDrawable(0)
        )

        view.textDeleteNote.setOnClickListener {
            noteViewModel.deleteNote(currentNote)
            mView.findNavController().navigate(
                R.id.action_updateNoteFragment_to_homeFragment
            )
            dialogDeleteNote.dismiss()
        }

        view.textCancel.setOnClickListener {
            dialogDeleteNote.dismiss()
        }
    }

    private fun `setting_up_noteData`() {
        inputFields[0].setText(currentNote.noteTitle)
        inputFields[1].setText(currentNote.noteBody)
        textFields[0].text = currentNote.dateTime

        if (currentNote.imagePath != null &&
            currentNote.imagePath.toString().trim().isNotEmpty()
        ) {
            activity?.baseContext?.let {
                Glide.with(it).load(currentNote.imagePath)
                    .into(imageViews[2])
            }
            imageViews[2].visibility = View.VISIBLE
            imageViews[3].visibility = View.VISIBLE
            imagePath = currentNote.imagePath!!
        }

        if (currentNote.webLink != null &&
            currentNote.webLink.toString().trim().isNotEmpty()
        ) {
            textFields[1].text = currentNote.webLink
            layoutWebURL.visibility = View.VISIBLE
        }

        if (currentNote.noteColor != null &&
            currentNote.noteColor!!.trim().isNotEmpty()
        ) {
            when (currentNote.noteColor) {
                colors[0] -> viewColors[0].performClick()
                colors[1] -> viewColors[1].performClick()
                colors[2] -> viewColors[2].performClick()
                colors[3] -> viewColors[3].performClick()
                colors[4] -> viewColors[4].performClick()
            }
        }
    }

    private fun setImage() {
        if (all_permissions_granted_read_external_storage()) {
            selectImage()
        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                CreateNoteFragment.REQUEST_CODE_STORAGE_PERMISSION
            )
        }
    }

    private fun `all_permissions_granted_read_external_storage`(): Boolean {
        return activity?.let {
            ContextCompat.checkSelfPermission(
                it.applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
        if (all_permissions_granted_read_external_storage()) {
            selectImage()
        } else activity?.toast("Permission Denied!")
    }

    private fun selectImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity?.let {
            intent.resolveActivity(it.packageManager)?.let {
                startActivityForResult(intent, CreateNoteFragment.REQUEST_CODE_SELECT_IMAGE)
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CreateNoteFragment.REQUEST_CODE_SELECT_IMAGE
            && resultCode == Activity.RESULT_OK
        ) {
            data?.let {
                val selectedImageUri = it.data
                selectedImageUri?.also {
                    try {
                        activity?.baseContext?.let { it1 ->
                            Glide.with(it1).load(selectedImageUri).into(imageViews[2])
                        }
                        imageViews[2].visibility = View.VISIBLE
                        imageViews[3].visibility = View.VISIBLE

                        imagePath = selectedImageUri.toString()

                    } catch (exception: Exception) {
                        activity?.toast(exception.message.toString())
                    }
                }
            }
        }
    }

    private fun updateNote(view: View) {
        val noteTitle = inputFields[0].text.toString().trim()
        val date = textFields[0].text.toString().trim()
        val noteSubTitle = inputFields[1].text.toString().trim()
        val noteBody = inputFields[2].text.toString().trim()

        if (noteTitle.isEmpty()) {
            activity?.toast("Note title can't be empty!")
            return
        } else if (noteSubTitle.isEmpty()) {
            activity?.toast("Note subtitle can't be empty!")
            return
        } else if(noteBody.isEmpty()) {
            activity?.toast("Note can't be empty!")
            return
        }

        val note = Note(
            currentNote.id,
            noteTitle,
            date,
            noteSubTitle,
            noteBody,
            imagePath,
            selectedNoteColor
        )

        if (layoutWebURL.visibility == View.VISIBLE) {
            note.webLink = textFields[1].text.toString()
        }

        noteViewModel.updateNote(note)

        activity?.toast("Note updated!")

        mView.findNavController().navigate(
            R.id.action_updateNoteFragment_to_homeFragment
        )
    }

    override fun onClick(view: View?) {
        when (view) {
            imageViews[0] -> {
                mView.findNavController().navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }

            imageViews[1] -> {
                updateNote(mView)
            }

            imageViews[3] -> {
                imageViews[2].setImageBitmap(null)
                imageViews[2].visibility = View.GONE
                imageViews[3].visibility = View.GONE
                imagePath = ""
            }

            imageViews[4] -> {
                textFields[1].text = null
                layoutWebURL.visibility = View.GONE
            }
        }
    }
}