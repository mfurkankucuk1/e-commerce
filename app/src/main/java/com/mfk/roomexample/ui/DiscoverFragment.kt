package com.mfk.roomexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.databinding.FragmentDiscoverBinding
import com.mfk.roomexample.ui.adapter.CardStackAdapter
import com.mfk.roomexample.utils.CardStackCallback
import com.mfk.roomexample.viewModel.ProductViewModel
import com.yuyakaido.android.cardstackview.*


class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding: FragmentDiscoverBinding get() = _binding!!
    private var manager: CardStackLayoutManager? = null
    private val productViewModel: ProductViewModel by activityViewModels()
    private val cardStackAdapter: CardStackAdapter by lazy { CardStackAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupAdapter()
        setupStackView()
    }

    private fun setupStackView() {

        manager = CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction) {

                if (direction === Direction.Right) {
                    Toast.makeText(requireContext(), "Direction Right", Toast.LENGTH_SHORT).show()
                }
                if (direction === Direction.Top) {
                    Toast.makeText(requireContext(), "Direction Top", Toast.LENGTH_SHORT).show()
                }
                if (direction === Direction.Left) {
                    Toast.makeText(requireContext(), "Direction Left", Toast.LENGTH_SHORT).show()
                }
                if (direction === Direction.Bottom) {
                    Toast.makeText(requireContext(), "Direction Bottom", Toast.LENGTH_SHORT).show()
                }

                // Paginating
                manager?.let {
                    if (it.topPosition == cardStackAdapter.itemCount - 5) {
                        paginate()
                    }
                }

            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View, position: Int) {
                productViewModel.updateAddFavorite(productViewModel.getUnFavoriteProduct()[position].id!!,1)
            }

            override fun onCardDisappeared(view: View, position: Int) {
                productViewModel.updateAddFavorite(productViewModel.getUnFavoriteProduct()[position].id!!,0)
            }
        })
        manager?.let {
            it.setStackFrom(StackFrom.None)
            it.setVisibleCount(3)
            it.setTranslationInterval(8.0f)
            it.setScaleInterval(0.95f)
            it.setSwipeThreshold(0.3f)
            it.setMaxDegree(20.0f)
            it.setDirections(Direction.FREEDOM)
            it.setCanScrollHorizontal(true)
            it.setSwipeableMethod(SwipeableMethod.Manual)
            it.setOverlayInterpolator(LinearInterpolator())
        }
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = cardStackAdapter
        binding.cardStackView.itemAnimator = DefaultItemAnimator()
        val array = ArrayList<Product>()
        array.addAll(productViewModel.getUnFavoriteProduct())
        cardStackAdapter.list = array
    }

    private fun setupAdapter() {

    }

    private fun initialize() {

    }

    private fun paginate() {
        val oldList: List<Product> = cardStackAdapter.list
        val newList = productViewModel.getUnFavoriteProduct()
        val callback = CardStackCallback(oldList, newList)
        val hasil = DiffUtil.calculateDiff(callback)
        val array = ArrayList<Product>()
        array.addAll(newList)
        cardStackAdapter.list = array
        hasil.dispatchUpdatesTo(cardStackAdapter)
    }

}