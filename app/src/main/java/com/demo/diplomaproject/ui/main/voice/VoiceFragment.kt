package com.demo.diplomaproject.ui.main.voice

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.main.voice.VoicePresenter
import com.demo.diplomaproject.presentation.main.voice.VoiceView
import kotlinx.android.synthetic.main.fragment_voice.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class VoiceFragment : BaseFragment(), VoiceView {

    override val layoutRes = R.layout.fragment_voice

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: VoicePresenter

    @ProvidePresenter
    fun providePresenter(): VoicePresenter =
        scope.getInstance(VoicePresenter::class.java)

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        voiceImage.setOnClickListener { onStartTestClicked() }
    }

    private fun onStartTestClicked() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
        } else {
            presenter.startTest()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_AUDIO_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_AUDIO_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_AUDIO_CODE = 101
    }
}