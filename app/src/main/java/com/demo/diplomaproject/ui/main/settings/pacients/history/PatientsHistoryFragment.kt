package com.demo.diplomaproject.ui.main.settings.pacients.history

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.di.DI
import com.demo.diplomaproject.domain.entity.TestResult
import com.demo.diplomaproject.domain.entity.UserProfile
import com.demo.diplomaproject.extensions.gone
import com.demo.diplomaproject.extensions.tryToGetParcelable
import com.demo.diplomaproject.extensions.visible
import com.demo.diplomaproject.presentation.main.settings.pacients.history.PatientsHistoryPresenter
import com.demo.diplomaproject.presentation.main.settings.pacients.history.PatientsHistoryView
import kotlinx.android.synthetic.main.fragment_patient_history.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope
import toothpick.config.Module

class PatientsHistoryFragment : BaseFragment(), PatientsHistoryView {

    override val layoutRes = R.layout.fragment_patient_history

    override val parentFragmentScopeName = DI.SERVER_SCOPE

    private lateinit var adapter: PatientsHistoryAdapter

    override fun installScopeModules(scope: Scope) {
        val patient = tryToGetParcelable<UserProfile>(KEY_PATIENT)
        scope.installModules(object : Module() {
            init {
                bind(UserProfile::class.java).toInstance(patient)
            }
        })
    }

    @InjectPresenter
    lateinit var presenter: PatientsHistoryPresenter

    @ProvidePresenter
    fun providePresenter(): PatientsHistoryPresenter =
        scope.getInstance(PatientsHistoryPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientHistoryRecycler.layoutManager = LinearLayoutManager(requireContext())
        patientHistoryToolbar.setNavigationOnClickListener { presenter.onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }

    override fun showError() {
        patientHistoryNoItems.visible()
        patientNoHistoryText.visible()
        patientHistoryRecycler.gone()
    }

    override fun showHistory(history: List<TestResult>) {
        patientHistoryNoItems.gone()
        patientNoHistoryText.gone()
        patientHistoryRecycler.visible()

        adapter = PatientsHistoryAdapter()
        patientHistoryRecycler.adapter = adapter
        adapter.showPatientHistory(history)
    }

    companion object {
        private const val KEY_PATIENT = "KEY_PATIENT"

        fun newInstance(patient: UserProfile) =
            PatientsHistoryFragment().apply {
                arguments = bundleOf(
                    KEY_PATIENT to patient
                )
            }
    }
}