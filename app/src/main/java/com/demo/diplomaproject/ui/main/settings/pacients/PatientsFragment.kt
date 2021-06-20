package com.demo.diplomaproject.ui.main.settings.pacients

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
import com.demo.diplomaproject.presentation.main.settings.pacients.PatientsPresenter
import com.demo.diplomaproject.presentation.main.settings.pacients.PatientsView
import com.demo.diplomaproject.ui.main.settings.doctor.DoctorAdapter
import kotlinx.android.synthetic.main.fragment_about_doctor.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class PatientsFragment : BaseFragment(), PatientsView {

    override val layoutRes = R.layout.fragment_about_doctor

    override fun installScopeModules(scope: Scope) {}

    override val parentFragmentScopeName = DI.SERVER_SCOPE

    @InjectPresenter
    lateinit var presenter: PatientsPresenter

    private lateinit var adapter: DoctorAdapter

    @ProvidePresenter
    fun providePresenter(): PatientsPresenter =
        scope.getInstance(PatientsPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            //doctorFindButton.setOnClickListener { presenter.onFindDoctorClicked() }
        doctorRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}