package com.example.academy_proj2_githubapp.reactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.databinding.ReactionPickerDialogBinding
import com.example.academy_proj2_githubapp.reactions.models.ReactionType

class ReactionPickerDialog : DialogFragment() {

    companion object {
        fun newInstance(
            chosenReactions: List<ReactionType>,
            onReactionChosen: (ReactionType) -> Unit
        ): ReactionPickerDialog {
            return ReactionPickerDialog().apply {
                callback = onReactionChosen
                highlightedReactions = chosenReactions
            }
        }

        const val REACTION_PICKER_TAG = "REACTION_PICKER_TAG"
    }

    private var _binding: ReactionPickerDialogBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val reactionViews: HashMap<View, ReactionType> by lazy {
        hashMapOf(
            binding.ivPlusOne to ReactionType.PLUS_ONE,
            binding.ivMinusOne to ReactionType.MINUS_ONE,
            binding.ivEyes to ReactionType.EYES,
            binding.ivConfused to ReactionType.CONFUSED,
            binding.ivHeart to ReactionType.HEART,
            binding.ivHooray to ReactionType.HOORAY,
            binding.ivRocket to ReactionType.ROCKET,
            binding.ivLaugh to ReactionType.LAUGH,
        )
    }

    var callback: (ReactionType) -> Unit = {}
    var highlightedReactions: List<ReactionType> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReactionPickerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reactionViews.keys.forEach { reactionView ->
            if (reactionViews[reactionView] in highlightedReactions) {
                reactionView.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.active_reaction_bg)
            }
            reactionView.setOnClickListener {
                callback(reactionViews[it] ?: ReactionType.UNDEFINED)
                dialog?.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}