package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
@Preview(showBackground = true)
fun LinearStepIndicatorPreview() {
    var currentStep by remember { mutableStateOf(0) }
    val maxStep = 2

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        LinearStepIndicator(stepSize = maxStep, currentStep = currentStep)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                if(currentStep > 0) currentStep--
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = "",
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(onClick = {
                if(currentStep < maxStep - 1) currentStep++
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_light),
                    contentDescription = "",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

@Composable
fun LinearStepIndicator(
    stepSize: Int,
    currentStep: Int
) {
    val percent = remember { Animatable(0f) }
    var prevStep by remember { mutableStateOf(currentStep) }

    LaunchedEffect(currentStep) {
        if(currentStep == prevStep) return@LaunchedEffect

        percent.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 250,
                easing = FastOutSlowInEasing
            )
        )

        prevStep = currentStep

        percent.snapTo(0f)
    }

    Row(
        modifier = Modifier.height(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
    ) {
        for (index in 0 until stepSize) {
            Row(
                modifier = Modifier
                    .height(4.dp)
                    .weight(1f)
                    .background(RemindMaterialTheme.colorScheme.bg_subtle),
                horizontalArrangement = when {
                    prevStep < currentStep && index == prevStep -> Arrangement.End
                    prevStep > currentStep && index == currentStep -> Arrangement.End
                    else -> Arrangement.Start
                }
            ) {
                Box(
                    modifier = Modifier
                        .background(RemindMaterialTheme.colorScheme.accent_default)
                        .fillMaxHeight()
                        .let {
                            when {
                                prevStep < currentStep && index == currentStep -> it.fillMaxWidth(percent.value)
                                prevStep > currentStep && index == currentStep -> it.fillMaxWidth(percent.value)
                                prevStep < currentStep && index == prevStep -> it.fillMaxWidth(1f - percent.value)
                                prevStep > currentStep && index == prevStep -> it.fillMaxWidth(1f - percent.value)
                                prevStep == currentStep && currentStep == index -> it.fillMaxWidth(1f)
                                else -> it
                            }
                        }
                ) {}
            }
        }
    }
}

@Composable
fun StepBar(
    maxStep: Int,
    currentStep: Int
) {
    Row(
        modifier = Modifier.height(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
    ) {
        for (i in 0 until maxStep) {
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .weight(1f)
                    .background(
                        if (i == currentStep) RemindMaterialTheme.colorScheme.accent_default
                        else RemindMaterialTheme.colorScheme.bg_subtle
                    )
            )
        }
    }
}