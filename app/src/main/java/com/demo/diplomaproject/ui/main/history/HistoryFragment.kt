package com.demo.diplomaproject.ui.main.history

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.domain.entity.TestResult
import com.demo.diplomaproject.extensions.gone
import com.demo.diplomaproject.extensions.visible
import com.demo.diplomaproject.presentation.main.history.HistoryPresenter
import com.demo.diplomaproject.presentation.main.history.HistoryView
import kotlinx.android.synthetic.main.fragment_history.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class HistoryFragment : BaseFragment(), HistoryView {

    override val layoutRes = R.layout.fragment_history

    override fun installScopeModules(scope: Scope) {}

    private lateinit var adapter: HistoryAdapter

    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    @ProvidePresenter
    fun providePresenter(): HistoryPresenter =
        scope.getInstance(HistoryPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }

    override fun showHistory(history: List<TestResult>) {
        historyRecycler.visible()
        noHistoryText.gone()
        historyNoItems.gone()
        adapter = HistoryAdapter()
        historyRecycler.adapter = adapter
        adapter.showHistory(history)
    }
}