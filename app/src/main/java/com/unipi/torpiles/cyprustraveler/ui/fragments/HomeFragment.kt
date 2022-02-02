package com.unipi.torpiles.cyprustraveler.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.unipi.torpiles.cyprustraveler.adapters.DestinationListAdapter
import com.unipi.torpiles.cyprustraveler.adapters.TopDestinationListAdapter
import com.unipi.torpiles.cyprustraveler.database.FirestoreHelper
import com.unipi.torpiles.cyprustraveler.databinding.FragmentHomeBinding
import com.unipi.torpiles.cyprustraveler.models.Destination

class HomeFragment : BaseFragment() {

    // ~~~~~~~VARIABLES~~~~~~~
    private var _binding: FragmentHomeBinding? = null  // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private val binding get() = _binding!!
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun init() {
        loadDestinations()
        loadTopDestinations()

        setupClickListeners()
    }

    private fun loadDestinations() {
        FirestoreHelper().getDestinationsList(this)
    }

    fun successDestinationsListFromFireStore(destinationsList: ArrayList<Destination>) {

        if (destinationsList.size > 0) {

            // Show the recycler and remove the empty state layout completely.
            binding.apply {
                veilRecyclerViewDestinations.visibility = View.VISIBLE
            }

            // Sets VeilRecyclerView's properties
            binding.veilRecyclerViewDestinations.run {
                layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL ,false)
                adapter =
                    DestinationListAdapter(
                    requireContext(),
                    destinationsList
                )
                setHasFixedSize(true)
            }
        }
    }

    private fun loadTopDestinations() {
        FirestoreHelper().getTopDestinationsList(this@HomeFragment)
    }

    fun successTopDestinationsListFromFireStore(topDestinationsList: ArrayList<Destination>) {

        if (topDestinationsList.size > 0) {

            // Show the recycler and remove the empty state layout completely.
            binding.apply {
                veilRecyclerViewTopDestinations.visibility = View.VISIBLE
                layoutEmptyStateTopDestinations.root.visibility = View.GONE
            }

            // Sets VeilRecyclerView's properties
            binding.veilRecyclerViewTopDestinations.run {
                layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL ,false)
                adapter =
                    TopDestinationListAdapter(
                        requireContext(),
                        topDestinationsList
                    )
                setHasFixedSize(true)
            }
        } else {
            // Hide the recycler and show the empty state layout.
            binding.apply {
                veilRecyclerViewTopDestinations.visibility = View.INVISIBLE
                layoutEmptyStateTopDestinations.root.visibility = View.VISIBLE
            }
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            // txtViewTopDestinationsViewAll.setOnClickListener { IntentUtils().goToListProductsActivity(requireActivity(), "Deals") }
        }
    }

    override fun onResume() {
        super.onResume()

        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
