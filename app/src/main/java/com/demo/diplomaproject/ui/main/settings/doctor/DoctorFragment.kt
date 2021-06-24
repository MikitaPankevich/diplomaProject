package com.demo.diplomaproject.ui.main.settings.doctor

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.di.DI
import com.demo.diplomaproject.domain.entity.UserProfile
import com.demo.diplomaproject.extensions.gone
import com.demo.diplomaproject.extensions.visible
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorPresenter
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorView
import kotlinx.android.synthetic.main.fragment_about_doctor.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class DoctorFragment : BaseFragment(), DoctorView {

    override val layoutRes = R.layout.fragment_about_doctor

    override fun installScopeModules(scope: Scope) {}

    override val parentFragmentScopeName = DI.SERVER_SCOPE

    @InjectPresenter
    lateinit var presenter: DoctorPresenter

    private lateinit var adapter: DoctorAdapter

    @ProvidePresenter
    fun providePresenter(): DoctorPresenter =
        scope.getInstance(DoctorPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doctorFindButton.setOnClickListener { presenter.onFindDoctorClicked() }
        doctorRecycler.layoutManager = LinearLayoutManager(requireContext())
        doctorToolbar.setNavigationOnClickListener { presenter.onBackPressed() }
    }

    override fun showDoctorList(doctors: List<UserProfile>) {
        doctorFindButton.gone()
        doctorNotFoundText.gone()
        doctorNotFoundImage.gone()
        doctorRecycler.visible()
        adapter = DoctorAdapter { presenter.onChooseDoctorClicked(it) }
        doctorRecycler.adapter = adapter
        adapter.showHistory(doctors)
        currentDoctorContainer.gone()
    }

    override fun showNoDoctorView() {
        doctorFindButton.visible()
        doctorNotFoundText.visible()
        doctorNotFoundImage.visible()
        doctorRecycler.gone()
        currentDoctorContainer.gone()
    }

    override fun showCurrentDoctor(doctor: UserProfile) {
        doctorFindButton.gone()
        doctorNotFoundText.gone()
        doctorNotFoundImage.gone()
        doctorRecycler.gone()
        currentDoctorContainer.visible()
        currentDoctorName.text = doctor.name
        currentDoctorSurname.text = doctor.surname
        currentDoctorUniversity.text = doctor.university
        currentDoctorExperience.text = doctor.experience
        currentDoctorClear.setOnClickListener { presenter.onClearDoctorClicked() }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}