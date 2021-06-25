package com.demo.diplomaproject.ui.main.voice.test

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.core.os.bundleOf
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.di.DI
import com.demo.diplomaproject.di.PrimitiveWrapper
import com.demo.diplomaproject.extensions.invisible
import com.demo.diplomaproject.extensions.tryToGetLong
import com.demo.diplomaproject.extensions.visible
import com.demo.diplomaproject.model.data.service.SpeechService
import com.demo.diplomaproject.presentation.main.voice.test.reading.ReadingPresenter
import com.demo.diplomaproject.presentation.main.voice.test.reading.ReadingView
import kotlinx.android.synthetic.main.fragment_reading.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope
import toothpick.config.Module

class ReadingFragment : BaseFragment(), ReadingView {

    override val layoutRes = R.layout.fragment_reading

    override val parentFragmentScopeName = DI.SERVER_SCOPE

    private var isServiceConnected = false

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceConnected = false
            presenter.onServiceDisconnected()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (service != null) {
                isServiceConnected = true
                presenter.onServiceBind((service as SpeechService.SpeechServiceBinder).getService())
            }
        }
    }

    override fun installScopeModules(scope: Scope) {
        scope.installModules(object : Module() {

            init {
                bind(PrimitiveWrapper::class.java)
                    .toInstance(PrimitiveWrapper(tryToGetLong(KEY_AVERAGE_RMS)))
            }
        })
    }

    @InjectPresenter
    lateinit var presenter: ReadingPresenter

    @ProvidePresenter
    fun providePresenter(): ReadingPresenter =
        scope.getInstance(ReadingPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readingPlay.setOnClickListener { presenter.onStartClicked() }
        readingStop.setOnClickListener { presenter.onStopClicked() }
        val barsColor = requireContext().getColor(R.color.blue_nuke_end)
        val colors = intArrayOf(
            barsColor,
            barsColor,
            barsColor,
            barsColor,
            barsColor,
            barsColor,
            barsColor
        )
        readingProgressView.setColors(colors)
        readingProgressView.setBarMaxHeightsInDp(intArrayOf(55, 45, 60, 53, 42, 58, 49))
        readingProgressView.play()
        readingToolbar.setNavigationOnClickListener { presenter.onBackPressed() }
    }

    override fun bindSpeechService() {
        val intent = Intent(context, SpeechService::class.java)
        requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun unbindSpeechService() {
        if (isServiceConnected) {
            isServiceConnected = false
            requireContext().unbindService(serviceConnection)
        }
    }

    override fun playAnimation(rms: Float) {
        readingProgressView.onRmsChanged(rms)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }

    override fun changeIcon(isStart: Boolean) {
        if (isStart) {
            readingStop.invisible()
            readingPlay.visible()
        } else {
            readingPlay.invisible()
            readingStop.visible()
        }
    }

    companion object {
        private const val KEY_AVERAGE_RMS = "KEY_AVERAGE_RMS"

        fun newInstance(averageRms: Long) =
            ReadingFragment().apply {
                arguments = bundleOf(
                    KEY_AVERAGE_RMS to averageRms
                )
            }
    }
}