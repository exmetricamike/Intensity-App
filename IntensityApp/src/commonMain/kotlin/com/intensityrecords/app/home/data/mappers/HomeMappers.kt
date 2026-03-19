package com.intensityrecords.app.home.data.mappers

import com.intensityrecords.app.home.data.dto.HomeDto
import com.intensityrecords.app.home.data.dto.UiBlockDto
import com.intensityrecords.app.home.domain.UiBlock
import com.intensityrecords.app.home.domain.UiConfig

fun HomeDto.toUiConfig(): UiConfig {
    return UiConfig(
        id = id,
        name = name,
        urlName = urlName,
        design = uiBlockDesign,
//        blocks = uiBlocks.map { it.toUiBlock() }
        blocks = uiBlocks
            .sortedBy { it.order }
            .map { it.toUiBlock() }
    )
}

fun UiBlockDto.toUiBlock(): UiBlock {
    return UiBlock(
        id = id,
        title = title,
        imageUrl = image,
        order = order,
        url = url
    )
}
