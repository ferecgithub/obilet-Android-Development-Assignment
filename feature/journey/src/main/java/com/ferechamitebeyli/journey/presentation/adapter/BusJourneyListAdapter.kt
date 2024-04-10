package com.ferechamitebeyli.journey.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ferechamitebeyli.data.model.journey.JourneyDataUiModel
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.LayoutItemBusJourneyBinding
import com.ferechamitebeyli.ui.util.UiHelpers.formatDateToTime
import com.ferechamitebeyli.ui.util.UiHelpers.formatTime
import com.ferechamitebeyli.ui.util.UiHelpers.loadPartnerLogo
import com.ferechamitebeyli.ui.util.UiHelpers.startRotationAnimation
import com.ferechamitebeyli.ui.util.UiHelpers.visible

class BusJourneyListAdapter(private val context: Context) :
    RecyclerView.Adapter<BusJourneyListAdapter.JourneyViewHolder>() {
    inner class JourneyViewHolder(val binding: LayoutItemBusJourneyBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<JourneyDataUiModel>() {
        override fun areItemsTheSame(
            oldItem: JourneyDataUiModel,
            newItem: JourneyDataUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: JourneyDataUiModel,
            newItem: JourneyDataUiModel
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<JourneyDataUiModel>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder =
        JourneyViewHolder(
            LayoutItemBusJourneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: JourneyViewHolder, position: Int) {

        val journey = differ.currentList[position]

        holder.binding.apply {

            imageViewItemBusJourneyPartnerLogo.loadPartnerLogo(journey.partnerId)
            textViewItemBusJourneySeat.text = journey.busType

            journey.journey?.departure?.let {
                textViewItemBusJourneyDeparture.text = formatDateToTime(it)
            }

            journey.journey?.duration?.let { time ->
                textViewItemBusJourneyDuration.text = formatTime(time)
            }

            val currency =
                if (journey.journey?.currency == "TRY") "TL" else journey.journey?.currency

            val price = if (journey.journey?.internetPrice != null) {
                journey.journey?.internetPrice.toString()
            } else {
                context.getString(com.ferechamitebeyli.ui.R.string.label_notAvailable)
            }

            textViewItemBusJourneyPrice.text = context.getString(
                com.ferechamitebeyli.ui.R.string.label_priceWithCurrency,
                price,
                currency
            )

            val ifTheSeatCountIsNotZeroAndLessThan10 =
                (journey.availableSeats?.compareTo(0) == 0).not() && journey.availableSeats?.compareTo(
                    10
                ) == -1

            val availableSeats =
                if (ifTheSeatCountIsNotZeroAndLessThan10) journey.availableSeats.toString() else ""


            // If available seat count is less than 10, the view will be visible
            if (availableSeats.isNotBlank()) {
                textViewItemBusJourneyLastSeatsWarning.visible(true)
                textViewItemBusJourneyLastSeatsWarning.text = context.getString(
                    com.ferechamitebeyli.ui.R.string.label_lastSeatsWarning,
                    availableSeats
                )
            }


            textViewItemBusJourneyOriginToDestination.text = context.getString(
                com.ferechamitebeyli.ui.R.string.label_originToDestination,
                journey.originLocation,
                journey.destinationLocation
            )


            imageViewItemBusJourneyExpandArrow.setOnClickListener {
                journey.isExpanded = !journey.isExpanded
                it.setBackgroundResource(adjustArrowDirection(journey.isExpanded))
                notifyItemChanged(position)
            }

            constraintLayoutItemBusJourneyHidden.visible(journey.isExpanded)

            drawBusSeats(holder, context)
        }

    }

    override fun getItemCount(): Int = differ.currentList.size

    private fun adjustArrowDirection(isExpanded: Boolean): Int {
        return if (isExpanded) com.ferechamitebeyli.ui.R.drawable.ic_double_arrow_up else com.ferechamitebeyli.ui.R.drawable.ic_double_arrow_down
    }

    private fun drawBusSeats(holder: JourneyViewHolder, context: Context) {
        val totalSeats = 40
        val seatsPerRow = 2
        val totalRows = totalSeats / seatsPerRow

        val seatButtons = mutableListOf<Button>()

        val buttonContainer = holder.binding.layoutBusSeatsSelection.linearLayoutBusSeatsContainer

        for (row in 1..totalRows) {
            val rowContainer = LinearLayout(context)
            rowContainer.orientation = LinearLayout.HORIZONTAL

            for (seat in 1..seatsPerRow) {
                val seatNumber = (row - 1) * seatsPerRow + seat
                val seatButton = Button(context)
                seatButton.text = "$seatNumber"

                seatButton.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )

                rowContainer.addView(seatButton)
                seatButtons.add(seatButton)
            }

            buttonContainer.addView(rowContainer)
        }

        randomlyPaintSeatsForGenders(seatButtons)
    }
}

private fun randomlyPaintSeatsForGenders(seatList: List<Button>) {
    seatList.forEachIndexed { index, button ->
        val colorToPaint = if (index % 2 == 0) {
            Color.RED
        } else {
            Color.BLUE
        }

        button.setBackgroundColor(colorToPaint)
    }
}

