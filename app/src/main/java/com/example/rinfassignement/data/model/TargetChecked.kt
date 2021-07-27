package com.example.rinfassignement.data.model

import com.example.rinfassignement.data.db.model.Target

data class TargetChecked(
    val target: Target,
    var isChecked: Boolean = false
)